package com.kiri.ai.data.models

data class User(
    val id: String,
    val name: String,
    val email: String,
    val plan: String,
    val dailyRequests: Int,
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

data class Conversation(
    val id: String,
    val title: String,
    val model: String,
    val isPinned: Boolean,
    val messageCount: Int,
    val lastMessage: String,
    val updatedAt: String
)

data class ChatMessage(
    val role: String, // "user" or "assistant"
    val content: String
)

data class ChatDetail(
    val id: String,
    val title: String,
    val messages: List<ChatMessage>
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
    val requestsRemaining: String
)
