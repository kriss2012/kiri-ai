package com.kiri.ai.ui.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiri.ai.data.models.*
import com.kiri.ai.data.repository.AuthRepository
import com.kiri.ai.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    var uiState by mutableStateOf(ChatUiState())
        private set

    init {
        observeUserData()
        loadConversations()
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
                uiState = uiState.copy(conversations = list)
            }
        }
    }

    fun selectConversation(id: String) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoadingMessages = true, currentConversationId = id, error = null)
                chatRepository.getConversationDetail(id).onSuccess { detail ->
                    uiState = uiState.copy(
                        messages = detail.messages ?: emptyList(),
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
                uiState = uiState.copy(isLoadingMessages = false, error = e.message)
            }
        }
    }

    fun onMessageChange(msg: String) { uiState = uiState.copy(inputMessage = msg) }

    fun sendMessage() {
        if (uiState.inputMessage.isBlank() || uiState.isSending) return
        
        val userMsg = ChatMessage("user", uiState.inputMessage)
        val currentInput = uiState.inputMessage
        
        uiState = uiState.copy(
            messages = uiState.messages + userMsg,
            inputMessage = "",
            isSending = true,
            error = null
        )

        viewModelScope.launch {
            try {
                chatRepository.sendMessage(currentInput, uiState.currentConversationId).onSuccess { res ->
                    val assistantMsg = ChatMessage("assistant", res.message ?: "")
                    uiState = uiState.copy(
                        messages = uiState.messages + assistantMsg,
                        isSending = false,
                        currentConversationId = res.conversationId,
                        currentTitle = res.title ?: uiState.currentTitle
                    )
                    loadConversations()
                }.onFailure { error ->
                    uiState = uiState.copy(
                        isSending = false,
                        error = error.message ?: "Failed to send message"
                    )
                }
            } catch (e: Exception) {
                uiState = uiState.copy(isSending = false, error = e.message)
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
