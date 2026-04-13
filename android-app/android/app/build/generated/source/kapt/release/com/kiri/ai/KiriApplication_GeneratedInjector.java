package com.kiri.ai;

import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = KiriApplication.class
)
@GeneratedEntryPoint
@InstallIn(SingletonComponent.class)
public interface KiriApplication_GeneratedInjector {
  void injectKiriApplication(KiriApplication kiriApplication);
}
