package com.abt.basic.app;

import android.app.Application;

import com.abt.basic.BuildConfig;
import com.abt.basic.debug.DebugManage;

/**
 * @描述：     @基类application
 * @作者：     @黄卫旗
 * @创建时间： @2017-04-25
 */
public abstract class BasicApplication extends Application{

    private static BasicApplication sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        init();
        initComplete();
    }

    public static final BasicApplication getAppContext(){
        return sContext;
    }

    private final void init(){
        if(BuildConfig.DEBUG){
            DebugManage.initialize(this);
        }
    }

    public abstract void initComplete();

}
