package com.example.tvtraker.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(context: Context) {

    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init {
        dataStore = applicationContext.createDataStore("tvTraker_data_store")
    }

    val loginKey: Flow<String?>
    get() = dataStore.data.map { preferences ->
        preferences[LOGIN_KEY]
    }

     suspend fun saveLoginKey(loginKey: String) {
        dataStore.edit { preferences ->
            preferences[LOGIN_KEY] = loginKey
        }
    }

    companion object {
        private val LOGIN_KEY = stringPreferencesKey("login_key")
    }


}