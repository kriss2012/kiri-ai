package com.kiri.ai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kiri.ai.data.models.ChatMessage
import com.kiri.ai.ui.theme.*
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography

/**
 * DANGER // CRITICAL_RENDERING_LAYER
 * This file handles the core message bubble rendering. The layout here is EXTREMELY
 * sensitive to nesting. Deep nesting or excessive text will trigger a native 
 * 'dispatchGetDisplayList' recursive crash on the Android hardware renderer.
 * 
 * CORE_RULES:
 * 1. Maintain a flat hierarchy (Surface > Box > Column/Text).
 * 2. Always use graphicsLayer(clip = true) on the Surface to break drawing recursion.
 * 3. Never allow unbounded AI output to render raw; use the safety truncation.
 */

@Composable
fun KiriMessageBubble(message: ChatMessage?) {
    if (message == null) return

    val role = message.role ?: "assistant"
    val content = message.content ?: ""
    val isUser = role == "user"
    val clipboardManager = LocalClipboardManager.current
    val colorScheme = MaterialTheme.colorScheme

    // SAFETY_TRUNCATION: Prevents the hardware renderer from blowing its stack on massive outputs
    val safeContent = remember(content) {
        if (content.length > 15000) {
            content.take(15000) + "\n\n... [TRUNCATED_FOR_STABILITY]"
        } else {
            content
        }
    }
    
    // REDUCED NESTING: Flat structure to prevent native rendering recursion
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        // Technical Header
        Text(
            text = if (isUser) "YOU" else "KIRI AI",
            style = KiriTypography.labelMedium.copy(
                color = colorScheme.onBackground.copy(alpha = 0.5f),
                letterSpacing = 1.sp
            ),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        
        // Content Area
        Surface(
            color = if (isUser) colorScheme.primary else colorScheme.surfaceVariant,
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp, 
                bottomStart = if (isUser) 16.dp else 4.dp,
                bottomEnd = if (isUser) 4.dp else 16.dp
            ),
            modifier = Modifier
                .widthIn(max = 300.dp)
                .graphicsLayer { 
                    // ENFORCE_DRAWING_BOUNDARY: Forces the renderer to treat this as a separate layer, 
                    // preventing recursive display list builds during hardware acceleration.
                    this.clip = true
                    this.shape = RoundedCornerShape(16.dp)
                }
        ) {
            Box(modifier = Modifier.padding(12.dp)) {
                if (isUser) {
                    Column {
                        // Check for image URI in content
                        val imageRegex = Regex("\\[IMAGE_URI: (.*?)\\]")
                        val match = imageRegex.find(content)
                        val displayText = if (match != null) content.replace(match.value, "").trim() else content

                        if (match != null) {
                            val uri = match.groupValues[1]
                            AsyncImage(
                                model = uri,
                                contentDescription = "Attached Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 200.dp)
                                    .padding(bottom = 8.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }

                        if (displayText.isNotEmpty()) {
                            Text(
                                text = displayText,
                                style = KiriTypography.bodyMedium.copy(color = colorScheme.onPrimary)
                            )
                        }
                    }
                } else {
                    // FLATTENED RENDERER: We minimize the number of Composable levels
                    val markdownText = if (safeContent.contains("---")) {
                        safeContent.replace("---", "\n\n***\n\n")
                    } else {
                        safeContent
                    }

                    Markdown(
                        content = markdownText,
                        colors = markdownColor(
                            text = colorScheme.onSurfaceVariant,
                            codeText = colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                            linkText = colorScheme.primary
                        ),
                        typography = markdownTypography(
                            h1 = KiriTypography.headlineLarge.copy(color = colorScheme.onSurfaceVariant, fontSize = 24.sp),
                            h2 = KiriTypography.headlineMedium.copy(color = colorScheme.onSurfaceVariant, fontSize = 20.sp),
                            paragraph = KiriTypography.bodyMedium.copy(color = colorScheme.onSurfaceVariant, lineHeight = 22.sp)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
