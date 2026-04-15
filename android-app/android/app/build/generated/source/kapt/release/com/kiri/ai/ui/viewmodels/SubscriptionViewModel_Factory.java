package com.kiri.ai.ui.viewmodels;

import com.kiri.ai.data.local.AuthDataStore;
import com.kiri.ai.data.repository.SubscriptionRepository;
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
public final class SubscriptionViewModel_Factory implements Factory<SubscriptionViewModel> {
  private final Provider<SubscriptionRepository> repositoryProvider;

  private final Provider<AuthDataStore> authDataStoreProvider;

  public SubscriptionViewModel_Factory(Provider<SubscriptionRepository> repositoryProvider,
      Provider<AuthDataStore> authDataStoreProvider) {
    this.repositoryProvider = repositoryProvider;
    this.authDataStoreProvider = authDataStoreProvider;
  }

  @Override
  public SubscriptionViewModel get() {
    return newInstance(repositoryProvider.get(), authDataStoreProvider.get());
  }

  public static SubscriptionViewModel_Factory create(
      javax.inject.Provider<SubscriptionRepository> repositoryProvider,
      javax.inject.Provider<AuthDataStore> authDataStoreProvider) {
    return new SubscriptionViewModel_Factory(Providers.asDaggerProvider(repositoryProvider), Providers.asDaggerProvider(authDataStoreProvider));
  }

  public static SubscriptionViewModel_Factory create(
      Provider<SubscriptionRepository> repositoryProvider,
      Provider<AuthDataStore> authDataStoreProvider) {
    return new SubscriptionViewModel_Factory(repositoryProvider, authDataStoreProvider);
  }

  public static SubscriptionViewModel newInstance(SubscriptionRepository repository,
      AuthDataStore authDataStore) {
    return new SubscriptionViewModel(repository, authDataStore);
  }
}
