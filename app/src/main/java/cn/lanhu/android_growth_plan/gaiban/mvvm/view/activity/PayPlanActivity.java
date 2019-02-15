package cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.databinding.ActivityPayPlanBinding;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingActivity;
import cn.lanhu.android_growth_plan.utils.uirelated.rxui.RxViewUtils;

public class PayPlanActivity extends BaseBindingActivity<ActivityPayPlanBinding> {
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_pay_plan;
    }

    @Override
    protected void initView() {
        mVB.titleBar.tvTitle.setText("定制专属成长计划");
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
