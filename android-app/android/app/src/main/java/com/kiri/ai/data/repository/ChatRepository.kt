package com.kiri.ai.data.repository

import com.kiri.ai.data.models.*
import com.kiri.ai.data.remote.ChatApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val chatApi: ChatApi
) {
    suspend fun sendMessage(message: String, conversationId: String? = null): Result<ChatResponse> {
        return try {
            val response = chatApi.sendMessage(ChatRequest(message, conversationId))
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                val errorMsg = try {
                    val errorBody = response.errorBody()?.string()
                    // Try to parse error message from JSON if possible
                    if (errorBody?.contains("message") == true) {
                         com.google.gson.Gson().fromJson(errorBody, GenericResponse::class.java).message
                    } else {
                        "Error ${response.code()}: ${response.message()}"
                    }
                } catch (e: Exception) {
                    "Error ${response.code()}: ${response.message()}"
                }
                Result.failure(Exception(errorMsg ?: "Unknown error"))
            }
        } catch (e: java.net.UnknownHostException) {
            Result.failure(Exception("No internet connection. Please check your network."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getConversations(): Result<List<Conversation>> {
        return try {
            val response = chatApi.getConversations()
            if (response.isSuccessful) {
                Result.success(response.body()?.conversations ?: emptyList())
            } else {
                Result.failure(Exception("Failed to load conversations (${response.code()})"))
            }
        } catch (e: java.net.UnknownHostException) {
            Result.failure(Exception("Offline: Conversations could not be loaded."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getConversationDetail(id: String): Result<ChatDetail> {
        return try {
            val response = chatApi.getConversation(id)
            if (response.isSuccessful) {
                val detail = response.body()?.conversation
                if (detail != null) {
                    Result.success(detail)
                } else {
                    Result.success(ChatDetail(id = id, title = "Untitled", messages = emptyList()))
                }
            } else {
                Result.failure(Exception("Conversation not found or server error (${response.code()})"))
            }
        } catch (e: java.net.UnknownHostException) {
            Result.failure(Exception("Offline: Message history unavailable."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteConversation(id: String): Result<GenericResponse> {
        return try {
            val response = chatApi.deleteConversation(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
