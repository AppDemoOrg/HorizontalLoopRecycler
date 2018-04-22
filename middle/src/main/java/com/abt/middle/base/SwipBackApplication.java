package com.abt.middle.base;

import com.abt.basic.app.BasicApplication;
import com.abt.basic.concurrent.ThreadPool;
import com.abt.middle.swipback.SwipBackConfig;
import com.abt.middle.swipback.SwipBackManager;
import com.bugtags.library.Bugtags;

import java.util.Locale;

/**
 * @描述：     @应用上层应用基类
 * @作者：     @黄卫旗
 * @创建时间： @2018-02-01
 */
public abstract class SwipBackApplication extends BasicApplication{

    public abstract void executeAsyncInit();

    @Override
    public void initComplete() {
        initSwipBack();
        ThreadPool.Builder.single().setImmediatelyShutdown(true).
                builder().execute(new Runnable() {
            @Override
            public void run() {
                executeAsyncInit();
            }
        });
        //initBugs();
    }

    private final void initSwipBack(){
        SwipBackManager.initialize(this,new SwipBackConfig.Builder().
                edgeOnly(false).lock(false).rotateScreen(false).create());
    }

    //加载bugs框架
    private final void initBugs(){
        Bugtags.start(getAppKey(), SwipBackApplication.this, Bugtags.BTGInvocationEventBubble);
    }

    public abstract String getAppKey();

    /**
     * 获取系统语言
     * @return
     */
    public final boolean isZh() {
        Locale locale   = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }

}
