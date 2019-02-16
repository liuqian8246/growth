package cn.lanhu.android_growth_plan.gaiban.mvvm.model.api;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Map;

import cn.lanhu.android_growth_plan.entity.DaohangEntity;
import cn.lanhu.android_growth_plan.entity.DateEntity;
import cn.lanhu.android_growth_plan.entity.ListEntity;
import cn.lanhu.android_growth_plan.entity.LoginEntity;
import cn.lanhu.android_growth_plan.entity.UserInfo;
import cn.lanhu.android_growth_plan.entity.base.BaseBean;
import cn.lanhu.android_growth_plan.gaiban.app.config.RxApplication;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseModel;
import cn.lanhu.android_growth_plan.net.RetrofitUtils;
import cn.lanhu.android_growth_plan.net.rxevent.RxBus;
import cn.lanhu.android_growth_plan.net.rxevent.RxCodeConstants;
import cn.lanhu.android_growth_plan.utils.uirelated.ToastUtils;
import okhttp3.MultipartBody;
import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserModel extends BaseModel {

    private static volatile UserModel instance;

    public static UserModel getInstance() {
        if (instance == null) {
            synchronized (UserModel.class) {
                if (instance == null) {
                    instance = new UserModel();
                }
            }
        }
        return instance;
    }

    private UserModel() {
    }

    public Observable<BaseBean<String>> getYzCode(Map<String, String> map) {
        return RetrofitUtils.getInstance().getYzCode(map);
    }

    /**
     * 注册账号
     *
     * @param
     * @return
     */
    public Observable<BaseBean<String>> registerAccount(String mobile, String password, String verify) {
        return RetrofitUtils.getInstance().registerAccount(mobile, password, verify);
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    public Observable<BaseBean<ListEntity>> getProvince() {
        return RetrofitUtils.getInstance().getProvince();
    }

    /**
     * 补充信息
     *
     * @param id
     * @param nickname
     * @param relation
     * @param birthday
     * @param province
     * @param bbnickname
     * @param sex
     * @param bbbirthday
     * @param classId
     * @param professionId
     * @return
     */
    public Observable<BaseBean<LoginEntity>> uploadInfo(String id, String password, String nickname, String relation, String birthday, String province, String bbnickname, String sex, String bbbirthday, String classId, String professionId) {
        return RetrofitUtils.getInstance().uploadInfo(id, password, nickname, relation, birthday, province, bbnickname, sex, bbbirthday, classId, professionId);
    }

    /**
     * yzm登陆
     */
    public Observable<BaseBean<LoginEntity>> verifyLogin(String mobile, String verify) {
        return RetrofitUtils.getInstance().verifyLogin(mobile, verify);
    }

    public Observable<BaseBean<LoginEntity>> passwordLogin(String mobile, String verify) {
        return RetrofitUtils.getInstance().passwordLogin(mobile, verify);
    }

    public void userLogin(LoginEntity loginEntity) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(loginEntity.getUser().getId());
        userInfo.setMobile(loginEntity.getUser().getMobile());
        userInfo.setInfoStatus(loginEntity.getUser().getInfoStatus());
        userInfo.setHeadPicUrl(loginEntity.getUser().getHeadPicUrl());
        userInfo.setNickname(loginEntity.getUser().getNickname());
        userInfo.setProvince(loginEntity.getUser().getProvince());
        userInfo.setRelation(loginEntity.getUser().getRelation());
        userInfo.setBirthday(loginEntity.getUser().getBirthday());

        userInfo.setBaby_id(loginEntity.getBaby().getId());
        userInfo.setBaby_userId(loginEntity.getBaby().getUserId());
        userInfo.setBaby_grownNo(loginEntity.getBaby().getGrownNo());
        userInfo.setBaby_nickname(loginEntity.getBaby().getNickname());
        userInfo.setBaby_sex(loginEntity.getBaby().getSex());
        userInfo.setBaby_birthday(loginEntity.getBaby().getBirthday());
        userInfo.setBaby_classId(loginEntity.getBaby().getClassId());
        userInfo.setBaby_className(loginEntity.getBaby().getClassName());
        userInfo.setBaby_professionId(loginEntity.getBaby().getProfessionId());
        userInfo.setBaby_professionName(loginEntity.getBaby().getProfessionName());
        RxApplication.getInstance().saveUserInfo(userInfo);
        RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE, RxCodeConstants.TYPE_SIGN_SUC);
        ToastUtils.showToast("登录成功");
    }

    //判断用户是否登录
    public boolean isUserLogin() {
        return RxApplication.getInstance().isLogin();
    }

    //获取用户id
    public String getUserId() {
        String uid = RxApplication.getInstance().getProperty("user.uid");
        return uid == null ? "" : uid;
    }

    //获取用户mobile
    public String getMobile() {
        String uid = RxApplication.getInstance().getProperty("user.mobile");
        return uid == null ? "" : uid;
    }

    //获取用户Salt
    public String getSalt() {
        String uid = RxApplication.getInstance().getProperty("user.salt");
        return uid == null ? "" : uid;
    }

    public UserInfo getUserInfo() {
        return RxApplication.getInstance().getLoginUser();
    }

    public Observable<BaseBean<String>> forgetPassword(String phone, String yzm, String password) {
        return RetrofitUtils.getInstance().forgetPassword(phone, yzm, password);
    }

    public Observable<BaseBean<String>> sign(String uid) {
        return RetrofitUtils.getInstance().sign(getUserId(), uid);
    }

    public Observable<BaseBean<ArrayList<DateEntity>>> getSign(String uid) {
        return RetrofitUtils.getInstance().getSign(getUserId(), uid);
    }

    public Observable<BaseBean<String>> uploadHead(MultipartBody.Part photo) {
        return RetrofitUtils.getInstance().uploadHead(getUserId(), photo)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BaseBean<ArrayList<String>>> getBanner() {
        return RetrofitUtils.getInstance().getBanner()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BaseBean<ArrayList<DaohangEntity>>> navigation() {
        return RetrofitUtils.getInstance().navigation()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
