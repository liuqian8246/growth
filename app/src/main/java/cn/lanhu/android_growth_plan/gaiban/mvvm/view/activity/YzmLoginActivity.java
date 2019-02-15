package cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity;

import android.arch.lifecycle.ViewModelProviders;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.databinding.ActivityYzmLoginBinding;
import cn.lanhu.android_growth_plan.entity.LoginEntity;
import cn.lanhu.android_growth_plan.entity.base.BaseBean;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingActivity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.viewmodel.UserViewModel;
import cn.lanhu.android_growth_plan.utils.TextTimerUtils;
import cn.lanhu.android_growth_plan.utils.sysrelated.IntentUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.ToastUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.rxui.RxViewUtils;
import rx.Subscriber;
import rx.Subscription;

public class YzmLoginActivity extends BaseBindingActivity<ActivityYzmLoginBinding> {

    private Subscription mSubscribeDuanxin;
    private UserViewModel mUserViewModel;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_yzm_login;
    }

    @Override
    protected void initView() {
        String phone = getIntent().getStringExtra("phone");
        if(phone != null) {
            mVB.tvOldpassword.setText(phone);
        }
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        RxViewUtils.onViewClick(mVB.btnLogin,() ->{
            if(verify()) {
                mUserViewModel.verifyLogin(mVB.tvOldpassword.getText().toString(),mVB.etYzm.getText().toString()).subscribe(new Subscriber<BaseBean<LoginEntity>>() {
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
                    public void onNext(BaseBean<LoginEntity> loginEntityBaseBean) {
                        if(loginEntityBaseBean.getCode().equals("0")) {
                            if(loginEntityBaseBean.getData().getUser().getInfoStatus().equals("0")) {
                                new IntentUtils.Builder(mContext)
                                        .setTargetActivity(SupplementaryActvity.class)
                                        .setParcelableExtra("loginEntity",loginEntityBaseBean.getData())
                                        .build().startActivity(true);
                            } else {
                                LoginEntity loginEntity = loginEntityBaseBean.getData();
                                mUserViewModel.userLogin
                                        (loginEntity);
                                new IntentUtils.Builder(mContext)
                                        .setTargetActivity(MainActivity.class)
                                        .build().startActivity(true);
                            }
                        } else {
                            ToastUtils.showToast(loginEntityBaseBean.getMsg());
                        }
                    }
                });
            }
        });

        RxViewUtils.onViewClick(mVB.getyzm.tvName,() ->{
            //开始倒计时
            mSubscribeDuanxin = TextTimerUtils.getSubsrription(mVB.getyzm.tvName);
            //结束倒计时
            //TextTimerUtils.unsubscribe(mSubscribeDuanxin, mVB.getyzm.tvName);
        });

        RxViewUtils.onViewClick(mVB.tvPassLogin,() ->{
            new IntentUtils.Builder(mContext)
                    .setTargetActivity(PasswordLoginActivity.class)
                    .setStringExtra("phone",mVB.tvOldpassword.getText().toString())
                    .build().startActivityWithFinishUi(true);
        });
    }

    private boolean verify() {
        if(mVB.tvOldpassword.getText().toString().isEmpty()) {
            ToastUtils.showToast("手机号码不能为空");
            return false;
        } else if(mVB.etYzm.getText().toString().isEmpty()) {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity();
        overridePendingTransition(0,0);
    }
}
