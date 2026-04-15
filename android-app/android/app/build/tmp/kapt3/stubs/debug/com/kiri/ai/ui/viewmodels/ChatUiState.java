package com.kiri.ai.ui.viewmodels;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\"\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0093\u0001\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u0012\b\b\u0002\u0010\f\u001a\u00020\n\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\n\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0011\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\n\u00a2\u0006\u0002\u0010\u0015J\u000b\u0010$\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\u0011H\u00c6\u0003J\t\u0010&\u001a\u00020\u0011H\u00c6\u0003J\u000b\u0010\'\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\u000f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u000f\u0010)\u001a\b\u0012\u0004\u0012\u00020\b0\u0005H\u00c6\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\t\u0010+\u001a\u00020\nH\u00c6\u0003J\t\u0010,\u001a\u00020\nH\u00c6\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\t\u0010/\u001a\u00020\u0011H\u00c6\u0003J\u0097\u0001\u00100\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\n2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\u0013\u001a\u00020\u00112\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\nH\u00c6\u0001J\u0013\u00101\u001a\u00020\u00112\b\u00102\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00103\u001a\u000204H\u00d6\u0001J\t\u00105\u001a\u00020\nH\u00d6\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u000b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019R\u0011\u0010\f\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0019R\u0011\u0010\u0013\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u001dR\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u001dR\u0011\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u001dR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0017R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0019R\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#\u00a8\u00066"}, d2 = {"Lcom/kiri/ai/ui/viewmodels/ChatUiState;", "", "user", "Lcom/kiri/ai/data/models/User;", "conversations", "", "Lcom/kiri/ai/data/models/Conversation;", "messages", "Lcom/kiri/ai/data/models/ChatMessage;", "currentConversationId", "", "currentTitle", "inputMessage", "selectedFileUri", "Landroid/net/Uri;", "selectedFileName", "isLoadingMessages", "", "isSending", "isConnected", "error", "(Lcom/kiri/ai/data/models/User;Ljava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/net/Uri;Ljava/lang/String;ZZZLjava/lang/String;)V", "getConversations", "()Ljava/util/List;", "getCurrentConversationId", "()Ljava/lang/String;", "getCurrentTitle", "getError", "getInputMessage", "()Z", "getMessages", "getSelectedFileName", "getSelectedFileUri", "()Landroid/net/Uri;", "getUser", "()Lcom/kiri/ai/data/models/User;", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class ChatUiState {
    @org.jetbrains.annotations.Nullable()
    private final com.kiri.ai.data.models.User user = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.kiri.ai.data.models.Conversation> conversations = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.kiri.ai.data.models.ChatMessage> messages = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String currentConversationId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String currentTitle = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String inputMessage = null;
    @org.jetbrains.annotations.Nullable()
    private final android.net.Uri selectedFileUri = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String selectedFileName = null;
    private final boolean isLoadingMessages = false;
    private final boolean isSending = false;
    private final boolean isConnected = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    
    public ChatUiState(@org.jetbrains.annotations.Nullable()
    com.kiri.ai.data.models.User user, @org.jetbrains.annotations.NotNull()
    java.util.List<com.kiri.ai.data.models.Conversation> conversations, @org.jetbrains.annotations.NotNull()
    java.util.List<com.kiri.ai.data.models.ChatMessage> messages, @org.jetbrains.annotations.Nullable()
    java.lang.String currentConversationId, @org.jetbrains.annotations.NotNull()
    java.lang.String currentTitle, @org.jetbrains.annotations.NotNull()
    java.lang.String inputMessage, @org.jetbrains.annotations.Nullable()
    android.net.Uri selectedFileUri, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedFileName, boolean isLoadingMessages, boolean isSending, boolean isConnected, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.kiri.ai.data.models.User getUser() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.kiri.ai.data.models.Conversation> getConversations() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.kiri.ai.data.models.ChatMessage> getMessages() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCurrentConversationId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCurrentTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getInputMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.net.Uri getSelectedFileUri() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSelectedFileName() {
        return null;
    }
    
    public final boolean isLoadingMessages() {
        return false;
    }
    
    public final boolean isSending() {
        return false;
    }
    
    public final boolean isConnected() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    public ChatUiState() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.kiri.ai.data.models.User component1() {
        return null;
    }
    
    public final boolean component10() {
        return false;
    }
    
    public final boolean component11() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.kiri.ai.data.models.Conversation> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.kiri.ai.data.models.ChatMessage> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.net.Uri component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.kiri.ai.ui.viewmodels.ChatUiState copy(@org.jetbrains.annotations.Nullable()
    com.kiri.ai.data.models.User user, @org.jetbrains.annotations.NotNull()
    java.util.List<com.kiri.ai.data.models.Conversation> conversations, @org.jetbrains.annotations.NotNull()
    java.util.List<com.kiri.ai.data.models.ChatMessage> messages, @org.jetbrains.annotations.Nullable()
    java.lang.String currentConversationId, @org.jetbrains.annotations.NotNull()
    java.lang.String currentTitle, @org.jetbrains.annotations.NotNull()
    java.lang.String inputMessage, @org.jetbrains.annotations.Nullable()
    android.net.Uri selectedFileUri, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedFileName, boolean isLoadingMessages, boolean isSending, boolean isConnected, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}