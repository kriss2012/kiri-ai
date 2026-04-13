package com.kiri.ai.data.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id", alternate = ["id"])
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val plan: String? = "free",
    val dailyRequests: Int? = 0,
    val totalRequests: Int? = 0,
    val isVerified: Boolean? = false,
    val avatar: String? = null,
    val isPremium: Boolean? = false,
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
    val success: Boolean? = null,
    val conversations: List<Conversation>? = emptyList()
)

data class Conversation(
    @SerializedName("_id", alternate = ["id"])
    val id: String? = null,
    val title: String? = "Untitled",
    val model: String? = null,
    val isPinned: Boolean? = false,
    val messageCount: Int? = 0,
    val lastMessage: String? = "",
    val updatedAt: String? = ""
)

data class ConversationDetailResponse(
    val success: Boolean? = null,
    val conversation: ChatDetail? = null
)

data class ChatMessage(
    val role: String = "user", // "user" or "assistant"
    val content: String = ""
)

data class ChatDetail(
    @SerializedName("_id", alternate = ["id"])
    val id: String? = null,
    val title: String? = "Untitled",
    val messages: List<ChatMessage>? = emptyList(),
    val model: String? = null,
    val updatedAt: String? = null
)

data class ChatRequest(
    val message: String,
    val conversationId: String? = null,
    val model: String = "google/gemini-2.0-flash-001"
)

data class ChatResponse(
    val success: Boolean? = null,
    val message: String? = null,
    val conversationId: String? = null,
    val title: String? = null,
    val requestsUsed: Int? = null,
    @SerializedName("requestsRemaining")
    private val _requestsRemaining: Any? = null
) {
    val requestsRemaining: String
        get() = _requestsRemaining?.toString() ?: "0"
}
