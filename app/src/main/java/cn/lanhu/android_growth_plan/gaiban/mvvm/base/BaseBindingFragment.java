package cn.lanhu.android_growth_plan.gaiban.mvvm.base;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.trello.rxlifecycle.components.support.RxFragment;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.gaiban.app.config.RxApplication;
import cn.lanhu.android_growth_plan.utils.trilateralrelated.LogUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.MdDialogUtils;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yx on 2017/8/18.
 * 基类 databinding fragment
 */
public abstract class BaseBindingFragment<VB extends ViewDataBinding> extends RxFragment {

    public VB mVB;
    protected Context mContext;
    protected Resources mResources;
    protected RxPermissions mRxPermissions;
    private CompositeSubscription mCompositeSubscription;
    private Dialog waitDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mResources = RxApplication.getInstance().getResources();
        mRxPermissions = new RxPermissions(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mVB = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return mVB.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        destroyView();
        mVB.unbind();
        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    public Dialog showWaitDialog(String message) {

        try {
            if (!getActivity().isFinishing()) {
                if (waitDialog == null) {
                    waitDialog = MdDialogUtils.getWaitDialog(mContext, message);
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

    public void hideWaitDialog() {
        if (waitDialog != null) {
            waitDialog.dismiss();
        }
    }


    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

    protected abstract void destroyView();
}
