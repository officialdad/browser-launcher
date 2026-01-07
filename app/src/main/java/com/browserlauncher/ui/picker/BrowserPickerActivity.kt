package com.browserlauncher.ui.picker

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import com.browserlauncher.data.model.BrowserInfo
import com.browserlauncher.ui.theme.BrowserLauncherTheme
import kotlinx.coroutines.launch

class BrowserPickerActivity : ComponentActivity() {

    private val viewModel: BrowserPickerViewModel by viewModels()
    private var pendingUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Extract URL from intent
        val url = intent?.data?.toString()
        if (url == null) {
            finish()
            return
        }
        pendingUrl = url

        // Always show picker (no default browser bypass)
        viewModel.loadBrowsers(url)

        setContent {
            BrowserLauncherTheme {
                val uiState by viewModel.uiState.collectAsState()

                BrowserPickerSheet(
                    uiState = uiState,
                    onBrowserSelected = { browser ->
                        lifecycleScope.launch {
                            viewModel.onBrowserSelected(browser)
                            launchBrowser(browser, pendingUrl ?: return@launch)
                        }
                    },
                    onDismiss = { finish() }
                )
            }
        }
    }

    private fun launchBrowser(browser: BrowserInfo, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                )

                // Firefox variants require explicit ComponentName targeting their
                // IntentReceiverActivity for cold start launches to work properly.
                // Using setPackage() alone fails when Firefox is not in recent apps.
                // See: https://github.com/mozilla-mobile/fenix/issues/15513
                val firefoxComponent = getFirefoxIntentReceiver(browser.packageName)
                if (firefoxComponent != null) {
                    component = firefoxComponent
                } else {
                    setPackage(browser.packageName)
                }
            }
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "Could not open ${browser.label}",
                Toast.LENGTH_SHORT
            ).show()
        }
        finish()
    }

    private fun getFirefoxIntentReceiver(packageName: String): ComponentName? {
        // Firefox Fenix-based browsers (main Firefox app and variants)
        // These use org.mozilla.fenix.IntentReceiverActivity regardless of package name
        val fenixPackages = setOf(
            "org.mozilla.firefox",
            "org.mozilla.firefox_beta",
            "org.mozilla.fenix",
            "org.mozilla.fenix.nightly"
        )

        // Firefox Focus/Klar use a different activity class
        val focusPackages = setOf(
            "org.mozilla.focus",
            "org.mozilla.focus.beta",
            "org.mozilla.focus.nightly",
            "org.mozilla.klar"
        )

        return when (packageName) {
            in fenixPackages -> ComponentName(
                packageName,
                "org.mozilla.fenix.IntentReceiverActivity"
            )
            in focusPackages -> ComponentName(
                packageName,
                "org.mozilla.focus.activity.IntentReceiverActivity"
            )
            else -> null
        }
    }
}
