package cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.databinding.ActivityMainBinding;
import cn.lanhu.android_growth_plan.entity.UserInfo;
import cn.lanhu.android_growth_plan.entity.base.BaseBean;
import cn.lanhu.android_growth_plan.gaiban.app.config.RxApplication;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingActivity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingFragment;
import cn.lanhu.android_growth_plan.gaiban.mvvm.model.utils.StatusBarUtils;
import cn.lanhu.android_growth_plan.gaiban.mvvm.view.fragment.HomeFragment;
import cn.lanhu.android_growth_plan.gaiban.mvvm.view.fragment.StadyFragment;
import cn.lanhu.android_growth_plan.gaiban.mvvm.view.fragment.MineFragment;
import cn.lanhu.android_growth_plan.gaiban.mvvm.view.fragment.PlanFragment;
import cn.lanhu.android_growth_plan.gaiban.mvvm.viewmodel.UserViewModel;
import cn.lanhu.android_growth_plan.net.rxevent.RxBus;
import cn.lanhu.android_growth_plan.net.rxevent.RxBusBaseMessage;
import cn.lanhu.android_growth_plan.net.rxevent.RxCodeConstants;
import cn.lanhu.android_growth_plan.utils.filerelated.FileUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.ToastUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.rxui.OnViewClick;
import cn.lanhu.android_growth_plan.utils.uirelated.rxui.RxViewUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;

import static cn.lanhu.android_growth_plan.gaiban.mvvm.view.fragment.MineFragment.REQUEST_CODE_PHOTO;

public class MainActivity extends BaseBindingActivity<ActivityMainBinding> implements OnViewClick {

    private List<BaseBindingFragment> mFragments = new ArrayList<>();
    private FragmentManager mFragmentManager;
    private int mCurrentIndex = 0;
    private UserViewModel mUserViewModel;

    private HomeFragment mHomeFragment;
    private StadyFragment mInloanFragment;
    private PlanFragment mOutloanFragment;
    private MineFragment mMineFragment;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        initFragment();
    }


    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();
        mHomeFragment = new HomeFragment();
        mInloanFragment = new StadyFragment();
        mOutloanFragment = new PlanFragment();
        mMineFragment = new MineFragment();
        mFragments.add(mHomeFragment);
        mFragments.add(mInloanFragment);
        mFragments.add(mOutloanFragment);
        mFragments.add(mMineFragment);
        //设置默认选中Fragment
        mFragmentManager.beginTransaction().add(mVB.flContent.getId(), mHomeFragment, "0").commitAllowingStateLoss();
        mCurrentIndex = 0;
        mVB.mainBottom.llShouye.setSelected(true);

    }

    @Override
    protected void initData() {

    }


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    protected void initEvent() {
        RxViewUtils.onViewClick(mVB.mainBottom.llShouye, this);
        RxViewUtils.onViewClick(mVB.mainBottom.llJiekuan, this);
        RxViewUtils.onViewClick(mVB.mainBottom.llChujie, this);
        RxViewUtils.onViewClick(mVB.mainBottom.llMine, this);
    }

    private void setStatusBarLight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorWhite), 0);
            StatusBarUtils.StatusBarLightMode(this);
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorWhite), 68);
        }
    }

    private void setStatusTranslate() {
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
    }

    @Override
    public void onClick(View view) {
        int index = 0;
        mVB.mainBottom.llShouye.setSelected(false);
        mVB.mainBottom.llJiekuan.setSelected(false);
        mVB.mainBottom.llChujie.setSelected(false);
        mVB.mainBottom.llMine.setSelected(false);
        switch (view.getId()) {
            case R.id.ll_shouye:
                index = 0;
                mVB.mainBottom.llShouye.setSelected(true);
                setStatusBarLight();
                break;
            case R.id.ll_jiekuan:
                index = 1;
                mVB.mainBottom.llJiekuan.setSelected(true);
                setStatusBarLight();
                break;
            case R.id.ll_chujie:
                index = 2;
                mVB.mainBottom.llChujie.setSelected(true);
                setStatusBarLight();
                break;
            case R.id.ll_mine:
                index = 3;
                setStatusTranslate();
                mVB.mainBottom.llMine.setSelected(true);
                break;
        }

        if (index == mCurrentIndex) {
            return;
        }

        BaseBindingFragment baseFragment = mFragments.get(index);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (baseFragment.isAdded()) {
            fragmentTransaction.show(baseFragment);
        } else {
            fragmentTransaction.add(mVB.flContent.getId(), baseFragment, index + "");
            fragmentTransaction.show(baseFragment);
        }
        fragmentTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.hide(mFragments.get(mCurrentIndex));
        fragmentTransaction.commitAllowingStateLoss();
        mCurrentIndex = index;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);//回到后台
    }


    @Override
    protected void destroyView() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == REQUEST_CODE_PHOTO) {
            List<String> value = Matisse.obtainPathResult(data);
            Observable.just(value)
                    .map(list -> {
                        try {
                            return Luban.with(mContext).load(list).get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    })
                    .filter(new Func1<List<File>, Boolean>() {
                        @Override
                        public Boolean call(List<File> files) {
                            File file = files.get(0);
                            long l = file.length() / 1024;
                            Logger.w("file size : " + l + "K");
                            return true;
                        }
                    })
                    .map(pathList1 ->{
                        return pathList1.get(0).getAbsolutePath();
                    })
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(path -> {
                        File file = new File(path);
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
                        MultipartBody.Part photo = MultipartBody.Part.createFormData("headImage", mUserViewModel.getMobile() + ".png", requestBody);
                        mUserViewModel.uploadHead(photo)
                                .subscribe(new Subscriber<BaseBean<String>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable throwable) {

                                    }

                                    @Override
                                    public void onNext(BaseBean<String> baseBean) {
                                        if(baseBean.getCode().equals("0")) {
                                            UserInfo userInfo = mUserViewModel.getUserInfo();
                                            userInfo.setHeadPicUrl(baseBean.getData());
                                            RxApplication.getInstance().updateUserInfo(userInfo);
                                            RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE, RxCodeConstants.TYPE_ANACCOUNT_PICK_PHOTO);
                                        } else {
                                            ToastUtils.showToast(baseBean.getMsg());
                                        }
                                    }
                                });

                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.w("photo cut e : " + throwable.getMessage());
                            ToastUtils.showToast("图片解析出错，请重试!");
                        }
                    });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
