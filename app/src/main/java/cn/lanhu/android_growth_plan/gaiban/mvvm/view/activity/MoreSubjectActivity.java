package cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import java.util.ArrayList;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.adapter.absrecyclerview.CommonAdapter;
import cn.lanhu.android_growth_plan.adapter.absrecyclerview.base.ViewHolder;
import cn.lanhu.android_growth_plan.databinding.ActivityMoreSubBinding;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingActivity;
import cn.lanhu.android_growth_plan.net.rxevent.RxBus;
import cn.lanhu.android_growth_plan.net.rxevent.RxBusBaseMessage;
import cn.lanhu.android_growth_plan.net.rxevent.RxCodeConstants;
import cn.lanhu.android_growth_plan.utils.uirelated.ToastUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.rxui.RxViewUtils;

public class MoreSubjectActivity extends BaseBindingActivity<ActivityMoreSubBinding> {

    private ArrayList<String> mStrings = new ArrayList<>();
    private ArrayList<String> mSelectStrings = new ArrayList<>();
    private CommonAdapter<String> mCommonAdapter;

    {
        mStrings.add("语文");
        mStrings.add("数学");
        mStrings.add("英语");
        mStrings.add("地理");
        mStrings.add("历史");
        mStrings.add("政治");
        mStrings.add("物理");
        mStrings.add("生物");
        mStrings.add("化学");
        mStrings.add("音乐");
        mStrings.add("美术");
        mStrings.add("品德");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_more_sub;
    }

    @Override
    protected void initView() {
        mSelectStrings = getIntent().getStringArrayListExtra("selectStrings");
        GridLayoutManager gl = new GridLayoutManager(mContext, 3);
        mVB.recyclerView.setLayoutManager(gl);
        mVB.recyclerView.setNestedScrollingEnabled(false);
        mCommonAdapter = new CommonAdapter<String>(mContext, R.layout.layout_kecheng_qiehuan, mStrings) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_kecheng, s);

                View view = holder.getView(R.id.tv_kecheng);

                if (mSelectStrings.contains(s)) {
                    view.setBackgroundResource(R.drawable.background_item_select_kecheng);
                } else {
                    view.setBackgroundResource(R.drawable.background_item_kecheng);
                }

                RxViewUtils.onViewClick(holder.itemView, view1 -> {
                    if (mSelectStrings.contains(s)) {
                        if (mSelectStrings.size() > 2) {
                            mSelectStrings.remove(s);
                            notifyDataSetChanged();
                        } else {
                            ToastUtils.showToast("最少选择1个");
                        }
                    } else {
                        if (mSelectStrings.size() < 10) {
                            mSelectStrings.add(s);
                            notifyDataSetChanged();
                        } else {
                            ToastUtils.showToast("最多选择9个");
                        }
                    }
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
