# Texas4Win — تطبيق Android

## نبذة عن المشروع
تطبيق Android احترافي لمنصة Texas4Win لإدارة الوكلاء.
مبني بتقنية **WebView** مما يجعله سهل التحديث والتعديل.

---

## متطلبات البناء
- **Android Studio** (أحدث إصدار) — [تحميل](https://developer.android.com/studio)
- **Java JDK 11** أو أعلى
- اتصال بالإنترنت (لتحميل dependencies)

---

## خطوات بناء الـ APK

### 1. فتح المشروع
```
افتح Android Studio → File → Open → اختر مجلد texas4win-android
```

### 2. مزامنة Gradle
انتظر حتى يكتمل التزامن تلقائياً.
أو: **File → Sync Project with Gradle Files**

### 3. بناء APK للتجربة (Debug)
```
Build → Build Bundle(s) / APK(s) → Build APK(s)
```
الملف الناتج: `app/build/outputs/apk/debug/app-debug.apk`

### 4. بناء APK للنشر (Release)
```
Build → Generate Signed Bundle / APK → APK → إنشاء مفتاح جديد
```

---

## كيفية تحديث التطبيق 🔄

### تحديث الواجهة (الأسهل)
فقط **استبدل ملف الـ HTML**:
```
app/src/main/assets/index.html
```
ثم أعد البناء. **لا حاجة لتغيير أي كود Java.**

### تحديث بيانات اللاعبين
عدّل المصفوفة `players` في ملف `index.html`.

### إضافة تبويب جديد
في ملف `index.html`، أضف عنصراً للمصفوفة `tabs` ثم أضف المحتوى في قسم CONTENT.

### تغيير الألوان
غيّر قيم المصفوفة `C` في أعلى ملف `index.html`:
```javascript
const C = {
  bg:"#060810",    // خلفية التطبيق
  gold:"#c9a84c",  // اللون الذهبي
  // ...
};
```

---

## هيكل المشروع
```
texas4win-android/
├── app/
│   ├── src/main/
│   │   ├── assets/
│   │   │   └── index.html          ← ← ← الملف الرئيسي للتعديل
│   │   ├── java/com/texas4win/app/
│   │   │   └── MainActivity.java   ← إعدادات WebView
│   │   ├── res/
│   │   │   ├── layout/activity_main.xml
│   │   │   └── values/themes.xml
│   │   └── AndroidManifest.xml
│   └── build.gradle
└── settings.gradle
```

---

## التوافق مع أنظمة Android
| الإصدار | دعم |
|---------|-----|
| Android 5.0+ (API 21+) | ✅ مدعوم كاملاً |
| Android 6, 7, 8, 9 | ✅ |
| Android 10, 11, 12 | ✅ |
| Android 13, 14, 15 | ✅ |

---

## ميزات التطبيق
- ✅ تحميل محلي (لا يحتاج سيرفر)
- ✅ سحب للتحديث (Swipe to Refresh)
- ✅ زر الرجوع يعمل داخل التطبيق
- ✅ لون شريط الحالة مطابق للتصميم
- ✅ وضع أفقي/عمودي محكوم
- ✅ دعم كامل للغة العربية RTL

---

## ملاحظة للتحديثات المستقبلية
إذا أردت تحديث التطبيق عن بُعد دون الحاجة لإعادة نشر الـ APK:
عدّل `MainActivity.java` السطر:
```java
webView.loadUrl("file:///android_asset/index.html");
// غيّره إلى:
webView.loadUrl("https://your-server.com/app.html");
```
