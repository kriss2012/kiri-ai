package com.kiri.ai.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\b\u0007\u0018\u0000 \r2\u00020\u0001:\u0001\rB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0006*\u0004\u0018\u00010\u00010\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/kiri/ai/utils/KiriCrashHandler;", "Ljava/lang/Thread$UncaughtExceptionHandler;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "defaultHandler", "kotlin.jvm.PlatformType", "uncaughtException", "", "thread", "Ljava/lang/Thread;", "throwable", "", "Companion", "app_release"})
public final class KiriCrashHandler implements java.lang.Thread.UncaughtExceptionHandler {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    private final java.lang.Thread.UncaughtExceptionHandler defaultHandler = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "kiri_crash_prefs";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_LAST_CRASH = "last_crash_trace";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_CRASH_COUNT = "crash_count";
    @org.jetbrains.annotations.NotNull()
    public static final com.kiri.ai.utils.KiriCrashHandler.Companion Companion = null;
    
    public KiriCrashHandler(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @java.lang.Override()
    public void uncaughtException(@org.jetbrains.annotations.NotNull()
    java.lang.Thread thread, @org.jetbrains.annotations.NotNull()
    java.lang.Throwable throwable) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/kiri/ai/utils/KiriCrashHandler$Companion;", "", "()V", "KEY_CRASH_COUNT", "", "KEY_LAST_CRASH", "PREFS_NAME", "getAndClearLastCrash", "context", "Landroid/content/Context;", "initialize", "", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final void initialize(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getAndClearLastCrash(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}