package com.kiri.ai.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.kiri.ai.data.models.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

@Singleton
class AuthDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val gson = Gson()
    private val TOKEN_KEY = stringPreferencesKey("jwt_token")
    private val USER_KEY = stringPreferencesKey("user_data")

    val token: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[TOKEN_KEY]
    }

    val user: Flow<User?> = context.dataStore.data.map { preferences ->
        preferences[USER_KEY]?.let {
            try {
                gson.fromJson(it, User::class.java)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun saveUser(user: User?) {
        context.dataStore.edit { preferences ->
            if (user != null) {
                preferences[USER_KEY] = gson.toJson(user)
            } else {
                preferences.remove(USER_KEY)
            }
        }
    }

    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
            preferences.remove(USER_KEY)
        }
    }
}
