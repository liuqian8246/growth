package cn.lanhu.android_growth_plan.entity.base;

import java.util.List;

/**
 * Created by yx on 2016/11/11.
 *
 */

public class BaseEntity<T> {

    private List<T> Rows;

    public List<T> getRows() {
        return Rows;
    }

    public void setRows(List<T> Rows) {
        this.Rows = Rows;
    }



    @Override
    public String toString() {
        return "BaseEntity{" +
                "Rows=" + Rows +
                '}';
    }
}
