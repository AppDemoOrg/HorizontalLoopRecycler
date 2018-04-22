package com.abt.recycler;

import com.abt.middle.base.SwipBackApplication;
import com.abt.recycler.demo.DiscreteScrollViewOptions;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * @描述：     @直播application
 * @作者：     @黄卫旗
 * @创建时间： @2018-03-05
 */
public class LiveApplication extends SwipBackApplication {

    private static LiveApplication instance;

    public static LiveApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DiscreteScrollViewOptions.init(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public void initComplete() {
        super.initComplete();
    }

    @Override
    public void executeAsyncInit() {

    }

    @Override
    public String getAppKey() {
        return "05ba0d9873dfd6e478f6161caa56792e";
    }
}
