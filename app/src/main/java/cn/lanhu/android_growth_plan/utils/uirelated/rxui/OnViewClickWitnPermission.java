package cn.lanhu.android_growth_plan.utils.uirelated.rxui;

import com.tbruyelle.rxpermissions.Permission;

/**
 * Created by yx on 2017/8/24.
 * 控件点击需要权限回调
 */

public interface OnViewClickWitnPermission {

    void onPermission(Permission permission);
}
