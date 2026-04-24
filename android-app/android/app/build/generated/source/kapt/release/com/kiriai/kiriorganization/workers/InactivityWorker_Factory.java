package com.kiriai.kiriorganization.workers;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
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
public final class InactivityWorker_Factory {
  public InactivityWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams);
  }

  public static InactivityWorker_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static InactivityWorker newInstance(Context appContext, WorkerParameters workerParams) {
    return new InactivityWorker(appContext, workerParams);
  }

  private static final class InstanceHolder {
    static final InactivityWorker_Factory INSTANCE = new InactivityWorker_Factory();
  }
}
