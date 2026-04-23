# Proguard Rules for Kiri AI - High Stability Configuration

# General Preservation
-keepattributes Signature, *Annotation*, InnerClasses, EnclosingMethod, SourceFile, LineNumberTable
-keep public class * extends android.app.Service
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Kotlin Metadata & Reflection
-keep class kotlin.Metadata { *; }
-keep class kotlin.reflect.jvm.internal.** { *; }
-keep class kotlin.reflect.** { *; }

# Gson: Crucial for java.lang.reflect.ParameterizedType errors
-keepattributes Signature
-keep class com.google.gson.** { *; }
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Retrofit 2
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembernames interface * {
    @retrofit2.http.* <methods>;
}

# OkHttp 3
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

# Data Models - Prevent obfuscation of fields used for serialization
-keep class com.kiriai.kiriorganization.data.models.** { *; }
-keepclassmembers class com.kiriai.kiriorganization.data.models.** {
    <init>(...);
    <fields>;
}

# Hilt / Dagger
-keep class dagger.hilt.android.internal.managers.** { *; }
-keep class * { @dagger.hilt.android.EntryPoint *; }

# Razorpay
-keep class com.razorpay.** {*;}
-dontwarn com.razorpay.**
-keep class proguard.annotation.Keep
-keep class proguard.annotation.KeepClassMembers

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.coroutines.android.HandlerContext {
    private final android.os.Handler handler;
}
