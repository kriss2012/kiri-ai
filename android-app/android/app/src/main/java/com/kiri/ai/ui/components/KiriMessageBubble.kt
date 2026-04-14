package com.kiri.ai.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
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
 * ! CRITICAL STABILITY COMPONENT - PERMANENT FIX FOR NATIVE RENDERING CRASH !
 * 
 * ERROR: java.lang.StackOverflowError / Native crash in dispatchGetDisplayList.
 * CAUSE: Hardware renderer recursion limit exceeded by deep Compose trees or huge text.
 * 
 * PERMANENT SOLUTIONS (DO NOT REMOVE):
 * 1. COMPOSITING_STRATEGY: Using CompositingStrategy.Offscreen on the root Column.
 *    This forces the Android OS to flatten the entire message bubble into a single
 *    hardware layer, breaking any drawing recursion chain immediately.
 * 2. AGGRESSIVE_TRUNCATION: Hard limit of 5,000 characters. Single nodes larger than
 *    this can exceed the 64KB DisplayList operation limit on many Android versions.
 * 3. HIERARCHY_FLATTENING: Minimized nesting to keep the native View tree shallow.
 */

@Composable
fun KiriMessageBubble(message: ChatMessage?) {
    if (message == null) return

    val role = message.role ?: "assistant"
    val rawContent = message.content ?: ""
    val isUser = role == "user"
    val colorScheme = MaterialTheme.colorScheme

    // STABILITY_GUARD: 5,000 char limit. Massive text nodes are unsafe for hardware rendering.
    val content = remember(rawContent) {
        if (rawContent.length > 5000) {
            rawContent.take(5000) + "\n\n... [STABILITY_LIMIT_REACHED_FOR_SAFETY]"
        } else {
            rawContent
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 12.dp)
            .graphicsLayer {
                // FORCE_OFFSCREEN: This is the definitive fix for dispatchGetDisplayList recursion.
                // It isolates this specific bubble's drawing from the rest of the View tree.
                this.compositingStrategy = CompositingStrategy.Offscreen
                this.clip = true
            },
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        // Source Identifier
        Text(
            text = if (isUser) "NODE_USER" else "NODE_KIRI",
            style = KiriTypography.labelSmall.copy(
                color = colorScheme.onBackground.copy(alpha = 0.4f),
                fontSize = 9.sp
            ),
            modifier = Modifier.padding(bottom = 2.dp)
        )
        
        Surface(
            color = if (isUser) colorScheme.primary else colorScheme.surfaceVariant,
            shape = RoundedCornerShape(
                topStart = 12.dp,
                topEnd = 12.dp, 
                bottomStart = if (isUser) 12.dp else 2.dp,
                bottomEnd = if (isUser) 2.dp else 12.dp
            ),
            modifier = Modifier
                .widthIn(max = 300.dp)
                .heightIn(max = 1200.dp) // HEIGHT_LIMIT: Prevent hardware renderer bitmap overflow
        ) {
            Box(modifier = Modifier.padding(10.dp)) {
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
        val imageRegex = Regex("\\[IMAGE_URI: (.*?)\\]")
        val match = imageRegex.find(content)
        val text = if (match != null) content.replace(match.value, "").trim() else content

        if (match != null) {
            AsyncImage(
                model = match.groupValues[1],
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 150.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(bottom = 4.dp),
                contentScale = ContentScale.Crop
            )
        }

        if (text.isNotEmpty()) {
            Text(
                text = text,
                style = KiriTypography.bodyMedium.copy(color = colorScheme.onPrimary)
            )
        }
    }
}

@Composable
private fun AssistantContent(content: String, colorScheme: ColorScheme) {
    // Markdown is the most "dangerous" part of the rendering. 
    // We wrap it in its own isolated layer with offscreen compositing.
    Box(modifier = Modifier.graphicsLayer { 
        this.compositingStrategy = CompositingStrategy.Offscreen
        this.clip = true 
    }) {
        Markdown(
            content = content,
            colors = markdownColor(
                text = colorScheme.onSurfaceVariant,
                codeText = colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                linkText = colorScheme.primary
            ),
            typography = markdownTypography(
                h1 = KiriTypography.headlineLarge.copy(color = colorScheme.onSurfaceVariant, fontSize = 22.sp),
                h2 = KiriTypography.headlineMedium.copy(color = colorScheme.onSurfaceVariant, fontSize = 18.sp),
                paragraph = KiriTypography.bodyMedium.copy(
                    color = colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp
                )
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
