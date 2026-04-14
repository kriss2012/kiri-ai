package com.kiri.ai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
 * Bugatti v2 Professional Message Bubble
 * 
 * Aesthetic:
 * - Direct typography on Cinema Black
 * - Structured segments (CONTEXT // OUTPUT // NEXT)
 * - Code copy functionality enabled via headers
 */

@Composable
fun KiriMessageBubble(message: ChatMessage?) {
    if (message == null) return

    val role = message.role ?: "assistant"
    val content = message.content ?: ""
    val isUser = role == "user"
    val clipboardManager = LocalClipboardManager.current
    val colorScheme = MaterialTheme.colorScheme
    
    // Auto-structure logic: Look for explicit delimiters or professional markers
    val sections = if (!isUser) {
        if (content.contains("---")) {
            content.split("---")
        } else {
            // Support common AI sectioning patterns
            val patterns = listOf("QUESTION:", "OUTPUT:", "SUGGESTIONS:", "NEXT STEPS:", "NEXT:")
            var currentContent = content
            patterns.forEach { p ->
                currentContent = currentContent.replace(p, "\n---\n$p")
            }
            if (currentContent.contains("---")) currentContent.split("---") else listOf(content)
        }
    } else {
        listOf(content)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        // Technical Header Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            Text(
                text = if (isUser) "YOU" else "KIRI AI",
                style = KiriTypography.labelMedium.copy(
                    color = colorScheme.onBackground.copy(alpha = 0.6f),
                    letterSpacing = 1.sp
                )
            )
            if (!isUser) {
                Spacer(modifier = Modifier.width(8.dp))
                // Global Copy Button
                IconButton(
                    onClick = { clipboardManager.setText(AnnotatedString(content)) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.ContentCopy, 
                        contentDescription = "Copy All", 
                        tint = colorScheme.onBackground.copy(alpha = 0.4f),
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
        
        // Structured Content Area
        Box(
            modifier = Modifier
                .widthIn(max = 300.dp)
                .background(
                    color = if (isUser) colorScheme.primary else colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isUser) 16.dp else 4.dp,
                        bottomEnd = if (isUser) 4.dp else 16.dp
                    )
                )
                .padding(12.dp)
        ) {
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
                Column {
                    sections.forEachIndexed { index, section ->
                        val sectionLabel = when(index) {
                            0 -> if (sections.size > 1) "CONTEXT" else null
                            1 -> "OUTPUT"
                            2 -> "NEXT_STEPS"
                            else -> null
                        }

                        if (sectionLabel != null) {
                            Text(
                                text = "// $sectionLabel",
                                style = KiriTypography.labelMedium.copy(color = colorScheme.onSurfaceVariant.copy(alpha = 0.7f), fontSize = 10.sp),
                                modifier = Modifier.padding(top = if (index > 0) 12.dp else 0.dp, bottom = 4.dp)
                            )
                        }

                        // Bugatti Styled Markdown
                        Markdown(
                            content = section.trim(),
                            colors = markdownColor(
                                text = colorScheme.onSurfaceVariant,
                                codeText = colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                                linkText = BrandPink
                            ),
                            typography = markdownTypography(
                                h1 = KiriTypography.headlineLarge.copy(color = colorScheme.onSurfaceVariant),
                                h2 = KiriTypography.headlineMedium.copy(color = colorScheme.onSurfaceVariant),
                                paragraph = KiriTypography.bodyMedium.copy(color = colorScheme.onSurfaceVariant, lineHeight = 22.sp)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        if (index < sections.size - 1) {
                            HorizontalDivider(
                                color = colorScheme.onSurfaceVariant.copy(alpha = 0.1f),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
