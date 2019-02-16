package cn.lanhu.android_growth_plan.entity;

/**
 * Created by lq on 2019/1/4.
 */

public class HomeListEntity {

    public HomeListEntity(String title, String content, String time, String imgUrl) {
        this.title = title;
        this.content = content;
        this.time = time;
        this.imgUrl = imgUrl;
    }

    private String title;
    private String content;
    private String time;
    private String imgUrl;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
