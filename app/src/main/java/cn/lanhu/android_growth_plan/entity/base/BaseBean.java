package cn.lanhu.android_growth_plan.entity.base;

/**
 * Created by lq on 2016/11/11.
 *  基本实体类
 */

public class BaseBean<T> {

    private String code;
    private String msg;
    private T data;

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public T getData() {
        return data;
    }
}
