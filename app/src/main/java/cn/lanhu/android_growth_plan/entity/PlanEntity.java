package cn.lanhu.android_growth_plan.entity;

public class PlanEntity {

    private String date;
    private String shixiang;
    private String note;

    public PlanEntity(String date, String shixiang, String note) {
        this.date = date;
        this.shixiang = shixiang;
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShixiang() {
        return shixiang;
    }

    public void setShixiang(String shixiang) {
        this.shixiang = shixiang;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
