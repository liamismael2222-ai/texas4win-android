# قواعد ProGuard/R8 للأمان والتشفير
# ================================================

# ============ الحفاظ على الفئات الضرورية ============

# WebView Interface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# WebView
-keepclassmembers class android.webkit.WebView {
    public *;
}

# React
-keep class * extends androidx.appcompat.app.AppCompatActivity

# احتفظ باسم التطبيق الرئيسي
-keep class com.texas4win.app.** { *; }
-keep class com.texas4win.app.MainActivity { *; }

# ============ تشفير وإخفاء الأسماء ============

# شفر جميع الفئات الأخرى
-repackageclasses

# إزالة السجلات التي لا ضرورة لها
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# ============ التحسينات ============

# تحسينات متقدمة
-optimizationpasses 5
-dontusemixedcaseclassnames
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*

# ============ الأمان ============

# منع الوصول من الخارج
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

# كتبة مدعومة
-keep class org.** { *; }

# ============ معالجة الأخطاء ============

# احتفظ بمعلومات الاستثناءات للتصحيح
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# ============ حماية إضافية ============

# تعطيل Reflection
-dontoptimize

# إبقاء جميع الفئات العامة والحقول والطرق
-keep public class * {
    public protected *;
}

# ============ تحذيرات ============

# تجاهل التحذيرات غير الضرورية
-dontwarn android.**
-dontwarn androidx.**
-dontwarn org.**