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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    application: Application,
    private val chatRepository: ChatRepository,
    private val authRepository: AuthRepository
) : AndroidViewModel(application) {

    var uiState by mutableStateOf(ChatUiState())
        private set

    private var isAppInBackground = false
    
    private val lifecycleObserver = LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_STOP -> isAppInBackground = true
            Lifecycle.Event.ON_START -> isAppInBackground = false
            else -> {}
        }
    }

    init {
        observeUserData()
        loadConversations()
        try {
            ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)
        } catch (e: Exception) {
            // Fallback if ProcessLifecycleOwner is not available in certain environments
        }
    }

    override fun onCleared() {
        super.onCleared()
        try {
            ProcessLifecycleOwner.get().lifecycle.removeObserver(lifecycleObserver)
        } catch (e: Exception) {}
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
                    val baseId = conv.id ?: "conv_${index}"
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
                    val sanitizedMessages = detail?.messages?.mapIndexed { index, msg ->
                        val baseId = msg.id ?: "msg_${id}_${index}"
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
                        currentTitle = detail?.title ?: "Untitled Chat"
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

    fun sendMessage() {
        val input = uiState.inputMessage.trim()
        if (input.isBlank() || uiState.isSending) return
        
        val userMsgId = "user_${UUID.randomUUID()}"
        val userMsg = ChatMessage(
            role = "user", 
            content = input,
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
                chatRepository.sendMessage(input, uiState.currentConversationId).onSuccess { res ->
                    if (res?.success == true) {
                        val assistantMsg = ChatMessage(
                            role = "assistant", 
                            content = res.message ?: "",
                            id = "ai_${UUID.randomUUID()}"
                        )
                        
                        uiState = uiState.copy(
                            messages = uiState.messages + assistantMsg,
                            isSending = false,
                            currentConversationId = res.conversationId ?: uiState.currentConversationId,
                            currentTitle = res.title ?: uiState.currentTitle
                        )
                        
                        if (isAppInBackground) {
                            NotificationHelper.showResponseNotification(
                                getApplication(),
                                "Kiri AI Response",
                                assistantMsg.content ?: "You have a new message"
                            )
                        }
                        
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
    val isLoadingMessages: Boolean = false,
    val isSending: Boolean = false,
    val error: String? = null
)
