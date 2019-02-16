package cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import java.util.ArrayList;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.databinding.ActivitySupplementaryBinding;
import cn.lanhu.android_growth_plan.entity.ListEntity;
import cn.lanhu.android_growth_plan.entity.LoginEntity;
import cn.lanhu.android_growth_plan.entity.base.BaseBean;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseBindingActivity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.viewmodel.DateViewModel;
import cn.lanhu.android_growth_plan.gaiban.mvvm.viewmodel.UserViewModel;
import cn.lanhu.android_growth_plan.utils.sysrelated.CustomUtils;
import cn.lanhu.android_growth_plan.utils.sysrelated.IntentUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.ToastUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.rxui.RxViewUtils;
import cn.lanhu.android_growth_plan.widget.CustomerOptionsPickerView;
import rx.Subscriber;

public class SupplementaryActvity extends BaseBindingActivity<ActivitySupplementaryBinding> {

    private UserViewModel mUserViewModel;
    private ArrayList<String> mSex = new ArrayList<>();
    private ArrayList<String> mIdentity = new ArrayList<>();

    {
        mSex.add("男");
        mSex.add("女");
    }

    {
        mIdentity.add("爸爸");
        mIdentity.add("妈妈");
        mIdentity.add("爷爷/外公");
        mIdentity.add("奶奶/外婆");
        mIdentity.add("其他");
    }

    private LoginEntity mLoginEntity;
    private String flag;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_supplementary;
    }

    @Override
    protected void initView() {
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mLoginEntity = getIntent().getParcelableExtra("loginEntity");
        flag = getIntent().getStringExtra("flag");

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        RxViewUtils.onViewClick(mVB.btnSeemore, () -> {
            if (verify()) {
                if (flag != null && flag.equals("change")) {

                } else {
                    mUserViewModel.uploadInfo(mLoginEntity.getUser().getId(),
                            mVB.tvPassword.getText().toString(),
                            mVB.tvNicheng.getText().toString(),
                            mIdentity.indexOf(mVB.tvShenfen.getText().toString()) + "",
                            mVB.tvBrith.getText().toString().replace("-", ""),
                            mVB.tvCity.getText().toString(),
                            mVB.tvBabyNicheng.getText().toString(),
                            mVB.tvSex.getText().toString().equals("男") ? "0" : "1",
                            mVB.tcBabyBirth.getText().toString().replace("-", ""),
                            "",
                            mVB.tvMengxiang.getText().toString())
                            .subscribe(new Subscriber<BaseBean<LoginEntity>>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable throwable) {

                                }

                                @Override
                                public void onNext(BaseBean<LoginEntity> loginEntityBaseBean) {
                                    if (loginEntityBaseBean.getCode().equals("0")) {
                                        LoginEntity loginEntity = loginEntityBaseBean.getData();
                                        mUserViewModel.userLogin
                                                (loginEntity);
                                        new IntentUtils.Builder(mContext)
                                                .setTargetActivity(MainActivity.class)
                                                .build().startActivity(true);
                                    } else {
                                        ToastUtils.showToast(loginEntityBaseBean.getMsg());
                                    }
                                }
                            });
                }
            }
        });

        RxViewUtils.onViewClick(mVB.tvNicheng,() ->{
           new IntentUtils.Builder(mContext)
                   .setTargetActivity(WriteInfoActivity.class)
                   .setStringExtra("title","您的信息")
                   .setStringExtra("flag","1")
                   .build()
                   .startActivityForResult(101);
        });

        RxViewUtils.onViewClick(mVB.tvBabyNicheng,() ->{
            new IntentUtils.Builder(mContext)
                    .setTargetActivity(WriteInfoActivity.class)
                    .setStringExtra("title","孩子昵称")
                    .setStringExtra("flag","2")
                    .build()
                    .startActivityForResult(101);
        });

        RxViewUtils.onViewClick(mVB.tvMengxiang,() ->{
            new IntentUtils.Builder(mContext)
                    .setTargetActivity(WriteInfoActivity.class)
                    .setStringExtra("title","孩子梦想")
                    .setStringExtra("flag","3")
                    .build()
                    .startActivityForResult(101);
        });

        RxViewUtils.onViewClick(mVB.tvShenfen, () -> {

        });

        RxViewUtils.onViewClick(mVB.tvBrith, () -> {

        });

        RxViewUtils.onViewClick(mVB.tcBabyBirth, () -> {

        });

        RxViewUtils.onViewClick(mVB.tvShenfen, () -> {

        });

        RxViewUtils.onViewClick(mVB.tvSex, () -> {

        });

//        RxViewUtils.onViewClick(mVB.tvMengxiang, () -> {
//            if (mProfession.size() > 0) {
//                OptionsPickerView optionsPickerView = mProfessionView.build();
//                optionsPickerView.setPicker(mProfessions);
//                optionsPickerView.show();
//            } else {
//                mUserViewModel.getProvince().subscribe(new Subscriber<BaseBean<ListEntity>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onNext(BaseBean<ListEntity> baseBean) {
//                        if (baseBean.getCode().equals("0")) {
//                            if (baseBean.getData() != null) {
//                                mProvince.clear();
//                                mClasss.clear();
//                                mClassId.clear();
//                                mProfession.clear();
//                                mProfessions.clear();
//                                mProvince.addAll(baseBean.getData().getProvince());
//                                mClassId.addAll(baseBean.getData().getClassId());
//                                mProfession.addAll(baseBean.getData().getProfession());
//                                for (ListEntity.Bean bean : mProfession) {
//                                    mProfessions.add(bean.getName());
//                                }
//
//                                for (ListEntity.Bean bean : mClassId) {
//                                    mClasss.add(bean.getName());
//                                }
//                                OptionsPickerView optionsPickerView = mProfessionView.build();
//                                optionsPickerView.setPicker(mProfessions);
//                                optionsPickerView.show();
//                            }
//                        } else {
//                            ToastUtils.showToast(baseBean.getMsg());
//                        }
//                    }
//                });
//            }
//        });

        RxViewUtils.onViewClick(mVB.tvClass, () -> {

        });

        RxViewUtils.onViewClick(mVB.tvCity, () -> {

        });

    }

    private boolean verify() {
        if (TextUtils.isEmpty(mVB.tvNicheng.getText().toString())) {
            ToastUtils.showToast("昵称不能为空");
            return false;
        } else if (TextUtils.isEmpty(mVB.tvShenfen.getText().toString())) {
            ToastUtils.showToast("身份不能为空");
            return false;
        } else if (TextUtils.isEmpty(mVB.tvBrith.getText().toString())) {
            ToastUtils.showToast("生日不能为空");
            return false;
        } else if (TextUtils.isEmpty(mVB.tvCity.getText().toString())) {
            ToastUtils.showToast("城市不能为空");
            return false;
        } else if (TextUtils.isEmpty(mVB.tvPassword.getText().toString())) {
            ToastUtils.showToast("密码不能为空");
            return false;
        }  else if (mVB.tvPassword.getText().toString().length() < 6 || mVB.tvPassword.getText().toString().length() > 18) {
            ToastUtils.showToast("密码不能小于6位或者大于18位");
            return false;
        } else if (TextUtils.isEmpty(mVB.tvBabyNicheng.getText().toString())) {
            ToastUtils.showToast("宝贝昵称不能为空");
            return false;
        } else if (TextUtils.isEmpty(mVB.tvSex.getText().toString())) {
            ToastUtils.showToast("宝贝性别不能为空");
            return false;
        } else if (TextUtils.isEmpty(mVB.tcBabyBirth.getText().toString())) {
            ToastUtils.showToast("宝贝生日不能为空");
            return false;
        } else if (TextUtils.isEmpty(mVB.tvClass.getText().toString())) {
            ToastUtils.showToast("宝贝年纪不能为空");
            return false;
        } else if (TextUtils.isEmpty(mVB.tvMengxiang.getText().toString())) {
            ToastUtils.showToast("宝贝梦想不能为空");
            return false;
        }
        return true;
    }

    @Override
    protected void destroyView() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101) {
            if(resultCode == 1) {
                mVB.tvNicheng.setText(data.getStringExtra("content"));
            } else if(resultCode == 2) {
                mVB.tvBabyNicheng.setText(data.getStringExtra("content"));
            }else if(resultCode == 3) {
                mVB.tvMengxiang.setText(data.getStringExtra("content"));
            }
        }
    }
}
