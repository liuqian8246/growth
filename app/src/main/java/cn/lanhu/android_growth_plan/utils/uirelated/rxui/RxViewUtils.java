package cn.lanhu.android_growth_plan.utils.uirelated.rxui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxAdapterView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.concurrent.TimeUnit;

import cn.lanhu.android_growth_plan.gaiban.app.config.Constants;
import cn.lanhu.android_growth_plan.gaiban.app.config.RxApplication;
import cn.lanhu.android_growth_plan.utils.sysrelated.NetWorkStateUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.ToastUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yx on 2017/7/25.
 * rxbinding工具类
 */

public class RxViewUtils {

    private RxViewUtils() {
    }

    /**
     * rxbind控件点击方法
     *
     * @param view          点击的控件
     * @param second        设置抖动秒数
     * @param clicklistener 点击回调
     */
    public static void onViewClick(View view, int second, ViewClicklistener clicklistener) {
        RxView.clicks(view).throttleFirst(second, TimeUnit.SECONDS)
                .filter(aVoid -> {
                    boolean hasNetWork = NetWorkStateUtils.isNetworkConnected();
                    if (!hasNetWork) {
                        ToastUtils.showToast(Constants.NO_NETWORK);
                    }
                    return hasNetWork;
                })
                .subscribeOn(Schedulers.immediate()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> clicklistener.onViewClick());
    }

    public static Observable<Void> onViewClick(View view, int second) {
        return RxView.clicks(view).throttleFirst(second, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.immediate()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * rxbind控件点击方法
     *
     * @param view          点击的控件
     * @param clicklistener 点击回调
     */
    public static void onViewClick(View view, OnViewClick clicklistener) {
        RxView.clicks(view)
                .subscribeOn(Schedulers.immediate()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> clicklistener.onClick(view));
    }


    /**
     * 默认间隔500毫秒点击
     *
     * @param view
     * @param clicklistener
     */
    public static void onViewClick(View view, ViewClicklistener clicklistener) {
        RxView.clicks(view).throttleFirst(500, TimeUnit.MILLISECONDS)
                .filter(aVoid -> {
                    boolean hasNetWork = NetWorkStateUtils.isNetworkConnected();
                    if (!hasNetWork) {
                        ToastUtils.showToast(Constants.NO_NETWORK);
                    }
                    return hasNetWork;
                })
                .subscribeOn(Schedulers.immediate()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    if (!(view instanceof EditText)) {
                        Context context = RxApplication.getInstance().getTopActivity();
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm.isActive() && ((Activity) context).getCurrentFocus() != null) {
                            if (((Activity) context).getCurrentFocus().getWindowToken() != null) {
                                imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            }
                        }
                    }
                    clicklistener.onViewClick();
                });
    }

    public static void onViewClickNeedPermission(View view, OnViewClickWitnPermission onViewClick, String... permissions) {
        RxView.clicks(view)
                .compose(new RxPermissions((Activity) view.getContext())
                        .ensureEach(permissions))
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.immediate()).observeOn(AndroidSchedulers.mainThread())
                .filter(aVoid -> {
                    boolean hasNetWork = NetWorkStateUtils.isNetworkConnected();
                    if (!hasNetWork) {
                        ToastUtils.showToast(Constants.NO_NETWORK);
                    }
                    return hasNetWork;
                })
                .subscribe(onViewClick::onPermission);

    }

    /**
     * 设置textview text
     *
     * @param textView
     * @param text
     */
    public static void setTvText(TextView textView, CharSequence text) {
        RxTextView.text(textView).call(text);
    }

    /**
     * 设置textview hint
     *
     * @param textView
     * @param text
     */
    public static void setTvHint(TextView textView, CharSequence text) {
        RxTextView.hint(textView).call(text);
    }

    /**
     * 设置textview color
     *
     * @param textView
     * @param color
     */
    public static void setTvColor(TextView textView, int color) {
        RxTextView.color(textView).call(color);
    }

    /**
     * 设置控件是否可用
     *
     * @param view
     * @param enable
     */
    public static void setViewEnable(View view, boolean enable) {
        RxView.enabled(view).call(enable);
    }

    /**
     * textview text状态改变观察者
     *
     * @param textView
     * @param second
     */
    public static void onTvTextChanges(TextView textView, int second, TextviewTextChangeListener changeListener) {
        RxTextView.textChanges(textView).delay(second, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.immediate()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(charSequence ->
                        changeListener.onTextChange(charSequence.toString()));
    }

    /**
     * 适配器条目点击
     *
     * @param view
     */
    public static void onAdapterItemClick(AdapterView<? extends Adapter> view, int second,
                                          OnAdapterItemClickListener itemClickListener) {
        RxAdapterView.itemClicks(view).throttleFirst(second, TimeUnit.SECONDS)
                .filter(aVoid -> {
                    boolean hasNetWork = NetWorkStateUtils.isNetworkConnected();
                    if (!hasNetWork) {
                        ToastUtils.showToast(Constants.NO_NETWORK);
                    }
                    return hasNetWork;
                })
                .subscribeOn(Schedulers.immediate()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(itemClickListener::onItemClick);
    }

}
