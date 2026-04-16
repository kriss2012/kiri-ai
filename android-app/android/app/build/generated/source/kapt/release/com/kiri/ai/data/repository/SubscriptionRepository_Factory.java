package com.kiri.ai.data.repository;

import com.kiri.ai.data.remote.SubscriptionApi;
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
public final class SubscriptionRepository_Factory implements Factory<SubscriptionRepository> {
  private final Provider<SubscriptionApi> subscriptionApiProvider;

  public SubscriptionRepository_Factory(Provider<SubscriptionApi> subscriptionApiProvider) {
    this.subscriptionApiProvider = subscriptionApiProvider;
  }

  @Override
  public SubscriptionRepository get() {
    return newInstance(subscriptionApiProvider.get());
  }

  public static SubscriptionRepository_Factory create(
      javax.inject.Provider<SubscriptionApi> subscriptionApiProvider) {
    return new SubscriptionRepository_Factory(Providers.asDaggerProvider(subscriptionApiProvider));
  }

  public static SubscriptionRepository_Factory create(
      Provider<SubscriptionApi> subscriptionApiProvider) {
    return new SubscriptionRepository_Factory(subscriptionApiProvider);
  }

  public static SubscriptionRepository newInstance(SubscriptionApi subscriptionApi) {
    return new SubscriptionRepository(subscriptionApi);
  }
}
