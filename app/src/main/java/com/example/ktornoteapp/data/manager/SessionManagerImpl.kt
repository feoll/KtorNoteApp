package com.example.ktornoteapp.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.ktornoteapp.data.common.SESSION_MANAGER
import com.example.ktornoteapp.data.common.SESSION_MANAGER_TOKEN
import kotlinx.coroutines.flow.first

class SessionManagerImpl(
    private val context: Context
) : SessionManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(SESSION_MANAGER)

    override suspend fun updateSession(token: String) {
        val tokenKey = stringPreferencesKey(SESSION_MANAGER_TOKEN)
        context.dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    override suspend fun getToken(): String? {
        val tokenKey = stringPreferencesKey(SESSION_MANAGER_TOKEN)
        val preferences = context.dataStore.data.first()
        return preferences[tokenKey]
    }

    override suspend fun logout() {
        context.dataStore.edit {
            it.clear()
        }
    }
}