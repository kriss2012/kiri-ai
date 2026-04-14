package com.kiri.ai.ui.viewmodels;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014J\u001e\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R+\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8F@BX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r\u00a8\u0006\u0019"}, d2 = {"Lcom/kiri/ai/ui/viewmodels/SubscriptionViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/kiri/ai/data/repository/SubscriptionRepository;", "authDataStore", "Lcom/kiri/ai/data/local/AuthDataStore;", "(Lcom/kiri/ai/data/repository/SubscriptionRepository;Lcom/kiri/ai/data/local/AuthDataStore;)V", "<set-?>", "Lcom/kiri/ai/ui/viewmodels/SubscriptionUiState;", "uiState", "getUiState", "()Lcom/kiri/ai/ui/viewmodels/SubscriptionUiState;", "setUiState", "(Lcom/kiri/ai/ui/viewmodels/SubscriptionUiState;)V", "uiState$delegate", "Landroidx/compose/runtime/MutableState;", "clearError", "", "createOrder", "plan", "", "onPaymentSuccess", "orderId", "paymentId", "signature", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SubscriptionViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.kiri.ai.data.repository.SubscriptionRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kiri.ai.data.local.AuthDataStore authDataStore = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState uiState$delegate = null;
    
    @javax.inject.Inject()
    public SubscriptionViewModel(@org.jetbrains.annotations.NotNull()
    com.kiri.ai.data.repository.SubscriptionRepository repository, @org.jetbrains.annotations.NotNull()
    com.kiri.ai.data.local.AuthDataStore authDataStore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.kiri.ai.ui.viewmodels.SubscriptionUiState getUiState() {
        return null;
    }
    
    private final void setUiState(com.kiri.ai.ui.viewmodels.SubscriptionUiState p0) {
    }
    
    public final void createOrder(@org.jetbrains.annotations.NotNull()
    java.lang.String plan) {
    }
    
    public final void onPaymentSuccess(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    java.lang.String paymentId, @org.jetbrains.annotations.NotNull()
    java.lang.String signature) {
    }
    
    public final void clearError() {
    }
}