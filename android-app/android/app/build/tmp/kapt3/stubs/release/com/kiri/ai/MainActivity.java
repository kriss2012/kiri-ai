package com.kiri.ai;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0007H\u0003J\u0012\u0010\u0011\u001a\u00020\u000f2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014J$\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u00072\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\u001c\u0010\u001a\u001a\u00020\u000f2\b\u0010\u001b\u001a\u0004\u0018\u00010\u00072\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010\u001c\u001a\u00020\u000fH\u0002J\b\u0010\u001d\u001a\u00020\u000fH\u0002R\u001a\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\b\u001a\u00020\t8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u001e"}, d2 = {"Lcom/kiri/ai/MainActivity;", "Landroidx/activity/ComponentActivity;", "Lcom/razorpay/PaymentResultWithDataListener;", "()V", "requestPermissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "", "subscriptionViewModel", "Lcom/kiri/ai/ui/viewmodels/SubscriptionViewModel;", "getSubscriptionViewModel", "()Lcom/kiri/ai/ui/viewmodels/SubscriptionViewModel;", "subscriptionViewModel$delegate", "Lkotlin/Lazy;", "CrashDialog", "", "crashTrace", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onPaymentError", "code", "", "response", "paymentData", "Lcom/razorpay/PaymentData;", "onPaymentSuccess", "razorpayPaymentId", "requestPermissions", "startKiriService", "app_release"})
public final class MainActivity extends androidx.activity.ComponentActivity implements com.razorpay.PaymentResultWithDataListener {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy subscriptionViewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> requestPermissionLauncher = null;
    
    public MainActivity() {
        super();
    }
    
    private final com.kiri.ai.ui.viewmodels.SubscriptionViewModel getSubscriptionViewModel() {
        return null;
    }
    
    /**
     * ARCHITECTURAL_STABILITY_NOTICE
     * This application uses a flat, technical design system to prevent native rendering
     * recursion crashes (dispatchGetDisplayList). 
     *
     * CORE_GUIDELINES:
     * 1. Avoid nesting NavHosts or multiple Scaffolds.
     * 2. Ensure all screens handle WindowInsets (IME, status, and navigation bars) at the root.
     * 3. chat-related components must use explicit drawing layers (graphicsLayer).
     */
    private final void startKiriService() {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @androidx.compose.runtime.Composable()
    private final void CrashDialog(java.lang.String crashTrace) {
    }
    
    private final void requestPermissions() {
    }
    
    @java.lang.Override()
    public void onPaymentSuccess(@org.jetbrains.annotations.Nullable()
    java.lang.String razorpayPaymentId, @org.jetbrains.annotations.Nullable()
    com.razorpay.PaymentData paymentData) {
    }
    
    @java.lang.Override()
    public void onPaymentError(int code, @org.jetbrains.annotations.Nullable()
    java.lang.String response, @org.jetbrains.annotations.Nullable()
    com.razorpay.PaymentData paymentData) {
    }
}