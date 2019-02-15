package cn.lanhu.android_growth_plan.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lq on 2019/1/4.
 */

public class DateEntity {

    @SerializedName("signFlag")
    private boolean qdStatus;
    private String month;
    @SerializedName("day")
    private String date;
    private String overFlag;

    public DateEntity(boolean qdStatus, String month, String date,String overFlag) {
        this.qdStatus = qdStatus;
        this.month = month;
        this.date = date;
        this.overFlag = overFlag;
    }

    public boolean getQdStatus() {
        return qdStatus;
    }

    public void setQdStatus(boolean qdStatus) {
        this.qdStatus = qdStatus;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOverFlag() {
        return overFlag;
    }

    public void setOverFlag(String overFlag) {
        this.overFlag = overFlag;
    }
}
