package cn.lanhu.android_growth_plan.net;

/**
 * Created by yx on 2017/8/18.
 * retrofit请求回调
 */

public interface RetrofitNetListener<T> {

    void start();

    void complete();

    void error(Throwable e);

    void success(T t);

}
