# Add any ProGuard configurations here
-keep public class * extends java.lang.Exception

# Retrofit
-keepattributes Signature,InnerClasses,EnclosingMethod,RuntimeVisibleAnnotations,RuntimeVisibleParameterAnnotations
-keepattributes *Annotation*
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keep class retrofit2.Response { *; }
-keep class okhttp3.ResponseBody { *; }

# API Service
-keep class com.etice.lessons.data.api.ETiceApiService { *; }

# Kotlin
-keep class kotlin.Metadata { *; }
-keep class kotlin.reflect.** { *; }
-keep class kotlinx.coroutines.** { *; }

# Gson
-keep class com.google.gson.** { *; }
-keep class com.google.inject.** { *; }
-keep class org.apache.** { *; }
-keep class com.google.android.gms.** { *; }
-keep class com.google.firebase.** { *; }

# Data Models (Specific for this app)
-keep class com.etice.lessons.data.models.** { *; }

# OkHttp
-keepattributes EnclosingMethod
-keepattributes InnerClasses
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**

