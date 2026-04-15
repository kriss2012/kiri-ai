package com.kiri.ai.di;

import com.kiri.ai.data.remote.ChatApi;
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
public final class NetworkModule_ProvideChatApiFactory implements Factory<ChatApi> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideChatApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public ChatApi get() {
    return provideChatApi(retrofitProvider.get());
  }

  public static NetworkModule_ProvideChatApiFactory create(Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideChatApiFactory(retrofitProvider);
  }

  public static ChatApi provideChatApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideChatApi(retrofit));
  }
}
