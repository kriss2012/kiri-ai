package com.kiri.ai.workers;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ChatPollingWorker_AssistedFactory_Impl implements ChatPollingWorker_AssistedFactory {
  private final ChatPollingWorker_Factory delegateFactory;

  ChatPollingWorker_AssistedFactory_Impl(ChatPollingWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public ChatPollingWorker create(Context arg0, WorkerParameters arg1) {
    return delegateFactory.get(arg0, arg1);
  }

  public static Provider<ChatPollingWorker_AssistedFactory> create(
      ChatPollingWorker_Factory delegateFactory) {
    return InstanceFactory.create(new ChatPollingWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<ChatPollingWorker_AssistedFactory> createFactoryProvider(
      ChatPollingWorker_Factory delegateFactory) {
    return InstanceFactory.create(new ChatPollingWorker_AssistedFactory_Impl(delegateFactory));
  }
}
