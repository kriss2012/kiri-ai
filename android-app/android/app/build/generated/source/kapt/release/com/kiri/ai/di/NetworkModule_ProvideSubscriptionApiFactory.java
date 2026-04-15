package com.kiri.ai.di;

import com.kiri.ai.data.remote.SubscriptionApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
    "cast"
})
public final class NetworkModule_ProvideSubscriptionApiFactory implements Factory<SubscriptionApi> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideSubscriptionApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public SubscriptionApi get() {
    return provideSubscriptionApi(retrofitProvider.get());
  }

  public static NetworkModule_ProvideSubscriptionApiFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideSubscriptionApiFactory(retrofitProvider);
  }

  public static SubscriptionApi provideSubscriptionApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideSubscriptionApi(retrofit));
  }
}
