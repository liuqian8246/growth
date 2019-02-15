package cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity;

import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.adapter.absrecyclerview.CommonAdapter;
import cn.lanhu.android_growth_plan.adapter.absrecyclerview.base.ViewHolder;
import cn.lanhu.android_growth_plan.databinding.ActivityHistoryBinding;
import cn.lanhu.android_growth_plan.entity.HistoryEntity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingActivity;

public class HistoryActivity extends BaseBindingActivity<ActivityHistoryBinding> {

    private ArrayList<HistoryEntity> mHistoryEntities = new ArrayList<>();
    private CommonAdapter<HistoryEntity> mCommonAdapter;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_history;
    }

    @Override
    protected void initView() {
        mVB.recyclerView.setNestedScrollingEnabled(false);
        mVB.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mHistoryEntities.add(new HistoryEntity("2018-02-12","133","从 2015 年 4 月起，Ant Design 在蚂蚁金服中后台产品线迅速推广，对接多条业务线，覆盖系统 800 个以上。定位"));
        mHistoryEntities.add(new HistoryEntity("2018-02-12","133","从 2015 年 4 月起，Ant Design 在蚂蚁金服中后台产品线迅速推广，对接多条业务线，覆盖系统 800 个以上。定位"));
        mHistoryEntities.add(new HistoryEntity("2018-02-12","133","从 2015 年 4 月起，Ant Design 在蚂蚁金服中后台产品线迅速推广，对接多条业务线，覆盖系统 800 个以上。定位"));
        mHistoryEntities.add(new HistoryEntity("2018-02-12","133","从 2015 年 4 月起，Ant Design 在蚂蚁金服中后台产品线迅速推广，对接多条业务线，覆盖系统 800 个以上。定位"));
        mHistoryEntities.add(new HistoryEntity("2018-02-12","133","从 2015 年 4 月起，Ant Design 在蚂蚁金服中后台产品线迅速推广，对接多条业务线，覆盖系统 800 个以上。定位"));
        mHistoryEntities.add(new HistoryEntity("2018-02-12","133","从 2015 年 4 月起，Ant Design 在蚂蚁金服中后台产品线迅速推广，对接多条业务线，覆盖系统 800 个以上。定位"));
        mHistoryEntities.add(new HistoryEntity("2018-02-12","133","从 2015 年 4 月起，Ant Design 在蚂蚁金服中后台产品线迅速推广，对接多条业务线，覆盖系统 800 个以上。定位"));
        mCommonAdapter = new CommonAdapter<HistoryEntity>(mContext,R.layout.layout_history_item,mHistoryEntities) {
            @Override
            protected void convert(ViewHolder holder, HistoryEntity historyEntity, int position) {
                holder.setText(R.id.tv_date,historyEntity.getDate());
                holder.setText(R.id.tv_dxnum,historyEntity.getPeople());
                holder.setText(R.id.tv_content,historyEntity.getContent());
            }
        };
        mVB.recyclerView.setAdapter(mCommonAdapter);
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
