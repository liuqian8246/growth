package cn.lanhu.android_growth_plan.gaiban.mvvm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import cn.lanhu.android_growth_plan.gaiban.mvvm.model.DateModel;
import cn.lanhu.android_growth_plan.widget.CustomerOptionsPickerView;

/**
 * Created by yx on 2017/8/23.
 * 日期 viewmodel
 */

public class DateViewModel extends ViewModel {

    private DateModel mDateModel;

    public DateViewModel() {
        mDateModel = DateModel.getInstance();
    }

    //获取年份
    public LiveData<String> getYear() {
        return mDateModel.getYear();
    }

    //获取月
    public LiveData<String> getMonth() {
        return mDateModel.getMonth();
    }

    //获取日
    public LiveData<String> getDay() {
        return mDateModel.getDay();
    }

    //获取星期
    public LiveData<String> getWeek() {
        return mDateModel.getWeek();
    }

    //获取年月和星期
    public LiveData<String> getMonthDayWeek() {
        return mDateModel.getMonthDayWeek();
    }

    //获取今天日期
    public LiveData<String> getTodayTime() {
        return mDateModel.getTodayTime();
    }

    /**
     * 比较两个日期的大小
     * @param date1
     * @return 1表示date1小于当前时间，-1表示date1大于当前时间，0表示相等
     *
     */
    public int compareCurrentDate(String date1) {
        return mDateModel.compareCurrentDate(date1);
    }

    public int compareCurrentDate(String date1,String format) {
        return mDateModel.compareCurrentDate(date1,format);
    }


    //初始化时间选择器
    public LiveData<String> initTimePickerViewBuilder(int type) {
        return mDateModel.initTimePickerViewBuilder(type);

    }

    //获取时间选择器pickerview
    public TimePickerView.Builder getTimePickerViewBuilder() {
        return mDateModel.getTimePickerViewBuilder();
    }

    //初始化条件选择器
    public LiveData<String> initOptionsPickerViewBuidler() {
        return mDateModel.initOptionsPickerViewBuidler();
    }

    public LiveData<String> initCustomerOptionsPickerViewBuidler(CustomerOptionsPickerView.OnOptionCancelListener listener){
        return mDateModel.initCustomerOptionsPickerViewBuidler(listener);
    }

    //获取条件选择器
    public OptionsPickerView.Builder getOptionsPickerViewBuider() {
        return mDateModel.getOptionsPickerViewBuilder();
    }

    public CustomerOptionsPickerView.Builder getCustomerOptionsPickerViewBuilder() {
        return mDateModel.getCustomerOptionsPickerViewBuilder();
    }

}
