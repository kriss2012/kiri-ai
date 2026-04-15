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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.viewModelScope
import com.kiri.ai.data.models.*
import com.kiri.ai.data.repository.AuthRepository
import com.kiri.ai.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import android.net.Uri
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.kiri.ai.workers.ChatPollingWorker
import java.io.File
import java.util.concurrent.TimeUnit
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import java.util.UUID

@HiltViewModel
class ChatViewModel @Inject constructor(
    application: Application,
    private val chatRepository: ChatRepository,
    private val authRepository: AuthRepository
) : AndroidViewModel(application) {

    private val workManager = WorkManager.getInstance(application)
    
    private val lifecycleObserver = LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_STOP -> scheduleBackgroundPolling()
            Lifecycle.Event.ON_START -> cancelBackgroundPolling()
            else -> {}
        }
    }

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        observeUserData()
        observeConnectivity()
        loadConversations()
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)
    }

    private fun observeConnectivity() {
        chatRepository.isConnected
            .distinctUntilChanged()
            .onEach { connected ->
                _uiState.update { it.copy(isConnected = connected) }
                if (connected) {
                    // AUTO_RELOAD: On reconnection, refresh current context
                    val currentId = _uiState.value.currentConversationId
                    if (currentId != null) {
                        selectConversation(currentId)
                    } else {
                        loadConversations()
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        ProcessLifecycleOwner.get().lifecycle.removeObserver(lifecycleObserver)
        cancelBackgroundPolling()
    }

    private fun scheduleBackgroundPolling() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        
        val workRequest = PeriodicWorkRequestBuilder<ChatPollingWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        
        workManager.enqueueUniquePeriodicWork(
            "ChatPolling",
            androidx.work.ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    private fun cancelBackgroundPolling() {
        workManager.cancelUniqueWork("ChatPolling")
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
        viewModelScope.launch {
            chatRepository.getConversations().onSuccess { list ->
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
                _uiState.update { it.copy(error = "Failed to load chats: ${error.message}") }
            }
        }
    }

    fun selectConversation(id: String) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoadingMessages = true, currentConversationId = id, error = null) }
                chatRepository.getConversationDetail(id).onSuccess { detail ->
                    // Ensure each message has a unique stable ID for LazyColumn to prevent crashes
                    val seenIds = mutableSetOf<String>()
                    val sanitizedMessages = detail.messages?.mapIndexed { index, msg ->
                        val baseId = if (msg.id.isNullOrBlank()) "msg_${id}_${index}" else msg.id!!
                        var finalId = baseId
                        var counter = 1
                        while (seenIds.contains(finalId)) {
                            finalId = "${baseId}_${counter}"
                            counter++
                        }
                        seenIds.add(finalId)
                        
                        // SANITIZE_CONTENT: Permanent stability truncation at the source
                        val cleanContent = if ((msg.content?.length ?: 0) > 10000) {
                            msg.content?.take(10000) + "\n\n... [TRUNCATED_FOR_STABILITY]"
                        } else {
                            msg.content ?: ""
                        }
                        
                        msg.copy(id = finalId, content = cleanContent)
                    } ?: emptyList()

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

    fun onMessageChange(msg: String) { _uiState.update { it.copy(inputMessage = msg) } }

    fun onFileSelected(uri: Uri?, name: String?) {
        _uiState.update { it.copy(selectedFileUri = uri, selectedFileName = name) }
    }

    fun clearSelectedFile() {
        _uiState.update { it.copy(selectedFileUri = null, selectedFileName = null) }
    }

    fun sendMessage() {
        val currentState = _uiState.value
        val input = currentState.inputMessage.trim()
        val fileUri = currentState.selectedFileUri
        if (input.isBlank() && fileUri == null || currentState.isSending) return
        
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
                val result = if (fileUri != null) {
                    uploadFileAndSend(input, fileUri)
                } else {
                    chatRepository.sendMessage(input, currentConvId)
                }

                result.onSuccess { res ->
                    if (res?.success == true) {
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
                        
                        loadConversations()

                    } else {
                        _uiState.update {
                            it.copy(
                                isSending = false,
                                error = res?.message ?: "Server returned error"
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
            val contentResolver = getApplication<Application>().contentResolver
            val inputStream = contentResolver.openInputStream(uri)
            val file = File(getApplication<Application>().cacheDir, "upload_${System.currentTimeMillis()}")
            file.outputStream().use { inputStream?.copyTo(it) }

            val requestFile = file.asRequestBody(contentResolver.getType(uri)?.toMediaTypeOrNull())
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
        _uiState.update {
            it.copy(
                currentConversationId = null,
                messages = emptyList(),
                currentTitle = "New Chat",
                inputMessage = ""
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
