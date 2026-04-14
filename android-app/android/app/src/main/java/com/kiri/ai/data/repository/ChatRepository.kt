package com.kiri.ai.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val chatApi: ChatApi,
    @ApplicationContext private val context: Context
) {
    private val _isConnected = MutableStateFlow(true)
    val isConnected: StateFlow<Boolean> = _isConnected

    init {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        
        connectivityManager.registerNetworkCallback(networkRequest, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) { _isConnected.value = true }
            override fun onLost(network: Network) { _isConnected.value = false }
        })
    }

    suspend fun sendMessage(message: String, conversationId: String? = null): Result<ChatResponse> {
        return try {
            val response = chatApi.sendMessage(ChatRequest(message, conversationId))
            handleResponse(response)
        } catch (e: java.net.UnknownHostException) {
            Result.failure(Exception("NO_CONNECTIVITY: Checking network status..."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendMessageWithFile(
        content: okhttp3.RequestBody,
        conversationId: okhttp3.RequestBody?,
        filePart: okhttp3.MultipartBody.Part
    ): Result<ChatResponse> {
        return try {
            val response = chatApi.sendMessageWithFile(content, conversationId, filePart)
            handleResponse(response)
        } catch (e: java.net.UnknownHostException) {
            Result.failure(Exception("NO_CONNECTIVITY: Upload suspended until network restored."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun handleResponse(response: retrofit2.Response<ChatResponse>): Result<ChatResponse> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Result.success(body)
            } else {
                Result.failure(Exception("EMPTY_RESPONSE: Server returned no data"))
            }
        } else {
            val errorMsg = try {
                val errorBody = response.errorBody()?.string()
                if (errorBody?.contains("message") == true) {
                    com.google.gson.Gson().fromJson(errorBody, GenericResponse::class.java).message
                } else {
                    "API_ERROR_${response.code()}"
                }
            } catch (e: Exception) {
                "API_ERROR_${response.code()}"
            }
            Result.failure(Exception(errorMsg ?: "UNKNOWN_ERROR"))
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
