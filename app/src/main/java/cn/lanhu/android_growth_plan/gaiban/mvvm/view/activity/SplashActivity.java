package cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity;

import android.content.Intent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingActivity;
import cn.lanhu.android_growth_plan.utils.filerelated.SpUtils;
import cn.lanhu.android_growth_plan.utils.sysrelated.IntentUtils;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class SplashActivity extends BaseBindingActivity {

    private Subscription mSubUi;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mSubUi = Observable.interval(1, TimeUnit.SECONDS)
                .take(4)
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onCompleted() {
                        Logger.w("onCompleted");
                        openHomeUi();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.w("onError --->" + e.getMessage());
                        openHomeUi();
                    }

                    @Override
                    public void onNext(Long aLong) {

                    }
                });
    }

    private void openHomeUi() {
        mSubUi.unsubscribe();
        if(SpUtils.getBoolean("isRegister")) {
            new IntentUtils.Builder(mContext)
                    .setTargetActivity(MainActivity.class)
                    .build().startActivityWithFinishUi(true);
        } else {
            new IntentUtils.Builder(mContext)
                    .setTargetActivity(MainActivity.class)
                    .build().startActivityWithFinishUi(true);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void destroyView() {
        if(mSubUi != null) {
            mSubUi.unsubscribe();
        }
    }
}
