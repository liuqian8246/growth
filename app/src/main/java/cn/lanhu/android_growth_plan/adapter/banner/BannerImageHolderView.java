package cn.lanhu.android_growth_plan.adapter.banner;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.entity.BannerEntity;
import cn.lanhu.android_growth_plan.gaiban.mvvm.view.widget.RoundImageView;
import cn.lanhu.android_growth_plan.net.HttpUrlApi;
import cn.lanhu.android_growth_plan.widget.convenientbanner.holder.Holder;
import okhttp3.HttpUrl;


/**
 * Created by lq on 2018/8/21.
 */

public class BannerImageHolderView implements Holder<String> {

    private RoundImageView mIvBanner;

    private List<String> mBannerList;

    public BannerImageHolderView(List<String> list) {
        this.mBannerList = list;
    }

    @Override
    public View createView(Context context) {
        View view = View.inflate(context, R.layout.home_top_banner_view, null);
        mIvBanner = (RoundImageView) view.findViewById(R.id.iv_banner);
        return view;
    }

    @Override
    public void UpdateUI(final Context context, int position, final String data) {
        Glide.with(context).load(HttpUrlApi.imgUrl + data)
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .centerCrop()
            .into(mIvBanner);
        mIvBanner.setOnClickListener(v -> {

        });
    }
}
