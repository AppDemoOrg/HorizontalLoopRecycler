package com.abt.recycler.demo;

import com.abt.recycler.R;

import java.util.Arrays;
import java.util.List;

/**
 * @描述：     @LivePlatform
 * @作者：     @黄卫旗
 * @创建时间： @2018-03-31
 */
public class LivePlatform {

    public static LivePlatform get() {
        return new LivePlatform();
    }

    private LivePlatform() {
    }

    public List<Live> getPlatforms() {
        return Arrays.asList(
                new Live(R.drawable.platform_rtmp),
                new Live(R.drawable.platform_weibo),
                new Live( R.drawable.platform_facebook),
                new Live(R.drawable.platform_youtube),
                new Live( R.drawable.platform_pi));
    }
}
