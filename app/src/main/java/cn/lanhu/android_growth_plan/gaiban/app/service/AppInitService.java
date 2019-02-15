package cn.lanhu.android_growth_plan.gaiban.app.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.squareup.leakcanary.LeakCanary;

import org.litepal.LitePal;

import cn.lanhu.android_growth_plan.BuildConfig;
import cn.lanhu.android_growth_plan.utils.trilateralrelated.LogUtils;

/**
 * Created by yx on 2016/11/10.
 * app启动intentService,
 * 用于处理耗时操作,加快app启动速度
 */

public class AppInitService extends IntentService {

    private static final String ACTION_INIT_WHEN_APP_CREATE = "service.action.INIT";

    public AppInitService() {
        super("AppInitService");
    }

    public static void startService(Context context) {
        Intent intent = new Intent(context, AppInitService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                hanldeInit();
            }
        }

    }

    private void hanldeInit() {
        //LitePal初始化
        LitePal.initialize(this.getApplicationContext());
        //init log
        LogUtils.initLog();
        initImagePicker();
        initLeakCanary();
    }


    private void initUmeng() {
        //友盟统计,友盟渠道设置
        if (BuildConfig.DEBUG) {
        }
    }

    private void initImagePicker() {

    }

    private void initLeakCanary() {

        if (LeakCanary.isInAnalyzerProcess(getApplicationContext())) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        if (BuildConfig.USE_CANARY) {
            LeakCanary.install(getApplication());
        }
    }

}