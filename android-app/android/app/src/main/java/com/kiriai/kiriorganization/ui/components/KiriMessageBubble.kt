package com.kiriai.kiriorganization.ui.components

import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
 * Neo-Brutalist Message Bubble with high contrast, offset shadows, and 3dp borders.
 */
@Composable
fun KiriMessageBubble(message: ChatMessage?) {
    if (message == null) return
    val role = message.role ?: "assistant"
    val isUser = role == "user"
    val content = message.content ?: ""

    val bubbleBg = if (isUser) BrutalistYellow else BrutalistWhite
    val textColor = BrutalistBlack
    val shadowColor = BrutalistBlack

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        // MONOGRAM_IDENTIFIER
        Text(
            text = (if (isUser) "USER // ATELIER" else "KIRI // INTELLIGENCE").uppercase(),
            style = KiriTypography.labelMedium.copy(
                color = BrutalistBlack,
                letterSpacing = 2.sp,
                fontWeight = FontWeight.Black
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(
            modifier = Modifier.widthIn(max = 320.dp)
        ) {
            // Offset Shadow
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .offset(x = 4.dp, y = 4.dp)
                    .background(color = shadowColor, shape = RoundedCornerShape(8.dp))
                    .border(width = 3.dp, color = BrutalistBlack, shape = RoundedCornerShape(8.dp))
            )

            // Foreground container
            Box(
                modifier = Modifier
                    .widthIn(max = 320.dp)
                    .background(bubbleBg, RoundedCornerShape(8.dp))
                    .border(width = 3.dp, color = BrutalistBlack, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                if (isUser) {
                    UserContent(content)
                } else {
                    AssistantContent(message)
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
            val uriString = it.groupValues[1]
            AsyncImage(
                model = uriString,
                contentDescription = "Attachment",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 320.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .border(2.dp, BrutalistBlack, RoundedCornerShape(4.dp))
                    .padding(bottom = 8.dp)
                    .background(BrutalistLightGray),
                contentScale = ContentScale.Fit
            )
        }
        if (textPart.isNotEmpty()) {
            Text(
                text = textPart,
                style = KiriTypography.bodyMedium.copy(
                    color = BrutalistBlack,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
private fun AssistantContent(message: ChatMessage) {
    val content = message.content ?: ""
    val clipboard = LocalClipboardManager.current

    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                     text = "ANALYSIS_STREAM",
                     style = KiriTypography.labelMedium.copy(
                         color = BrutalistDarkGray,
                         fontSize = 11.sp,
                         fontWeight = FontWeight.Black
                     )
                )
                val modelName = message.model
                if (modelName != null) {
                    val shortModelName = modelName.split("/").last().uppercase()
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .background(BrutalistYellowDark, RoundedCornerShape(4.dp))
                            .border(1.5.dp, BrutalistBlack, RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = shortModelName,
                            style = KiriTypography.labelSmall.copy(
                                color = BrutalistBlack,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Black
                            )
                        )
                    }
                }
            }
            IconButton(
                onClick = { clipboard.setText(AnnotatedString(content)) },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    Icons.Default.ContentCopy, 
                    contentDescription = "Copy", 
                    tint = BrutalistBlack, 
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        val markdownTypography = markdownTypography(
            h1 = KiriTypography.headlineLarge.copy(color = BrutalistBlack, fontWeight = FontWeight.Black),
            h2 = KiriTypography.headlineMedium.copy(color = BrutalistBlack, fontWeight = FontWeight.Bold),
            paragraph = KiriTypography.bodyMedium.copy(
                color = BrutalistBlack,
                lineHeight = 24.sp,
                letterSpacing = 0.25.sp,
                fontWeight = FontWeight.Bold
            ),
            code = KiriTypography.labelMedium.copy(
                color = BrutalistBlack,
                background = BrutalistLightGray
            )
        )
        val markdownColors = markdownColor(
            text = BrutalistBlack,
            codeText = BrutalistBlack,
            inlineCodeText = BrutalistBlack,
            linkText = BrutalistDarkGray
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
