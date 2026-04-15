package com.kiri.ai.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kiri.ai.data.models.ChatMessage
import com.kiri.ai.ui.theme.*
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography

/**
 * PROJECT_ZERO_G // FINAL_STABILITY_LAYER
 * 
 * This component has been flattened to the absolute minimum required for rendering.
 * With Activity-wide hardware acceleration disabled, we avoid the 'dispatchGetDisplayList'
 * stack recursion entirely.
 * 
 * STABILITY_RULES:
 * 1. ZERO complex graphics layers (no offscreen compositing).
 * 2. Flat container hierarchy.
 * 3. String-level truncation to protect the software renderer.
 */

@Composable
fun KiriMessageBubble(message: ChatMessage?) {
    if (message == null) return

    val role = message.role ?: "assistant"
    val rawContent = message.content ?: ""
    val isUser = role == "user"
    val colorScheme = MaterialTheme.colorScheme

    // STABILITY_GUARD: Protect software renderer Memory
    val content = remember(rawContent) {
        if (rawContent.length > 8000) {
            rawContent.take(8000) + "\n\n... [LOG_LIMIT_EXCEEDED]"
        } else {
            rawContent
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 12.dp),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Surface(
            color = if (isUser) colorScheme.primary else colorScheme.surfaceVariant,
            shape = RoundedCornerShape(
                topStart = 12.dp,
                topEnd = 12.dp, 
                bottomStart = if (isUser) 12.dp else 2.dp,
                bottomEnd = if (isUser) 2.dp else 12.dp
            ),
            modifier = Modifier.widthIn(max = 310.dp)
        ) {
            Box(modifier = Modifier.padding(12.dp)) {
                if (isUser) {
                    UserContent(content, colorScheme)
                } else {
                    AssistantContent(content, colorScheme)
                }
            }
        }
    }
}

@Composable
private fun UserContent(content: String, colorScheme: ColorScheme) {
    Column {
        // Support both local [IMAGE_URI: ...] and server [IMAGE_ATTACHMENT: ...] formats
        val imageRegex = Regex("\\[(?:IMAGE_URI|IMAGE_ATTACHMENT): (.*?)\\]")
        val match = imageRegex.find(content)
        val textPart = if (match != null) content.replace(match.value, "").trim() else content

        if (match != null) {
            val imageUrl = match.groupValues[1]
            AsyncImage(
                model = imageUrl,
                contentDescription = "User uploaded image",
                placeholder = androidx.compose.ui.graphics.painter.ColorPainter(colorScheme.surfaceVariant),
                error = androidx.compose.ui.graphics.painter.ColorPainter(colorScheme.errorContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 240.dp) // Slightly increased for better visibility
                    .clip(RoundedCornerShape(8.dp))
                    .padding(bottom = 6.dp),
                contentScale = ContentScale.Fit // Fit is safer for mixed aspect ratios
            )
        }

        if (textPart.isNotEmpty()) {
            Text(
                text = textPart,
                style = KiriTypography.bodyMedium.copy(color = colorScheme.onPrimary)
            )
        }
    }
}

@Composable
private fun AssistantContent(content: String, colorScheme: ColorScheme) {
    Markdown(
        content = content,
        colors = markdownColor(
            text = colorScheme.onSurfaceVariant,
            codeText = colorScheme.onSurfaceVariant.copy(alpha = 0.9f),
            linkText = colorScheme.primary
        ),
        typography = markdownTypography(
            paragraph = KiriTypography.bodyMedium.copy(
                color = colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
        ),
        modifier = Modifier.fillMaxWidth()
    )
}
