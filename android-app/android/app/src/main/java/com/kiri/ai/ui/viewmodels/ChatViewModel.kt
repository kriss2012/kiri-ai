package com.kiri.ai.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.viewModelScope
import com.kiri.ai.data.models.*
import com.kiri.ai.data.repository.AuthRepository
import com.kiri.ai.data.repository.ChatRepository
import com.kiri.ai.utils.NotificationHelper
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn
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

    var uiState by mutableStateOf(ChatUiState())
        private set

    init {
        observeUserData()
        loadConversations()
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)
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
                uiState = uiState.copy(user = user)
            }
            .launchIn(viewModelScope)

        viewModelScope.launch {
            authRepository.getMe()
        }
    }

    fun loadConversations() {
        viewModelScope.launch {
            chatRepository.getConversations().onSuccess { list ->
                // Sanitize IDs to prevent LazyColumn key collisions
                val seenIds = mutableSetOf<String>()
                val sanitized = list.mapIndexed { index, conv ->
                    val baseId = if (conv.id.isNullOrBlank()) "conv_${index}" else conv.id!!
                    var finalId = baseId
                    var counter = 1
                    while (seenIds.contains(finalId)) {
                        finalId = "${baseId}_${counter}"
                        counter++
                    }
                    seenIds.add(finalId)
                    conv.copy(id = finalId)
                }
                uiState = uiState.copy(conversations = sanitized)
            }.onFailure { error ->
                uiState = uiState.copy(error = "Failed to load chats: ${error.message}")
            }
        }
    }

    fun selectConversation(id: String) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoadingMessages = true, currentConversationId = id, error = null)
                chatRepository.getConversationDetail(id).onSuccess { detail ->
                    // Ensure each message has a unique stable ID for LazyColumn to prevent crashes
                    val seenIds = mutableSetOf<String>()
                    val sanitizedMessages = detail.messages?.mapIndexed { index, msg ->
                        // More robust ID generation: check if null, blank or duplicate
                        val baseId = if (msg.id.isNullOrBlank()) "msg_${id}_${index}" else msg.id!!
                        var finalId = baseId
                        var counter = 1
                        while (seenIds.contains(finalId)) {
                            finalId = "${baseId}_${counter}"
                            counter++
                        }
                        seenIds.add(finalId)
                        msg.copy(id = finalId)
                    } ?: emptyList()

                    uiState = uiState.copy(
                        messages = sanitizedMessages,
                        isLoadingMessages = false,
                        currentTitle = detail.title ?: "Untitled Chat"
                    )
                }.onFailure { error ->
                    uiState = uiState.copy(
                        isLoadingMessages = false,
                        error = "Could not load conversation: ${error.message}"
                    )
                }
            } catch (e: Exception) {
                uiState = uiState.copy(isLoadingMessages = false, error = "Error: ${e.message}")
            }
        }
    }

    fun onMessageChange(msg: String) { uiState = uiState.copy(inputMessage = msg) }

    fun onFileSelected(uri: Uri?, name: String?) {
        uiState = uiState.copy(selectedFileUri = uri, selectedFileName = name)
    }

    fun clearSelectedFile() {
        uiState = uiState.copy(selectedFileUri = null, selectedFileName = null)
    }

    fun sendMessage() {
        val input = uiState.inputMessage.trim()
        val fileUri = uiState.selectedFileUri
        if (input.isBlank() && fileUri == null || uiState.isSending) return
        
        val userMsgId = "user_${java.util.UUID.randomUUID()}"
        val userMsg = ChatMessage(
            role = "user", 
            content = input + (if (fileUri != null) "\n[IMAGE_URI: $fileUri]" else ""),
            id = userMsgId
        )
        
        uiState = uiState.copy(
            messages = uiState.messages + userMsg,
            inputMessage = "",
            isSending = true,
            error = null
        )

        viewModelScope.launch {
            try {
                val result = if (fileUri != null) {
                    uploadFileAndSend(input, fileUri)
                } else {
                    chatRepository.sendMessage(input, uiState.currentConversationId)
                }

                result.onSuccess { res ->
                    if (res?.success == true) {
                        val assistantMsg = ChatMessage(
                            role = "assistant", 
                            content = res.message ?: "",
                            id = "ai_${java.util.UUID.randomUUID()}"
                        )
                        
                        uiState = uiState.copy(
                            messages = uiState.messages + assistantMsg,
                            isSending = false,
                            selectedFileUri = null,
                            selectedFileName = null,
                            currentConversationId = res.conversationId ?: uiState.currentConversationId,
                            currentTitle = res.title ?: uiState.currentTitle
                        )
                        
                        loadConversations()

                    } else {
                        uiState = uiState.copy(
                            isSending = false,
                            error = res?.message ?: "Server returned error"
                        )
                    }
                }.onFailure { error ->
                    uiState = uiState.copy(
                        isSending = false,
                        error = error.message ?: "Failed to send message"
                    )
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isSending = false, 
                    error = "Unexpected error: ${e.message}"
                )
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
            val body = MultipartBody.Part.createFormData("file", uiState.selectedFileName ?: file.name, requestFile)
            
            // AI Analysis: If message is empty, provide a default analysis prompt
            val prompt = if (message.trim().isEmpty()) "Analyze this image and explain what is shown in it." else message
            
            val contentBody = prompt.toRequestBody("text/plain".toMediaTypeOrNull())
            val convIdBody = uiState.currentConversationId?.toRequestBody("text/plain".toMediaTypeOrNull())

            chatRepository.sendMessageWithFile(contentBody, convIdBody, body)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun newChat() {
        uiState = uiState.copy(
            currentConversationId = null,
            messages = emptyList(),
            currentTitle = "New Chat",
            inputMessage = ""
        )
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
    val error: String? = null
)
