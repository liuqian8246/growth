package cn.lanhu.android_growth_plan.utils;

import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import cn.lanhu.android_growth_plan.utils.uirelated.rxui.RxViewUtils;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lq on 2017/9/4.
 */

public class TextTimerUtils {

    public static Subscription getSubsrription(TextView textView) {
        return Observable.interval(1, TimeUnit.SECONDS)
                .take(60)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnNext(aLong -> RxView.enabled(textView).call(false))
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        RxTextView.text(textView).call("60秒后重试");
                        textView.setSelected(true);
                    }

                    @Override
                    public void onCompleted() {
                        RxTextView.text(textView).call("重新获取验证码");
                        RxView.enabled(textView).call(true);
                        textView.setSelected(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        RxTextView.text(textView).call((59 - aLong) + "秒后重试");
                    }
                });
    }

    public static void unsubscribe(Subscription subscription,TextView textView) {
        if (subscription != null) {
            subscription.unsubscribe();
            RxViewUtils.setTvText(textView, "重新获取验证码");
            RxViewUtils.setViewEnable(textView, true);
            textView.setSelected(false);
        }
    }
}
