# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /usr/local/Cellar/android-sdk/24.3.3/tools/proguard/proguard-android.txt

# Keep data classes used with Room and Supabase
-keep class com.glassheart.fragilefocus.data.** { *; }
-keepclassmembers class com.glassheart.fragilefocus.data.** { *; }

# Supabase
-keep class io.github.jan.supabase.** { *; }
