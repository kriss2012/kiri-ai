package com.kiri.ai.ui.viewmodels

/**
 * ====================================================================================
 * STABILITY_ARCHITECTURE_NOTICE
 * ====================================================================================
 * 
 * ERROR_FIXED: SnapshotStateObserver Crash - 2026-04-15
 * 
 * ARCHITECTURAL_VIOLATION_CORRECTED:
 * ----------------------------------
 * Previously this ViewModel used 'mutableStateOf()' which is a COMPOSE-ONLY API.
 * mutableStateOf is designed for use INSIDE @Composable functions with 'remember'.
 * Using it in ViewModels causes snapshot transaction violations when:
 *   - State updates occur outside composition (file pickers, callbacks)
 *   - Multiple threads access state concurrently
 *   - Activity lifecycle transitions trigger state reads
 * 
 * CORRECT_PATTERN_IMPLEMENTED:
 * ----------------------------
 * Now using StateFlow which is the proper Kotlin Flow-based state holder for ViewModels:
 *   - MutableStateFlow for internal updates (thread-safe, atomic)
 *   - StateFlow for external observation (lifecycle-aware)
 *   - _uiState.update { } for immutable state mutations
 * 
 * Key difference: StateFlow uses Kotlin Coroutines for concurrency, while mutableStateOf
 * uses Compose's snapshot system which fails outside composition.
 * 
 * DO_NOT_CHANGE: Reverting to mutableStateOf will reintroduce the crash.
 * ====================================================================================
 */

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kiri.ai.data.models.*
import com.kiri.ai.data.repository.AuthRepository
import com.kiri.ai.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import android.net.Uri
import java.io.File
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map

@HiltViewModel
class ChatViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val chatRepository: ChatRepository,
    private val authRepository: AuthRepository
) : AndroidViewModel(application) {

    // SAVED_STATE_KEYS: Keys for process death restoration
    private companion object {
        const val KEY_INPUT = "chat_input_text"
        const val KEY_FILE_PATH = "chat_file_path"
        const val KEY_FILE_NAME = "chat_file_name"
    }

    private val _uiState = MutableStateFlow(ChatUiState(
        inputMessage = savedStateHandle.get<String>(KEY_INPUT) ?: "",
        selectedFileName = savedStateHandle.get<String>(KEY_FILE_NAME)
    ))
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        restoreAttachmentState()
        observeUserData()
        observeConnectivity()
        loadConversations()
    }

    private fun restoreAttachmentState() {
        val path = savedStateHandle.get<String>(KEY_FILE_PATH)
        if (path != null) {
            val file = File(path)
            if (file.exists()) {
                _uiState.update { it.copy(selectedFileUri = Uri.fromFile(file)) }
            } else {
                savedStateHandle.remove<String>(KEY_FILE_PATH)
            }
        }
    }

    private fun observeConnectivity() {
        chatRepository.isConnected
            .onEach { connected ->
                // DEFERRED_UPDATE: Use viewModelScope to move state update out of the observation collector
                // to prevent SnapshotStateObserver violations if this collector is triggered during composition.
                viewModelScope.launch {
                    val wasConnected = _uiState.value.isConnected
                    _uiState.update { it.copy(isConnected = connected) }
                    
                    if (connected && !wasConnected) {
                        val currentId = _uiState.value.currentConversationId
                        if (currentId != null) {
                            selectConversation(currentId)
                        } else {
                            loadConversations()
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        // STABILITY_CLEANUP: Wipe temporary uploads to prevent disk bloat
        viewModelScope.launch {
            try {
                getApplication<Application>().cacheDir.listFiles()?.forEach { file ->
                    if (file.name.startsWith("upload_")) {
                        file.delete()
                    }
                }
            } catch (e: Exception) { /* Silently fail during cleanup */ }
        }
    }


    private fun observeUserData() {
        authRepository.user
            .onEach { user ->
                _uiState.update { it.copy(user = user) }
            }
            .launchIn(viewModelScope)

        viewModelScope.launch {
            authRepository.getMe()
        }
    }

    fun loadConversations() {
        android.util.Log.d("Kiri_DEBUG", "ChatViewModel: Loading conversations...")
        viewModelScope.launch {
            chatRepository.getConversations().onSuccess { list ->
                android.util.Log.d("Kiri_DEBUG", "ChatViewModel: Successfully loaded ${list.size} conversations")
                // STABILITY_FIX: Guarantee absolute uniqueness for LazyColumn keys
                val seenIds = mutableSetOf<String>()
                val sanitized = list.mapIndexed { index, conv ->
                    var finalId = conv.id ?: "temp_${index}"
                    if (seenIds.contains(finalId)) {
                        finalId = "${finalId}_${java.util.UUID.randomUUID().toString().take(4)}"
                    }
                    seenIds.add(finalId)
                    conv.copy(id = finalId)
                }
                _uiState.update { it.copy(conversations = sanitized) }
            }.onFailure { error ->
                android.util.Log.e("Kiri_DEBUG", "ChatViewModel: loadConversations failed", error)
                _uiState.update { it.copy(error = "Failed to load chats: ${error.message}") }
            }
        }
    }

    fun selectConversation(id: String) {
        if (id.isBlank()) {
            android.util.Log.e("Kiri_DEBUG", "ChatViewModel: Aborting selection - target id is blank")
            return
        }
        android.util.Log.d("Kiri_DEBUG", "ChatViewModel: Selecting conversation id=$id")
        
        // PREVENT_REDUNDANT_RECOMPOSITION: If already loading or selected, skip
        if (_uiState.value.isLoadingMessages && _uiState.value.currentConversationId == id) {
            android.util.Log.d("Kiri_DEBUG", "ChatViewModel: Selection skipped - already loading same ID")
            return
        }
        
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoadingMessages = true, currentConversationId = id, error = null) }
                chatRepository.getConversationDetail(id).onSuccess { detail ->
                    android.util.Log.d("Kiri_DEBUG", "ChatViewModel: Detail fetched for id=$id, messages=${detail.messages?.size ?: 0}")
                    // Ensure each message has a unique stable ID for LazyColumn to prevent crashes
                    val seenIds = mutableSetOf<String>()
                    val sanitizedMessages = (detail.messages ?: emptyList()).mapIndexed { index, msg ->
                        // ID_STABILITY_ENFORCEMENT: Permanent unique ID generation to stop SnapshotStateObserver
                        val baseId = if (msg.id.isNullOrBlank()) "msg_${id}_${index}" else msg.id
                        var finalId = baseId
                        var counter = 1
                        while (seenIds.contains(finalId)) {
                            finalId = "${baseId}_${counter}"
                            counter++
                        }
                        seenIds.add(finalId)
                        
                        // SANITIZE_CONTENT: Permanent stability truncation at the source
                        val cleanContent = if ((msg.content?.length ?: 0) > 10000) {
                            (msg.content?.take(10000) ?: "") + "\n\n... [TRUNCATED_FOR_STABILITY]"
                        } else {
                            msg.content ?: ""
                        }
                        
                        msg.copy(id = finalId, content = cleanContent)
                    }

                    _uiState.update {
                        it.copy(
                            messages = sanitizedMessages,
                            isLoadingMessages = false,
                            currentTitle = detail.title ?: "Untitled Chat"
                        )
                    }
                }.onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoadingMessages = false,
                            error = "Could not load conversation: ${error.message}"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoadingMessages = false, error = "Error: ${e.message}") }
            }
        }
    }

    fun onMessageChange(msg: String) { 
        _uiState.update { it.copy(inputMessage = msg) } 
        savedStateHandle.set(KEY_INPUT, msg)
    }

    fun onFileSelected(uri: Uri?, name: String?) {
        android.util.Log.d("Kiri_DEBUG", "ChatViewModel: onFileSelected uri=$uri, name=$name")
        val secureUri = uri ?: run {
            android.util.Log.d("Kiri_DEBUG", "ChatViewModel: uri is null, clearing file")
            clearSelectedFile()
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMessages = true) } // Show small indicator during copy
            val cachedFile = copyUriToInternalStorage(secureUri, name)
            
            if (cachedFile != null) {
                android.util.Log.d("Kiri_DEBUG", "ChatViewModel: File secured at ${cachedFile.absolutePath}")
                val cachedUri = Uri.fromFile(cachedFile)
                _uiState.update { 
                    it.copy(
                        selectedFileUri = cachedUri, 
                        selectedFileName = name,
                        isLoadingMessages = false
                    ) 
                }
                savedStateHandle.set(KEY_FILE_PATH, cachedFile.absolutePath)
                savedStateHandle.set(KEY_FILE_NAME, name)
            } else {
                android.util.Log.e("Kiri_DEBUG", "ChatViewModel: Failed to secure file")
                _uiState.update { 
                    it.copy(
                        isLoadingMessages = false,
                        error = "FILE_ACCESS_ERROR: Could not secure attachment."
                    ) 
                }
            }
        }
    }

    private suspend fun copyUriToInternalStorage(uri: Uri, name: String?): File? {
        return try {
            kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                val contentResolver = getApplication<Application>().contentResolver
                val inputStream = contentResolver.openInputStream(uri) ?: return@withContext null
                
                // Create a permanent unique file in cache to survive permission revocation
                val extension = name?.substringAfterLast('.', "") ?: "tmp"
                val fileName = "upload_${System.currentTimeMillis()}.${extension}"
                val file = File(getApplication<Application>().cacheDir, fileName)
                
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
                file
            }
        } catch (e: Exception) {
            android.util.Log.e("ChatViewModel", "Copy failed: ${e.message}")
            null
        }
    }

    fun clearSelectedFile() {
        _uiState.update { it.copy(selectedFileUri = null, selectedFileName = null) }
        savedStateHandle.remove<String>(KEY_FILE_PATH)
        savedStateHandle.remove<String>(KEY_FILE_NAME)
    }

    fun sendMessage() {
        val currentState = _uiState.value
        val input = currentState.inputMessage.trim()
        val fileUri = currentState.selectedFileUri
        android.util.Log.d("Kiri_DEBUG", "ChatViewModel: sendMessage input_length=${input.length}, has_file=${fileUri != null}")
        
        if (input.isBlank() && fileUri == null || currentState.isSending) {
            android.util.Log.w("Kiri_DEBUG", "ChatViewModel: sendMessage blocked - empty input or already sending")
            return
        }
        
        val userMsgId = "user_${java.util.UUID.randomUUID()}"
        val userMsg = ChatMessage(
            role = "user", 
            content = input + (if (fileUri != null) "\n[IMAGE_URI: $fileUri]" else ""),
            id = userMsgId
        )
        
        _uiState.update {
            it.copy(
                messages = it.messages + userMsg,
                inputMessage = "",
                isSending = true,
                error = null
            )
        }

        viewModelScope.launch {
            try {
                val currentConvId = _uiState.value.currentConversationId
                android.util.Log.d("Kiri_DEBUG", "ChatViewModel: Launching send with convId=$currentConvId")
                
                val result = if (fileUri != null) {
                    uploadFileAndSend(input, fileUri)
                } else {
                    chatRepository.sendMessage(input, currentConvId)
                }

                result.onSuccess { res ->
                    if (res.success == true) {
                        // SANITIZE_AI_RESPONSE: Permanent stability truncation for incoming AI logs
                        val cleanAiMsg = if ((res.message?.length ?: 0) > 10000) {
                            res.message?.take(10000) + "\n\n... [TRUNCATED_FOR_STABILITY]"
                        } else {
                            res.message ?: ""
                        }

                        val assistantMsg = ChatMessage(
                            role = "assistant", 
                            content = cleanAiMsg,
                            id = "ai_${java.util.UUID.randomUUID()}"
                        )
                        
                        _uiState.update {
                            it.copy(
                                messages = it.messages + assistantMsg,
                                isSending = false,
                                selectedFileUri = null,
                                selectedFileName = null,
                                currentConversationId = res.conversationId ?: it.currentConversationId,
                                currentTitle = res.title ?: it.currentTitle
                            )
                        }
                        
                        // Clear saved state after successful send
                        savedStateHandle.remove<String>(KEY_INPUT)
                        savedStateHandle.remove<String>(KEY_FILE_PATH)
                        savedStateHandle.remove<String>(KEY_FILE_NAME)
                        
                        loadConversations()

                    } else {
                        _uiState.update {
                            it.copy(
                                isSending = false,
                                error = res.message ?: "Server returned error"
                            )
                        }
                    }
                }.onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isSending = false,
                            error = error.message ?: "Failed to send message"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSending = false,
                        error = "Unexpected error: ${e.message}"
                    )
                }
            }
        }
    }

    private suspend fun uploadFileAndSend(message: String, uri: Uri): Result<ChatResponse> {
        return try {
            val file = if (uri.scheme == "file") {
                File(uri.path ?: "")
            } else {
                // Fallback (should not happen with new copy logic)
                val contentResolver = getApplication<Application>().contentResolver
                val inputStream = contentResolver.openInputStream(uri)
                val tempFile = File(getApplication<Application>().cacheDir, "upload_fallback_${System.currentTimeMillis()}")
                tempFile.outputStream().use { inputStream?.copyTo(it) }
                tempFile
            }

            if (!file.exists()) return Result.failure(Exception("FILE_NOT_FOUND"))

            val requestFile = file.asRequestBody(getApplication<Application>().contentResolver.getType(uri)?.toMediaTypeOrNull() ?: "application/octet-stream".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", _uiState.value.selectedFileName ?: file.name, requestFile)
            
            // AI Analysis: If message is empty, provide a default analysis prompt
            val prompt = if (message.trim().isEmpty()) "Analyze this image and explain what is shown in it." else message
            
            val contentBody = prompt.toRequestBody("text/plain".toMediaTypeOrNull())
            val convIdBody = _uiState.value.currentConversationId?.toRequestBody("text/plain".toMediaTypeOrNull())

            chatRepository.sendMessageWithFile(contentBody, convIdBody, body)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun newChat() {
        android.util.Log.d("Kiri_DEBUG", "ChatViewModel: Initializing NEW_CHAT session")
        _uiState.update {
            it.copy(
                currentConversationId = null,
                messages = emptyList(),
                currentTitle = "Kiri AI",
                inputMessage = "",
                error = null
            )
        }
    }
}

data class ChatUiState(
    val user: User? = null,
    val conversations: List<Conversation> = emptyList(),
    val messages: List<ChatMessage> = emptyList(),
    val currentConversationId: String? = null,
    val currentTitle: String = "Kiri AI",
    val inputMessage: String = "",
    val selectedFileUri: Uri? = null,
    val selectedFileName: String? = null,
    val isLoadingMessages: Boolean = false,
    val isSending: Boolean = false,
    val isConnected: Boolean = true,
    val error: String? = null
)
