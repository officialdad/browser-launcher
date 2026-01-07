package com.browserlauncher.data.repository

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import com.browserlauncher.data.model.BrowserInfo

/**
 * Repository for querying installed browser applications.
 */
class BrowserRepository(private val context: Context) {

    private val packageManager: PackageManager = context.packageManager

    /**
     * Returns a list of installed browsers that can handle http/https URLs.
     * Excludes this app from the results.
     */
    fun getInstalledBrowsers(): List<BrowserInfo> {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://example.com"))

        val resolveInfoList: List<ResolveInfo> = packageManager.queryIntentActivities(
            intent,
            PackageManager.MATCH_ALL
        )

        return resolveInfoList
            .filter { it.activityInfo.packageName != context.packageName }
            .mapNotNull { resolveInfo ->
                try {
                    BrowserInfo(
                        packageName = resolveInfo.activityInfo.packageName,
                        activityName = resolveInfo.activityInfo.name,
                        label = resolveInfo.loadLabel(packageManager).toString(),
                        icon = resolveInfo.loadIcon(packageManager)
                    )
                } catch (e: Exception) {
                    null
                }
            }
            .distinctBy { it.packageName }
            .sortedBy { it.label.lowercase() }
    }
}
