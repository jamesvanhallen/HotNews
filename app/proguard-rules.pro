# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/james/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
# retrofit2
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions

# gson
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

# support library
-keep class android.support.v4.** { *; }
-dontwarn android.support.v4.**

-keep class android.support.v7.** { *; }
-dontwarn android.support.v7.**

-keep class android.support.design.** { *; }
-dontwarn android.support.design.**

# okhttp
-dontwarn okhttp3.**
-dontwarn okio.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# picasso
-dontwarn com.squareup.okhttp.**


