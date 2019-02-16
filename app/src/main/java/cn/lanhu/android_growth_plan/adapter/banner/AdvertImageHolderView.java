package cn.lanhu.android_growth_plan.adapter.banner;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.entity.AdvertEntity;
import cn.lanhu.android_growth_plan.widget.convenientbanner.holder.Holder;

/**
 * @author yx
 * @date 2018/3/19
 * 广告条holder
 */

public class AdvertImageHolderView implements Holder<String> {

    private FrameLayout mFlBanner;
    private AppCompatImageView mIvBanner;

    private AdvertEntity mAdvertEntity;
    private String mFlag;//获取图片和链接的标记

    public AdvertImageHolderView(AdvertEntity advertEntity, String flag) {
        this.mAdvertEntity = advertEntity;
        this.mFlag = flag;
    }

    @Override
    public View createView(Context context) {
        View view = View.inflate(context, R.layout.home_banner_view, null);
        mFlBanner = view.findViewById(R.id.fl_banner);
        mIvBanner = view.findViewById(R.id.iv_banner);
        return view;
    }


    @Override
    public void UpdateUI(Context context, int position, String data) {

        Glide.with(context).load(data)
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .into(mIvBanner);

        mFlBanner.setOnClickListener(v -> {


            String name = "";

            //name 统计
//                  home_bottom：广告(首页底部)
//                  home_centre：广告(首页中间)
//                  life_bottom：广告(生活底部)
//                  life_centre：广告(生活中间)
//                  car_centre：广告(汽车中间)
//                  finance_centre：广告(财务中间)



        });


    }


}
