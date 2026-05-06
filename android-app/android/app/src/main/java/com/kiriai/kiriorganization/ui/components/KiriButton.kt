package com.kiriai.kiriorganization.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.kiriai.kiriorganization.ui.theme.*

/**
 * Bugatti Design System Buttons
 * 
 * Primary: White Outlined Pill (9999px radius)
 * Secondary: Technical Gray Outline (6px radius)
 */

@Composable
fun KiriButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color = Color.Transparent,
    contentColor: Color = ShowroomWhite,
    isLoading: Boolean = false,
    shape: Shape = RoundedCornerShape(12.dp),
    border: BorderStroke? = BorderStroke(1.dp, ShowroomWhite.copy(alpha = 0.8f))
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        enabled = enabled && !isLoading,
        shape = shape,
        color = containerColor,
        border = border,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = contentColor,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = text.uppercase(),
                    style = KiriTypography.labelLarge.copy(
                        color = contentColor,
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
    enabled: Boolean = true
) {
    // Technical Gray Outline from DESIGN.md (6px radius)
    KiriButton(
        text = text,
        onClick = onClick,
        modifier = modifier.height(38.dp),
        enabled = enabled,
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, SilverMist),
        contentColor = ShowroomWhite
    )
}

@Composable
fun KiriOutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    // In Bugatti system, the Primary IS an outline but let's keep this for compatibility
    KiriButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        border = BorderStroke(1.dp, SilverMist),
        contentColor = SilverMist
    )
}
