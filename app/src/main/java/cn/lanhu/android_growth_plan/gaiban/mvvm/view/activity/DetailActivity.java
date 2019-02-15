package cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity;

import android.text.Html;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.databinding.ActivityDetailBinding;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingActivity;

public class DetailActivity extends BaseBindingActivity<ActivityDetailBinding> {
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initView() {
        mVB.tvContent.loadData("<p>这是注册登录的UI页面图</p><br/><img src=\"http://60.205.230.170/content/test.png\" /><br/><p>请过目</p>","text/html; charset=UTF-8", null);
//        Html.fromHtml("<p>这是注册登录的UI页面图</p><br/><img src=\"http://60.205.230.170/content/test.png\" /><br/><p>请过目</p>");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void destroyView() {

    }
}
