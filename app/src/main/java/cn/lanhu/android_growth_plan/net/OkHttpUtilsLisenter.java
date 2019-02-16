package cn.lanhu.android_growth_plan.net;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by xy on 2017/6/8.
 * okhttputils回调接口
 */

public interface OkHttpUtilsLisenter {

    void onRequestStart(Request request, int id);

    void onRequestComplete(int id);

    void onRequestError(Call call, Exception e, int id);

    void onRequestSuccess(String response, int id);

}
