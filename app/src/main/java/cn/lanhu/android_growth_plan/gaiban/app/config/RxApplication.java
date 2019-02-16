package cn.lanhu.android_growth_plan.gaiban.app.config;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.ArraySet;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.litepal.LitePalApplication;

import java.lang.ref.WeakReference;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import cn.lanhu.android_growth_plan.entity.UserInfo;
import cn.lanhu.android_growth_plan.gaiban.app.cache.DataCleanManager;
import cn.lanhu.android_growth_plan.gaiban.app.service.AppInitService;
import cn.lanhu.android_growth_plan.net.NetWorkApi;
import cn.lanhu.android_growth_plan.net.RXNetService;
import cn.lanhu.android_growth_plan.utils.filerelated.FileUtils;
import cn.lanhu.android_growth_plan.utils.filerelated.SpUtils;
import cn.lanhu.android_growth_plan.utils.trilateralrelated.LogUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.ToastUtils;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by yx on 2016/7/12.
 * 启动Application
 */
public class RxApplication extends LitePalApplication {

    public static RxApplication instance;
    private Context mContext;
    private Resources mResources;
    private int mLoginUid;
    private boolean mIsLogin;
    private RXNetService mRxNetServiceApi;
    private int mActivityRefCount = 0;//actvity的引用个数

    private Activity mTopActivity;//栈顶activty
    private String mTopActivityName;//栈顶activity名称

    public static synchronized RxApplication getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
        initLogin();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // TODO: 2017/10/13

    }


    private void init() {
        mContext = RxApplication.getInstance().getApplicationContext();
        mResources = mContext.getResources();
        mRxNetServiceApi = NetWorkApi.getInstance().gradleRetrofit(this).create(RXNetService.class);
        SpUtils.init(mContext);//初始化sp工具
        AppInitService.startService(this);
        initActivityLifecycle();
    }

    private boolean pushDialogIsShow = false;

    public boolean isPushDialogIsShow() {
        return pushDialogIsShow;
    }

    public void setPushDialogIsShow(boolean pushDialogIsShow) {
        this.pushDialogIsShow = pushDialogIsShow;
    }

    public void initLogin() {
        Observable.just(getLoginUser())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.immediate())
                .subscribe(user -> {
                    if(user != null && !user.getId().isEmpty()) {
                        mIsLogin = true;
                    }
                });


    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    private Set<Activity> mActivitySet = new ArraySet<>();
    private WeakReference<Set<Activity>> mWpSet = new WeakReference<>(mActivitySet);//set去重

    public Set<Activity> getActivityCountSet() {
        return mActivitySet;
    }

    /**
     * 注册activity生命周期
     * 1.目前主要可以统计activity的引用个数,如果refcount=0,表示程序处于后台
     */
    private void initActivityLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                mActivityRefCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {
                mWpSet.get().add(activity);
                mTopActivityName = activity.getComponentName().getClassName();
                mTopActivity = activity;
                Logger.w("mTopActivityName :" + mTopActivityName);

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                mActivityRefCount--;

                mWpSet.get().remove(activity);
                mActivityRefCount--;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public synchronized Context getmContext() {
        return mContext;
    }

    public synchronized Resources getmResources() {
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        mResources.updateConfiguration(configuration, mResources.getDisplayMetrics());
        return mResources;
    }


    public synchronized RXNetService getRetrogitService() {
        return mRxNetServiceApi;
    }

    public synchronized int getActivityRefCount() {
        return mActivityRefCount;
    }

    public Activity getTopActivity() {
        return mTopActivity;
    }

    public String getTopActivityName() {
        return mTopActivityName;
    }

    public boolean containsProperty(String key) {
        Properties props = getProperties();
        return props.containsKey(key);
    }

    public void setProperties(Properties ps) {
        AppConfig.getAppConfig(this).set(ps);
    }

    public Properties getProperties() {
        return AppConfig.getAppConfig(this).get();
    }

    public void setProperty(String key, String value) {
        AppConfig.getAppConfig(this).set(key, value);
    }

    /**
     * 获取cookie时传AppConfig.CONF_COOKIE
     */
    public String getProperty(String key) {
        return AppConfig.getAppConfig(this).get(key);
    }

    public void removeProperty(String... key) {
        AppConfig.getAppConfig(this).remove(key);
    }

    /**
     * 获取App唯一标识
     */
    public String getAppId() {
        String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID);
        if (TextUtils.isEmpty(uniqueID)) {
            uniqueID = UUID.randomUUID().toString();
            setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
        }
        return uniqueID;
    }

    private String deviceId;

    @SuppressLint("MissingPermission")
    public String getDeviceImei() {
        new RxPermissions(getTopActivity()).request(Manifest.permission.READ_PHONE_STATE)
                .subscribe(grated -> {
                    if (grated) {
                        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                        deviceId = tm.getDeviceId();
                    } else {
                        deviceId = "";
                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.w("获取手机状态权限失败.");
                        ToastUtils.showToast("获取手机状态权限失败.");
                    }
                });
        return deviceId;

    }


    /**
     * 保存登录信息
     *
     * @param
     */
    @SuppressWarnings("serial")
    public void saveUserInfo(UserInfo userInfo) {
        this.mIsLogin = true;
        setProperties(new Properties() {
            {
                setProperty("user.uid", userInfo.getId());
                setProperty("user.mobile", userInfo.getMobile());
                setProperty("user.infoStatus", userInfo.getInfoStatus());
                setProperty("user.headPicUrl", userInfo.getHeadPicUrl());
                setProperty("user.nickname", userInfo.getNickname());
                setProperty("user.province", userInfo.getProvince());
                setProperty("user.birthday", userInfo.getBirthday());
                setProperty("user.baby_id", userInfo.getBaby_id());
                setProperty("user.baby_userId", userInfo.getBaby_userId());
                setProperty("user.baby_grownNo", userInfo.getBaby_grownNo());
                setProperty("user.baby_nickname", userInfo.getBaby_nickname());
                setProperty("user.baby_sex", userInfo.getBaby_sex());
                setProperty("user.baby_birthday", userInfo.getBaby_birthday());
                setProperty("user.baby_classId", userInfo.getBaby_classId());
                setProperty("user.baby_className", userInfo.getBaby_className());
                setProperty("user.baby_professionId", userInfo.getBaby_professionId());
                setProperty("user.baby_professionName", userInfo.getBaby_professionName());
                setProperty("user.relation", userInfo.getRelation());
                setProperty("user.baby_age", userInfo.getBaby_age());
            }
        });

    }

    /**
     * 更新用户信息
     */
    @SuppressWarnings("serial")
    public void updateUserInfo(final UserInfo userInfo) {
        setProperties(new Properties() {
            {
                setProperty("user.uid", userInfo.getId());
                setProperty("user.mobile", userInfo.getMobile());
                setProperty("user.infoStatus", userInfo.getInfoStatus());
                setProperty("user.headPicUrl", userInfo.getHeadPicUrl());
                setProperty("user.nickname", userInfo.getNickname());
                setProperty("user.province", userInfo.getProvince());
                setProperty("user.birthday", userInfo.getBirthday());
                setProperty("user.baby_id", userInfo.getBaby_id());
                setProperty("user.baby_userId", userInfo.getBaby_userId());
                setProperty("user.baby_grownNo", userInfo.getBaby_grownNo());
                setProperty("user.baby_nickname", userInfo.getBaby_nickname());
                setProperty("user.baby_sex", userInfo.getBaby_sex());
                setProperty("user.baby_birthday", userInfo.getBaby_birthday());
                setProperty("user.baby_classId", userInfo.getBaby_classId());
                setProperty("user.baby_className", userInfo.getBaby_className());
                setProperty("user.baby_professionId", userInfo.getBaby_professionId());
                setProperty("user.baby_professionName", userInfo.getBaby_professionName());
                setProperty("user.relation", userInfo.getRelation());
                setProperty("user.baby_age", userInfo.getBaby_age());
            }
        });
    }

    /**
     * 获得登录用户的信息
     */
    public UserInfo getLoginUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(getProperty("user.uid"));
        userInfo.setMobile(getProperty("user.mobile"));
        userInfo.setInfoStatus(getProperty("user.infoStatus"));
        userInfo.setHeadPicUrl(getProperty("user.headPicUrl"));
        userInfo.setNickname(getProperty("user.nickname"));
        userInfo.setProvince(getProperty("user.province"));
        userInfo.setRelation(getProperty("user.relation"));
        userInfo.setBirthday(getProperty("user.birthday"));

        userInfo.setBaby_id(getProperty("user.baby_id"));
        userInfo.setBaby_userId(getProperty("user.baby_userId"));
        userInfo.setBaby_grownNo(getProperty("user.baby_grownNo"));
        userInfo.setBaby_nickname(getProperty("user.baby_nickname"));
        userInfo.setBaby_sex(getProperty("user.baby_sex"));
        userInfo.setBaby_birthday(getProperty("user.baby_birthday"));
        userInfo.setBaby_classId(getProperty("user.baby_classId"));
        userInfo.setBaby_className(getProperty("user.baby_className"));
        userInfo.setBaby_professionId(getProperty("user.baby_professionId"));
        userInfo.setBaby_professionName(getProperty("user.baby_professionName"));
        userInfo.setBaby_age(getProperty("user_baby_age"));
        return userInfo;
    }

    /**
     * 清除登录信息
     */
    public void cleanLoginInfo() {
        this.mLoginUid = 0;
        this.mIsLogin = false;
        removeProperty("user.uid", "user.mobile", "user.infoStatus",
                "user.headPicUrl", "user.nickname", "user.province",
                "user.birthday", "user.baby_id", "user.baby_userId",
                "user.baby_userId", "user.baby_grownNo", "user.baby_nickname",
                "user.baby_sex", "user.baby_birthday", "user.baby_classId", "user.relation",
                "user.baby_className", "user.baby_professionId", "user.baby_professionId", "user.baby_professionName");
    }

    public int getLoginUid() {
        return mLoginUid;
    }

    public boolean isLogin() {
        return mIsLogin;
    }

    /**
     * 用户注销
     */
    public void Logout() {
        cleanLoginInfo();
        this.cleanCookie();
        this.mIsLogin = false;
        this.mLoginUid = 0;
    }


    /**
     * 清除保存的缓存
     */
    public void cleanCookie() {
        removeProperty(AppConfig.CONF_COOKIE);
    }

    /**
     * 清除app缓存
     */
    public void clearAppCache() {
//        DataCleanManager.cleanDatabases();
        // 清除数据缓存
        DataCleanManager.cleanInternalCache();
        FileUtils.deleteAvata();
//        DataCleanManager.cleanSharedPreference();
        // 清除编辑器保存的临时内容
        Properties props = getProperties();
        for (Object key : props.keySet()) {
            String _key = key.toString();
            if (_key.startsWith("temp"))
                removeProperty(_key);
        }
    }

}
