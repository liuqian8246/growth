package cn.lanhu.android_growth_plan.gaiban.mvvm.view.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by lq on 2018/12/17.
 */

public class ScrollWebView extends WebView {


    public ScrollWebView(Context context) {
        this(context, null);
    }

    public ScrollWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void init() {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }

}
