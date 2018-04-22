package com.abt.recycler.adapter;

import android.support.v7.widget.RecyclerView;

import com.abt.recycler.databinding.LayoutPlatformItemBinding;

/**
 * @描述：     @平台适配器Holder
 * @作者：     @黄卫旗
 * @创建时间： @2018-03-07
 */
public class PlatformViewHolder extends RecyclerView.ViewHolder {

    private LayoutPlatformItemBinding mItemBinding;

    public PlatformViewHolder(LayoutPlatformItemBinding itemBinding) {
        super(itemBinding.getRoot());
        this.mItemBinding = itemBinding;
    }

    public LayoutPlatformItemBinding getBinding() {
        return mItemBinding;
    }

    public void setBinding(LayoutPlatformItemBinding itemBinding) {
        this.mItemBinding = itemBinding;
    }

}
