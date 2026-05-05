package com.kiriai.kiriorganization.data.models

import com.google.gson.annotations.SerializedName

/**
 * STABILITY_NOTICE: All fields MUST have @SerializedName for R8/ProGuard compatibility.
 * Use 'private val _field: Any?' pattern for types that might vary between environments.
 */

data class User(
    @SerializedName("_id", alternate = ["id"])
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("plan")
    val plan: String? = "free",
    @SerializedName("dailyRequests")
    private val _dailyRequests: Any? = 0,
    @SerializedName("totalRequests")
    private val _totalRequests: Any? = 0,
    @SerializedName("isVerified")
    private val _isVerified: Any? = false,
    @SerializedName("avatar")
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
    @SerializedName("success")
    val success: Boolean? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("user")
    val user: User? = null
)

data class GenericResponse(
    @SerializedName("success")
    val success: Boolean? = null,
    @SerializedName("message")
    val message: String? = null
)

data class OrderResponse(
    @SerializedName("success")
    val success: Boolean? = null,
    @SerializedName("orderId")
    val orderId: String? = null,
    @SerializedName("amount")
    private val _amount: Any? = null,
    @SerializedName("currency")
    val currency: String? = null,
    @SerializedName("keyId")
    val keyId: String? = null,
    @SerializedName("message")
    val message: String? = null
) {
    val amount: Int
        get() = _amount?.toString()?.toDoubleOrNull()?.toInt() ?: 0
}

data class ConversationsResponse(
    @SerializedName("success")
    val success: Boolean? = null,
    @SerializedName("conversations")
    val conversations: List<Conversation>? = emptyList()
)

data class Conversation(
    @SerializedName("_id", alternate = ["id"])
    val id: String? = null,
    @SerializedName("title")
    val title: String? = "Untitled",
    @SerializedName("model")
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
    @SerializedName("success")
    val success: Boolean? = null,
    @SerializedName("conversation")
    val conversation: ChatDetail? = null
)

data class ChatMessage(
    @SerializedName("role")
    val role: String? = "user",
    @SerializedName("content")
    val content: String? = "",
    @SerializedName("model")
    val model: String? = "auto",
    @SerializedName("_id", alternate = ["id"])
    val id: String? = null,
    @SerializedName("timestamp")
    private val _timestamp: Any? = null,
    val localId: String = java.util.UUID.randomUUID().toString()
) {
    val timestamp: String?
        get() = _timestamp?.toString()
    fun getStableId(): String = id ?: localId
}

data class ChatDetail(
    @SerializedName("_id", alternate = ["id"])
    val id: String? = null,
    @SerializedName("title")
    val title: String? = "Untitled",
    @SerializedName("messages")
    val messages: List<ChatMessage>? = emptyList(),
    @SerializedName("model")
    val model: String? = null,
    @SerializedName("updatedAt")
    private val _updatedAt: Any? = null
) {
    val updatedAt: String?
        get() = _updatedAt?.toString()
}

data class ChatRequest(
    @SerializedName("message")
    val message: String,
    @SerializedName("conversationId")
    val conversationId: String? = null,
    @SerializedName("model")
    val model: String = "auto"
)

data class ChatResponse(
    @SerializedName("success")
    val success: Boolean? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("conversationId")
    val conversationId: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("model")
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
