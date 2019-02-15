package cn.lanhu.android_growth_plan.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class LoginEntity implements Parcelable{

    private User user;
    private Baby baby;

    protected LoginEntity(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        baby = in.readParcelable(Baby.class.getClassLoader());
    }

    public static final Creator<LoginEntity> CREATOR = new Creator<LoginEntity>() {
        @Override
        public LoginEntity createFromParcel(Parcel in) {
            return new LoginEntity(in);
        }

        @Override
        public LoginEntity[] newArray(int size) {
            return new LoginEntity[size];
        }
    };

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Baby getBaby() {
        return baby;
    }

    public void setBaby(Baby baby) {
        this.baby = baby;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeParcelable(baby, flags);
    }

    public static class User implements Parcelable{
        private String id;
        private String mobile;
        private String infoStatus;
        private String headPicUrl;
        private String nickname;
        private String province;
        private String relation;
        private String birthday;

        protected User(Parcel in) {
            id = in.readString();
            mobile = in.readString();
            infoStatus = in.readString();
            headPicUrl = in.readString();
            nickname = in.readString();
            province = in.readString();
            relation = in.readString();
            birthday = in.readString();
        }

        public static final Creator<User> CREATOR = new Creator<User>() {
            @Override
            public User createFromParcel(Parcel in) {
                return new User(in);
            }

            @Override
            public User[] newArray(int size) {
                return new User[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getInfoStatus() {
            return infoStatus;
        }

        public void setInfoStatus(String infoStatus) {
            this.infoStatus = infoStatus;
        }

        public String getHeadPicUrl() {
            return headPicUrl;
        }

        public void setHeadPicUrl(String headPicUrl) {
            this.headPicUrl = headPicUrl;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(mobile);
            dest.writeString(infoStatus);
            dest.writeString(headPicUrl);
            dest.writeString(nickname);
            dest.writeString(province);
            dest.writeString(relation);
            dest.writeString(birthday);
        }
    }

    public static class Baby implements Parcelable{
        private String id;
        private String userId;
        private String grownNo;
        private String nickname;
        private String sex;
        private String birthday;
        private String classId;
        private String className;
        private String professionId;
        private String profession;
        private String age;

        protected Baby(Parcel in) {
            id = in.readString();
            userId = in.readString();
            grownNo = in.readString();
            nickname = in.readString();
            sex = in.readString();
            birthday = in.readString();
            classId = in.readString();
            className = in.readString();
            professionId = in.readString();
            profession = in.readString();
            age = in.readString();
        }

        public static final Creator<Baby> CREATOR = new Creator<Baby>() {
            @Override
            public Baby createFromParcel(Parcel in) {
                return new Baby(in);
            }

            @Override
            public Baby[] newArray(int size) {
                return new Baby[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getGrownNo() {
            return grownNo;
        }

        public void setGrownNo(String grownNo) {
            this.grownNo = grownNo;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getProfessionId() {
            return professionId;
        }

        public void setProfessionId(String professionId) {
            this.professionId = professionId;
        }

        public String getProfessionName() {
            return profession;
        }

        public void setProfessionName(String professionName) {
            this.profession = professionName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(userId);
            dest.writeString(grownNo);
            dest.writeString(nickname);
            dest.writeString(sex);
            dest.writeString(birthday);
            dest.writeString(classId);
            dest.writeString(className);
            dest.writeString(professionId);
            dest.writeString(profession);
            dest.writeString(age);
        }
    }
}
