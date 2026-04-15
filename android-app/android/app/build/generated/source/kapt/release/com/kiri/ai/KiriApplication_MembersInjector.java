package com.kiri.ai;

import androidx.hilt.work.HiltWorkerFactory;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;

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
public final class KiriApplication_MembersInjector implements MembersInjector<KiriApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public KiriApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<KiriApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new KiriApplication_MembersInjector(workerFactoryProvider);
  }

  public static MembersInjector<KiriApplication> create(
      javax.inject.Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new KiriApplication_MembersInjector(Providers.asDaggerProvider(workerFactoryProvider));
  }

  @Override
  public void injectMembers(KiriApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.kiri.ai.KiriApplication.workerFactory")
  public static void injectWorkerFactory(KiriApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
