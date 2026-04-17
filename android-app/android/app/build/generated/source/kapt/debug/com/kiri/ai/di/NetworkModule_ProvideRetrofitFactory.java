package com.kiri.ai.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;
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
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class NetworkModule_ProvideRetrofitFactory implements Factory<Retrofit> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  public NetworkModule_ProvideRetrofitFactory(Provider<OkHttpClient> okHttpClientProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public Retrofit get() {
    return provideRetrofit(okHttpClientProvider.get());
  }

  public static NetworkModule_ProvideRetrofitFactory create(
      javax.inject.Provider<OkHttpClient> okHttpClientProvider) {
    return new NetworkModule_ProvideRetrofitFactory(Providers.asDaggerProvider(okHttpClientProvider));
  }

  public static NetworkModule_ProvideRetrofitFactory create(
      Provider<OkHttpClient> okHttpClientProvider) {
    return new NetworkModule_ProvideRetrofitFactory(okHttpClientProvider);
  }

  public static Retrofit provideRetrofit(OkHttpClient okHttpClient) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideRetrofit(okHttpClient));
  }
}
