package com.kiri.ai.ui.viewmodels;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000 .2\u00020\u0001:\u0001.B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0006\u0010\u0012\u001a\u00020\u0013J\"\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0082@\u00a2\u0006\u0002\u0010\u001aJ\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0006\u0010\u001c\u001a\u00020\u0013J\u0006\u0010\u001d\u001a\u00020\u0013J\b\u0010\u001e\u001a\u00020\u0013H\u0002J\b\u0010\u001f\u001a\u00020\u0013H\u0002J\b\u0010 \u001a\u00020\u0013H\u0014J\u001a\u0010!\u001a\u00020\u00132\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019J\u000e\u0010\"\u001a\u00020\u00132\u0006\u0010#\u001a\u00020\u0019J\b\u0010$\u001a\u00020\u0013H\u0002J\u000e\u0010%\u001a\u00020\u00132\u0006\u0010&\u001a\u00020\u0019J\u0006\u0010\'\u001a\u00020\u0013J,\u0010(\u001a\b\u0012\u0004\u0012\u00020*0)2\u0006\u0010+\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u0017H\u0082@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b,\u0010-R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006/"}, d2 = {"Lcom/kiri/ai/ui/viewmodels/ChatViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "chatRepository", "Lcom/kiri/ai/data/repository/ChatRepository;", "authRepository", "Lcom/kiri/ai/data/repository/AuthRepository;", "(Landroid/app/Application;Landroidx/lifecycle/SavedStateHandle;Lcom/kiri/ai/data/repository/ChatRepository;Lcom/kiri/ai/data/repository/AuthRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/kiri/ai/ui/viewmodels/ChatUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearSelectedFile", "", "copyUriToInternalStorage", "Ljava/io/File;", "uri", "Landroid/net/Uri;", "name", "", "(Landroid/net/Uri;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMimeType", "loadConversations", "newChat", "observeConnectivity", "observeUserData", "onCleared", "onFileSelected", "onMessageChange", "msg", "restoreAttachmentState", "selectConversation", "id", "sendMessage", "uploadFileAndSend", "Lkotlin/Result;", "Lcom/kiri/ai/data/models/ChatResponse;", "message", "uploadFileAndSend-0E7RQCE", "(Ljava/lang/String;Landroid/net/Uri;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ChatViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.SavedStateHandle savedStateHandle = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kiri.ai.data.repository.ChatRepository chatRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kiri.ai.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public static final java.lang.String KEY_INPUT = "chat_input_text";
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public static final java.lang.String KEY_FILE_PATH = "chat_file_path";
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public static final java.lang.String KEY_FILE_NAME = "chat_file_name";
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.kiri.ai.ui.viewmodels.ChatUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.kiri.ai.ui.viewmodels.ChatUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private static final com.kiri.ai.ui.viewmodels.ChatViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public ChatViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application, @org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle, @org.jetbrains.annotations.NotNull()
    com.kiri.ai.data.repository.ChatRepository chatRepository, @org.jetbrains.annotations.NotNull()
    com.kiri.ai.data.repository.AuthRepository authRepository) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.kiri.ai.ui.viewmodels.ChatUiState> getUiState() {
        return null;
    }
    
    private final void restoreAttachmentState() {
    }
    
    private final void observeConnectivity() {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
    
    private final void observeUserData() {
    }
    
    public final void loadConversations() {
    }
    
    public final void selectConversation(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void onMessageChange(@org.jetbrains.annotations.NotNull()
    java.lang.String msg) {
    }
    
    public final void onFileSelected(@org.jetbrains.annotations.Nullable()
    android.net.Uri uri, @org.jetbrains.annotations.Nullable()
    java.lang.String name) {
    }
    
    private final java.lang.Object copyUriToInternalStorage(android.net.Uri uri, java.lang.String name, kotlin.coroutines.Continuation<? super java.io.File> $completion) {
        return null;
    }
    
    public final void clearSelectedFile() {
    }
    
    public final void sendMessage() {
    }
    
    public final void newChat() {
    }
    
    private final java.lang.String getMimeType(android.net.Uri uri) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/kiri/ai/ui/viewmodels/ChatViewModel$Companion;", "", "()V", "KEY_FILE_NAME", "", "KEY_FILE_PATH", "KEY_INPUT", "app_release"})
    static final class Companion {
        
        private Companion() {
            super();
        }
    }
}