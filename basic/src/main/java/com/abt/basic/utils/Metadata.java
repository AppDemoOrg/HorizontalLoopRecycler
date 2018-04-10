package com.abt.basic.utils;

import android.graphics.Bitmap;

/**
 * @描述：     @视频meta封装实体类
 * @作者：     @黄卫旗
 * @创建时间： @2017-04-25
 */
public final class Metadata {

    /**
     * 一帧的图像
     */
    public Bitmap firstFrame;

    /**
     * 帧率
     */
    public String fps;

    /**
     * 时长
     */
    public int duration;

    /**
     * 视频宽
     */
    public String width;

    /**
     * 视频高
     */
    public String height;

    /**
     * 创建日期
     */
    public String date;

    /**
     * 编码信息
     */
    public String codec;

    /**
     * 相机
     */
    public String source;

    /**
     * 视频大小
     */
    public long length;

}
