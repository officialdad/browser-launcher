package com.browserlauncher.ui.picker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.browserlauncher.data.model.BrowserInfo
import com.browserlauncher.data.preferences.UserPreferences
import com.browserlauncher.data.repository.BrowserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

sealed class PickerUiState {
    object Loading : PickerUiState()
    data class Success(
        val browsers: List<BrowserInfo>,
        val url: String,
        val compactMode: Boolean = false
    ) : PickerUiState()
    data class Error(val message: String) : PickerUiState()
    object NoBrowsers : PickerUiState()
}

class BrowserPickerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = BrowserRepository(application)
    private val preferences = UserPreferences(application)

    private val _uiState = MutableStateFlow<PickerUiState>(PickerUiState.Loading)
    val uiState: StateFlow<PickerUiState> = _uiState.asStateFlow()

    fun loadBrowsers(url: String) {
        viewModelScope.launch {
            try {
                val browsers = repository.getInstalledBrowsers()
                if (browsers.isEmpty()) {
                    _uiState.value = PickerUiState.NoBrowsers
                } else {
                    val showLastUsedFirst = preferences.showLastUsedFirst.first()
                    val compactMode = preferences.compactMode.first()

                    val sortedBrowsers = if (showLastUsedFirst) {
                        val lastUsed = preferences.lastUsedBrowser.first()
                        browsers.sortedByDescending { it.packageName == lastUsed }
                    } else {
                        browsers.sortedBy { it.label }
                    }
                    _uiState.value = PickerUiState.Success(sortedBrowsers, url, compactMode)
                }
            } catch (e: Exception) {
                _uiState.value = PickerUiState.Error(e.message ?: "Failed to load browsers")
            }
        }
    }

    suspend fun onBrowserSelected(browser: BrowserInfo) {
        // Track last used browser for sorting
        preferences.setLastUsedBrowser(browser.packageName)
    }
}
