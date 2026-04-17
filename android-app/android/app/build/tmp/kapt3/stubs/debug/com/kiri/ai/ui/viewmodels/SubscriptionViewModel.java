package com.kiri.ai.ui.viewmodels;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012J\u001e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0012R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0017"}, d2 = {"Lcom/kiri/ai/ui/viewmodels/SubscriptionViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/kiri/ai/data/repository/SubscriptionRepository;", "authDataStore", "Lcom/kiri/ai/data/local/AuthDataStore;", "(Lcom/kiri/ai/data/repository/SubscriptionRepository;Lcom/kiri/ai/data/local/AuthDataStore;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/kiri/ai/ui/viewmodels/SubscriptionUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearError", "", "createOrder", "plan", "", "onPaymentSuccess", "orderId", "paymentId", "signature", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SubscriptionViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.kiri.ai.data.repository.SubscriptionRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kiri.ai.data.local.AuthDataStore authDataStore = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.kiri.ai.ui.viewmodels.SubscriptionUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.kiri.ai.ui.viewmodels.SubscriptionUiState> uiState = null;
    
    @javax.inject.Inject()
    public SubscriptionViewModel(@org.jetbrains.annotations.NotNull()
    com.kiri.ai.data.repository.SubscriptionRepository repository, @org.jetbrains.annotations.NotNull()
    com.kiri.ai.data.local.AuthDataStore authDataStore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.kiri.ai.ui.viewmodels.SubscriptionUiState> getUiState() {
        return null;
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