package com.creadle.aplikasigithubuser.ui.mode

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UIModePreference(var context: Context) {


    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ui_mode_preference")

    val uiMode: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            val uiMode = preferences[UI_MODE_KEY] ?: false
            uiMode
        }

    suspend fun saveToDataStore(isNightMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[UI_MODE_KEY] = isNightMode
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: UIModePreference? = null

        private val UI_MODE_KEY = booleanPreferencesKey("ui_mode")
        fun getInstance(context: Context): UIModePreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UIModePreference(context)
                INSTANCE = instance
                instance
            }
        }
    }
}