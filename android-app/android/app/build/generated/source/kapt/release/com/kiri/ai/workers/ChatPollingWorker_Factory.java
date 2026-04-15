package com.kiri.ai.workers;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.kiri.ai.data.repository.ChatRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
    "cast"
})
public final class ChatPollingWorker_Factory {
  private final Provider<ChatRepository> chatRepositoryProvider;

  public ChatPollingWorker_Factory(Provider<ChatRepository> chatRepositoryProvider) {
    this.chatRepositoryProvider = chatRepositoryProvider;
  }

  public ChatPollingWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, chatRepositoryProvider.get());
  }

  public static ChatPollingWorker_Factory create(Provider<ChatRepository> chatRepositoryProvider) {
    return new ChatPollingWorker_Factory(chatRepositoryProvider);
  }

  public static ChatPollingWorker newInstance(Context appContext, WorkerParameters workerParams,
      ChatRepository chatRepository) {
    return new ChatPollingWorker(appContext, workerParams, chatRepository);
  }
}
