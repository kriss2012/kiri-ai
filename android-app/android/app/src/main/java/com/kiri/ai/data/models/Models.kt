package com.kiri.ai.data.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id", alternate = ["id"])
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val plan: String = "free",
    val dailyRequests: Int = 0,
    val totalRequests: Int = 0,
    val isVerified: Boolean = false,
    val avatar: String? = null,
    val isPremium: Boolean = false,
    val dailyLimit: String? = "50"
)

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val token: String? = null,
    val user: User? = null
)

data class GenericResponse(
    val success: Boolean,
    val message: String
)

data class ConversationsResponse(
    val success: Boolean,
    val conversations: List<Conversation>
)

data class Conversation(
    @SerializedName("_id", alternate = ["id"])
    val id: String = "",
    val title: String = "Untitled",
    val model: String = "",
    val isPinned: Boolean = false,
    val messageCount: Int = 0,
    val lastMessage: String = "",
    val updatedAt: String = ""
)

data class ConversationDetailResponse(
    val success: Boolean,
    val conversation: ChatDetail? = null
)

data class ChatMessage(
    val role: String = "user", // "user" or "assistant"
    val content: String = ""
)

data class ChatDetail(
    @SerializedName("_id", alternate = ["id"])
    val id: String = "",
    val title: String = "Untitled",
    val messages: List<ChatMessage> = emptyList(),
    val model: String? = null,
    val updatedAt: String? = null
)

data class ChatRequest(
    val message: String,
    val conversationId: String? = null,
    val model: String = "google/gemini-2.0-flash-001"
)

data class ChatResponse(
    val success: Boolean,
    val message: String,
    val conversationId: String,
    val title: String,
    val requestsUsed: Int,
    @SerializedName("requestsRemaining")
    private val _requestsRemaining: Any
) {
    val requestsRemaining: String
        get() = _requestsRemaining.toString()
}
