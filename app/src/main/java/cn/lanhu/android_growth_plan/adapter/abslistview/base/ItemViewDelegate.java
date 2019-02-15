package cn.lanhu.android_growth_plan.adapter.abslistview.base;


import cn.lanhu.android_growth_plan.adapter.abslistview.ViewHolder;

public interface ItemViewDelegate<T>
{

    public abstract int getItemViewLayoutId();

    public abstract boolean isForViewType(T item, int position);

    public abstract void convert(ViewHolder holder, T t, int position);



}
