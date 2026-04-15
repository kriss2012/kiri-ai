package com.kiri.ai.data.remote;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u001e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00032\b\b\u0001\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00032\b\b\u0001\u0010\u0010\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u0012J4\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00032\b\b\u0001\u0010\u0014\u001a\u00020\u00152\n\b\u0001\u0010\u0016\u001a\u0004\u0018\u00010\u00152\b\b\u0001\u0010\u0017\u001a\u00020\u0018H\u00a7@\u00a2\u0006\u0002\u0010\u0019\u00a8\u0006\u001a"}, d2 = {"Lcom/kiri/ai/data/remote/ChatApi;", "", "deleteAllConversations", "Lretrofit2/Response;", "Lcom/kiri/ai/data/models/GenericResponse;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteConversation", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getConversation", "Lcom/kiri/ai/data/models/ConversationDetailResponse;", "getConversations", "Lcom/kiri/ai/data/models/ConversationsResponse;", "sendMessage", "Lcom/kiri/ai/data/models/ChatResponse;", "request", "Lcom/kiri/ai/data/models/ChatRequest;", "(Lcom/kiri/ai/data/models/ChatRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendMessageWithFile", "content", "Lokhttp3/RequestBody;", "conversationId", "file", "Lokhttp3/MultipartBody$Part;", "(Lokhttp3/RequestBody;Lokhttp3/RequestBody;Lokhttp3/MultipartBody$Part;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface ChatApi {
    
    @retrofit2.http.GET(value = "chat/conversations")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getConversations(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.kiri.ai.data.models.ConversationsResponse>> $completion);
    
    @retrofit2.http.GET(value = "chat/conversations/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getConversation(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.kiri.ai.data.models.ConversationDetailResponse>> $completion);
    
    @retrofit2.http.POST(value = "chat/message")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object sendMessage(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.kiri.ai.data.models.ChatRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.kiri.ai.data.models.ChatResponse>> $completion);
    
    @retrofit2.http.Multipart()
    @retrofit2.http.POST(value = "chat/message/upload")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object sendMessageWithFile(@retrofit2.http.Part(value = "content")
    @org.jetbrains.annotations.NotNull()
    okhttp3.RequestBody content, @retrofit2.http.Part(value = "conversationId")
    @org.jetbrains.annotations.Nullable()
    okhttp3.RequestBody conversationId, @retrofit2.http.Part()
    @org.jetbrains.annotations.NotNull()
    okhttp3.MultipartBody.Part file, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.kiri.ai.data.models.ChatResponse>> $completion);
    
    @retrofit2.http.DELETE(value = "chat/conversations/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteConversation(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.kiri.ai.data.models.GenericResponse>> $completion);
    
    @retrofit2.http.DELETE(value = "chat/conversations")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllConversations(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.kiri.ai.data.models.GenericResponse>> $completion);
}