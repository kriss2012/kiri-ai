package com.kiri.ai.data.remote

import com.kiri.ai.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {
    @POST("api/auth/register")
    suspend fun register(@Body request: Map<String, String>): Response<AuthResponse>

    @POST("api/auth/login")
    suspend fun login(@Body request: Map<String, String>): Response<AuthResponse>

    @GET("api/auth/me")
    suspend fun getMe(): Response<AuthResponse>

    @PUT("api/auth/profile")
    suspend fun updateProfile(@Body request: Map<String, String>): Response<AuthResponse>

    @PUT("api/auth/change-password")
    suspend fun changePassword(@Body request: Map<String, String>): Response<GenericResponse>
}

interface ChatApi {
    @GET("api/chat/conversations")
    suspend fun getConversations(): Response<Map<String, Any>> // Using map for flexibility on dynamic list

    @GET("api/chat/conversations/{id}")
    suspend fun getConversation(@Path("id") id: String): Response<Map<String, Any>>

    @POST("api/chat/message")
    suspend fun sendMessage(@Body request: ChatRequest): Response<ChatResponse>

    @DELETE("api/chat/conversations/{id}")
    suspend fun deleteConversation(@Path("id") id: String): Response<GenericResponse>

    @DELETE("api/chat/conversations")
    suspend fun deleteAllConversations(): Response<GenericResponse>
}
