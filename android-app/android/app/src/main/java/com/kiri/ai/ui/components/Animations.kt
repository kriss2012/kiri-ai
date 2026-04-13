package com.kiri.ai.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset

@Composable
fun FadeUpAnimation(
    visible: Boolean,
    modifier: Modifier = Modifier,
    delayMillis: Int = 0,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(600, delayMillis)) +
                slideInVertically(
                    animationSpec = tween(600, delayMillis),
                    initialOffsetY = { 40 }
                ),
        modifier = modifier
    ) {
        content()
    }
}
