package cn.lanhu.android_growth_plan.gaiban.mvvm.base;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;

import com.jaeger.library.StatusBarUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.gaiban.app.config.RxApplication;
import cn.lanhu.android_growth_plan.gaiban.mvvm.model.utils.StatusBarUtils;
import cn.lanhu.android_growth_plan.utils.trilateralrelated.LogUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.MdDialogUtils;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yx on 2017/4/19.
 * 基类 databinding activity
 */
public abstract class BaseBindingActivity<VB extends ViewDataBinding> extends RxAppCompatActivity {

    public VB mVB;
    protected Resources mResources;
    private Dialog waitDialog;
    private CompositeSubscription mCompositeSubscription;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVB = DataBindingUtil.setContentView(this, getLayoutRes());
        mContext = this;
        mResources = RxApplication.getInstance().getmResources();
        setStatusBar();
        initView();
        initData();
        initEvent();
    }

    @UiThread
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorWhite), 0);
            StatusBarUtils.StatusBarLightMode(this);
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorWhite), 68);
        }
    }



    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_enter2, R.anim.anim_exit2);
    }

    public Dialog showWaitDialog() {
        return showWaitDialog(R.string.loading);
    }

    public Dialog showWaitDialog(int resid) {
        return showWaitDialog(getString(resid));
    }

    public Dialog showWaitDialog(String message) {
        try {
            if (!this.isFinishing()) {
                if (waitDialog == null) {
                    waitDialog = MdDialogUtils.getWaitDialog(this, message);
                    waitDialog.setCancelable(false);
                }
                if (waitDialog != null) {
                    AppCompatTextView tvMsg = waitDialog.findViewById(R.id.tv_loading);
                    tvMsg.setText(message);
                    waitDialog.show();
                }
                return waitDialog;
            }
        } catch (Exception e) {
            LogUtils.w("e :" + e.getMessage());
        }


        return null;
    }

    public Dialog showWaitDialog(String message, boolean isCancel) {
        if (!this.isFinishing()) {
            if (waitDialog == null) {
                waitDialog = MdDialogUtils.getWaitDialog(this, message, isCancel);
                waitDialog.setCancelable(false);
            }
            if (waitDialog != null) {
                AppCompatTextView tvMsg = waitDialog.findViewById(R.id.tv_loading);
                tvMsg.setText(message);
                waitDialog.show();
            }
            return waitDialog;
        }

        return null;
    }

    public void hideWaitDialog() {
        try {
            if (waitDialog != null) {
                waitDialog.dismiss();
                waitDialog = null;
            }
        } catch (Exception e) {
            waitDialog = null;
            LogUtils.w("waitDialog dismiss error : " + e.getMessage());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVB.unbind();
        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
        }
        destroyView();
    }

    public void finishActivity() {
        finish();
        overridePendingTransition(R.anim.anim_enter2, R.anim.anim_exit2);
    }

    public void finishActivityWithBottom() {
        finish();
        overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_out_top);
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

    protected abstract void destroyView();
}
