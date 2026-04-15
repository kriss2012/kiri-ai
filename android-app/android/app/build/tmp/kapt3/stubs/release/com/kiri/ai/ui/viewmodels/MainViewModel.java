package com.kiri.ai.ui.viewmodels;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\u0006\u0010\u0012\u001a\u00020\u0011R\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\rR\u0019\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/kiri/ai/ui/viewmodels/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "authRepository", "Lcom/kiri/ai/data/repository/AuthRepository;", "themeRepository", "Lcom/kiri/ai/data/repository/ThemeRepository;", "(Lcom/kiri/ai/data/repository/AuthRepository;Lcom/kiri/ai/data/repository/ThemeRepository;)V", "_startDestination", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "isDarkMode", "Lkotlinx/coroutines/flow/StateFlow;", "", "()Lkotlinx/coroutines/flow/StateFlow;", "startDestination", "getStartDestination", "checkAuthStatus", "", "toggleTheme", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.kiri.ai.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kiri.ai.data.repository.ThemeRepository themeRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _startDestination = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> startDestination = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isDarkMode = null;
    
    @javax.inject.Inject()
    public MainViewModel(@org.jetbrains.annotations.NotNull()
    com.kiri.ai.data.repository.AuthRepository authRepository, @org.jetbrains.annotations.NotNull()
    com.kiri.ai.data.repository.ThemeRepository themeRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getStartDestination() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isDarkMode() {
        return null;
    }
    
    public final void toggleTheme() {
    }
    
    private final void checkAuthStatus() {
    }
}