package com.kiri.ai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiri.ai.ui.theme.*

@Composable
fun ChatInputBar(
    message: String,
    onMessageChange: (String) -> Unit,
    onSend: () -> Unit,
    onAttachClick: () -> Unit,
    selectedFileUri: android.net.Uri? = null,
    selectedFileName: String? = null,
    onClearFile: () -> Unit = {},
    isSending: Boolean = false,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        // Advanced File/Image Preview
        if (selectedFileName != null) {
            Surface(
                color = colorScheme.surfaceVariant,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, colorScheme.outline.copy(alpha = 0.2f)),
                modifier = Modifier.padding(bottom = 8.dp, start = 8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Thumbnail if it's an image (simulated check via extension)
                    val isImage = selectedFileName.lowercase().let { 
                        it.endsWith(".jpg") || it.endsWith(".jpeg") || it.endsWith(".png") || it.endsWith(".webp")
                    }
                    
                    if (isImage && selectedFileUri != null) {
                        coil.compose.AsyncImage(
                            model = selectedFileUri,
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.Black.copy(alpha = 0.05f)),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }

                    Column {
                        Text(
                            text = "FILE_ATTACHED",
                            style = KiriTypography.labelSmall.copy(color = colorScheme.primary.copy(alpha = 0.6f))
                        )
                        Text(
                            text = selectedFileName,
                            style = KiriTypography.labelMedium.copy(color = colorScheme.onSurfaceVariant),
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                            modifier = Modifier.widthIn(max = 200.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    IconButton(
                        onClick = onClearFile, 
                        modifier = Modifier
                            .size(24.dp)
                            .background(colorScheme.onSurfaceVariant.copy(alpha = 0.1f), CircleShape)
                    ) {
                        Icon(
                            Icons.Default.Close, 
                            contentDescription = "Clear", 
                            tint = colorScheme.onSurfaceVariant, 
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Main Input Pill
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 48.dp)
                    .background(
                        color = colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(horizontal = 8.dp)
            ) {
                // Attach Button (Plus Icon)
                IconButton(
                    onClick = onAttachClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add, // Using Add (Plus) for modern feel
                        contentDescription = "Attach File",
                        tint = colorScheme.primary
                    )
                }

                BasicTextField(
                    value = message,
                    onValueChange = onMessageChange,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                    textStyle = TextStyle(
                        color = colorScheme.onSurface,
                        fontSize = 16.sp
                    ),
                    cursorBrush = SolidColor(colorScheme.primary),
                    decorationBox = { innerTextField ->
                        if (message.isEmpty()) {
                            Text(
                                text = "MESSAGE / LOG",
                                style = TextStyle(
                                    color = colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                    fontSize = 15.sp,
                                    letterSpacing = 1.sp
                                )
                            )
                        }
                        innerTextField()
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            if ((message.isNotBlank() || selectedFileName != null) && !isSending) {
                                onSend()
                                keyboardController?.hide()
                            }
                        }
                    )
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Action Button (Send)
            val showSend = message.isNotBlank() || selectedFileName != null
            
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = if (showSend) colorScheme.primary else colorScheme.primary.copy(alpha = 0.3f),
                        shape = CircleShape
                    )
                    .clickable(
                        enabled = showSend && !isSending,
                        onClick = onSend
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isSending) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = if (showSend) colorScheme.onPrimary else colorScheme.onPrimary.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}
