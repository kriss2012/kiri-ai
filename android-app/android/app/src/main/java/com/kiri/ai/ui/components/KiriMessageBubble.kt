package com.kiri.ai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiri.ai.data.models.ChatMessage
import com.kiri.ai.ui.theme.*
import com.mikepenz.markdown.m3.Markdown

@Composable
fun KiriMessageBubble(message: ChatMessage) {
    val isUser = message.role == "user"
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        if (!isUser) {
            AIAvatar()
            Spacer(modifier = Modifier.width(12.dp))
        }
        
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .background(
                    color = if (isUser) TerracottaBrand.copy(alpha = 0.1f) else Ivory,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isUser) 16.dp else 4.dp,
                        bottomEnd = if (isUser) 4.dp else 16.dp
                    )
                )
                .padding(12.dp)
                .let { 
                    if (!isUser) it.background(Color.Transparent, RoundedCornerShape(16.dp)) 
                    else it 
                }
        ) {
            if (isUser) {
                Text(
                    text = message.content,
                    style = KiriTypography.bodyMedium.copy(
                        color = AnthropicNearBlack,
                        lineHeight = 22.sp
                    )
                )
            } else {
                Markdown(
                    content = message.content,
                    typography = com.mikepenz.markdown.model.markdownTypography(
                        body1 = KiriTypography.bodyMedium,
                        h1 = KiriTypography.headlineLarge,
                        h2 = KiriTypography.headlineMedium
                    )
                )
            }
        }
        
        if (isUser) {
            Spacer(modifier = Modifier.width(12.dp))
            UserAvatar()
        }
    }
}

@Composable
fun AIAvatar() {
    Box(
        modifier = Modifier
            .size(36.dp)
            .background(TerracottaBrand, androidx.compose.foundation.shape.CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "K",
            style = KiriTypography.labelLarge.copy(color = Ivory, fontWeight = FontWeight.ExtraBold)
        )
    }
}

@Composable
fun UserAvatar() {
    Box(
        modifier = Modifier
            .size(36.dp)
            .background(WarmSand, androidx.compose.foundation.shape.CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "U",
            style = KiriTypography.labelLarge.copy(color = CharcoalWarm, fontWeight = FontWeight.Bold)
        )
    }
}
