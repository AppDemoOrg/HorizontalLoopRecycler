package com.abt.middle.model;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.abt.basic.utils.ResourceUtil;

/**
 * @描述： @状态栏VM
 * @作者： @黄卫旗
 * @创建时间： @2017-12-01
 */
public final class ToolbarViewModel extends BaseObservable {

    /**
     * 显示标题
     */
    public final ObservableField<String> title          = new ObservableField<>();

    public ToolbarViewModel(int title) {
        this.title.set(ResourceUtil.getString(title));
    }

}
