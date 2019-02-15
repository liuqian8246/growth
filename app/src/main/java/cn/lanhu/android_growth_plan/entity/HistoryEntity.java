package cn.lanhu.android_growth_plan.entity;

public class HistoryEntity {

    private String date;
    private String people;
    private String content;

    public HistoryEntity(String date, String people, String content) {
        this.date = date;
        this.people = people;
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
