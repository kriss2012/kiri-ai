package com.kiri.ai.workers;

import androidx.hilt.work.WorkerAssistedFactory;
import dagger.assisted.AssistedFactory;

@AssistedFactory
public interface ChatPollingWorker_AssistedFactory extends WorkerAssistedFactory<ChatPollingWorker> {
}
