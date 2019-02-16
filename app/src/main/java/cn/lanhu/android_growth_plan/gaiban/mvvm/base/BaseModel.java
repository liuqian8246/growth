package cn.lanhu.android_growth_plan.gaiban.mvvm.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;

import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.gaiban.app.config.RxApplication;
import cn.lanhu.android_growth_plan.utils.uirelated.MdDialogUtils;

/**
 * Created by yx on 2017/8/20.
 * 基类model
 */

public class BaseModel {

    private WeakReference<Activity> mActivityWeakReference;

    public BaseModel(){

    }

    //获取app上下文
    protected Context getAppContext() {
        return RxApplication.getInstance().getmContext();
    }

    //获取当前activity
    protected Activity getCurrentActivity() {
        return RxApplication.getInstance().getTopActivity();
    }

    //获取资源对象
    protected Resources getResources() {
        return RxApplication.getInstance().getmResources();
    }

    //获取等待加载框
    private Dialog waitDialog;

    @MainThread
    protected Dialog showWaitDialog() {
        return showWaitDialog(R.string.loading);
    }

    @MainThread
    protected Dialog showWaitDialog(@StringRes int resid) {
        String str = "";
        if(getCurrentActivity() != null) {
            str = getCurrentActivity().getString(resid);
        }
        return showWaitDialog(str);
    }

    @MainThread
    protected Dialog showWaitDialog(@NonNull String message) {
        return showWaitDialog(message, true);
    }

    @UiThread
    public Dialog showWaitDialog(@NonNull String message, boolean canClickBack) {
        if (getCurrentActivity()!= null && !getCurrentActivity().isFinishing()) {
//                if (waitDialog == null) {
//                }
            waitDialog = MdDialogUtils.getWaitDialog(getCurrentActivity(), message);
            if (waitDialog != null) {
                AppCompatTextView tvMsg = waitDialog.findViewById(R.id.tv_loading);
                tvMsg.setText(message);
                waitDialog.show();
            }
            return waitDialog;

        }

        return null;
    }

    @MainThread
    protected void hideWaitDialog() {
        if (waitDialog != null && waitDialog.isShowing()) {
            try {
                waitDialog.dismiss();
                waitDialog = null;
            } catch (Exception ex) {
                Logger.w("极其个别的机型,会报错-_-");
            }
        }
    }
}
