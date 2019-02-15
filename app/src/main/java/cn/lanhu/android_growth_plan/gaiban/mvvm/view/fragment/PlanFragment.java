package cn.lanhu.android_growth_plan.gaiban.mvvm.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;

import java.util.ArrayList;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.adapter.absrecyclerview.CommonAdapter;
import cn.lanhu.android_growth_plan.adapter.absrecyclerview.base.ViewHolder;
import cn.lanhu.android_growth_plan.databinding.FragmentOutloanBinding;
import cn.lanhu.android_growth_plan.entity.PlanEntity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingFragment;
import cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity.PasswordLoginActivity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity.SupplementaryActvity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.viewmodel.UserViewModel;
import cn.lanhu.android_growth_plan.net.rxevent.RxBus;
import cn.lanhu.android_growth_plan.net.rxevent.RxCodeConstants;
import cn.lanhu.android_growth_plan.utils.sysrelated.IntentUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.rxui.RxViewUtils;
import rx.Subscription;

/**
 * Created by yx on 2017/12/26.
 * 出借fragment
 */

public class PlanFragment extends BaseBindingFragment<FragmentOutloanBinding> {

    private ArrayList<PlanEntity> mPlanEntities = new ArrayList<>();
    private CommonAdapter<PlanEntity> mCommonAdapter;
    private UserViewModel mUserViewModel;

    private ArrayList<int[]> mInts = new ArrayList<>();

    {
        mInts.add(new int[]{Color.parseColor("#EAFFFD"), Color.parseColor("#FCFFFF")});
        mInts.add(new int[]{Color.parseColor("#FEE0FF"), Color.parseColor("#FEFCFF")});
        mInts.add(new int[]{Color.parseColor("#F2FFDD"), Color.parseColor("#FEFFFC")});
        mInts.add(new int[]{Color.parseColor("#DDDFFF"), Color.parseColor("#FCFCFF")});
        mInts.add(new int[]{Color.parseColor("#FFF4E2"), Color.parseColor("#FFFEFC")});
        mInts.add(new int[]{Color.parseColor("#FFE1EA"), Color.parseColor("#FACBD9")});
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_outloan;
    }

    @Override
    protected void initView() {
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mVB.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mCommonAdapter = new CommonAdapter<PlanEntity>(mContext, R.layout.layout_item_plan, mPlanEntities) {
            @Override
            protected void convert(ViewHolder holder, PlanEntity planEntity, int position) {
                holder.setText(R.id.tv_time, planEntity.getDate());
                holder.setText(R.id.tv_shixiang, planEntity.getShixiang());
                holder.setText(R.id.tv_note, planEntity.getNote());
                LinearLayout linearLayout = holder.getView(R.id.root_view);
                int i = position;
                if (i > mInts.size() - 1) {
                    i = i % (mInts.size() - 1);
                }
                GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, mInts.get(i));
                linearLayout.setBackgroundDrawable(gradientDrawable);
            }
        };
        mVB.recyclerView.setNestedScrollingEnabled(false);
        mPlanEntities.add(new PlanEntity("2018年9月", "基础知识第二轮   学习", "后代的坏蛋会啊胡"));
        mPlanEntities.add(new PlanEntity("2018年9月", "基础知识第二轮   学习", "后代的坏蛋会啊胡"));
        mPlanEntities.add(new PlanEntity("2018年9月", "基础知识第二轮   学习", "后代的坏蛋会啊胡"));
        mPlanEntities.add(new PlanEntity("2018年9月", "基础知识第二轮   学习", "后代的坏蛋会啊胡"));
        mPlanEntities.add(new PlanEntity("2018年9月", "基础知识第二轮   学习", "后代的坏蛋会啊胡"));
        mPlanEntities.add(new PlanEntity("2018年9月", "基础知识第二轮   学习", "后代的坏蛋会啊胡"));
        mPlanEntities.add(new PlanEntity("2018年9月", "基础知识第二轮   学习", "后代的坏蛋会啊胡"));
        mPlanEntities.add(new PlanEntity("2018年9月", "基础知识第二轮   学习", "后代的坏蛋会啊胡"));
        mPlanEntities.add(new PlanEntity("2018年9月", "基础知识第二轮   学习", "后代的坏蛋会啊胡"));
        mPlanEntities.add(new PlanEntity("2018年9月", "基础知识第二轮   学习", "后代的坏蛋会啊胡"));
        mPlanEntities.add(new PlanEntity("2018年9月", "基础知识第二轮   学习", "后代的坏蛋会啊胡"));
        mPlanEntities.add(new PlanEntity("2018年9月", "基础知识第二轮   学习", "后代的坏蛋会啊胡"));
        mPlanEntities.add(new PlanEntity("2018年9月", "基础知识第二轮   学习", "后代的坏蛋会啊胡"));
        mPlanEntities.add(new PlanEntity("2018年9月", "基础知识第二轮   学习", "后代的坏蛋会啊胡"));
        mVB.recyclerView.setAdapter(mCommonAdapter);
        if (mUserViewModel.isUserLogin()) {
            mVB.tvMengx.setText(mUserViewModel.getUserInfo().getBaby_professionName());
            mVB.tvXx.setText(mUserViewModel.getUserInfo().getBaby_sex().equals("1") ? "男" : "女" + " " + mUserViewModel.getUserInfo().getBaby_age() + " " + mUserViewModel.getUserInfo().getBaby_className());
        }

        initRxBus();
    }

    private void initRxBus() {
        Subscription subscription = RxBus.getDefault().toObservable(RxCodeConstants.JUMP_TYPE, Integer.class).subscribe(integer -> {
            switch (integer) {
                case RxCodeConstants.TYPE_USER_LOGIN:
                case RxCodeConstants.TYPE_INFO_CHANGE:
                    if (mUserViewModel.isUserLogin()) {
                        mVB.tvMengx.setText(mUserViewModel.getUserInfo().getBaby_professionName());
                        mVB.tvXx.setText(mUserViewModel.getUserInfo().getBaby_sex().equals("1") ? "男" : "女" + " " + mUserViewModel.getUserInfo().getBaby_age() + " " + mUserViewModel.getUserInfo().getBaby_className());
                    }
                    break;
                case RxCodeConstants.TYPE_USER_LOGOUT:
                    mVB.tvMengx.setText("");
                    mVB.tvXx.setText("");
                    break;
            }
        });
        addSubscription(subscription);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        RxViewUtils.onViewClick(mVB.ivBj, () -> {
            if (mUserViewModel.isUserLogin()) {
                new IntentUtils.Builder(mContext)
                        .setTargetActivity(SupplementaryActvity.class)
                        .setStringExtra("flag", "change")
                        .build()
                        .startActivity(true);
            } else {
                showLoginUi();
            }
        });
    }

    private void showLoginUi() {
        new IntentUtils.Builder(mContext)
                .setTargetActivity(PasswordLoginActivity.class)
                .build()
                .startActivity(true);
    }

    @Override
    protected void destroyView() {

    }
}
