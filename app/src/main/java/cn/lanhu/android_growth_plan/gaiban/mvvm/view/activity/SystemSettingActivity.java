package cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.orhanobut.logger.Logger;

import cn.lanhu.android_growth_plan.BuildConfig;
import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.databinding.ActivitySystemSettingBinding;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingActivity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.model.cache.DataCleanManager;
import cn.lanhu.android_growth_plan.utils.sysrelated.IntentUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.MdDialogUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.ToastUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.rxui.RxViewUtils;

public class SystemSettingActivity extends BaseBindingActivity<ActivitySystemSettingBinding> {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_system_setting;
    }

    @Override
    protected void initView() {
        mVB.titleBar.tvTitle.setText("系统设置");
        mVB.titleBar.ivBianji.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        RxViewUtils.onViewClick(mVB.llQchc, () -> {
            MdDialogUtils.showMesseageDialog(mContext, "提示", "确认缓存吗？", view -> {
                DataCleanManager.clearAllCache();
            });
        });

        RxViewUtils.onViewClick(mVB.titleBar.ivBack, () -> {
            finishActivity();
        });

        RxViewUtils.onViewClick(mVB.llGwpf, () -> {
            try {
                new IntentUtils.Builder(mContext)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID))
                        .setFlag(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .build()
                        .startActivity(false);
            } catch (Exception e) {
                ToastUtils.showToast("请先安装应用市场!");
                Logger.e("评分出错了 :" + e.getMessage());
            }
        });

        RxViewUtils.onViewClick(mVB.llMzsm, () -> {

        });
    }


    @Override
    protected void destroyView() {

    }
}
