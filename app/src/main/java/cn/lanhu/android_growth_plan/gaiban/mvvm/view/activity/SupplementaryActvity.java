package cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity;

import android.arch.lifecycle.ViewModelProviders;
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
    private ArrayList<String> mProvince = new ArrayList<>();
    private ArrayList<ListEntity.Bean> mClassId = new ArrayList<>();
    private ArrayList<String> mClasss = new ArrayList<>();
    //    private ArrayList<ListEntity.Bean> mProfession = new ArrayList<>();
//    private ArrayList<String> mProfessions = new ArrayList<>();
    private ArrayList<String> mSex = new ArrayList<>();
    private ArrayList<String> mIdentity = new ArrayList<>();
    private DateViewModel mDateViewModel;
    private String andl;

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


    private TimePickerView.Builder mTime;
    private TimePickerView.Builder mBabyTime;
    private OptionsPickerView.Builder mIdView;
    private OptionsPickerView.Builder mISexView;
    private OptionsPickerView.Builder mCityView;
    private OptionsPickerView.Builder mClassView;
//    private OptionsPickerView.Builder mProfessionView;

    private LoginEntity mLoginEntity;
    private String flag;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_supplementary;
    }

    @Override
    protected void initView() {
        mDateViewModel = ViewModelProviders.of(this).get(DateViewModel.class);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mLoginEntity = getIntent().getParcelableExtra("loginEntity");
        flag = getIntent().getStringExtra("flag");
        getlist();
        if (flag != null && flag.equals("change")) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.dip_200));
            lp.setMargins((int) getResources().getDimension(R.dimen.dip_11), (int) getResources().getDimension(R.dimen.dip_20), (int) getResources().getDimension(R.dimen.dip_11), 0);
            mVB.llUser.setLayoutParams(lp);
            mVB.llPassword.setVisibility(View.GONE);
            mVB.tvNicheng.setText(mUserViewModel.getUserInfo().getNickname());
            mVB.tvShenfen.setText(mIdentity.get(Integer.valueOf(mUserViewModel.getUserInfo().getRelation())));
            mVB.tvBrith.setText(CustomUtils.getFormatDate(mUserViewModel.getUserInfo().getBirthday(), "yyyyMMdd", "yyyy-MM-dd"));
            mVB.tvCity.setText(mUserViewModel.getUserInfo().getProvince());
            mVB.tvBabyNicheng.setText(mUserViewModel.getUserInfo().getBaby_nickname());
            mVB.tvSex.setText(mUserViewModel.getUserInfo().getBaby_sex().equals("1") ? "男" : "女");
            mVB.tcBabyBirth.setText(CustomUtils.getFormatDate(mUserViewModel.getUserInfo().getBaby_birthday(), "yyyyMMdd", "yyyy-MM-dd"));
            mVB.tvClass.setText(mUserViewModel.getUserInfo().getBaby_className());
            mVB.tvMengxiang.setText(mUserViewModel.getUserInfo().getBaby_professionName());
        }
    }

    private void getlist() {
        mUserViewModel.getProvince().subscribe(new Subscriber<BaseBean<ListEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(BaseBean<ListEntity> baseBean) {
                if (baseBean.getCode().equals("0")) {
                    if (baseBean.getData() != null) {
                        mProvince.addAll(baseBean.getData().getProvince());
                        mClassId.addAll(baseBean.getData().getClassId());
//                        mProfession.addAll(baseBean.getData().getProfession());
//                        for (ListEntity.Bean bean : mProfession) {
//                            mProfessions.add(bean.getName());
//                        }

                        for (ListEntity.Bean bean : mClassId) {
                            mClasss.add(bean.getName());
                        }
                    }
                } else {
                    ToastUtils.showToast(baseBean.getMsg());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initTime();
        initBabyTime();
        initIdView();
        initCityView();
        initSexView();
        initClassView();
//        initProfessionView();
    }

    private void initClassView() {
        if (mClassView == null) {
            mDateViewModel.initOptionsPickerViewBuidler()
                    .observe(this, s -> {
                        String[] str = s.split("-");
                        mVB.tvClass.setText(mClassId.get(Integer.valueOf(str[0])).getName());
                    });
        }
        mClassView = mDateViewModel.getOptionsPickerViewBuider();
    }

//    private void initProfessionView() {
//        if (mProfessionView == null) {
//            mDateViewModel.initOptionsPickerViewBuidler()
//                    .observe(this, s -> {
//                        String[] str = s.split("-");
//                        mVB.tvMengxiang.setText(mProfession.get(Integer.valueOf(str[0])).getName());
//                    });
//        }
//        mProfessionView = mDateViewModel.getOptionsPickerViewBuider();
//    }

    private int getPosition(ArrayList<ListEntity.Bean> arrayList, String name) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getName().equals(name)) {
                return i;
            }
        }
        return 0;
    }

    private void initSexView() {
        if (mISexView == null) {
            mDateViewModel.initOptionsPickerViewBuidler()
                    .observe(this, s -> {
                        String[] str = s.split("-");
                        mVB.tvSex.setText(mSex.get(Integer.valueOf(str[0])));
                    });
        }
        mISexView = mDateViewModel.getOptionsPickerViewBuider();
    }

    private void initCityView() {
        if (mCityView == null) {
            mDateViewModel.initOptionsPickerViewBuidler()
                    .observe(this, s -> {
                        String[] str = s.split("-");
                        mVB.tvCity.setText(mProvince.get(Integer.valueOf(str[0])));
                    });
        }
        mCityView = mDateViewModel.getOptionsPickerViewBuider();
    }

    private void initIdView() {
        if (mIdView == null) {
            mDateViewModel.initOptionsPickerViewBuidler()
                    .observe(this, s -> {
                        String[] str = s.split("-");
                        mVB.tvShenfen.setText(mIdentity.get(Integer.valueOf(str[0])));
                    });
        }
        mIdView = mDateViewModel.getOptionsPickerViewBuider();
    }

    private void initBabyTime() {
        if (mBabyTime == null) {
            mDateViewModel.initTimePickerViewBuilder(2).observe(this, date -> {
                String[] str = date.split("-");
                if (str.length >= 2) {
                    mVB.tcBabyBirth.setText(date);
                }
            });
            mBabyTime = mDateViewModel.getTimePickerViewBuilder();
        }
    }

    private void initTime() {
        if (mTime == null) {
            mDateViewModel.initTimePickerViewBuilder(2).observe(this, date -> {
                String[] str = date.split("-");
                if (str.length >= 2) {
                    mVB.tvBrith.setText(date);
                }
            });
            mTime = mDateViewModel.getTimePickerViewBuilder();
        }
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
                            mClassId.get(getPosition(mClassId, mVB.tvClass.getText().toString())).getId(),
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

        RxViewUtils.onViewClick(mVB.tvShenfen, () -> {

        });

        RxViewUtils.onViewClick(mVB.tvBrith, () -> {
            mTime.setRange(1970, 2050)
                    .setType(new boolean[]{true, true, true, false, false, false})
                    .build().show();
        });

        RxViewUtils.onViewClick(mVB.tcBabyBirth, () -> {
            mBabyTime.setRange(1970, 2050)
                    .setType(new boolean[]{true, true, true, false, false, false})
                    .build().show();
        });

        RxViewUtils.onViewClick(mVB.tvShenfen, () -> {
            OptionsPickerView optionsPickerView = mIdView.build();
            optionsPickerView.setPicker(mIdentity);
            optionsPickerView.show();
        });

        RxViewUtils.onViewClick(mVB.tvSex, () -> {
            OptionsPickerView optionsPickerView = mISexView.build();
            optionsPickerView.setPicker(mSex);
            optionsPickerView.show();
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
            if (mClassId.size() > 0) {
                OptionsPickerView optionsPickerView = mClassView.build();
                optionsPickerView.setPicker(mClasss);
                optionsPickerView.show();
            } else {
                mUserViewModel.getProvince().subscribe(new Subscriber<BaseBean<ListEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(BaseBean<ListEntity> baseBean) {
                        if (baseBean.getCode().equals("0")) {
                            if (baseBean.getData() != null) {
                                mProvince.clear();
                                mClasss.clear();
                                mClassId.clear();
//                                mProfession.clear();
//                                mProfessions.clear();
                                mProvince.addAll(baseBean.getData().getProvince());
                                mClassId.addAll(baseBean.getData().getClassId());
//                                mProfession.addAll(baseBean.getData().getProfession());
//                                for (ListEntity.Bean bean : mProfession) {
//                                    mProfessions.add(bean.getName());
//                                }

                                for (ListEntity.Bean bean : mClassId) {
                                    mClasss.add(bean.getName());
                                }
                                OptionsPickerView optionsPickerView = mClassView.build();
                                optionsPickerView.setPicker(mClasss);
                                optionsPickerView.show();
                            }
                        } else {
                            ToastUtils.showToast(baseBean.getMsg());
                        }
                    }
                });
            }
        });

        RxViewUtils.onViewClick(mVB.tvCity, () -> {
            if (mProvince.size() > 0) {
                OptionsPickerView optionsPickerView = mCityView.build();
                optionsPickerView.setPicker(mProvince);
                optionsPickerView.show();
            } else {
                mUserViewModel.getProvince().subscribe(new Subscriber<BaseBean<ListEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(BaseBean<ListEntity> baseBean) {
                        if (baseBean.getCode().equals("0")) {
                            if (baseBean.getData() != null) {
                                mProvince.clear();
                                mClasss.clear();
                                mClassId.clear();
//                                mProfession.clear();
//                                mProfessions.clear();
                                mProvince.addAll(baseBean.getData().getProvince());
                                mClassId.addAll(baseBean.getData().getClassId());
//                                mProfession.addAll(baseBean.getData().getProfession());
//                                for (ListEntity.Bean bean : mProfession) {
//                                    mProfessions.add(bean.getName());
//                                }

                                for (ListEntity.Bean bean : mClassId) {
                                    mClasss.add(bean.getName());
                                }
                                OptionsPickerView optionsPickerView = mClassView.build();
                                optionsPickerView.setPicker(mClassId);
                                optionsPickerView.show();
                            }
                        } else {
                            ToastUtils.showToast(baseBean.getMsg());
                        }
                    }
                });
            }
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
}
