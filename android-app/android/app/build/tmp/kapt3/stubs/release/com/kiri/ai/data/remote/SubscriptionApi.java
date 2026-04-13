package com.kiri.ai.data.remote;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J6\u0010\u0002\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u00040\u00032\u0014\b\u0001\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0007J \u0010\b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\tJ*\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00032\u0014\b\u0001\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0007\u00a8\u0006\f"}, d2 = {"Lcom/kiri/ai/data/remote/SubscriptionApi;", "", "createOrder", "Lretrofit2/Response;", "", "", "request", "(Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getStatus", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "verifyPayment", "Lcom/kiri/ai/data/models/AuthResponse;", "app_release"})
public abstract interface SubscriptionApi {
    
    @retrofit2.http.POST(value = "api/subscription/create-order")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createOrder(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.Map<java.lang.String, java.lang.Object>>> $completion);
    
    @retrofit2.http.POST(value = "api/subscription/verify-payment")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object verifyPayment(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ? extends java.lang.Object> request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.kiri.ai.data.models.AuthResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/subscription/status")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getStatus(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.Map<java.lang.String, java.lang.Object>>> $completion);
}