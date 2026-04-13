package com.kiri.ai;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0012\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0014J$\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\u001c\u0010\u0015\u001a\u00020\u000b2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0017"}, d2 = {"Lcom/kiri/ai/MainActivity;", "Landroidx/activity/ComponentActivity;", "Lcom/razorpay/PaymentResultWithDataListener;", "()V", "subscriptionViewModel", "Lcom/kiri/ai/ui/viewmodels/SubscriptionViewModel;", "getSubscriptionViewModel", "()Lcom/kiri/ai/ui/viewmodels/SubscriptionViewModel;", "subscriptionViewModel$delegate", "Lkotlin/Lazy;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onPaymentError", "code", "", "response", "", "paymentData", "Lcom/razorpay/PaymentData;", "onPaymentSuccess", "razorpayPaymentId", "app_release"})
public final class MainActivity extends androidx.activity.ComponentActivity implements com.razorpay.PaymentResultWithDataListener {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy subscriptionViewModel$delegate = null;
    
    public MainActivity() {
        super();
    }
    
    private final com.kiri.ai.ui.viewmodels.SubscriptionViewModel getSubscriptionViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
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