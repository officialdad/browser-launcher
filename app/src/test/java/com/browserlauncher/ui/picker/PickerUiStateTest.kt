package com.browserlauncher.ui.picker

import android.graphics.drawable.Drawable
import com.browserlauncher.data.model.BrowserInfo
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PickerUiStateTest {

    private val mockIcon: Drawable = mockk(relaxed = true)

    @Test
    fun `Loading state is singleton`() {
        val state1 = PickerUiState.Loading
        val state2 = PickerUiState.Loading

        assertTrue(state1 === state2)
    }

    @Test
    fun `NoBrowsers state is singleton`() {
        val state1 = PickerUiState.NoBrowsers
        val state2 = PickerUiState.NoBrowsers

        assertTrue(state1 === state2)
    }

    @Test
    fun `Success state contains browsers and url`() {
        val browsers = listOf(
            BrowserInfo(
                packageName = "com.chrome",
                activityName = "com.chrome.Main",
                label = "Chrome",
                icon = mockIcon
            )
        )
        val url = "https://example.com"

        val state = PickerUiState.Success(browsers, url)

        assertEquals(browsers, state.browsers)
        assertEquals(url, state.url)
    }

    @Test
    fun `Error state contains message`() {
        val message = "Failed to load browsers"

        val state = PickerUiState.Error(message)

        assertEquals(message, state.message)
    }

    @Test
    fun `Success state with empty browsers list`() {
        val state = PickerUiState.Success(emptyList(), "https://test.com")

        assertTrue(state.browsers.isEmpty())
        assertEquals("https://test.com", state.url)
    }
}
