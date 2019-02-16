package cn.lanhu.android_growth_plan.utils.filerelated;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yx on 2016/7/12.
 * sp工具类
 */
public final class SpUtils {

    private static SharedPreferences sp;
    private static final String SP_NAME = "config";

    public static void init(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
    }

    /**
     * 存储boolean型sp
     *
     * @param key
     * @param value
     */
    public static void putBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 获取boolean型sp
     *
     * @param key
     * @return
     */
    public static boolean getBoolean( String key) {
        return  sp.getBoolean(key, false);
    }

    /**
     * 存储String型sp
     *
     * @param key
     * @param value
     */
    public static void putString( String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    public static void putLong( String key, Long value) {
        sp.edit().putLong(key, value).commit();
    }

    /**
     * 获取String型sp
     *
     * @param key
     * @return
     */
    public static String getString( String key) {
        return sp.getString(key, "");
    }

    public static Long getLong( String key) {
        return sp.getLong(key, 0);
    }

    /**
     * 存储int型sp
     *
     * @param key
     * @param value
     */
    public static void putInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

    /**
     * 获取int型sp
     * @param key
     * @return
     */
    public static int getInt(String key) {
        return sp.getInt(key, -1);
    }


    public static void clearAll(String key) {
        sp.edit().remove(key).commit();
    }
}
