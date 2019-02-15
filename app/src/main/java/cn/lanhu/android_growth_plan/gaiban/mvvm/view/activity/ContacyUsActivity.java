package cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.databinding.ActivityContacyUsBinding;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingActivity;
import cn.lanhu.android_growth_plan.utils.uirelated.rxui.RxViewUtils;

public class ContacyUsActivity extends BaseBindingActivity<ActivityContacyUsBinding> {
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_contacy_us;
    }

    @Override
    protected void initView() {
        mVB.titleBar.tvTitle.setText("联系我们");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        RxViewUtils.onViewClick(mVB.llWeixin,() ->{
            //微信点击
        });

        RxViewUtils.onViewClick(mVB.llGongzhonghao,() ->{
            //公众号点击
        });

        RxViewUtils.onViewClick(mVB.titleBar.ivBack,() ->{
            finishActivity();
        });
    }

    @Override
    protected void destroyView() {

    }
}
