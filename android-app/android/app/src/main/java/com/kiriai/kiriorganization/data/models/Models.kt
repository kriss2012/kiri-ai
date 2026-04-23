package com.kiriai.kiriorganization.data.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id", alternate = ["id"])
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val plan: String? = "free",
    @SerializedName("dailyRequests")
    private val _dailyRequests: Any? = 0,
    @SerializedName("totalRequests")
    private val _totalRequests: Any? = 0,
    @SerializedName("isVerified")
    private val _isVerified: Any? = false,
    val avatar: String? = null,
    @SerializedName("isPremium")
    private val _isPremium: Any? = false,
    @SerializedName("dailyLimit")
    private val _dailyLimit: Any? = null
) {
    val dailyRequests: Int
        get() = _dailyRequests?.toString()?.toDoubleOrNull()?.toInt() ?: 0
    val totalRequests: Int
        get() = _totalRequests?.toString()?.toDoubleOrNull()?.toInt() ?: 0
    val isVerified: Boolean
        get() = _isVerified?.toString()?.let { it.equals("true", true) || it == "1" } ?: false
    val isPremium: Boolean
        get() = _isPremium?.toString()?.let { it.equals("true", true) || it == "1" } ?: false
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
    @SerializedName("amount")
    private val _amount: Any? = null,
    val currency: String? = null,
    val keyId: String? = null,
    val message: String? = null
) {
    val amount: Int
        get() = _amount?.toString()?.toDoubleOrNull()?.toInt() ?: 0
}

data class ConversationsResponse(
    val success: Boolean? = null,
    val conversations: List<Conversation>? = emptyList()
)

data class Conversation(
    @SerializedName("_id", alternate = ["id"])
    val id: String? = null,
    val title: String? = "Untitled",
    val model: String? = null,
    @SerializedName("isPinned")
    private val _isPinned: Any? = false,
    @SerializedName("messageCount")
    private val _messageCount: Any? = 0,
    @SerializedName("lastMessage")
    private val _lastMessage: Any? = "",
    @SerializedName("updatedAt")
    private val _updatedAt: Any? = ""
) {
    val isPinned: Boolean
        get() = _isPinned?.toString()?.let { it.equals("true", true) || it == "1" } ?: false
    val messageCount: Int
        get() = _messageCount?.toString()?.toDoubleOrNull()?.toInt() ?: 0

    val lastMessage: String
        get() = _lastMessage?.toString() ?: ""

    val updatedAt: String
        get() = _updatedAt?.toString() ?: ""

    fun getStableId(): String = id ?: "conv_${title?.hashCode() ?: 0}_${updatedAt.hashCode()}"
}

data class ConversationDetailResponse(
    val success: Boolean? = null,
    val conversation: ChatDetail? = null
)

data class ChatMessage(
    val role: String? = "user", // "user" or "assistant"
    val content: String? = "",
    val model: String? = "auto",
    @SerializedName("_id", alternate = ["id"])
    val id: String? = null,
    @SerializedName("timestamp")
    private val _timestamp: Any? = null,
    val localId: String = java.util.UUID.randomUUID().toString() // PERMANENT_STABILITY_ANCHOR
) {
    val timestamp: String?
        get() = _timestamp?.toString()

    fun getStableId(): String = id ?: localId
}

data class ChatDetail(
    @SerializedName("_id", alternate = ["id"])
    val id: String? = null,
    val title: String? = "Untitled",
    val messages: List<ChatMessage>? = emptyList(),
    val model: String? = null,
    @SerializedName("updatedAt")
    private val _updatedAt: Any? = null
) {
    val updatedAt: String?
        get() = _updatedAt?.toString()
}

data class ChatRequest(
    val message: String,
    val conversationId: String? = null,
    val model: String = "auto"
)

data class ChatResponse(
    val success: Boolean? = null,
    val message: String? = null,
    val conversationId: String? = null,
    val title: String? = null,
    val model: String? = null,
    @SerializedName("requestsUsed")
    private val _requestsUsed: Any? = null,
    @SerializedName("requestsRemaining")
    private val _requestsRemaining: Any? = null
) {
    val requestsUsed: Int
        get() = _requestsUsed?.toString()?.toDoubleOrNull()?.toInt() ?: 0
    val requestsRemaining: String
        get() = _requestsRemaining?.toString() ?: "0"
}
