package cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.databinding.ActivityWriteInfoBinding;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingActivity;
import cn.lanhu.android_growth_plan.utils.sysrelated.IntentUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.ToastUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.rxui.RxViewUtils;

/**
 * Created by lq on 2019/2/16.
 */

public class WriteInfoActivity extends BaseBindingActivity<ActivityWriteInfoBinding> {

    private String title;
    private String flag;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_write_info;
    }

    @Override
    protected void initView() {
        title = getIntent().getStringExtra("title");
        flag = getIntent().getStringExtra("flag");
    }

    @Override
    protected void initData() {
        mVB.titleBar.tvTitle.setText(title);
        mVB.titleBar.ivBianji.setVisibility(View.GONE);
        mVB.tvNicheng.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mVB.tvNick.setText(mVB.tvNicheng.getText().toString());
            }
        });
    }

    @Override
    protected void initEvent() {
        RxViewUtils.onViewClick(mVB.btnSeemore,() ->{
            if(mVB.tvNicheng.getText().toString().isEmpty()) {
                if(flag.equals("1")) {
                    ToastUtils.showToast("您的信息不能为空");
                } else if(flag.equals("2")) {
                    ToastUtils.showToast("孩子昵称不能为空");
                } else if(flag.equals("3")) {
                    ToastUtils.showToast("孩子的梦想不能为空");
                }
            } else {
                new IntentUtils.Builder(mContext)
                        .setStringExtra("content",mVB.tvNicheng.getText().toString())
                        .build()
                        .setResultOkWithFinishUi(Integer.valueOf(flag));
            }
        });
    }

    @Override
    protected void destroyView() {

    }
}
