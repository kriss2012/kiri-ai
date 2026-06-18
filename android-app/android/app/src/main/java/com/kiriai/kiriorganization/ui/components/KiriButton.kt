package com.kiriai.kiriorganization.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiriai.kiriorganization.ui.theme.*

/**
 * Neo-Brutalist Button with tactile offset shadow and physical press state animation.
 */
@Composable
fun KiriButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color = BrutalistYellow,
    contentColor: Color = BrutalistBlack,
    isLoading: Boolean = false,
    borderWidth: Dp = 3.dp,
    cornerRadius: Dp = 8.dp,
    shadowOffset: Dp = 4.dp
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val animatedOffset by animateDpAsState(
        targetValue = if (isPressed) 0.dp else shadowOffset,
        label = "buttonPress"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled && !isLoading,
                onClick = onClick
            )
    ) {
        if (animatedOffset > 0.dp) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .offset(x = shadowOffset, y = shadowOffset)
                    .background(
                        color = BrutalistBlack,
                        shape = RoundedCornerShape(cornerRadius)
                    )
            )
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(
                    x = if (isPressed) shadowOffset else 0.dp,
                    y = if (isPressed) shadowOffset else 0.dp
                )
                .background(
                    color = if (enabled) containerColor else BrutalistLightGray,
                    shape = RoundedCornerShape(cornerRadius)
                )
                .border(
                    width = borderWidth,
                    color = BrutalistBlack,
                    shape = RoundedCornerShape(cornerRadius)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    color = contentColor,
                    strokeWidth = 3.dp
                )
            } else {
                Text(
                    text = text.uppercase(),
                    style = KiriTypography.labelLarge.copy(
                        color = if (enabled) contentColor else BrutalistDarkGray,
                        letterSpacing = 1.sp
                    )
                )
            }
        }
    }
}

@Composable
fun KiriSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    KiriButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        isLoading = isLoading,
        containerColor = BrutalistWhite,
        contentColor = BrutalistBlack
    )
}

@Composable
fun KiriOutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    KiriButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        isLoading = isLoading,
        containerColor = BrutalistLightGray,
        contentColor = BrutalistDarkGray
    )
}
