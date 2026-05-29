package com.texas4win.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ضبط لون شريط الحالة ليطابق تصميم التطبيق
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0xFF060810);
        }

        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        setupWebView();
        setupSwipeRefresh();

        // تحميل التطبيق من ملف الأصول المحلي
        webView.loadUrl("file:///android_asset/index.html");
    }

    private void setupWebView() {
        WebSettings settings = webView.getSettings();

        // تفعيل JavaScript ضروري لـ React
        settings.setJavaScriptEnabled(true);

        // تخزين DOM لحفظ البيانات محلياً
        settings.setDomStorageEnabled(true);

        // ضبط العرض
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        // تعطيل التكبير/التصغير (تصميم محسّن للموبايل)
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(false);

        // السماح بتحميل المحتوى المختلط (HTTP داخل HTTPS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // تفعيل الوصول للملفات المحلية
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);

        // تحسين الأداء
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(false);
        }

        // معالج تحميل الصفحات
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // فتح الروابط الخارجية في المتصفح
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    if (!url.contains("file:///android_asset")) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                            return true;
                        } catch (Exception e) {
                            return false;
                        }
                    }
                }
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    String url = request.getUrl().toString();
                    return shouldOverrideUrlLoading(view, url);
                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // دعم console.log وميزات المتصفح الأخرى
        webView.setWebChromeClient(new WebChromeClient());
    }

    private void setupSwipeRefresh() {
        // ألوان تتوافق مع تصميم التطبيق الذهبي
        swipeRefreshLayout.setColorSchemeColors(0xFFC9A84C);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(0xFF0D1120);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            webView.reload();
        });
    }

    // معالجة زر الرجوع - يرجع داخل التطبيق أولاً
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
}
