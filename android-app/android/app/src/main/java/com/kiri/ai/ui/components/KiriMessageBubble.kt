package com.kiri.ai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.model.markdownTypography

import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.kiri.ai.R

@Composable
fun KiriMessageBubble(message: ChatMessage) {
    val isUser = message.role == "user"
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 6.dp)
        ) {
            if (!isUser) {
                AIAvatar()
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Kiri AI",
                    style = KiriTypography.labelMedium.copy(fontWeight = FontWeight.Bold, color = AnthropicNearBlack)
                )
            } else {
                Text(
                    text = "You",
                    style = KiriTypography.labelMedium.copy(fontWeight = FontWeight.Bold, color = AnthropicNearBlack)
                )
                Spacer(modifier = Modifier.width(8.dp))
                UserAvatar()
            }
        }
        
        Box(
            modifier = Modifier
                .widthIn(max = 320.dp)
                .background(
                    color = if (isUser) TerracottaBrand.copy(alpha = 0.05f) else Ivory,
                    shape = RoundedCornerShape(12.dp)
                )
                .let { 
                    if (!isUser) it.border(1.dp, BorderCream, RoundedCornerShape(12.dp)) 
                    else it 
                }
                .padding(16.dp)
        ) {
            if (isUser) {
                Text(
                    text = message.content ?: "",
                    style = KiriTypography.bodyMedium.copy(
                        color = AnthropicNearBlack,
                        lineHeight = 24.sp
                    )
                )
            } else {
                Markdown(
                    content = message.content ?: "",
                    typography = markdownTypography(
                        paragraph = KiriTypography.bodyMedium,
                        h1 = KiriTypography.headlineMedium,
                        h2 = KiriTypography.titleLarge
                    )
                )
            }
        }
    }
}

@Composable
fun AIAvatar() {
    Image(
        painter = painterResource(id = R.drawable.app_logo),
        contentDescription = "Kiri AI",
        modifier = Modifier
            .size(36.dp)
            .background(Color.White, CircleShape)
            .padding(4.dp)
    )
}

@Composable
fun UserAvatar() {
    Box(
        modifier = Modifier
            .size(36.dp)
            .background(WarmSand, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "U",
            style = KiriTypography.labelLarge.copy(color = CharcoalWarm, fontWeight = FontWeight.Bold)
        )
    }
}
