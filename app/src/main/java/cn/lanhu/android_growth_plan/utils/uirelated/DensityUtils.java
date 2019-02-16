package cn.lanhu.android_growth_plan.utils.uirelated;

import android.content.Context;

import cn.lanhu.android_growth_plan.gaiban.app.config.RxApplication;

/**
 * Created by yx on 2016/7/12.
 * dip转px工具
 */
public class DensityUtils {

    /**
     * dip转px
     *
     * @param dpValue
     * @return
     */
    public static int dip2px(float dpValue) {
        Context context = RxApplication.getInstance().getmContext();
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    public static int dip2px(Context context,float dpValue) {
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * px转dip
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
