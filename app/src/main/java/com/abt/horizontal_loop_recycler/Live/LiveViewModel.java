package com.abt.horizontal_loop_recycler.Live;

import android.databinding.BaseObservable;

import com.abt.basic.arch.mvvm.IViewModel;

import java.lang.ref.WeakReference;

/**
 * @描述：     @直播VM
 * @作者：     @黄卫旗
 * @创建时间： @2018-03-05
 */
public class LiveViewModel extends BaseObservable
        implements IViewModel<LiveNavigator> {

    private static final String TAG                           = "LiveViewModel";

    private WeakReference<LiveNavigator>        mPreviewNavigator;
    private LiveActivity                        mPreviewActivity;

    public LiveViewModel() {

    }

    @Override
    public void initialize() {

    }

    public void setContext(LiveActivity activity) {
        mPreviewActivity = activity;
    }

    @Override
    public void setNavigator(LiveNavigator navigator) {
        mPreviewNavigator = new WeakReference<LiveNavigator>(navigator);
    }

}
