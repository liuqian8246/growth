package cn.lanhu.android_growth_plan.widget.verticaltablayout.adapter;


import cn.lanhu.android_growth_plan.widget.verticaltablayout.widgetview.TabView;

/**
 * Created by chqiu on 2017/1/20.
 */

public abstract class SimpleTabAdapter implements TabAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public TabView.TabBadge getBadge(int position) {
        return null;
    }

    @Override
    public TabView.TabIcon getIcon(int position) {
        return null;
    }

    @Override
    public TabView.TabTitle getTitle(int position) {
        return null;
    }

    @Override
    public int getBackground(int position) {
        return 0;
    }
}
