package cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.text.TextUtils;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.databinding.ActivityLoginPasswordBinding;
import cn.lanhu.android_growth_plan.entity.LoginEntity;
import cn.lanhu.android_growth_plan.entity.base.BaseBean;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingActivity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.viewmodel.UserViewModel;
import cn.lanhu.android_growth_plan.utils.sysrelated.IntentUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.ToastUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.rxui.RxViewUtils;
import rx.Subscriber;

/**
 * 密码登陆
 */
public class PasswordLoginActivity extends BaseBindingActivity<ActivityLoginPasswordBinding> {

    private UserViewModel mUserViewModel;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login_password;
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
                mUserViewModel.passwordLogin(mVB.tvOldpassword.getText().toString(),mVB.etNewpassword.getText().toString()).subscribe(new Subscriber<BaseBean<LoginEntity>>() {
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

        RxViewUtils.onViewClick(mVB.tvMsgLogin,() ->{
            //验证码登陆
            new IntentUtils.Builder(mContext)
                    .setTargetActivity(YzmLoginActivity.class)
                    .setStringExtra("phone",mVB.tvOldpassword.getText().toString())
                    .build().startActivityWithFinishUi(true);
        });

        RxViewUtils.onViewClick(mVB.tvForgerPassword,() ->{
            //忘记密码
            new IntentUtils.Builder(mContext)
                    .setTargetActivity(ForgetPasswordActivity.class)
                    .setStringExtra("phone",mVB.tvOldpassword.getText().toString())
                    .build().startActivity(true);
        });
    }

    private boolean verify() {
        if(TextUtils.isEmpty(mVB.tvOldpassword.getText().toString())) {
            ToastUtils.showToast("手机号码不能为空");
            return false;
        } else if(TextUtils.isEmpty(mVB.etNewpassword.getText().toString())) {
            ToastUtils.showToast("密码不能为空");
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity();
        overridePendingTransition(0,0);
    }

    @Override
    protected void destroyView() {

    }
}
