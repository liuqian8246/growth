package cn.lanhu.android_growth_plan.utils.sysrelated;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;

import com.orhanobut.logger.Logger;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.lanhu.android_growth_plan.gaiban.app.config.Cheese;
import cn.lanhu.android_growth_plan.gaiban.app.config.RxApplication;

/**
 * Created by yx on 2017/9/19.
 * 通用的工具类,方便调用,想到什么写什么吧
 */

public class CustomUtils {

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) RxApplication.getInstance().getmContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }


    /**
     * 显示软键盘
     */
    public static void showSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) RxApplication.getInstance().getmContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.showSoftInput(view, 0);
        }
    }

    /**
     * 隐藏或显示软键盘
     */
    public static void hideOrShowSoftInput() {
        InputMethodManager imm = (InputMethodManager) RxApplication.getInstance().getmContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    public static String getProvinceFromList(String list) {
        String[] temp = list.split(",");
        return temp[temp.length - 1];
    }

    public static String getOther(String list) {
        String[] temp = list.split(",");
        if (temp.length < 3) {
            return "";
        } else {
            return temp[1];
        }
    }

    public static String getFormatTime(String time) {
        String str = "";
        if (time != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            SimpleDateFormat ms = new SimpleDateFormat("HH : mm");
            try {
                Date date = sdf.parse(time);
                str = ms.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return time;
            }
        }
        return str;
    }

    public static String getFormatTimeSecond(String time) {
        String str = "";
        if (time != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date(Long.valueOf(time));
            str = sdf.format(date);
        }
        return str;
    }

    public static String getFormatHour(String time) {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        SimpleDateFormat ms = new SimpleDateFormat("HH");
        try {
            Date date = sdf.parse(time);
            str = ms.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str + "时";
    }

    public static String getColor(int str) {
        switch (str) {
            case 1:
                return "#a2f93d";
            case 2:
                return "#f9e535";
            case 3:
                return "#f79b16";
            case 4:
                return "#d34452";
            case 5:
                return "#b22f57";
            case 6:
                return "#911717";
            default:
                return "#a2f93d";
        }
    }

    public static String getFormatDate(String str) {
        String temp = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat ms = new SimpleDateFormat("MM/dd");
        try {
            Date date = sdf.parse(str);
            temp = ms.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return str;
        }
        return temp;
    }

    public static String getFormatDate(String str, String format, String srcFormat) {
        String temp = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        SimpleDateFormat ms = new SimpleDateFormat(srcFormat);
        try {
            Date date = sdf.parse(str);
            temp = ms.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return str;
        }
        return temp;
    }

    public static String getWeekFrom(String str) {
        String temp = "";
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        try {
            Date date = sdf.parse(str);
            //判断date有没有昨天
            int differentDays = differentDays(today, date);
            Logger.w("differentDays = " + differentDays);
            if (differentDays == -1) {
                temp = "昨天";
            } else if (differentDays == 0) {
                temp = "今天";
            } else if (differentDays == 1) {
                temp = "明天";
            } else {
                temp = dateFm.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return str;
        }
        return temp;
    }

    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        System.out.println("判断day2 - day1 : " + (day2 - day1));
        return day2 - day1;
    }

    /**
     * @param sunrise    日出时间
     * @param sunset     日落时间
     * @param secondTime
     * @return
     */
    public static boolean compareTime(String sunrise, String sunset, String secondTime) {
        SimpleDateFormat sdfSec = new SimpleDateFormat("HH");
        SimpleDateFormat sdfFir = new SimpleDateFormat("HH:mm");
        try {
            Date sunDate = sdfFir.parse(sunrise);
            Date setDate = sdfFir.parse(sunset);
            Date date2 = sdfSec.parse(secondTime);
            if (date2.compareTo(sunDate) == 1 && date2.compareTo(setDate) == -1) {
                return true;
            } else {
                return false;
            }
            //比日出时间大 比日落时间小
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getHasVirtualKey(Context context) {
        int dpi = 0;
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    public static int getNoHasVirtualKey(Context context) {
        int height = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
        return height;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            Logger.w("hasNav = " + hasNav);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    public static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

    public static boolean compareTime(Long oldTime) {
        long newTime = new Date().getTime();
        if ((newTime - oldTime) / 1000 / 60 >= 5) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean compareTime2(Long oldTime) {
        long newTime = new Date().getTime();
        if ((newTime - oldTime) / 1000 >= 60) {
            return true;
        } else {
            return false;
        }
    }

}
