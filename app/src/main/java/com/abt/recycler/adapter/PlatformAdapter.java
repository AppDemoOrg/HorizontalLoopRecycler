package com.abt.recycler.adapter;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abt.recycler.R;
import com.abt.recycler.databinding.LayoutPlatformItemBinding;

import java.util.ArrayList;

/**
 * @描述：     @平台适配器
 * @作者：     @黄卫旗
 * @创建时间： @2018-03-07
 */
public class PlatformAdapter extends RecyclerView.Adapter<PlatformViewHolder>
        implements View.OnClickListener, View.OnFocusChangeListener {

    /**
     * 用于模仿ListView的ItemClick事件，RecyclerView没有ItemClick
     */
    private OnItemClickListener mOnItemClickListener;

    private ArrayList<Integer> mItem;

    public PlatformAdapter(ArrayList<Integer> data) {
        this.mItem = data;
    }

    @Override
    public PlatformViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutPlatformItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.layout_platform_item, parent, false);
        itemBinding.getRoot().setOnClickListener(this);
        itemBinding.getRoot().setOnFocusChangeListener(this);
        return new PlatformViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(PlatformViewHolder holder, int position) {
        LayoutPlatformItemBinding itemBinding = holder.getBinding();
        itemBinding.setImage(mItem.get(position%mItem.size()));
        //将position保存在itemView的Tag中，以便点击时进行获取
        itemBinding.getRoot().setTag(position);
        // 立刻执行绑定
        itemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mItem == null ? 0 : Integer.MAX_VALUE/*mItem.size()*/;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            if (Build.VERSION.SDK_INT >= 21) {
                ViewCompat.animate(view).scaleX(1.17f).scaleY(1.17f).translationZ(1).start();
            } else {
                ViewCompat.animate(view).scaleX(1.17f).scaleY(1.17f).start();
                ViewGroup parent = (ViewGroup) view.getParent();
                parent.requestLayout();
                parent.invalidate();

            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}