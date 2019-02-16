package cn.lanhu.android_growth_plan.entity;

public class BuyEntity {

    private String time;
    private String orderNum;
    private String money;
    private String buyTime;

    public BuyEntity(String time, String orderNum, String money, String buyTime) {
        this.time = time;
        this.orderNum = orderNum;
        this.money = money;
        this.buyTime = buyTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }
}
