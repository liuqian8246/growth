package cn.lanhu.android_growth_plan.gaiban.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.orhanobut.logger.Logger;

import cn.lanhu.android_growth_plan.gaiban.app.config.Constants;
import cn.lanhu.android_growth_plan.net.rxevent.RxBus;
import cn.lanhu.android_growth_plan.net.rxevent.RxCodeConstants;
import cn.lanhu.android_growth_plan.utils.sysrelated.NetWorkStateUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.ToastUtils;

/**
 * Created by yx on 2017/8/10.
 * 网络状态改变监听广播
 */

public class NetStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (NetWorkStateUtils.isNetworkConnected()) {
            RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE,RxCodeConstants.TYPE_NETWORK_CHANGED_CONNECTED);

        } else {
            Logger.w("NetStateReceiver NO_NETWORK");
            ToastUtils.showToast(Constants.NO_NETWORK);
        }

    }
}
