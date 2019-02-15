package cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.text.TextUtils;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.databinding.ActivityRegisterBinding;
import cn.lanhu.android_growth_plan.entity.base.BaseBean;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingActivity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.viewmodel.UserViewModel;
import cn.lanhu.android_growth_plan.net.RetrofitUtils;
import cn.lanhu.android_growth_plan.utils.TextTimerUtils;
import cn.lanhu.android_growth_plan.utils.filerelated.SpUtils;
import cn.lanhu.android_growth_plan.utils.sysrelated.IntentUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.ToastUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.rxui.RxViewUtils;
import rx.Subscriber;
import rx.Subscription;

public class RegisterActivity extends BaseBindingActivity<ActivityRegisterBinding> {

    private UserViewModel mUserViewModel;

    private Subscription mSubscribeDuanxin;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        RxViewUtils.onViewClick(mVB.btnRegister, () -> {
            if (verify()) {
                mUserViewModel.registerAccount(mVB.tvOldpassword.getText().toString(),mVB.etNewpassword.getText().toString(),mVB.etYzm.getText().toString())
                        .subscribe(new Subscriber<BaseBean<String>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable throwable) {

                            }

                            @Override
                            public void onNext(BaseBean<String> stringBaseBean) {
                                if(stringBaseBean.getCode().equals("0")) {
                                    SpUtils.putBoolean("isRegister", true);
                                    new IntentUtils.Builder(mContext)
                                            .setTargetActivity(SupplementaryActvity.class)
                                            .build().startActivityWithFinishUi(true);
                                } else {
                                    ToastUtils.showToast(stringBaseBean.getMsg());
                                }
                            }
                        });

            }
        });

        RxViewUtils.onViewClick(mVB.getyzm.tvName, () -> {
            if (mVB.tvOldpassword.getText().toString().isEmpty()) {
                ToastUtils.showToast("手机号码不能为空");
                return;
            }
            mUserViewModel.getYzCode(RetrofitUtils.getInstance().getParamsMap("mobile", mVB.etYzm.getText().toString()))
                    .subscribe(new Subscriber<BaseBean<String>>() {

                        @Override
                        public void onStart() {
                            super.onStart();
                            //开始倒计时
                            mSubscribeDuanxin = TextTimerUtils.getSubsrription(mVB.getyzm.tvName);
                        }

                        @Override
                        public void onCompleted() {
                            //结束倒计时
                            TextTimerUtils.unsubscribe(mSubscribeDuanxin, mVB.getyzm.tvName);
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            //结束倒计时
                            TextTimerUtils.unsubscribe(mSubscribeDuanxin, mVB.getyzm.tvName);
                        }

                        @Override
                        public void onNext(BaseBean<String> stringBaseBean) {

                        }
                    });
        });
    }

    private boolean verify() {
        if (mVB.tvOldpassword.getText().toString().isEmpty()) {
            ToastUtils.showToast("手机号码不能为空");
            return false;
        } else if (TextUtils.isEmpty(mVB.etNewpassword.getText().toString())) {
            ToastUtils.showToast("密码不能为空");
            return false;
        } else if (TextUtils.isEmpty(mVB.etYzm.getText().toString())) {
            ToastUtils.showToast("验证码不能为空");
            return false;
        }
        return true;
    }

    @Override
    protected void destroyView() {
        if (mSubscribeDuanxin != null) {
            mSubscribeDuanxin.unsubscribe();
        }
    }
}
