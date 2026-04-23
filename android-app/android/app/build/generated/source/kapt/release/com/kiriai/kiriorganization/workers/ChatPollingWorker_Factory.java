package com.kiriai.kiriorganization.workers;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.kiriai.kiriorganization.data.local.NotificationPrefs;
import com.kiriai.kiriorganization.data.repository.ChatRepository;
import dagger.internal.DaggerGenerated;
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
public final class ChatPollingWorker_Factory {
  private final Provider<ChatRepository> chatRepositoryProvider;

  private final Provider<NotificationPrefs> notificationPrefsProvider;

  public ChatPollingWorker_Factory(Provider<ChatRepository> chatRepositoryProvider,
      Provider<NotificationPrefs> notificationPrefsProvider) {
    this.chatRepositoryProvider = chatRepositoryProvider;
    this.notificationPrefsProvider = notificationPrefsProvider;
  }

  public ChatPollingWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, chatRepositoryProvider.get(), notificationPrefsProvider.get());
  }

  public static ChatPollingWorker_Factory create(
      javax.inject.Provider<ChatRepository> chatRepositoryProvider,
      javax.inject.Provider<NotificationPrefs> notificationPrefsProvider) {
    return new ChatPollingWorker_Factory(Providers.asDaggerProvider(chatRepositoryProvider), Providers.asDaggerProvider(notificationPrefsProvider));
  }

  public static ChatPollingWorker_Factory create(Provider<ChatRepository> chatRepositoryProvider,
      Provider<NotificationPrefs> notificationPrefsProvider) {
    return new ChatPollingWorker_Factory(chatRepositoryProvider, notificationPrefsProvider);
  }

  public static ChatPollingWorker newInstance(Context appContext, WorkerParameters workerParams,
      ChatRepository chatRepository, NotificationPrefs notificationPrefs) {
    return new ChatPollingWorker(appContext, workerParams, chatRepository, notificationPrefs);
  }
}
