package cn.lanhu.android_growth_plan.net.rxevent;

/**
 * Created by yx on 2017/5/31.
 * rxbus消息常量
 */

public interface RxCodeConstants {
    int JUMP_TYPE = 0;//code多类型接收
    //    int JUMP_TYPE_TO_ONE = 1;//code单类型接收

    int TYPE_USER_LOGIN = 1;//登录
    int TYPE_USER_LOGOUT = 2;//登出
    int TYPE_USERINFO_CHANGED = 3;//用户信息改变
    int TYPE_REVISE_PWD_SUCCESS = 4;//修改密码成功
    int TYPE_NETWORK_CHANGED_CONNECTED = 5;//网络状态改变并且有网络时

    int TYPE_SUBJECT_CHANGE = 6;//科目切换
    int TYPE_GRADE_CHANGE = 7;//年纪切换
    int TYPE_SIGN_SUC = 8;//签到成功
    int TYPE_INFO_CHANGE = 9;//信息修改
    int TYPE_ANACCOUNT_PICK_PHOTO = 10;//选择图片
}
