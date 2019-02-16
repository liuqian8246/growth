package cn.lanhu.android_growth_plan.gaiban.mvvm.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.adapter.absrecyclerview.CommonAdapter;
import cn.lanhu.android_growth_plan.adapter.absrecyclerview.base.ViewHolder;
import cn.lanhu.android_growth_plan.adapter.banner.BannerImageHolderView;
import cn.lanhu.android_growth_plan.databinding.FragmentHomeBinding;
import cn.lanhu.android_growth_plan.entity.BannerEntity;
import cn.lanhu.android_growth_plan.entity.DaohangEntity;
import cn.lanhu.android_growth_plan.entity.HomeListEntity;
import cn.lanhu.android_growth_plan.entity.base.BaseBean;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingFragment;
import cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity.SpecialActivity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.viewmodel.UserViewModel;
import cn.lanhu.android_growth_plan.net.HttpUrlApi;
import cn.lanhu.android_growth_plan.utils.sysrelated.IntentUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.ToastUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.rxui.RxViewUtils;
import cn.lanhu.android_growth_plan.widget.convenientbanner.ConvenientBanner;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;

/**
 * Created by yx on 2017/12/26.
 * 首页fragment
 */

public class HomeFragment extends BaseBindingFragment<FragmentHomeBinding> {

    private ArrayList<HomeListEntity> mHomeListEntities = new ArrayList<>();
    private ArrayList<DaohangEntity> mDaohangEntities = new ArrayList<>();
    private CommonAdapter<DaohangEntity> mDaohangEntityCommonAdapter;
    private UserViewModel mUserViewModel;
    ArrayList<String> home_top = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        initBanner();
        mHomeListEntities.add(new HomeListEntity("哈哈哈", "我们是做游戏运营的   去年3月份 文化局就不给游戏备案了 所以才没事做，现在开始审批了，版号下来，2个月内就得上市", "2019-01-14", ""));
        mHomeListEntities.add(new HomeListEntity("哈哈哈", "我们是做游戏运营的   去年3月份 文化局就不给游戏备案了 所以才没事做，现在开始审批了，版号下来，2个月内就得上市", "2019-01-14", ""));
        mHomeListEntities.add(new HomeListEntity("哈哈哈", "我们是做游戏运营的   去年3月份 文化局就不给游戏备案了 所以才没事做，现在开始审批了，版号下来，2个月内就得上市", "2019-01-14", ""));
        mHomeListEntities.add(new HomeListEntity("哈哈哈", "我们是做游戏运营的   去年3月份 文化局就不给游戏备案了 所以才没事做，现在开始审批了，版号下来，2个月内就得上市", "2019-01-14", ""));
        mHomeListEntities.add(new HomeListEntity("哈哈哈", "我们是做游戏运营的   去年3月份 文化局就不给游戏备案了 所以才没事做，现在开始审批了，版号下来，2个月内就得上市", "2019-01-14", ""));
        mHomeListEntities.add(new HomeListEntity("哈哈哈", "我们是做游戏运营的   去年3月份 文化局就不给游戏备案了 所以才没事做，现在开始审批了，版号下来，2个月内就得上市", "2019-01-14", ""));
        mHomeListEntities.add(new HomeListEntity("哈哈哈", "我们是做游戏运营的   去年3月份 文化局就不给游戏备案了 所以才没事做，现在开始审批了，版号下来，2个月内就得上市", "2019-01-14", ""));
        mHomeListEntities.add(new HomeListEntity("哈哈哈", "我们是做游戏运营的   去年3月份 文化局就不给游戏备案了 所以才没事做，现在开始审批了，版号下来，2个月内就得上市", "2019-01-14", ""));
        mVB.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mVB.recyclerView.setNestedScrollingEnabled(false);
        mVB.recyclerView.setAdapter(new CommonAdapter<HomeListEntity>(mContext, R.layout.layout_item_home_list, mHomeListEntities) {
            @Override
            protected void convert(ViewHolder holder, HomeListEntity homeListEntity, int position) {
                holder.setText(R.id.tv_content, homeListEntity.getContent());
                holder.setText(R.id.tv_title, homeListEntity.getTitle());
                holder.setText(R.id.tv_time, homeListEntity.getTime());
            }
        });

        mVB.recycler.setLayoutManager(new GridLayoutManager(mContext,4));
        mVB.recycler.setNestedScrollingEnabled(false);
        mDaohangEntityCommonAdapter = new CommonAdapter<DaohangEntity>(mContext,R.layout.layout_item_home_daohang,mDaohangEntities) {
            @Override
            protected void convert(ViewHolder holder, DaohangEntity daohangEntity, int position) {
                holder.setText(R.id.tv_name,daohangEntity.getName());
                CircleImageView circleImageView = holder.getView(R.id.iv_icon);
                Glide.with(mContext).load(HttpUrlApi.imgUrl + daohangEntity.getIconUrl()).into(circleImageView);

                RxViewUtils.onViewClick(holder.itemView,() ->{
                    switch (daohangEntity.getId()) {

                    }
                });
            }
        };
        mVB.recycler.setAdapter(mDaohangEntityCommonAdapter);

        initDaohang();
    }

    private void initDaohang() {
        mUserViewModel.navigation().subscribe(new Subscriber<BaseBean<ArrayList<DaohangEntity>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(BaseBean<ArrayList<DaohangEntity>> arrayListBaseBean) {
                mDaohangEntities.addAll(arrayListBaseBean.getData());
                mDaohangEntityCommonAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initBanner() {
        mUserViewModel.getBanner().subscribe(new Subscriber<BaseBean<ArrayList<String>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(BaseBean<ArrayList<String>> arrayListBaseBean) {
                if (arrayListBaseBean.getCode().equals("0")) {

                    home_top.addAll(arrayListBaseBean.getData());
                    mVB.contentContainer.setPages(() ->
                            new BannerImageHolderView(home_top), home_top)
                            .setPageIndicator(new int[]{R.drawable.dot_normal_home_top, R.drawable.dop_focus_home_top})
                            .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
                } else {
                    ToastUtils.showToast(arrayListBaseBean.getMsg());
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
//        RxViewUtils.onViewClick(mVB.llAqal, () -> {
//            jumpActivity("安全案例");
//        });
//
//        RxViewUtils.onViewClick(mVB.llBzht, () -> {
//            jumpActivity("本周话题");
//        });
//
//        RxViewUtils.onViewClick(mVB.llJrsh, () -> {
//            jumpActivity("今日收获");
//        });
//
//        RxViewUtils.onViewClick(mVB.llQzhd, () -> {
//            jumpActivity("亲子互动");
//        });
    }

    private void jumpActivity(String title) {
        new IntentUtils.Builder(mContext)
                .setTargetActivity(SpecialActivity.class)
                .setStringExtra("title", title)
                .build().startActivity(true);
    }

    @Override
    protected void destroyView() {

    }
}
