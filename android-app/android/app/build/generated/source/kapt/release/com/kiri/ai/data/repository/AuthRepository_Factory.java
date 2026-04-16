package com.kiri.ai.data.repository;

import com.kiri.ai.data.local.AuthDataStore;
import com.kiri.ai.data.remote.AuthApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<AuthApi> authApiProvider;

  private final Provider<AuthDataStore> authDataStoreProvider;

  public AuthRepository_Factory(Provider<AuthApi> authApiProvider,
      Provider<AuthDataStore> authDataStoreProvider) {
    this.authApiProvider = authApiProvider;
    this.authDataStoreProvider = authDataStoreProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(authApiProvider.get(), authDataStoreProvider.get());
  }

  public static AuthRepository_Factory create(javax.inject.Provider<AuthApi> authApiProvider,
      javax.inject.Provider<AuthDataStore> authDataStoreProvider) {
    return new AuthRepository_Factory(Providers.asDaggerProvider(authApiProvider), Providers.asDaggerProvider(authDataStoreProvider));
  }

  public static AuthRepository_Factory create(Provider<AuthApi> authApiProvider,
      Provider<AuthDataStore> authDataStoreProvider) {
    return new AuthRepository_Factory(authApiProvider, authDataStoreProvider);
  }

  public static AuthRepository newInstance(AuthApi authApi, AuthDataStore authDataStore) {
    return new AuthRepository(authApi, authDataStore);
  }
}
