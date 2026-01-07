package com.browserlauncher.ui.settings

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.browserlauncher.data.preferences.UserPreferences
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val preferences = UserPreferences(context)
    val scope = rememberCoroutineScope()

    val showLastUsedFirst by preferences.showLastUsedFirst.collectAsState(initial = false)
    val compactMode by preferences.compactMode.collectAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Settings section
            Text(
                text = "Display",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            ListItem(
                headlineContent = { Text("Show last used browser first") },
                supportingContent = { Text("Move your most recently used browser to the top of the list") },
                trailingContent = {
                    Switch(
                        checked = showLastUsedFirst,
                        onCheckedChange = { enabled ->
                            scope.launch { preferences.setShowLastUsedFirst(enabled) }
                        }
                    )
                }
            )

            ListItem(
                headlineContent = { Text("Compact mode") },
                supportingContent = { Text("Show browser icons only, without names") },
                trailingContent = {
                    Switch(
                        checked = compactMode,
                        onCheckedChange = { enabled ->
                            scope.launch { preferences.setCompactMode(enabled) }
                        }
                    )
                }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // About section
            Text(
                text = "About",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            ListItem(
                headlineContent = { Text("Browser Launcher") },
                supportingContent = { Text("Version 1.0.0") }
            )

            ListItem(
                headlineContent = { Text("How it works") },
                supportingContent = {
                    Text("Set this app as your default browser. When you tap a link, Browser Launcher will let you choose which browser to use.")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
