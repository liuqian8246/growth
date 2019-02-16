package cn.lanhu.android_growth_plan.gaiban.mvvm.view.fragment;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.adapter.absrecyclerview.CommonAdapter;
import cn.lanhu.android_growth_plan.adapter.absrecyclerview.base.ViewHolder;
import cn.lanhu.android_growth_plan.databinding.FragmentMineBinding;
import cn.lanhu.android_growth_plan.entity.DateEntity;
import cn.lanhu.android_growth_plan.entity.base.BaseBean;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingFragment;
import cn.lanhu.android_growth_plan.gaiban.mvvm.model.filter.GifSizeFilter;
import cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity.AboutUsActivity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity.BuyListActivity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity.ChangePasswordActivity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity.ContacyUsActivity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity.PasswordLoginActivity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity.SystemSettingActivity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.viewmodel.UserViewModel;
import cn.lanhu.android_growth_plan.net.HttpUrlApi;
import cn.lanhu.android_growth_plan.net.rxevent.RxBus;
import cn.lanhu.android_growth_plan.net.rxevent.RxBusBaseMessage;
import cn.lanhu.android_growth_plan.net.rxevent.RxCodeConstants;
import cn.lanhu.android_growth_plan.utils.sysrelated.IntentUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.ToastUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.rxui.RxViewUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import cn.lanhu.android_growth_plan.BuildConfig;

/**
 * Created by yx on 2017/12/26.
 * 我的fragment
 */

public class MineFragment extends BaseBindingFragment<FragmentMineBinding> {

    private UserViewModel mUserViewModel;
    private ArrayList<DateEntity> mDateEntities = new ArrayList<DateEntity>();
    private CommonAdapter<DateEntity> mDateEntityCommonAdapter;
    public static final int REQUEST_CODE_PHOTO = 11;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 7);
        mVB.recyclerView.setLayoutManager(layoutManager);
        mDateEntityCommonAdapter = new CommonAdapter<DateEntity>(mContext, R.layout.layout_sign_item, mDateEntities) {
            @Override
            protected void convert(ViewHolder holder, DateEntity dateEntity, int position) {
                if (dateEntity.getQdStatus()) {
                    holder.setBackgroundColor(R.id.root_view, Color.parseColor("#4578FF"));
                } else {
                    holder.setBackgroundColor(R.id.root_view, Color.parseColor("#265180FF"));
                }
                holder.setText(R.id.tv_month, dateEntity.getMonth());
                holder.setText(R.id.tv_date, dateEntity.getDate());
            }
        };
        mVB.recyclerView.setAdapter(mDateEntityCommonAdapter);

        if (mUserViewModel.isUserLogin()) {
            getSignData();
            setData();
        }
        initRxBus();
    }

    private void setData() {
        Glide.with(mContext).load(HttpUrlApi.imgUrl + mUserViewModel.getUserInfo().getHeadPicUrl()).diskCacheStrategy(DiskCacheStrategy.NONE).into(mVB.ivHead);
        mVB.tvNick.setText(mUserViewModel.getUserInfo().getNickname());
        mVB.tvGronth.setText("成长号：" + mUserViewModel.getUserInfo().getBaby_grownNo());
    }

    private Bitmap mBitmap;

    private void initRxBus() {
        Subscription subscription = RxBus.getDefault().toObservable(RxCodeConstants.JUMP_TYPE, Integer.class).subscribe(integer -> {
            switch (integer) {
                case RxCodeConstants.TYPE_SIGN_SUC:
                    getSignData();
                    break;
                case RxCodeConstants.TYPE_USER_LOGIN:
                    getSignData();
                    setData();
                    break;
                case RxCodeConstants.TYPE_ANACCOUNT_PICK_PHOTO:
                    Glide.with(mContext).load(HttpUrlApi.imgUrl + mUserViewModel.getUserInfo().getHeadPicUrl()).diskCacheStrategy(DiskCacheStrategy.NONE).into(mVB.ivHead);
            }
        });

        addSubscription(subscription);
    }

    private void getSignData() {
        mUserViewModel.getSign(mUserViewModel.getUserId())
                .subscribe(new Subscriber<BaseBean<ArrayList<DateEntity>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(BaseBean<ArrayList<DateEntity>> baseBean) {
                        if (baseBean.getCode().equals("0")) {
                            mDateEntities.clear();
                            mDateEntities.addAll(baseBean.getData());
                            mDateEntityCommonAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.showToast(baseBean.getMsg());
                        }
                    }
                });
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initEvent() {

        RxViewUtils.onViewClickNeedPermission(mVB.ivHead, permission -> {
                    if (permission.granted) {
                        if (mUserViewModel.isUserLogin()) {
                            Matisse.from(getActivity())
                                    .choose(MimeType.ofImage())
                                    .countable(false)
                                    .capture(true)
                                    .captureStrategy(
                                            new CaptureStrategy(true, BuildConfig.APPLICATION_ID + ".provider"))
                                    .maxSelectable(1)
                                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                    .gridExpectedSize(
                                            getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine())
                                    .originalEnable(true)
                                    .maxOriginalSize(1)
                                    .forResult(REQUEST_CODE_PHOTO);
                        } else {
                            showLoginUi();
                        }
                    } else {
                        ToastUtils.showToast("权限被拒绝了,无法拍照！");

                    }
                }, Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);


        RxViewUtils.onViewClick(mVB.llGmjl, () -> {
            new IntentUtils.Builder(mContext)
                    .setTargetActivity(BuyListActivity.class)
                    .build().startActivity(true);
        });

        RxViewUtils.onViewClick(mVB.llLxwm, () -> {
            new IntentUtils.Builder(mContext)
                    .setTargetActivity(ContacyUsActivity.class)
                    .build().startActivity(true);
        });

        RxViewUtils.onViewClick(mVB.llGywm, () -> {
            new IntentUtils.Builder(mContext)
                    .setTargetActivity(AboutUsActivity.class)
                    .build().startActivity(true);
        });

        RxViewUtils.onViewClick(mVB.llMmxg, () -> {
            new IntentUtils.Builder(mContext)
                    .setTargetActivity(ChangePasswordActivity.class)
                    .build().startActivity(true);
        });

        RxViewUtils.onViewClick(mVB.btnSign, () -> {
            if (mUserViewModel.isUserLogin()) {
                if (jumpIsSign()) {
                    ToastUtils.showToast("已经签到了");
                    return;
                }
                mUserViewModel.sign(mUserViewModel.getUserId())
                        .subscribe(new Subscriber<BaseBean<String>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable throwable) {

                            }

                            @Override
                            public void onNext(BaseBean<String> baseBean) {
                                if (baseBean.getCode().equals("0")) {
                                    ToastUtils.showToast("签到成功");
                                    RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE, RxCodeConstants.TYPE_SIGN_SUC);
                                } else {
                                    ToastUtils.showToast(baseBean.getMsg());
                                }
                            }
                        });
            } else {
                showLoginUi();
            }

        });

        RxViewUtils.onViewClick(mVB.llSetting,() ->{
            new IntentUtils.Builder(mContext)
                    .setTargetActivity(SystemSettingActivity.class)
                    .build().startActivity(true);
        });
    }

    private void showLoginUi() {
        new IntentUtils.Builder(mContext)
                .setTargetActivity(PasswordLoginActivity.class)
                .build().startActivity(true);
    }

    private boolean jumpIsSign() {
        for (DateEntity dateEntity : mDateEntities) {
            if (dateEntity.getDate().equals(String.valueOf(Calendar.getInstance().get(Calendar.DATE)))) {
                if (dateEntity.getQdStatus()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void destroyView() {

    }
}
