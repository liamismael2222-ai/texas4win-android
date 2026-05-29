# قواعد ProGuard لـ Texas4Win
# الحفاظ على WebView JavaScript interface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# الحفاظ على WebView classes
-keepclassmembers class android.webkit.WebView {
    public *;
}

# عدم تشويش اسم التطبيق في السجلات
-keep class com.texas4win.app.** { *; }
