package com.browserlauncher.data.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import io.mockk.mockk
import android.graphics.drawable.Drawable

class BrowserInfoTest {

    private val mockIcon: Drawable = mockk(relaxed = true)

    @Test
    fun `browserInfo stores packageName correctly`() {
        val browserInfo = BrowserInfo(
            packageName = "com.android.chrome",
            activityName = "com.google.android.apps.chrome.Main",
            label = "Chrome",
            icon = mockIcon
        )

        assertEquals("com.android.chrome", browserInfo.packageName)
    }

    @Test
    fun `browserInfo stores activityName correctly`() {
        val browserInfo = BrowserInfo(
            packageName = "com.android.chrome",
            activityName = "com.google.android.apps.chrome.Main",
            label = "Chrome",
            icon = mockIcon
        )

        assertEquals("com.google.android.apps.chrome.Main", browserInfo.activityName)
    }

    @Test
    fun `browserInfo stores label correctly`() {
        val browserInfo = BrowserInfo(
            packageName = "org.mozilla.firefox",
            activityName = "org.mozilla.firefox.App",
            label = "Firefox",
            icon = mockIcon
        )

        assertEquals("Firefox", browserInfo.label)
    }

    @Test
    fun `browserInfo with same values are equal`() {
        val browser1 = BrowserInfo(
            packageName = "com.browser",
            activityName = "com.browser.Main",
            label = "Browser",
            icon = mockIcon
        )
        val browser2 = BrowserInfo(
            packageName = "com.browser",
            activityName = "com.browser.Main",
            label = "Browser",
            icon = mockIcon
        )

        assertEquals(browser1, browser2)
    }

    @Test
    fun `browserInfo with different packageName are not equal`() {
        val browser1 = BrowserInfo(
            packageName = "com.browser1",
            activityName = "com.browser.Main",
            label = "Browser",
            icon = mockIcon
        )
        val browser2 = BrowserInfo(
            packageName = "com.browser2",
            activityName = "com.browser.Main",
            label = "Browser",
            icon = mockIcon
        )

        assertNotEquals(browser1, browser2)
    }

    @Test
    fun `browserInfo hashCode is consistent`() {
        val browser1 = BrowserInfo(
            packageName = "com.browser",
            activityName = "com.browser.Main",
            label = "Browser",
            icon = mockIcon
        )
        val browser2 = BrowserInfo(
            packageName = "com.browser",
            activityName = "com.browser.Main",
            label = "Browser",
            icon = mockIcon
        )

        assertEquals(browser1.hashCode(), browser2.hashCode())
    }
}
