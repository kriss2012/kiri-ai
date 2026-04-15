package com.kiri.ai.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\r\u001a\u00020\tH\u0002J$\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0013\u0010\u0014J$\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0014J\"\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001b\u0010\u001cJ)\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u000f2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001e0 H\u0002\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b!\u0010\"J0\u0010#\u001a\b\u0012\u0004\u0012\u00020\u001e0\u000f2\u0006\u0010$\u001a\u00020\u00122\n\b\u0002\u0010%\u001a\u0004\u0018\u00010\u0012H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b&\u0010\'J6\u0010(\u001a\b\u0012\u0004\u0012\u00020\u001e0\u000f2\u0006\u0010)\u001a\u00020*2\b\u0010%\u001a\u0004\u0018\u00010*2\u0006\u0010+\u001a\u00020,H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b-\u0010.R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\f\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006/"}, d2 = {"Lcom/kiri/ai/data/repository/ChatRepository;", "", "chatApi", "Lcom/kiri/ai/data/remote/ChatApi;", "context", "Landroid/content/Context;", "(Lcom/kiri/ai/data/remote/ChatApi;Landroid/content/Context;)V", "_isConnected", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "isConnected", "Lkotlinx/coroutines/flow/StateFlow;", "()Lkotlinx/coroutines/flow/StateFlow;", "checkInitialConnectivity", "deleteConversation", "Lkotlin/Result;", "Lcom/kiri/ai/data/models/GenericResponse;", "id", "", "deleteConversation-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getConversationDetail", "Lcom/kiri/ai/data/models/ChatDetail;", "getConversationDetail-gIAlu-s", "getConversations", "", "Lcom/kiri/ai/data/models/Conversation;", "getConversations-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "handleResponse", "Lcom/kiri/ai/data/models/ChatResponse;", "response", "Lretrofit2/Response;", "handleResponse-IoAF18A", "(Lretrofit2/Response;)Ljava/lang/Object;", "sendMessage", "message", "conversationId", "sendMessage-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendMessageWithFile", "content", "Lokhttp3/RequestBody;", "filePart", "Lokhttp3/MultipartBody$Part;", "sendMessageWithFile-BWLJW6A", "(Lokhttp3/RequestBody;Lokhttp3/RequestBody;Lokhttp3/MultipartBody$Part;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class ChatRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.kiri.ai.data.remote.ChatApi chatApi = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isConnected = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isConnected = null;
    
    @javax.inject.Inject()
    public ChatRepository(@org.jetbrains.annotations.NotNull()
    com.kiri.ai.data.remote.ChatApi chatApi, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isConnected() {
        return null;
    }
    
    private final boolean checkInitialConnectivity() {
        return false;
    }
}