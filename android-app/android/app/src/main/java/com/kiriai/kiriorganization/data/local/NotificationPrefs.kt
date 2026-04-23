package com.kiriai.kiriorganization.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "notification_prefs")

@Singleton
class NotificationPrefs @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val LAST_NOTIFIED_ID = stringPreferencesKey("last_notified_id")

    val lastNotifiedId: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[LAST_NOTIFIED_ID]
        }

    suspend fun saveLastNotifiedId(id: String) {
        context.dataStore.edit { preferences ->
            preferences[LAST_NOTIFIED_ID] = id
        }
    }
}
