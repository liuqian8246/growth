package cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.databinding.ActivityAboutUsBinding;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingActivity;
import cn.lanhu.android_growth_plan.utils.APKVersionCodeUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.rxui.RxViewUtils;

public class AboutUsActivity extends BaseBindingActivity<ActivityAboutUsBinding> {
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView() {
        mVB.titleBar.tvTitle.setText("关于我们");
        mVB.tvBanben.setText("version " + APKVersionCodeUtils.getVerName(mContext));
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
