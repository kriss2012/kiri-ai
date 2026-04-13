package com.kiri.ai.ui.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiri.ai.data.models.*
import com.kiri.ai.data.repository.AuthRepository
import com.kiri.ai.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
        loadUser()
        loadConversations()
    }

    private fun loadUser() {
        viewModelScope.launch {
            authRepository.getMe().onSuccess { res ->
                uiState = uiState.copy(user = res.user)
            }
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
            uiState = uiState.copy(isLoadingMessages = true, currentConversationId = id)
            chatRepository.getConversationDetail(id).onSuccess { detail ->
                uiState = uiState.copy(
                    messages = detail.messages,
                    isLoadingMessages = false,
                    currentTitle = detail.title
                )
            }
        }
    }

    fun onMessageChange(msg: String) { uiState = uiState.copy(inputMessage = msg) }

    fun sendMessage() {
        if (uiState.inputMessage.isBlank()) return
        
        val userMsg = ChatMessage("user", uiState.inputMessage)
        val currentInput = uiState.inputMessage
        uiState = uiState.copy(
            messages = uiState.messages + userMsg,
            inputMessage = "",
            isSending = true
        )

        viewModelScope.launch {
            chatRepository.sendMessage(currentInput, uiState.currentConversationId).onSuccess { res ->
                val assistantMsg = ChatMessage("assistant", res.message)
                uiState = uiState.copy(
                    messages = uiState.messages + assistantMsg,
                    isSending = false,
                    currentConversationId = res.conversationId,
                    currentTitle = res.title
                )
                loadConversations()
                loadUser() // Update usage count
            }.onFailure {
                uiState = uiState.copy(isSending = false)
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
