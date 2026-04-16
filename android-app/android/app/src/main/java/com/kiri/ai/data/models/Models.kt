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
    @SerializedName("dailyLimit")
    private val _dailyLimit: Any? = null
) {
    val dailyLimit: String
        get() = _dailyLimit?.toString() ?: "50"
}

data class AuthResponse(
    val success: Boolean? = null,
    val message: String? = null,
    val token: String? = null,
    val user: User? = null
)

data class GenericResponse(
    val success: Boolean? = null,
    val message: String? = null
)

data class OrderResponse(
    val success: Boolean? = null,
    val orderId: String? = null,
    val amount: Int? = null,
    val currency: String? = null,
    val keyId: String? = null,
    val message: String? = null
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
) {
    // Helper to ensure we always have a non-null ID for LazyColumn keys
    fun getStableId(): String = id ?: "conv_${title?.hashCode() ?: 0}_${updatedAt?.hashCode() ?: 0}"
}

data class ConversationDetailResponse(
    val success: Boolean? = null,
    val conversation: ChatDetail? = null
)

data class ChatMessage(
    val role: String? = "user", // "user" or "assistant"
    val content: String? = "",
    @SerializedName("_id", alternate = ["id"])
    val id: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val localId: String = java.util.UUID.randomUUID().toString() // PERMANENT_STABILITY_ANCHOR
) {
    // Helper to ensure we always have a non-null ID for LazyColumn keys
    // We prioritize remote ID, then fixed localId. NEVER use content.hashCode() here.
    fun getStableId(): String = id ?: localId
}

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
