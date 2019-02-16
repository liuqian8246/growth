package cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.text.TextUtils;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.databinding.ActivityForgetPasswordBinding;
import cn.lanhu.android_growth_plan.entity.base.BaseBean;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingActivity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.viewmodel.UserViewModel;
import cn.lanhu.android_growth_plan.utils.TextTimerUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.ToastUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.rxui.RxViewUtils;
import rx.Subscriber;
import rx.Subscription;

public class ForgetPasswordActivity extends BaseBindingActivity<ActivityForgetPasswordBinding> {

    private Subscription mSubscribeDuanxin;
    private UserViewModel mUserViewModel;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initView() {
        String phone = getIntent().getStringExtra("phone");
        if (phone != null) {
            mVB.tvOldpassword.setText(phone);
        }
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        RxViewUtils.onViewClick(mVB.btnRegister, () -> {
            if (verify()) {
                mUserViewModel.forgetPassword(mVB.tvOldpassword.getText().toString(), mVB.etYzm.getText().toString(),mVB.etNewpassword.getText().toString()).subscribe(new Subscriber<BaseBean<String>>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        showWaitDialog();
                    }

                    @Override
                    public void onCompleted() {
                        hideWaitDialog();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        hideWaitDialog();
                    }

                    @Override
                    public void onNext(BaseBean<String> loginEntityBaseBean) {
                        if (loginEntityBaseBean.getCode().equals("0")) {
                            finishActivity();
                        } else {
                            ToastUtils.showToast(loginEntityBaseBean.getMsg());
                        }
                    }
                });

            }
        });

        RxViewUtils.onViewClick(mVB.getyzm.tvName, () -> {

            //开始倒计时
            mSubscribeDuanxin = TextTimerUtils.getSubsrription(mVB.getyzm.tvName);
            //结束倒计时
            //TextTimerUtils.unsubscribe(mSubscribeDuanxin, mVB.getyzm.tvName);
            finishActivity();

        });
    }

    private boolean verify() {
        if (TextUtils.isEmpty(mVB.tvOldpassword.getText().toString())) {
            ToastUtils.showToast("手机号码不能为空");
            return false;
        } else if (TextUtils.isEmpty(mVB.etYzm.getText().toString())) {
            ToastUtils.showToast("验证码不能为空");
            return false;
        } else if (TextUtils.isEmpty(mVB.etNewpassword.getText().toString())) {
            ToastUtils.showToast("密码不能为空");
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
