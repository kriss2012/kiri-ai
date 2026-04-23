package com.kiriai.kiriorganization.workers;

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
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class InactivityWorker_AssistedFactory_Impl implements InactivityWorker_AssistedFactory {
  private final InactivityWorker_Factory delegateFactory;

  InactivityWorker_AssistedFactory_Impl(InactivityWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public InactivityWorker create(Context arg0, WorkerParameters arg1) {
    return delegateFactory.get(arg0, arg1);
  }

  public static Provider<InactivityWorker_AssistedFactory> create(
      InactivityWorker_Factory delegateFactory) {
    return InstanceFactory.create(new InactivityWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<InactivityWorker_AssistedFactory> createFactoryProvider(
      InactivityWorker_Factory delegateFactory) {
    return InstanceFactory.create(new InactivityWorker_AssistedFactory_Impl(delegateFactory));
  }
}
