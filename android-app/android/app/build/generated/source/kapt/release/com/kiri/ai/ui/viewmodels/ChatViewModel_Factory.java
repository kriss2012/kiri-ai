package com.kiri.ai.ui.viewmodels;

import android.app.Application;
import androidx.lifecycle.SavedStateHandle;
import com.kiri.ai.data.repository.AuthRepository;
import com.kiri.ai.data.repository.ChatRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class ChatViewModel_Factory implements Factory<ChatViewModel> {
  private final Provider<Application> applicationProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<ChatRepository> chatRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public ChatViewModel_Factory(Provider<Application> applicationProvider,
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<ChatRepository> chatRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.applicationProvider = applicationProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.chatRepositoryProvider = chatRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public ChatViewModel get() {
    return newInstance(applicationProvider.get(), savedStateHandleProvider.get(), chatRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static ChatViewModel_Factory create(javax.inject.Provider<Application> applicationProvider,
      javax.inject.Provider<SavedStateHandle> savedStateHandleProvider,
      javax.inject.Provider<ChatRepository> chatRepositoryProvider,
      javax.inject.Provider<AuthRepository> authRepositoryProvider) {
    return new ChatViewModel_Factory(Providers.asDaggerProvider(applicationProvider), Providers.asDaggerProvider(savedStateHandleProvider), Providers.asDaggerProvider(chatRepositoryProvider), Providers.asDaggerProvider(authRepositoryProvider));
  }

  public static ChatViewModel_Factory create(Provider<Application> applicationProvider,
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<ChatRepository> chatRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new ChatViewModel_Factory(applicationProvider, savedStateHandleProvider, chatRepositoryProvider, authRepositoryProvider);
  }

  public static ChatViewModel newInstance(Application application,
      SavedStateHandle savedStateHandle, ChatRepository chatRepository,
      AuthRepository authRepository) {
    return new ChatViewModel(application, savedStateHandle, chatRepository, authRepository);
  }
}
