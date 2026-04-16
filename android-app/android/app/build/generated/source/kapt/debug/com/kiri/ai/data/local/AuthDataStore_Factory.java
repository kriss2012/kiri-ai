package com.kiri.ai.data.local;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AuthDataStore_Factory implements Factory<AuthDataStore> {
  private final Provider<Context> contextProvider;

  public AuthDataStore_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public AuthDataStore get() {
    return newInstance(contextProvider.get());
  }

  public static AuthDataStore_Factory create(javax.inject.Provider<Context> contextProvider) {
    return new AuthDataStore_Factory(Providers.asDaggerProvider(contextProvider));
  }

  public static AuthDataStore_Factory create(Provider<Context> contextProvider) {
    return new AuthDataStore_Factory(contextProvider);
  }

  public static AuthDataStore newInstance(Context context) {
    return new AuthDataStore(context);
  }
}
