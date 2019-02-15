package cn.lanhu.android_growth_plan.entity;

public class CourseEntity {

    private String name;
    private String seeNum;
    private String title;
    private String content;

    public CourseEntity(String name, String seeNum, String title, String content) {
        this.name = name;
        this.seeNum = seeNum;
        this.title = title;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeeNum() {
        return seeNum;
    }

    public void setSeeNum(String seeNum) {
        this.seeNum = seeNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
