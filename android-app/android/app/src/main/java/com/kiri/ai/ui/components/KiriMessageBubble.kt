package com.kiri.ai.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.kiri.ai.data.models.ChatMessage
import com.kiri.ai.ui.theme.*
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
                color = if (isUser) ShowroomWhite.copy(alpha = 0.4f) else SilverMist.copy(alpha = 0.6f),
                letterSpacing = 2.sp
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Surface(
            color = if (isUser) VelvetBlack else DarkGray,
            shape = RoundedCornerShape(
                topStart = if (isUser) 12.dp else 2.dp,
                topEnd = if (isUser) 2.dp else 12.dp,
                bottomStart = 12.dp,
                bottomEnd = 12.dp
            ),
            border = if (isUser) BorderStroke(1.dp, SilverMist.copy(alpha = 0.2f)) else null,
            modifier = Modifier
                .widthIn(max = 320.dp)
                .graphicsLayer { 
                    clip = true 
                    // PROJECT_ZERO_G: Layer isolation prevents the Draw-pass from recursing 
                    // into complex Markdown blocks on low-performance devices.
                }
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                if (isUser) {
                    UserContent(content)
                } else {
                    AssistantContent(content)
                }
            }
        }
    }
}

@Composable
private fun UserContent(content: String) {
    val imageRegex = Regex("\\[(?:IMAGE_URI|IMAGE_ATTACHMENT): (.*?)\\]")
    val match = imageRegex.find(content)
    val textPart = if (match != null) content.replace(match.value, "").trim() else content

    Column {
        match?.let {
            AsyncImage(
                model = it.groupValues[1],
                contentDescription = "Attachment",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 240.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(bottom = 12.dp),
                contentScale = ContentScale.Crop
            )
        }
        if (textPart.isNotEmpty()) {
            Text(
                text = textPart,
                style = KiriTypography.bodyMedium.copy(color = ShowroomWhite)
            )
        }
    }
}

@Composable
private fun AssistantContent(content: String) {
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

    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                 text = "ANALYSIS_STREAM",
                 style = KiriTypography.labelMedium.copy(
                     color = SilverMist,
                     fontSize = 10.sp
                 )
            )
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

        Markdown(
            content = content,
            modifier = Modifier.fillMaxWidth(),
            typography = markdownTypography(
                h1 = KiriTypography.headlineLarge,
                h2 = KiriTypography.headlineMedium,
                paragraph = KiriTypography.bodyMedium.copy(
                    color = ShowroomWhite,
                    lineHeight = 26.sp
                ),
                code = KiriTypography.labelMedium.copy(
                    color = ShowroomWhite,
                    background = VelvetBlack
                )
            ),
            colors = markdownColor(
                text = ShowroomWhite,
                codeText = ShowroomWhite,
                inlineCodeText = ShowroomWhite,
                linkText = SilverMist
            )
        )
    }
}

