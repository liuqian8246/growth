package cn.lanhu.android_growth_plan.entity;

public class UserInfo {



    private String id;
    private String mobile;
    private String infoStatus;
    private String headPicUrl;
    private String nickname;
    private String province;
    private String relation;
    private String birthday;

    private String baby_id;
    private String baby_age;
    private String baby_userId;
    private String baby_grownNo;
    private String baby_nickname;
    private String baby_sex;
    private String baby_birthday;
    private String baby_classId;
    private String baby_className;
    private String baby_professionId;
    private String baby_professionName;

    public UserInfo() {
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile == null ? "" : mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getInfoStatus() {
        return infoStatus == null ? "" : infoStatus;
    }

    public void setInfoStatus(String infoStatus) {
        this.infoStatus = infoStatus;
    }

    public String getHeadPicUrl() {
        return headPicUrl == null ? "" : headPicUrl;
    }

    public void setHeadPicUrl(String headPicUrl) {
        this.headPicUrl = headPicUrl;
    }

    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProvince() {
        return province == null ? "" : province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRelation() {
        return relation == null ? "" : relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getBirthday() {
        return birthday == null ? "" : birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBaby_id() {
        return baby_id == null ? "" : baby_id;
    }

    public void setBaby_id(String baby_id) {
        this.baby_id = baby_id;
    }

    public String getBaby_userId() {
        return baby_userId == null ? "" : baby_userId;
    }

    public void setBaby_userId(String baby_userId) {
        this.baby_userId = baby_userId;
    }

    public String getBaby_grownNo() {
        return baby_grownNo == null ? "" : baby_grownNo;
    }

    public void setBaby_grownNo(String baby_grownNo) {
        this.baby_grownNo = baby_grownNo;
    }

    public String getBaby_nickname() {
        return baby_nickname == null ? "" : baby_nickname;
    }

    public void setBaby_nickname(String baby_nickname) {
        this.baby_nickname = baby_nickname;
    }

    public String getBaby_sex() {
        return baby_sex == null ? "" : baby_sex;
    }

    public void setBaby_sex(String baby_sex) {
        this.baby_sex = baby_sex;
    }

    public String getBaby_birthday() {
        return baby_birthday == null ? "" : baby_birthday;
    }

    public void setBaby_birthday(String baby_birthday) {
        this.baby_birthday = baby_birthday;
    }

    public String getBaby_classId() {
        return baby_classId == null ? "" : baby_classId;
    }

    public void setBaby_classId(String baby_classId) {
        this.baby_classId = baby_classId;
    }

    public String getBaby_className() {
        return baby_className == null ? "" : baby_className;
    }

    public void setBaby_className(String baby_className) {
        this.baby_className = baby_className;
    }

    public String getBaby_professionId() {
        return baby_professionId == null ? "" : baby_professionId;
    }

    public void setBaby_professionId(String baby_professionId) {
        this.baby_professionId = baby_professionId;
    }

    public String getBaby_professionName() {
        return baby_professionName == null ? "" : baby_professionName;
    }

    public void setBaby_professionName(String baby_professionName) {
        this.baby_professionName = baby_professionName;
    }

    public String getBaby_age() {
        return baby_age == null ? "" : null;
    }

    public void setBaby_age(String baby_age) {
        this.baby_age = baby_age;
    }
}
