package com.kiri.ai.workers;

import androidx.hilt.work.WorkerAssistedFactory;
import androidx.work.ListenableWorker;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import javax.annotation.processing.Generated;

@Generated("androidx.hilt.AndroidXHiltProcessor")
@Module
@InstallIn(SingletonComponent.class)
@OriginatingElement(
    topLevelClass = ChatPollingWorker.class
)
public interface ChatPollingWorker_HiltModule {
  @Binds
  @IntoMap
  @StringKey("com.kiri.ai.workers.ChatPollingWorker")
  WorkerAssistedFactory<? extends ListenableWorker> bind(ChatPollingWorker_AssistedFactory factory);
}
