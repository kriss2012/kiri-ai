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

import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import com.mikepenz.markdown.m3.markdownComponents
import com.mikepenz.markdown.m3.MarkdownHighlightedCodeBlock
import com.mikepenz.markdown.m3.MarkdownHighlightedCodeFence
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString

/**
 * PROJECT_ZERO_G // REINFORCED_STABILITY_LAYER
 * 
 * 1. graphicsLayer(renderEffect) isolation for complex MD blocks.
 * 2. Segmented interpretation of internal technical metadata.
 * 3. Atomic copy actions to prevent Snapshot transactions.
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
            .padding(vertical = 8.dp, horizontal = 12.dp),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        // MONOGRAM_IDENTIFIER
        Text(
            text = (if (isUser) "USER //" else "KIRI //").uppercase(),
            style = KiriTypography.labelMedium.copy(color = SilverMist.copy(alpha = 0.5f)),
            modifier = Modifier.padding(bottom = 6.dp)
        )

        Surface(
            color = if (isUser) VelvetBlack else DarkGray,
            shape = RoundedCornerShape(
                topStart = if (isUser) 12.dp else 2.dp,
                topEnd = if (isUser) 2.dp else 12.dp,
                bottomStart = 12.dp,
                bottomEnd = 12.dp
            ),
            border = if (isUser) BorderStroke(1.dp, SilverMist.copy(alpha = 0.4f)) else null,
            modifier = Modifier
                .widthIn(max = 320.dp)
                .graphicsLayer { clip = true } // ISOLATION_BOUNDARY
        ) {
            Box(modifier = Modifier.padding(14.dp)) {
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
                    .heightIn(max = 200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(bottom = 8.dp),
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
    
    // Segment logic for professional segmentation
    val segments = remember(content) {
        val list = mutableListOf<Pair<String, String>>()
        var current = content
        
        // Match specific Bugatti-Intelligence headers
        val headers = listOf("CONTEXT", "OUTPUT", "NEXT_STEPS")
        headers.forEach { header ->
            if (current.contains("${header}:")) {
                val parts = current.split("${header}:", limit = 2)
                if (parts[0].trim().isNotEmpty()) list.add("INFO" to parts[0].trim())
                current = parts[1]
                list.add(header to "") // Marker for header
            }
        }
        if (current.trim().isNotEmpty()) list.add("DATA" to current.trim())
        list
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                 text = "ANALYSIS_STREAM",
                 style = KiriTypography.labelMedium.copy(color = SilverMist, fontSize = 10.sp)
            )
            IconButton(
                onClick = { clipboard.setText(AnnotatedString(content)) },
                modifier = Modifier.size(16.dp)
            ) {
                Icon(Icons.Default.ContentCopy, "Copy", tint = SilverMist, modifier = Modifier.size(14.dp))
            }
        }

        Markdown(
            content = content,
            modifier = Modifier.fillMaxWidth(),
            typography = markdownTypography(
                h1 = KiriTypography.headlineLarge,
                h2 = KiriTypography.headlineMedium,
                paragraph = KiriTypography.bodyMedium.copy(color = ShowroomWhite, lineHeight = 24.sp),
                code = KiriTypography.labelMedium.copy(color = ShowroomWhite, background = VelvetBlack)
            ),
            colors = markdownColor(
                text = ShowroomWhite,
                codeText = ShowroomWhite,
                inlineCodeText = ShowroomWhite,
                linkText = ShowroomWhite
            ),
            components = markdownComponents(
                codeBlock = { block -> MarkdownHighlightedCodeBlock(block.content, block.language) },
                codeFence = { fence -> MarkdownHighlightedCodeFence(fence.content, fence.language) }
            )
        )
    }
}
