package com.kiri.ai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInputBar(
    message: String,
    onMessageChange: (String) -> Unit,
    onSend: () -> Unit,
    isSending: Boolean = false,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Parchment,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .navigationBarsPadding()
                .imePadding(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = message,
                onValueChange = onMessageChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Message Kiri...", color = StoneGray) },
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = OliveGray.copy(alpha = 0.5f),
                    unfocusedBorderColor = OliveGray.copy(alpha = 0.2f),
                    focusedContainerColor = Ivory,
                    unfocusedContainerColor = Ivory,
                    cursorColor = AnthropicNearBlack
                ),
                maxLines = 5,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (message.isNotBlank() && !isSending) {
                            onSend()
                            keyboardController?.hide()
                        }
                    }
                ),
                textStyle = KiriTypography.bodyMedium
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            IconButton(
                onClick = onSend,
                enabled = message.isNotBlank() && !isSending,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = TerracottaBrand,
                    contentColor = Ivory,
                    disabledContainerColor = OliveGray.copy(alpha = 0.3f),
                    disabledContentColor = Ivory.copy(alpha = 0.5f)
                ),
                modifier = Modifier.size(48.dp)
            ) {
                if (isSending) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Ivory,
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(Icons.Default.Send, contentDescription = "Send")
                }
            }
        }
    }
}
