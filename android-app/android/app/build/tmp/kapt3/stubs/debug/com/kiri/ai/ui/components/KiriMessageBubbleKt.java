package com.kiri.ai.ui.components;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0003\u001a\u0012\u0010\u0006\u001a\u00020\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0007\u001a\u0018\u0010\t\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0003\u00a8\u0006\n"}, d2 = {"AssistantContent", "", "content", "", "colorScheme", "Landroidx/compose/material3/ColorScheme;", "KiriMessageBubble", "message", "Lcom/kiri/ai/data/models/ChatMessage;", "UserContent", "app_debug"})
public final class KiriMessageBubbleKt {
    
    /**
     * ! CRITICAL STABILITY COMPONENT - PERMANENT FIX FOR NATIVE RENDERING CRASH !
     *
     * ERROR: java.lang.StackOverflowError / Native crash in dispatchGetDisplayList.
     * CAUSE: Hardware renderer recursion limit exceeded by deep Compose trees or huge text.
     *
     * PERMANENT SOLUTIONS (DO NOT REMOVE):
     * 1. COMPOSITING_STRATEGY: Using CompositingStrategy.Offscreen on the root Column.
     *   This forces the Android OS to flatten the entire message bubble into a single
     *   hardware layer, breaking any drawing recursion chain immediately.
     * 2. AGGRESSIVE_TRUNCATION: Hard limit of 5,000 characters. Single nodes larger than
     *   this can exceed the 64KB DisplayList operation limit on many Android versions.
     * 3. HIERARCHY_FLATTENING: Minimized nesting to keep the native View tree shallow.
     */
    @androidx.compose.runtime.Composable()
    public static final void KiriMessageBubble(@org.jetbrains.annotations.Nullable()
    com.kiri.ai.data.models.ChatMessage message) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void UserContent(java.lang.String content, androidx.compose.material3.ColorScheme colorScheme) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AssistantContent(java.lang.String content, androidx.compose.material3.ColorScheme colorScheme) {
    }
}