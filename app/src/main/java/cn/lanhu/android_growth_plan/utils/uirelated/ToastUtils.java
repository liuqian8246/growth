package cn.lanhu.android_growth_plan.utils.uirelated;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.gaiban.app.config.RxApplication;

/**
 * Created by yx on 2016/7/22.
 * 自定义单例吐司
 */
public class ToastUtils {

    private static Toast sToast;

    public static void showToast(String content){
        Context context = RxApplication.getInstance().getmContext();
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout,null);
        TextView tvToast = (TextView) view.findViewById(R.id.tv_toast);
        if(sToast == null) {
            sToast = Toast.makeText(context,"",Toast.LENGTH_SHORT);
            sToast.setGravity(Gravity.BOTTOM,0, DensityUtils.dip2px(80));
        }
        tvToast.setText(content);
        sToast.setView(view);
        sToast.show();
    }

}
