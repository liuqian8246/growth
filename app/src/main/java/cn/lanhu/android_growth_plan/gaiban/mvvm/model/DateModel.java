package cn.lanhu.android_growth_plan.gaiban.mvvm.model;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.gaiban.mvvm.base.BaseModel;
import cn.lanhu.android_growth_plan.widget.CustomerOptionsPickerView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yx on 2017/8/23.
 * 日期  model
 */

public class DateModel extends BaseModel {

    private static volatile DateModel instance;

    private Calendar c;

    public static DateModel getInstance() {
        if (instance == null) {
            synchronized (DateModel.class) {
                if (instance == null) {
                    instance = new DateModel();
                }
            }
        }
        return instance;
    }

    private DateModel() {
        super();
    }

    public LiveData<String> getYear() {
        c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        MutableLiveData<String> data = new MutableLiveData<>();
        String year = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
        data.setValue(year);
        return data;
    }

    public LiveData<String> getMonth() {
        c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        MutableLiveData<String> data = new MutableLiveData<>();
        String month = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        data.setValue(month);
        return data;
    }

    public LiveData<String> getDay() {
        c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        MutableLiveData<String> data = new MutableLiveData<>();
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        data.setValue(day);
        return data;
    }

    public LiveData<String> getWeek() {
        c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        MutableLiveData<String> data = new MutableLiveData<>();
        String week = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(week)) {
            week = "日";
        } else if ("2".equals(week)) {
            week = "一";
        } else if ("3".equals(week)) {
            week = "二";
        } else if ("4".equals(week)) {
            week = "三";
        } else if ("5".equals(week)) {
            week = "四";
        } else if ("6".equals(week)) {
            week = "五";
        } else if ("7".equals(week)) {
            week = "六";
        }
        data.setValue(week);
        return data;
    }

    //获取首页的日期和星期
    public LiveData<String> getMonthDayWeek() {
        MutableLiveData<String> data = new MutableLiveData<>();
        String month = getMonth().getValue();
        String day = getDay().getValue();
        String week = getWeek().getValue();
        data.setValue(month + "-" + day + "  周" + week);
        return data;
    }

    //获取今天日期
    public LiveData<String> getTodayTime() {
        MutableLiveData<String> data = new MutableLiveData<>();
        String year = getYear().getValue();
        String month = getMonth().getValue();
        String day = getDay().getValue();
        data.setValue(year + "-" + month + "-" + day);
        return data;
    }


    /**
     * 比较两个日期的大小
     *
     * @param date1
     * @return 1表示date1大于当前时间，-1表示date1小于当前时间，0表示相等
     */
    public int compareCurrentDate(String date1) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = new Date();
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public int compareCurrentDate(String date1, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = new Date();
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    private TimePickerView.Builder mTimePickerViewBuilder;

    private OptionsPickerView.Builder mOptionsPickerViewBuilder;

    private CustomerOptionsPickerView.Builder mCustomerOptionsPickerViewBuilder;

    public TimePickerView.Builder getTimePickerViewBuilder() {
        return mTimePickerViewBuilder;
    }

    public OptionsPickerView.Builder getOptionsPickerViewBuilder() {
        return mOptionsPickerViewBuilder;
    }

    public CustomerOptionsPickerView.Builder getCustomerOptionsPickerViewBuilder() {
        return mCustomerOptionsPickerViewBuilder;
    }

    //初始化时间选择器
    @SuppressLint("SimpleDateFormat")
    public LiveData<String> initTimePickerViewBuilder(int type) {
        MutableLiveData<String> data = new MutableLiveData<>();
        mTimePickerViewBuilder = new TimePickerView.Builder(getCurrentActivity(), (date, v) -> {
            SimpleDateFormat format = null;
            switch (type) {
                case 1:
                    format = new SimpleDateFormat("yyyy-MM-dd HH:mm");//年月日时分
                    break;
                case 2:
                    format = new SimpleDateFormat("yyyy-MM-dd");//年月日
                    break;
                case 3:
                    format = new SimpleDateFormat("yyyy-MM");//年月
                    break;
                case 4:
                    format = new SimpleDateFormat("HH:mm");//时分
                    break;
            }
            String dateStr = format.format(date);
            data.setValue(dateStr);

        })
            .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
            .setCancelText("取消")//取消按钮文字
            .setSubmitText("确定")//确认按钮文字
            .setContentSize(18)//滚轮文字大小
            .setTitleSize(18)//标题文字大小
            .setSubCalSize(16)//确定和取消文字大小
//                .setTitleText("Title")//标题文字
            .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
            .isCyclic(false)//是否循环滚动
            .setTitleColor(getResources().getColor(R.color.colorText34))//标题文字颜色
            .setSubmitColor(getResources().getColor(R.color.colorAccent))//确定按钮文字颜色
            .setCancelColor(getResources().getColor(R.color.colorText6))//取消按钮文字颜色
//                .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                .setRangDate(startDate, endDate)//起始终止年月日设定
//                .setType(new boolean[]{true, true, true, true, true, true})
            .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
            .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
            .isDialog(false);//是否显示为对话框样

        return data;
    }

    //初始化条件选择器
    public LiveData<String> initOptionsPickerViewBuidler() {
        MutableLiveData<String> data = new MutableLiveData<>();
        mOptionsPickerViewBuilder = new OptionsPickerView.Builder(getCurrentActivity(), (options1, options2, options3, v) ->
            data.setValue(options1 + "-" + options2 + "-" + options3))
            .setSubmitText("确定")//确定按钮文字
            .setCancelText("取消")//取消按钮文字
            .setSubCalSize(16)//确定和取消文字大小
            .setTitleSize(18)//标题文字大小
            .setTitleColor(getResources().getColor(R.color.colorText34))//标题文字颜色
            .setSubmitColor(getResources().getColor(R.color.colorAccent))//确定按钮文字颜色
            .setCancelColor(getResources().getColor(R.color.colorText6))//取消按钮文字颜色
            .setContentTextSize(18)//滚轮文字大小
            .setLinkage(true)//设置是否联动，默认true
//                .setLabels("省", "市", "区")//设置选择的三级单位
//                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
            .setCyclic(false, false, false)//循环与否
            .setSelectOptions(0, 0, 0)  //设置默认选中项
            .setOutSideCancelable(true)//点击外部dismiss default true
            .isDialog(false);//是否显示为对话框样式

        return data;

    }

    public LiveData<String> initCustomerOptionsPickerViewBuidler(CustomerOptionsPickerView.OnOptionCancelListener listener) {
        MutableLiveData<String> data = new MutableLiveData<>();
        mCustomerOptionsPickerViewBuilder = new CustomerOptionsPickerView.Builder(getCurrentActivity(), (options1, options2, options3, v) ->
            data.setValue(options1 + "-" + options2 + "-" + options3))
            .setSubmitText("确定")//确定按钮文字
            .setCancelText("清除")//取消按钮文字
            .setSubCalSize(16)//确定和取消文字大小
            .setTitleSize(18)//标题文字大小
            .setTitleColor(getResources().getColor(R.color.colorText34))//标题文字颜色
            .setSubmitColor(getResources().getColor(R.color.colorAccent))//确定按钮文字颜色
            .setCancelColor(getResources().getColor(R.color.colorText6))//取消按钮文字颜色
            .setContentTextSize(18)//滚轮文字大小
            .setLinkage(true)//设置是否联动，默认true
            .setOptionCancelListener(listener)
            .setLabels(".", "kg", "")//设置选择的三级单位
//                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
            .setCyclic(false, false, false)//循环与否
            .setSelectOptions(0, 0, 0)  //设置默认选中项
            .setOutSideCancelable(true)//点击外部dismiss default true
            .isDialog(false);//是否显示为对话框样式
        return data;
    }

}
