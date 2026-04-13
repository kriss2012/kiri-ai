package com.kiri.ai.ui.viewmodels;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011J\u000e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0014J\u000e\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0014J\u0014\u0010\u0019\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R+\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@BX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\f\u0010\r\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b\u00a8\u0006\u001a"}, d2 = {"Lcom/kiri/ai/ui/viewmodels/AuthViewModel;", "Landroidx/lifecycle/ViewModel;", "authRepository", "Lcom/kiri/ai/data/repository/AuthRepository;", "(Lcom/kiri/ai/data/repository/AuthRepository;)V", "<set-?>", "Lcom/kiri/ai/ui/viewmodels/AuthUiState;", "uiState", "getUiState", "()Lcom/kiri/ai/ui/viewmodels/AuthUiState;", "setUiState", "(Lcom/kiri/ai/ui/viewmodels/AuthUiState;)V", "uiState$delegate", "Landroidx/compose/runtime/MutableState;", "login", "", "onSuccess", "Lkotlin/Function0;", "onEmailChange", "email", "", "onNameChange", "name", "onPasswordChange", "password", "register", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AuthViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.kiri.ai.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState uiState$delegate = null;
    
    @javax.inject.Inject()
    public AuthViewModel(@org.jetbrains.annotations.NotNull()
    com.kiri.ai.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.kiri.ai.ui.viewmodels.AuthUiState getUiState() {
        return null;
    }
    
    private final void setUiState(com.kiri.ai.ui.viewmodels.AuthUiState p0) {
    }
    
    public final void onNameChange(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void onEmailChange(@org.jetbrains.annotations.NotNull()
    java.lang.String email) {
    }
    
    public final void onPasswordChange(@org.jetbrains.annotations.NotNull()
    java.lang.String password) {
    }
    
    public final void login(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess) {
    }
    
    public final void register(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess) {
    }
}