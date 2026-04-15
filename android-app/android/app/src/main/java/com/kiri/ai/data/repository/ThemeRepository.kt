package com.kiri.ai.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class ThemeRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val themeKey = booleanPreferencesKey("is_dark_mode")

    val isDarkMode: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[themeKey] ?: true // Default to dark mode
        }

    suspend fun setDarkMode(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[themeKey] = isDark
        }
    }
}
