package com.kiriai.kiriorganization.ui.components

import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import coil.compose.AsyncImage
import com.kiriai.kiriorganization.data.models.ChatMessage
import com.kiriai.kiriorganization.ui.theme.*
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography

/**
 * KiriMessageBubble Component // PROJECT_ZERO_G REINFORCED
 * 
 * Implements the Bugatti Design System's monochromatic aesthetic.
 * 
 * STABILITY_CONTROLS:
 * 1. graphicsLayer isolation to prevent dispatchGetDisplayList recursion.
 * 2. Immutable monochromatic palette to reduce Draw-phase color resolution steps.
 * 3. Segmented intelligence interpretation.
 */
@Composable
fun KiriMessageBubble(message: ChatMessage?) {
    if (message == null) return
    val role = message.role ?: "assistant"
    val isUser = role == "user"
    val content = message.content ?: ""

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        // MONOGRAM_IDENTIFIER (Technical Header)
        Text(
            text = (if (isUser) "USER // ATELIER" else "KIRI // INTELLIGENCE").uppercase(),
            style = KiriTypography.labelMedium.copy(
                color = if (isUser) MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f) 
                        else MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                letterSpacing = 2.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Surface(
            color = if (isUser) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
            } else {
                if (androidx.compose.foundation.isSystemInDarkTheme()) GlassBlack.copy(alpha = 0.7f) 
                else Color.White.copy(alpha = 0.9f)
            },
            shape = RoundedCornerShape(
                topStart = if (isUser) 16.dp else 4.dp,
                topEnd = if (isUser) 4.dp else 16.dp,
                bottomStart = 16.dp,
                bottomEnd = 16.dp
            ),
            border = BorderStroke(
                width = 0.5.dp,
                color = if (isUser) {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                } else {
                    if (androidx.compose.foundation.isSystemInDarkTheme()) GlassBorderWhite.copy(alpha = 0.2f)
                    else GlassBorderBlack.copy(alpha = 0.1f)
                }
            ),
            modifier = Modifier
                .widthIn(max = 320.dp)
                .graphicsLayer {
                    shadowElevation = if (isUser) 0f else 4f
                    shape = RoundedCornerShape(
                        topStart = if (isUser) 16.dp else 4.dp,
                        topEnd = if (isUser) 4.dp else 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                    clip = true
                }
        ) {
            // DIRECT_CONTENT: Removed intermediate Box for flatter hierarchy
            if (isUser) {
                UserContent(content)
            } else {
                AssistantContent(message)
            }
        }
    }
}

@Composable
private fun UserContent(content: String) {
    val imageRegex = Regex("\\[(?:IMAGE_URI|IMAGE_ATTACHMENT): (.*?)\\]")
    val match = imageRegex.find(content)
    val textPart = if (match != null) content.replace(match.value, "").trim() else content

    Column(modifier = Modifier.padding(16.dp)) {
        match?.let {
            val uriString = it.groupValues[1]
            // We need to ensure the URI is something coil can handle.
            // If it's a content URI or file URI, it should work if permissions are right.
            AsyncImage(
                model = uriString,
                contentDescription = "Attachment",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 320.dp) // Increased height for better visibility
                    .clip(RoundedCornerShape(8.dp))
                    .padding(bottom = 12.dp)
                    .background(Color.Black.copy(alpha = 0.05f)),
                contentScale = ContentScale.Fit // Changed to Fit to see the whole image
            )
        }
        if (textPart.isNotEmpty()) {
            Text(
                text = textPart,
                style = KiriTypography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
private fun AssistantContent(message: ChatMessage) {
    val content = message.content ?: ""
    val clipboard = LocalClipboardManager.current
    
    // Segment logic for professional segmentation (Bugatti Intelligence Protocol)
    val segments = remember(content) {
        val list = mutableListOf<Pair<String, String>>()
        var current = content
        
        val types = listOf(
            "CONTEXT" to "TECHNICAL_CONTEXT",
            "OUTPUT" to "REASONING_OUTPUT",
            "NEXT_STEPS" to "ACTIONABLE_PROJECTION"
        )
        
        types.forEach { (marker, label) ->
            if (current.contains("${marker}:")) {
                val parts = current.split("${marker}:", limit = 2)
                if (parts[0].trim().isNotEmpty()) list.add("STREAM" to parts[0].trim())
                current = parts[1]
                list.add(label to "") // Marker
            }
        }
        if (current.trim().isNotEmpty()) list.add("DATA" to current.trim())
        list
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                     text = "ANALYSIS_STREAM",
                     style = KiriTypography.labelMedium.copy(
                         color = ShowroomWhite.copy(alpha = 0.7f),
                         fontSize = 11.sp,
                         fontWeight = FontWeight.SemiBold
                     )
                )
                val modelName = message.model
                if (modelName != null) {
                    val shortModelName = modelName.split("/").last().uppercase()
                    Surface(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(
                            text = shortModelName,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = KiriTypography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
            IconButton(
                onClick = { clipboard.setText(AnnotatedString(content)) },
                modifier = Modifier.size(20.dp)
            ) {
                Icon(
                    Icons.Default.ContentCopy, 
                    contentDescription = "Copy", 
                    tint = SilverMist, 
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        // STABILITY_OPTIMIZATION: markdownTypography and markdownColor are @Composable 
        // functions that already handle internal remembering. Call them directly.
        val markdownTypography = markdownTypography(
            h1 = KiriTypography.headlineLarge.copy(color = MaterialTheme.colorScheme.onSurface),
            h2 = KiriTypography.headlineMedium.copy(color = MaterialTheme.colorScheme.onSurface),
            paragraph = KiriTypography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 24.sp,
                letterSpacing = 0.25.sp,
                fontWeight = FontWeight.Medium
            ),
            code = KiriTypography.labelMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                background = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
            )
        )
        val markdownColors = markdownColor(
            text = MaterialTheme.colorScheme.onSurface,
            codeText = MaterialTheme.colorScheme.onSurface,
            inlineCodeText = MaterialTheme.colorScheme.onSurface,
            linkText = MaterialTheme.colorScheme.primary
        )

        val contentKey = remember(content) { content.hashCode() }
        key(contentKey) {
            Markdown(
                content = content,
                modifier = Modifier.fillMaxWidth(),
                typography = markdownTypography,
                colors = markdownColors
            )
        }
    }
}

