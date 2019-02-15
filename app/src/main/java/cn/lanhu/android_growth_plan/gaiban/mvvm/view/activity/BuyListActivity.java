package cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity;

import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.adapter.absrecyclerview.CommonAdapter;
import cn.lanhu.android_growth_plan.adapter.absrecyclerview.base.ViewHolder;
import cn.lanhu.android_growth_plan.databinding.ActivityBugListBinding;
import cn.lanhu.android_growth_plan.entity.BuyEntity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingActivity;
import cn.lanhu.android_growth_plan.utils.uirelated.rxui.RxViewUtils;

public class BuyListActivity extends BaseBindingActivity<ActivityBugListBinding> {

    private ArrayList<BuyEntity> mBuyEntities = new ArrayList<>();
    private CommonAdapter<BuyEntity> mCommonAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_bug_list;
    }

    @Override
    protected void initView() {
        mVB.recyclerView.setNestedScrollingEnabled(false);
        mVB.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mCommonAdapter = new CommonAdapter<BuyEntity>(mContext,R.layout.layout_item_buy_list,mBuyEntities) {
            @Override
            protected void convert(ViewHolder holder, BuyEntity buyEntity, int position) {
                holder.setText(R.id.tv_time,buyEntity.getTime());
                holder.setText(R.id.tv_order,buyEntity.getOrderNum());
                holder.setText(R.id.tv_money,buyEntity.getMoney());
                holder.setText(R.id.tv_zftime,buyEntity.getBuyTime());
            }
        };
        mVB.recyclerView.setAdapter(mCommonAdapter);

        mBuyEntities.add(new BuyEntity("2019-02-19","45451215454124545","22222","2019-02-19"));
        mBuyEntities.add(new BuyEntity("2019-02-19","45451215454124545","22222","2019-02-19"));
        mBuyEntities.add(new BuyEntity("2019-02-19","45451215454124545","22222","2019-02-19"));
        mBuyEntities.add(new BuyEntity("2019-02-19","45451215454124545","22222","2019-02-19"));
        mBuyEntities.add(new BuyEntity("2019-02-19","45451215454124545","22222","2019-02-19"));
        mBuyEntities.add(new BuyEntity("2019-02-19","45451215454124545","22222","2019-02-19"));
        mBuyEntities.add(new BuyEntity("2019-02-19","45451215454124545","22222","2019-02-19"));
        mCommonAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        RxViewUtils.onViewClick(mVB.titleBar.ivBack,view -> {
            finishActivity();
        });
    }

    @Override
    protected void destroyView() {

    }
}
