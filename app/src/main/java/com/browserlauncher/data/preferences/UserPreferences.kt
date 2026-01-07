package com.browserlauncher.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

/**
 * Manages user preferences using DataStore.
 */
class UserPreferences(private val context: Context) {

    private object Keys {
        val DEFAULT_BROWSER = stringPreferencesKey("default_browser")
        val ALWAYS_ASK = booleanPreferencesKey("always_ask")
        val LAST_USED_BROWSER = stringPreferencesKey("last_used_browser")
        val SHOW_LAST_USED_FIRST = booleanPreferencesKey("show_last_used_first")
        val COMPACT_MODE = booleanPreferencesKey("compact_mode")
    }

    /**
     * Flow of the default browser package name (null if not set).
     */
    val defaultBrowser: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[Keys.DEFAULT_BROWSER]
    }

    /**
     * Flow indicating whether to always show the picker.
     */
    val alwaysAsk: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.ALWAYS_ASK] ?: true
    }

    /**
     * Flow of the last used browser package name.
     */
    val lastUsedBrowser: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[Keys.LAST_USED_BROWSER]
    }

    /**
     * Flow indicating whether to show last used browser first (default false).
     */
    val showLastUsedFirst: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.SHOW_LAST_USED_FIRST] ?: false
    }

    /**
     * Flow indicating whether to use compact mode (default false).
     */
    val compactMode: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.COMPACT_MODE] ?: false
    }

    /**
     * Sets the default browser package.
     */
    suspend fun setDefaultBrowser(packageName: String?) {
        context.dataStore.edit { prefs ->
            if (packageName != null) {
                prefs[Keys.DEFAULT_BROWSER] = packageName
            } else {
                prefs.remove(Keys.DEFAULT_BROWSER)
            }
        }
    }

    /**
     * Sets whether to always show the picker.
     */
    suspend fun setAlwaysAsk(alwaysAsk: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.ALWAYS_ASK] = alwaysAsk
        }
    }

    /**
     * Records the last used browser.
     */
    suspend fun setLastUsedBrowser(packageName: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.LAST_USED_BROWSER] = packageName
        }
    }

    /**
     * Sets whether to show last used browser first.
     */
    suspend fun setShowLastUsedFirst(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.SHOW_LAST_USED_FIRST] = enabled
        }
    }

    /**
     * Sets whether to use compact mode.
     */
    suspend fun setCompactMode(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.COMPACT_MODE] = enabled
        }
    }

    /**
     * Clears all preferences.
     */
    suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
    }
}
