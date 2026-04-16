package com.kiri.ai.data.repository;

import android.content.Context;
import com.kiri.ai.data.remote.ChatApi;
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
public final class ChatRepository_Factory implements Factory<ChatRepository> {
  private final Provider<ChatApi> chatApiProvider;

  private final Provider<Context> contextProvider;

  public ChatRepository_Factory(Provider<ChatApi> chatApiProvider,
      Provider<Context> contextProvider) {
    this.chatApiProvider = chatApiProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public ChatRepository get() {
    return newInstance(chatApiProvider.get(), contextProvider.get());
  }

  public static ChatRepository_Factory create(javax.inject.Provider<ChatApi> chatApiProvider,
      javax.inject.Provider<Context> contextProvider) {
    return new ChatRepository_Factory(Providers.asDaggerProvider(chatApiProvider), Providers.asDaggerProvider(contextProvider));
  }

  public static ChatRepository_Factory create(Provider<ChatApi> chatApiProvider,
      Provider<Context> contextProvider) {
    return new ChatRepository_Factory(chatApiProvider, contextProvider);
  }

  public static ChatRepository newInstance(ChatApi chatApi, Context context) {
    return new ChatRepository(chatApi, context);
  }
}
