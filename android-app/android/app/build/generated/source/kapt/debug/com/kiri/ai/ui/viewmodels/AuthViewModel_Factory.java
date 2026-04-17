package com.kiri.ai.ui.viewmodels;

import androidx.lifecycle.SavedStateHandle;
import com.kiri.ai.data.repository.AuthRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class AuthViewModel_Factory implements Factory<AuthViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public AuthViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public AuthViewModel get() {
    return newInstance(savedStateHandleProvider.get(), authRepositoryProvider.get());
  }

  public static AuthViewModel_Factory create(
      javax.inject.Provider<SavedStateHandle> savedStateHandleProvider,
      javax.inject.Provider<AuthRepository> authRepositoryProvider) {
    return new AuthViewModel_Factory(Providers.asDaggerProvider(savedStateHandleProvider), Providers.asDaggerProvider(authRepositoryProvider));
  }

  public static AuthViewModel_Factory create(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new AuthViewModel_Factory(savedStateHandleProvider, authRepositoryProvider);
  }

  public static AuthViewModel newInstance(SavedStateHandle savedStateHandle,
      AuthRepository authRepository) {
    return new AuthViewModel(savedStateHandle, authRepository);
  }
}
