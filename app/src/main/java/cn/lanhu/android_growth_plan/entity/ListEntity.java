package cn.lanhu.android_growth_plan.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListEntity {

    private ArrayList<String> province;

    @SerializedName("class")
    private ArrayList<Bean> classId;

    private ArrayList<Bean> profession;

    public ArrayList<String> getProvince() {
        return province;
    }

    public void setProvince(ArrayList<String> province) {
        this.province = province;
    }

    public ArrayList<Bean> getClassId() {
        return classId;
    }

    public void setClassId(ArrayList<Bean> classId) {
        this.classId = classId;
    }

    public ArrayList<Bean> getProfession() {
        return profession;
    }

    public void setProfession(ArrayList<Bean> profession) {
        this.profession = profession;
    }

    public static class Bean {

        private String id;
        private String name;
        private String createTime;

        public Bean(String id, String name, String createTime) {
            this.id = id;
            this.name = name;
            this.createTime = createTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }

}
