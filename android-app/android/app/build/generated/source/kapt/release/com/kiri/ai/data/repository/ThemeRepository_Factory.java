package com.kiri.ai.data.repository;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
    "cast"
})
public final class ThemeRepository_Factory implements Factory<ThemeRepository> {
  private final Provider<Context> contextProvider;

  public ThemeRepository_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ThemeRepository get() {
    return newInstance(contextProvider.get());
  }

  public static ThemeRepository_Factory create(Provider<Context> contextProvider) {
    return new ThemeRepository_Factory(contextProvider);
  }

  public static ThemeRepository newInstance(Context context) {
    return new ThemeRepository(context);
  }
}
