package com.kiri.ai.ui.components

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.kiri.ai.ui.theme.*

/**
 * Bugatti Technical Input Bar
 * 
 * Featuring:
 * - Ultra-monochromatic palette (#000000)
 * - File attachment indicator (technical pill)
 * - 6px corner radius for input area
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInputBar(
    message: String,
    onMessageChange: (String) -> Unit,
    onSend: () -> Unit,
    onAttachClick: () -> Unit,
    selectedFileName: String? = null,
    onClearFile: () -> Unit = {},
    isSending: Boolean = false,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = VelvetBlack,
        border = BorderStroke(1.dp, SilverMist.copy(alpha = 0.2f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // File Preview Chip (Bugatti Technical Style)
            if (selectedFileName != null) {
                Surface(
                    color = DarkGray,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "FILE // ${selectedFileName.uppercase()}",
                            style = KiriTypography.labelMedium.copy(color = ShowroomWhite)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = onClearFile, modifier = Modifier.size(16.dp)) {
                            Icon(Icons.Default.Close, contentDescription = "Clear", tint = SilverMist, modifier = Modifier.size(12.dp))
                        }
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Attach Button (Monospace CAPS label style)
                IconButton(
                    onClick = onAttachClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Attach", tint = ShowroomWhite)
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Input Field (Cinema Black)
                TextField(
                    value = message,
                    onValueChange = onMessageChange,
                    modifier = Modifier.weight(1f),
                    placeholder = { 
                        Text("INPUT_QUERY...", style = KiriTypography.labelMedium.copy(color = SilverMist)) 
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = ShowroomWhite,
                        focusedTextColor = ShowroomWhite,
                        unfocusedTextColor = ShowroomWhite
                    ),
                    maxLines = 5,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            if ((message.isNotBlank() || selectedFileName != null) && !isSending) {
                                onSend()
                                keyboardController?.hide()
                            }
                        }
                    ),
                    textStyle = KiriTypography.bodyMedium
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Send Button (Pill or Direct)
                IconButton(
                    onClick = onSend,
                    enabled = (message.isNotBlank() || selectedFileName != null) && !isSending,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = ShowroomWhite,
                        disabledContentColor = SilverMist.copy(alpha = 0.3f)
                    )
                ) {
                    if (isSending) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = ShowroomWhite,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(Icons.Default.Send, contentDescription = "Send")
                    }
                }
            }
        }
    }
}
