package com.abt.horizontal_loop_recycler.Live;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

import com.abt.basic.arch.mvvm.BaseFragment;
import com.abt.middle.base.SwipBackActivity;

/**
 * @描述：     @LiveActivity
 * @作者：     @黄卫旗
 * @创建时间： @2018-03-18
 */
public class LiveActivity extends SwipBackActivity<LiveViewModel, LiveFragment>
        implements LiveNavigator {

    @NonNull
    @Override
    protected BaseFragment createFragment() {
        return LiveFragment.newInstance();
    }

    @NonNull
    @Override
    protected BaseObservable createViewModel() {
        return new LiveViewModel();
    }

    @Override
    public void openLiveSettingActivity() {

    }

    @Override
    protected boolean enableSwipBack() {
        return false;
    }
}
