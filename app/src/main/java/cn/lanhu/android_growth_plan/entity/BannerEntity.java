package cn.lanhu.android_growth_plan.entity;

/**
 * @author yuxiao
 * @date 2018/4/16
 */
public class BannerEntity {

    /**
     * pictureAddress : https://shanghai.99dai.cn/images4.0/litu03.png
     * chainedAddress : https://www.baidu.com
     * exposureTime : 5
     * banner : 5
     */

    private String pictureAddress;
    private String chainedAddress;
    private String exposureTime;
    private String isShare;
    private String sharetitle;
    private String sharecontent;
    private String isbenner;
    private String benner;
    private String city;
    private String hideCity;
    private String album_id;

    public String getBanner() {
        return benner;
    }

    public void setBanner(String benner) {
        this.benner = benner;
    }

    public String getPictureAddress() {
        return pictureAddress;
    }


    public String getChainedAddress() {
        return chainedAddress;
    }

    public void setChainedAddress(String chainedAddress) {
        this.chainedAddress = chainedAddress;
    }

    public void setPictureAddress(String pictureAddress) {
        this.pictureAddress = pictureAddress;
    }

    public String getExposureTime() {
        return exposureTime;
    }

    public String getIsshare() {
        return isShare;
    }

    public String getSharetitle() {
        return sharetitle;
    }

    public String getSharecontent() {
        return sharecontent;
    }

    public String getIsbenner() {
        return isbenner;
    }

    public String getBenner() {
        return benner;
    }

    public String getCity() {
        return city;
    }

    public String getHideCity() {
        return hideCity;
    }

    public String getAlbum_id() {
        return album_id;
    }
}

