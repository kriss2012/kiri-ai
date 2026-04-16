package com.kiri.ai.ui.viewmodels;

import com.kiri.ai.data.repository.AuthRepository;
import com.kiri.ai.data.repository.ThemeRepository;
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
public final class MainViewModel_Factory implements Factory<MainViewModel> {
  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<ThemeRepository> themeRepositoryProvider;

  public MainViewModel_Factory(Provider<AuthRepository> authRepositoryProvider,
      Provider<ThemeRepository> themeRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
    this.themeRepositoryProvider = themeRepositoryProvider;
  }

  @Override
  public MainViewModel get() {
    return newInstance(authRepositoryProvider.get(), themeRepositoryProvider.get());
  }

  public static MainViewModel_Factory create(
      javax.inject.Provider<AuthRepository> authRepositoryProvider,
      javax.inject.Provider<ThemeRepository> themeRepositoryProvider) {
    return new MainViewModel_Factory(Providers.asDaggerProvider(authRepositoryProvider), Providers.asDaggerProvider(themeRepositoryProvider));
  }

  public static MainViewModel_Factory create(Provider<AuthRepository> authRepositoryProvider,
      Provider<ThemeRepository> themeRepositoryProvider) {
    return new MainViewModel_Factory(authRepositoryProvider, themeRepositoryProvider);
  }

  public static MainViewModel newInstance(AuthRepository authRepository,
      ThemeRepository themeRepository) {
    return new MainViewModel(authRepository, themeRepository);
  }
}
