package com.kiri.ai.ui.viewmodels;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u0014\u001a\u00020\u0015J\u0006\u0010\u0016\u001a\u00020\u0015J\b\u0010\u0017\u001a\u00020\u0015H\u0002J\u000e\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001aJ\u0006\u0010\u001d\u001a\u00020\u0015J\u000e\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020\nR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R+\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f8F@BX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011\u00a8\u0006 "}, d2 = {"Lcom/kiri/ai/ui/viewmodels/ChatViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "chatRepository", "Lcom/kiri/ai/data/repository/ChatRepository;", "authRepository", "Lcom/kiri/ai/data/repository/AuthRepository;", "(Landroid/app/Application;Lcom/kiri/ai/data/repository/ChatRepository;Lcom/kiri/ai/data/repository/AuthRepository;)V", "isAppInBackground", "", "<set-?>", "Lcom/kiri/ai/ui/viewmodels/ChatUiState;", "uiState", "getUiState", "()Lcom/kiri/ai/ui/viewmodels/ChatUiState;", "setUiState", "(Lcom/kiri/ai/ui/viewmodels/ChatUiState;)V", "uiState$delegate", "Landroidx/compose/runtime/MutableState;", "loadConversations", "", "newChat", "observeUserData", "onMessageChange", "msg", "", "selectConversation", "id", "sendMessage", "setAppBackgroundState", "isInBackground", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ChatViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.kiri.ai.data.repository.ChatRepository chatRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kiri.ai.data.repository.AuthRepository authRepository = null;
    private boolean isAppInBackground = false;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState uiState$delegate = null;
    
    @javax.inject.Inject()
    public ChatViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application, @org.jetbrains.annotations.NotNull()
    com.kiri.ai.data.repository.ChatRepository chatRepository, @org.jetbrains.annotations.NotNull()
    com.kiri.ai.data.repository.AuthRepository authRepository) {
        super(null);
    }
    
    public final void setAppBackgroundState(boolean isInBackground) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.kiri.ai.ui.viewmodels.ChatUiState getUiState() {
        return null;
    }
    
    private final void setUiState(com.kiri.ai.ui.viewmodels.ChatUiState p0) {
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
    
    public final void sendMessage() {
    }
    
    public final void newChat() {
    }
}