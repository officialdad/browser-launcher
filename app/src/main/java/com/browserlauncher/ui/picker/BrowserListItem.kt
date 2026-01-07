package com.browserlauncher.ui.picker

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.browserlauncher.data.model.BrowserInfo

@Composable
fun BrowserListItem(
    browser: BrowserInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bitmap = remember(browser.icon) {
        browser.icon.toBitmap()
    }

    ListItem(
        headlineContent = {
            Text(
                text = browser.label,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        leadingContent = {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = browser.label,
                modifier = Modifier.size(40.dp)
            )
        },
        modifier = modifier.clickable(onClick = onClick)
    )
}

private fun Drawable.toBitmap(): Bitmap {
    if (this is BitmapDrawable && bitmap != null) {
        return bitmap
    }

    val width = if (intrinsicWidth > 0) intrinsicWidth else 96
    val height = if (intrinsicHeight > 0) intrinsicHeight else 96

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)
    return bitmap
}
