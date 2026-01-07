package com.browserlauncher.data.model

import android.content.ComponentName
import android.graphics.drawable.Drawable

/**
 * Represents an installed browser application.
 */
data class BrowserInfo(
    val packageName: String,
    val activityName: String,
    val label: String,
    val icon: Drawable
) {
    /**
     * Returns the ComponentName for launching this browser.
     */
    val componentName: ComponentName
        get() = ComponentName(packageName, activityName)
}
