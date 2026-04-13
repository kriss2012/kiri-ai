package com.kiri.ai.data.repository

import com.kiri.ai.data.local.AuthDataStore
import com.kiri.ai.data.models.AuthResponse
import com.kiri.ai.data.models.GenericResponse
import com.kiri.ai.data.remote.AuthApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val authDataStore: AuthDataStore
) {
    val token: Flow<String?> = authDataStore.token
    val user: Flow<com.kiri.ai.data.models.User?> = authDataStore.user

    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val response = authApi.login(mapOf("email" to email, "password" to password))
            if (response.isSuccessful && response.body() != null) {
                val authRes = response.body()!!
                authRes.token?.let { authDataStore.saveToken(it) }
                authRes.user?.let { authDataStore.saveUser(it) }
                Result.success(authRes)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(name: String, email: String, password: String): Result<AuthResponse> {
        return try {
            val response = authApi.register(mapOf("name" to name, "email" to email, "password" to password))
            if (response.isSuccessful && response.body() != null) {
                val authRes = response.body()!!
                authRes.token?.let { authDataStore.saveToken(it) }
                authRes.user?.let { authDataStore.saveUser(it) }
                Result.success(authRes)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMe(): Result<AuthResponse> {
        return try {
            val response = authApi.getMe()
            if (response.isSuccessful && response.body() != null) {
                val authRes = response.body()!!
                authRes.user?.let { authDataStore.saveUser(it) }
                Result.success(authRes)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateProfile(name: String): Result<AuthResponse> {
        return try {
            val response = authApi.updateProfile(mapOf("name" to name))
            if (response.isSuccessful && response.body() != null) {
                val authRes = response.body()!!
                authRes.user?.let { authDataStore.saveUser(it) }
                Result.success(authRes)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout() {
        authDataStore.clearToken()
    }
}
