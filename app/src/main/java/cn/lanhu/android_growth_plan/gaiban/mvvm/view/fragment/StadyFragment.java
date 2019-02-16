package cn.lanhu.android_growth_plan.gaiban.mvvm.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Collection;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.adapter.absrecyclerview.CommonAdapter;
import cn.lanhu.android_growth_plan.adapter.absrecyclerview.base.ViewHolder;
import cn.lanhu.android_growth_plan.databinding.FragmentInloanBinding;
import cn.lanhu.android_growth_plan.entity.CourseEntity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingFragment;
import cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity.GradeSwitchActivity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity.MoreSubjectActivity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.viewmodel.UserViewModel;
import cn.lanhu.android_growth_plan.net.rxevent.RxBus;
import cn.lanhu.android_growth_plan.net.rxevent.RxBusBaseMessage;
import cn.lanhu.android_growth_plan.net.rxevent.RxCodeConstants;
import cn.lanhu.android_growth_plan.utils.sysrelated.IntentUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.rxui.RxViewUtils;
import rx.Subscription;

/**
 * Created by yx on 2017/12/26.
 * 借款fragment
 */

public class StadyFragment extends BaseBindingFragment<FragmentInloanBinding> {

    private CommonAdapter<CourseEntity> mCourseAdapter;
    private ArrayList<CourseEntity> mCourseEntities = new ArrayList<>();
    private ArrayList<String> mStrings = new ArrayList<>();
    private CommonAdapter<String> mCommonAdapter;
    private UserViewModel mUserViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_inloan;
    }

    @Override
    protected void initView() {
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 5);
        mVB.gridview.setLayoutManager(gridLayoutManager);
        mVB.gridview.setNestedScrollingEnabled(false);
        mCommonAdapter = new CommonAdapter<String>(mContext, R.layout.layout_xuexi_kecheng, mStrings) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_kecheng, s);

                if (s.equals("更多")) {
                    RxViewUtils.onViewClick(holder.itemView, view -> {
                        new IntentUtils.Builder(mContext)
                                .setTargetActivity(MoreSubjectActivity.class)
                                .setStringArrayListExtra("selectStrings", mStrings)
                                .build().startActivity(true);
                    });
                }
            }
        };
        mVB.gridview.setAdapter(mCommonAdapter);
        mVB.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mCourseAdapter = new CommonAdapter<CourseEntity>(mContext, R.layout.layout_item_course, mCourseEntities) {
            @Override
            protected void convert(ViewHolder holder, CourseEntity courseEntity, int position) {
                holder.setText(R.id.tv_course, courseEntity.getName());
                holder.setText(R.id.tv_see_num, courseEntity.getSeeNum());
                holder.setText(R.id.tv_title, courseEntity.getTitle());
                holder.setText(R.id.tv_content, courseEntity.getContent());
                switch (courseEntity.getName()) {
                    case "语文":
                        holder.setImageResource(R.id.iv_icon, R.drawable.czjh_kechengyuwen13x);
                        break;
                    case "数学":
                        holder.setImageResource(R.id.iv_icon, R.drawable.czjh_kechengshuxue43x);
                        break;
                    case "化学":
                        holder.setImageResource(R.id.iv_icon, R.drawable.czjh_kecheng_huaxue);
                        break;
                    case "英语":
                        holder.setImageResource(R.id.iv_icon, R.drawable.czjh_kechengyingyu23x);
                        break;
                    case "地理":
                        holder.setImageResource(R.id.iv_icon, R.drawable.czjh_kechengdili33x);
                        break;
                    case "历史":
                        holder.setImageResource(R.id.iv_icon, R.drawable.czjh_kecheng_lishi);
                        break;
                    case "美术":
                        holder.setImageResource(R.id.iv_icon, R.drawable.czjh_kecheng_meishu);
                        break;
                    case "品德":
                        holder.setImageResource(R.id.iv_icon, R.drawable.czjh_kecheng_pinde);
                        break;
                    case "生物":
                        holder.setImageResource(R.id.iv_icon, R.drawable.czjh_kecheng_shengwu);
                        break;
                    case "物理":
                        holder.setImageResource(R.id.iv_icon, R.drawable.czjh_kecheng_wuli);
                        break;
                    case "音乐":
                        holder.setImageResource(R.id.iv_icon, R.drawable.czjh_kecheng_yinyue);
                        break;
                    case "政治":
                        holder.setImageResource(R.id.iv_icon, R.drawable.czjh_kecheng_zhengzhi);
                        break;
                }
            }
        };
        mVB.recyclerView.setAdapter(mCourseAdapter);
        mVB.recyclerView.setNestedScrollingEnabled(false);
        mCourseEntities.add(new CourseEntity("语文", "122", "AntV 是蚂蚁金服全新一代数据可视化解", "Ant Design是一个服务于企业级产品的设计体系，基于『确定』和『自然』的设计价值观和模块化的解决方案，让设计者专注于更好的用户体验。"));
        mCourseEntities.add(new CourseEntity("数学", "122", "AntV 是蚂蚁金服全新一代数据可视化解", "Ant Design是一个服务于企业级产品的设计体系，基于『确定』和『自然』的设计价值观和模块化的解决方案，让设计者专注于更好的用户体验。"));
        mCourseEntities.add(new CourseEntity("化学", "122", "AntV 是蚂蚁金服全新一代数据可视化解", "Ant Design是一个服务于企业级产品的设计体系，基于『确定』和『自然』的设计价值观和模块化的解决方案，让设计者专注于更好的用户体验。"));
        mCourseEntities.add(new CourseEntity("英语", "122", "AntV 是蚂蚁金服全新一代数据可视化解", "Ant Design是一个服务于企业级产品的设计体系，基于『确定』和『自然』的设计价值观和模块化的解决方案，让设计者专注于更好的用户体验。"));
        mCourseEntities.add(new CourseEntity("美术", "122", "AntV 是蚂蚁金服全新一代数据可视化解", "Ant Design是一个服务于企业级产品的设计体系，基于『确定』和『自然』的设计价值观和模块化的解决方案，让设计者专注于更好的用户体验。"));
        mCourseEntities.add(new CourseEntity("品德", "122", "AntV 是蚂蚁金服全新一代数据可视化解", "Ant Design是一个服务于企业级产品的设计体系，基于『确定』和『自然』的设计价值观和模块化的解决方案，让设计者专注于更好的用户体验。"));
        mCourseAdapter.notifyDataSetChanged();
        initRxBus();
        if(mUserViewModel.isUserLogin()) {
            mVB.tvAge.setText(mUserViewModel.getUserInfo().getBaby_className());
            mStrings.add("语文");
            mStrings.add("语文");
            mStrings.add("语文");
            mStrings.add("语文");
            mStrings.add("更多");
            mCommonAdapter.notifyDataSetChanged();
        } else {
            mStrings.add("语文");
            mStrings.add("化学");
            mStrings.add("数学");
            mStrings.add("语文");
            mStrings.add("语文");
            mStrings.add("语文");
            mStrings.add("语文");
            mStrings.add("语文");
            mStrings.add("语文");
            mStrings.add("更多");
            mCommonAdapter.notifyDataSetChanged();
        }
    }

    private void initRxBus() {
        Subscription subscription = RxBus.getDefault().toObservable(RxCodeConstants.JUMP_TYPE, RxBusBaseMessage.class).subscribe(rxBusBaseMessage -> {
            switch (rxBusBaseMessage.getCode()) {
                case RxCodeConstants.TYPE_SUBJECT_CHANGE:
                    mStrings.clear();
                    ArrayList<String> strings = (ArrayList<String>) rxBusBaseMessage.getObject();
                    mStrings.addAll(strings);
                    mCommonAdapter.notifyDataSetChanged();
                    break;
                case RxCodeConstants.TYPE_GRADE_CHANGE:
                    mVB.tvAge.setText(rxBusBaseMessage.getObject().toString());
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
        RxViewUtils.onViewClick(mVB.llQihuan,view -> {
            new IntentUtils.Builder(mContext)
                    .setTargetActivity(GradeSwitchActivity.class)
                    .setStringExtra("selectStrings",mVB.tvAge.getText().toString())
                    .build().startActivity(true);
        });
    }

    @Override
    protected void destroyView() {

    }
}
