package com.kiriai.kiriorganization.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiriai.kiriorganization.ui.theme.*

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
    val isDark = isSystemInDarkTheme()
    val textColor = if (isDark) BrutalistWhite else BrutalistBlack
    val inputBg = if (isDark) BrutalistDarkGray else BrutalistWhite
    val attachBtnColor = if (isDark) BrutalistYellow else BrutalistBlack

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Neo-Brutalist File/Image Preview
        if (selectedFileName != null) {
            Box(
                modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
            ) {
                // Offset shadow layer
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .offset(x = 4.dp, y = 4.dp)
                        .background(color = textColor, shape = RoundedCornerShape(8.dp))
                )

                // Foreground
                Row(
                    modifier = Modifier
                        .background(BrutalistYellow, RoundedCornerShape(8.dp))
                        .border(3.dp, textColor, RoundedCornerShape(8.dp))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val isImage = selectedFileName.lowercase().let { 
                        it.endsWith(".jpg") || it.endsWith(".jpeg") || it.endsWith(".png") || it.endsWith(".webp")
                    }
                    
                    if (isImage && selectedFileUri != null) {
                        coil.compose.AsyncImage(
                            model = selectedFileUri,
                            contentDescription = null,
                            placeholder = androidx.compose.ui.graphics.painter.ColorPainter(BrutalistLightGray),
                            error = androidx.compose.ui.graphics.painter.ColorPainter(KiriError),
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .border(2.dp, textColor, RoundedCornerShape(4.dp))
                                .background(BrutalistWhite),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }

                    Column(modifier = Modifier.weight(1f, fill = false)) {
                        Text(
                            text = "FILE_ATTACHED",
                            style = KiriTypography.labelSmall.copy(color = textColor)
                        )
                        Text(
                            text = selectedFileName,
                            style = KiriTypography.labelMedium.copy(color = textColor),
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                            modifier = Modifier.widthIn(max = 180.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    IconButton(
                        onClick = onClearFile, 
                        modifier = Modifier
                            .size(24.dp)
                            .background(textColor, CircleShape)
                    ) {
                        Icon(
                            Icons.Default.Close, 
                            contentDescription = "Clear", 
                            tint = if (isDark) BrutalistBlack else BrutalistWhite, 
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
            // Neo-Brutalist Text Input container
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 54.dp)
                    .background(inputBg, RoundedCornerShape(8.dp))
                    .border(3.dp, textColor, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp)
            ) {
                // Attach Button
                IconButton(
                    onClick = onAttachClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Attach File",
                        tint = attachBtnColor
                    )
                }

                BasicTextField(
                    value = message,
                    onValueChange = onMessageChange,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                    textStyle = TextStyle(
                        color = textColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    cursorBrush = SolidColor(textColor),
                    decorationBox = { innerTextField ->
                        if (message.isEmpty()) {
                            Text(
                                text = "MESSAGE / LOG",
                                style = TextStyle(
                                    color = textColor.copy(alpha = 0.5f),
                                    fontSize = 15.sp,
                                    letterSpacing = 1.sp,
                                    fontWeight = FontWeight.Black
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

            Spacer(modifier = Modifier.width(10.dp))

            // Action Button (Send) - Neo-Brutalist Button Card
            val showSend = message.isNotBlank() || selectedFileName != null
            val buttonBg = if (showSend) BrutalistYellow else BrutalistLightGray
            
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .background(buttonBg, RoundedCornerShape(8.dp))
                    .border(3.dp, textColor, RoundedCornerShape(8.dp))
                    .clickable(
                        enabled = showSend && !isSending,
                        onClick = onSend
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isSending) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = textColor,
                        strokeWidth = 3.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = textColor
                    )
                }
            }
        }
    }
}
