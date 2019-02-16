package cn.lanhu.android_growth_plan.gaiban.mvvm.viewmodel;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Map;

import cn.lanhu.android_growth_plan.entity.DaohangEntity;
import cn.lanhu.android_growth_plan.entity.DateEntity;
import cn.lanhu.android_growth_plan.entity.ListEntity;
import cn.lanhu.android_growth_plan.entity.LoginEntity;
import cn.lanhu.android_growth_plan.entity.UserInfo;
import cn.lanhu.android_growth_plan.entity.base.BaseBean;
import cn.lanhu.android_growth_plan.gaiban.app.config.RxApplication;
import cn.lanhu.android_growth_plan.gaiban.mvvm.model.api.UserModel;
import cn.lanhu.android_growth_plan.net.RetrofitUtils;
import okhttp3.MultipartBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserViewModel extends ViewModel {

    private UserModel mUserInfoModel;

    public UserViewModel() {
        mUserInfoModel = UserModel.getInstance();
    }

    /**
     * 获取注册的验证码
     *
     * @param map
     * @return
     */
    public Observable<BaseBean<String>> getYzCode(Map<String, String> map) {
        return mUserInfoModel.getYzCode(map);
    }

    /**
     * 注册账号
     *
     * @param
     * @return
     */
    public Observable<BaseBean<String>> registerAccount(String mobile, String password, String verify) {
        return mUserInfoModel.registerAccount(mobile, password, verify);
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    public Observable<BaseBean<ListEntity>> getProvince() {
        return mUserInfoModel.getProvince();
    }

    public Observable<BaseBean<LoginEntity>> uploadInfo(String id,String password, String nickname, String relation, String birthday, String province, String bbnickname, String sex, String bbbirthday, String classId, String professionId) {
        return mUserInfoModel.uploadInfo(id, password,nickname, relation, birthday, province, bbnickname, sex, bbbirthday, classId, professionId);
    }

    public Observable<BaseBean<LoginEntity>> verifyLogin(String mobile, String verify) {
        return mUserInfoModel.verifyLogin(mobile,verify);
    }

    public Observable<BaseBean<LoginEntity>> passwordLogin(String mobile, String verify) {
        return mUserInfoModel.passwordLogin(mobile,verify);
    }

    public void userLogin(LoginEntity loginEntity) {
        mUserInfoModel.userLogin(loginEntity);
    }

    public UserInfo getUserInfo() {
        return mUserInfoModel.getUserInfo();
    }

    //判断用户是否登录
    public boolean isUserLogin() {
        return mUserInfoModel.isUserLogin();
    }

    //获取用户id
    public String getUserId() {
       return mUserInfoModel.getUserId();
    }

    //获取用户mobile
    public String getMobile() {
        return mUserInfoModel.getMobile();
    }

    //获取用户Salt
    public String getSalt() {
       return mUserInfoModel.getSalt();
    }

    public Observable<BaseBean<String>> forgetPassword(String phone, String yzm, String password) {
        return mUserInfoModel.forgetPassword(phone,yzm,password);
    }

    public Observable<BaseBean<String>> sign(String uid) {
        return mUserInfoModel.sign(uid);
    }

    public Observable<BaseBean<ArrayList<DateEntity>>> getSign(String uid) {
        return mUserInfoModel.getSign(uid);
    }

    public Observable<BaseBean<String>> uploadHead( MultipartBody.Part photo) {
        return mUserInfoModel.uploadHead(photo)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BaseBean<ArrayList<String>>> getBanner() {
        return mUserInfoModel.getBanner()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BaseBean<ArrayList<DaohangEntity>>> navigation() {
        return mUserInfoModel.navigation()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
