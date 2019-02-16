package cn.lanhu.android_growth_plan.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author
 * @date 2018/3/19
 */

public class AdvertEntity implements Parcelable {


    protected AdvertEntity(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdvertEntity> CREATOR = new Creator<AdvertEntity>() {
        @Override
        public AdvertEntity createFromParcel(Parcel in) {
            return new AdvertEntity(in);
        }

        @Override
        public AdvertEntity[] newArray(int size) {
            return new AdvertEntity[size];
        }
    };
}
