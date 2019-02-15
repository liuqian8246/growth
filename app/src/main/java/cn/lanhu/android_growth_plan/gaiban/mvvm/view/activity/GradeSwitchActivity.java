package cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import java.util.ArrayList;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.adapter.absrecyclerview.CommonAdapter;
import cn.lanhu.android_growth_plan.adapter.absrecyclerview.base.ViewHolder;
import cn.lanhu.android_growth_plan.databinding.ActivityGradeSwitchBinding;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingActivity;
import cn.lanhu.android_growth_plan.net.rxevent.RxBus;
import cn.lanhu.android_growth_plan.net.rxevent.RxBusBaseMessage;
import cn.lanhu.android_growth_plan.net.rxevent.RxCodeConstants;
import cn.lanhu.android_growth_plan.utils.uirelated.rxui.RxViewUtils;

public class GradeSwitchActivity extends BaseBindingActivity<ActivityGradeSwitchBinding> {

    private ArrayList<String> mStrings = new ArrayList<>();
    private String mSelectStrings = "";
    private CommonAdapter<String> mCommonAdapter;

    {
        mStrings.add("学龄前");
        mStrings.add("幼儿园");
        mStrings.add("大学");
        mStrings.add("一年级");
        mStrings.add("二年级");
        mStrings.add("三年级");
        mStrings.add("四年级");
        mStrings.add("五年级");
        mStrings.add("六年级");
        mStrings.add("初一");
        mStrings.add("初二");
        mStrings.add("初三");
        mStrings.add("高一");
        mStrings.add("高二");
        mStrings.add("高三");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_grade_switch;
    }

    @Override
    protected void initView() {
        mSelectStrings = getIntent().getStringExtra("selectStrings");
        GridLayoutManager gl = new GridLayoutManager(mContext, 3);
        mVB.recyclerView.setLayoutManager(gl);
        mVB.recyclerView.setNestedScrollingEnabled(false);
        mCommonAdapter = new CommonAdapter<String>(mContext, R.layout.layout_kecheng_qiehuan, mStrings) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_kecheng, s);

                View view = holder.getView(R.id.tv_kecheng);

                if (mSelectStrings.equals(s)) {
                    view.setBackgroundResource(R.drawable.background_item_select_kecheng);
                } else {
                    view.setBackgroundResource(R.drawable.background_item_kecheng);
                }

                RxViewUtils.onViewClick(holder.itemView, view1 -> {
                    mSelectStrings = s;
                    notifyDataSetChanged();
                });
            }
        };
        mVB.recyclerView.setAdapter(mCommonAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        RxViewUtils.onViewClick(mVB.btnSave, view -> {

            RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE, new RxBusBaseMessage(RxCodeConstants.TYPE_SUBJECT_CHANGE, mSelectStrings));
            finishActivity();
        });
    }

    @Override
    protected void destroyView() {

    }
}
