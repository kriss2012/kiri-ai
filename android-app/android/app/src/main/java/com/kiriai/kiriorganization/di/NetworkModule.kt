package com.kiriai.kiriorganization.di

import com.kiriai.kiriorganization.data.local.AuthDataStore
import com.kiriai.kiriorganization.data.remote.AuthApi
import com.kiriai.kiriorganization.data.remote.ChatApi
import com.kiriai.kiriorganization.data.remote.SubscriptionApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://kiri-ai-backend.onrender.com/api/" // Corrected API path

    @Provides
    @Singleton
    fun provideAuthInterceptor(authDataStore: AuthDataStore): Interceptor {
        return Interceptor { chain ->
            val token = runBlocking {
                try {
                    authDataStore.token.first()
                } catch (e: Exception) {
                    null
                }
            }
            
            val requestBuilder = chain.request().newBuilder()
            if (!token.isNullOrBlank()) {
                requestBuilder.header("Authorization", "Bearer $token")
            }
            
            val response = chain.proceed(requestBuilder.build())
            
            // AUTOMATIC_LOGOUT_ON_401: If the server rejects the token, clear it locally
            // to prevent the "Invalid or expired token" loop.
            if (response.code == 401 && !token.isNullOrBlank()) {
                android.util.Log.e("Kiri_DEBUG", "NetworkModule: 401 Unauthorized detected. Clearing invalid token.")
                runBlocking {
                    authDataStore.clearToken()
                }
            }
            
            response
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: Interceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideSubscriptionApi(retrofit: Retrofit): SubscriptionApi = retrofit.create(SubscriptionApi::class.java)

    @Provides
    @Singleton
    fun provideChatApi(retrofit: Retrofit): ChatApi = retrofit.create(ChatApi::class.java)
}
