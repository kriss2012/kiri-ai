package com.kiri.ai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiri.ai.data.models.ChatMessage
import com.kiri.ai.ui.theme.*
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.m3.MarkdownHighlightedCodeBlock
import com.mikepenz.markdown.m3.MarkdownHighlightedCodeFence
import com.mikepenz.markdown.m3.markdownComponents
import com.mikepenz.markdown.model.markdownTypography

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
            .padding(vertical = 16.dp),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        // Technical Header Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(
                text = if (isUser) "CLIENT // QUERY" else "KIRI // RESPONSE",
                style = KiriTypography.labelMedium.copy(
                    color = if (isUser) SilverMist else ShowroomWhite,
                    letterSpacing = 2.sp
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            // Global Copy Button
            IconButton(
                onClick = { clipboardManager.setText(AnnotatedString(content)) },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    Icons.Default.ContentCopy, 
                    contentDescription = "Copy All", 
                    tint = SilverMist.copy(alpha = 0.5f),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        
        // Structured Content Area
        Box(
            modifier = Modifier
                .widthIn(max = 340.dp)
                .background(if (isUser) DarkGray else Color.Transparent)
                .then(if (isUser) Modifier.padding(12.dp) else Modifier)
        ) {
            if (isUser) {
                Text(
                    text = content,
                    style = KiriTypography.bodyMedium.copy(color = ShowroomWhite)
                )
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
                                style = KiriTypography.labelMedium.copy(color = SilverMist, fontSize = 10.sp),
                                modifier = Modifier.padding(top = if (index > 0) 16.dp else 4.dp, bottom = 4.dp)
                            )
                        }

                        // Bugatti Styled Markdown with Code Headers
                        Markdown(
                            content = section.trim(),
                            typography = markdownTypography(
                                h1 = KiriTypography.headlineLarge.copy(color = ShowroomWhite),
                                h2 = KiriTypography.headlineMedium.copy(color = ShowroomWhite),
                                body = KiriTypography.bodyMedium.copy(color = ShowroomWhite, lineHeight = 26.sp),
                                code = KiriTypography.labelMedium.copy(color = SilverMist, background = DarkGray)
                            ),
                            components = markdownComponents(
                                codeBlock = {
                                    MarkdownHighlightedCodeBlock(
                                        content = it.content,
                                        node = it.node,
                                        showHeader = true
                                    )
                                },
                                codeFence = {
                                    MarkdownHighlightedCodeFence(
                                        content = it.content,
                                        node = it.node,
                                        showHeader = true
                                    )
                                }
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        if (index < sections.size - 1) {
                            HorizontalDivider(
                                color = SilverMist.copy(alpha = 0.1f),
                                modifier = Modifier.padding(top = 12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
