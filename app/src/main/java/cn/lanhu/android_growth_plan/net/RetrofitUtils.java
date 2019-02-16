package cn.lanhu.android_growth_plan.net;


import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.lanhu.android_growth_plan.entity.DaohangEntity;
import cn.lanhu.android_growth_plan.entity.DateEntity;
import cn.lanhu.android_growth_plan.entity.ListEntity;
import cn.lanhu.android_growth_plan.entity.LoginEntity;
import cn.lanhu.android_growth_plan.entity.base.BaseBean;
import cn.lanhu.android_growth_plan.gaiban.app.config.RxApplication;
import okhttp3.MultipartBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yx on 2016/10/17.
 * retrofit加解密与网络请求类
 */

public class RetrofitUtils {

    private static volatile RetrofitUtils instance;

    private RXNetService mRxNetServiceApi = RxApplication.getInstance().getRetrogitService();


    public static RetrofitUtils getInstance() {
        if (instance == null) {
            synchronized (RetrofitUtils.class) {
                if (instance == null) {
                    instance = new RetrofitUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 获取注册的验证码
     *
     * @param map
     * @return
     */
    public Observable<BaseBean<String>> getYzCode(Map<String, String> map) {
        return mRxNetServiceApi.getYzCode(map)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 注册账号
     *
     * @param
     * @return
     */
    public Observable<BaseBean<String>> registerAccount(String mobile, String password, String verify) {
        return mRxNetServiceApi.registerAccount(RetrofitUtils.getInstance().getParamsMap("mobile", mobile, "password", password, "verify", verify))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取参数map集合
     *
     * @param str
     * @return
     */
    public Map<String, String> getParamsMap(String... str) {
        if (str.length < 2 && str.length % 2 != 0) {
            throw new IllegalStateException("参数个数必须是2的倍数!!!");
        }
        ArrayMap<String, String> map = new ArrayMap<>();
        for (int i = 0; i < str.length; i++) {
            if (i % 2 == 1) {
                map.put(str[i - 1], str[i]);
                //                Logger.wtf( str[i - 1] + " = " + str[i]);
            }
        }
        return map;
    }

    /**
     * 获取省份
     * @return
     */
    public Observable<BaseBean<ListEntity>> getProvince() {
        return mRxNetServiceApi.getProvince()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 补充信息
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
    public Observable<BaseBean<LoginEntity>> uploadInfo(String id, String password,String nickname, String relation, String birthday, String province, String bbnickname, String sex, String bbbirthday, String classId, String professionId) {
        Map<String,Map<String,String>> map = new HashMap<>();
        map.put("user",
                RetrofitUtils.getInstance().getParamsMap(
                        "id",id,"password",password,"nickname",nickname,"relation",relation,"birthday",birthday,"province",province));
        map.put("baby",
                RetrofitUtils.getInstance().getParamsMap(
                        "nickname",bbnickname,"sex",sex,"birthday",bbbirthday,"classId",classId,"profession",professionId));
        return mRxNetServiceApi.uploadInfo(map)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * yzm登陆
     * @param mobile
     * @param verify
     * @return
     */
    public Observable<BaseBean<LoginEntity>> verifyLogin(String mobile, String verify) {
        return mRxNetServiceApi.verifyLogin(RetrofitUtils.getInstance().getParamsMap(
                "mobile",mobile,"verify",verify))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 密码登陆
     * @param mobile
     * @param verify
     * @return
     */
    public Observable<BaseBean<LoginEntity>> passwordLogin(String mobile, String verify) {
        return mRxNetServiceApi.passwordLogin(RetrofitUtils.getInstance().getParamsMap(
                "mobile",mobile,"password",verify))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BaseBean<String>> forgetPassword(String phone, String yzm, String password) {
        return mRxNetServiceApi.forgetPassword(RetrofitUtils.getInstance().getParamsMap(
                "mobile",phone,"newPassword",password,"verify",yzm))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BaseBean<String>> sign(String userId,String uid) {
        return mRxNetServiceApi.sign(userId,RetrofitUtils.getInstance().getParamsMap(
                "userId",uid))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BaseBean<ArrayList<DateEntity>>> getSign(String userId,String uid) {
        return mRxNetServiceApi.getSign(userId,RetrofitUtils.getInstance().getParamsMap(
                "userId",uid))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BaseBean<String>> uploadHead(String userId,MultipartBody.Part photo) {
        HashMap<String,MultipartBody.Part> map = new HashMap<>();
        map.put("headImage",photo);
        return mRxNetServiceApi.uploadHead(userId,photo)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BaseBean<ArrayList<String>>> getBanner() {
        return mRxNetServiceApi.getBanner()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BaseBean<ArrayList<DaohangEntity>>> navigation() {
        return mRxNetServiceApi.navigation()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
