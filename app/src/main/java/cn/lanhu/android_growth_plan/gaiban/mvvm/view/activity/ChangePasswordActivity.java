package cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.databinding.ActivityChangePasswordBinding;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingActivity;
import cn.lanhu.android_growth_plan.utils.uirelated.rxui.RxViewUtils;

public class ChangePasswordActivity extends BaseBindingActivity<ActivityChangePasswordBinding> {
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initView() {
        mVB.titleBar.tvTitle.setText("修改密码");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        RxViewUtils.onViewClick(mVB.titleBar.ivBack,() ->{
            finishActivity();
        });
    }

    @Override
    protected void destroyView() {

    }
}
