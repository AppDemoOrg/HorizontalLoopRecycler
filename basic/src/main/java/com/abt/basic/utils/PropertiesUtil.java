package com.abt.basic.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * @描述： @属性文件工具类
 * @作者： @黄卫旗
 * @创建时间： @2017-04-25
 */
public final class PropertiesUtil {

    /**
     * 加载属性文件
     * @param filepath
     * @return
     */
    private static final Properties read(String filepath) {
        FileInputStream fis = null;
        Properties props    = new Properties();
        try {
            fis = new FileInputStream(new File(filepath));
            props.load(fis);
            CloseUtil.closeIO(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return props;
    }

    /**
     * 设置属性
     * @param filepath
     * @param p
     */
    private static final void setProps(String filepath,Properties p) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(filepath));
            p.store(fos, null);
            fos.flush();
            CloseUtil.closeIO(fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对外提供的保存key value方法
     * @param key
     * @param value
     */
    public static final void set(String filepath,String key, String value) {
        Properties props = read(filepath);
        props.setProperty(key, value);
        setProps(filepath,props);
    }

    /**
     * 获取属性
     * @param filepath
     * @param key
     * @return
     */
    public static final String get(String filepath,String key) {
        Properties props = read(filepath);
        return (props != null) ? props.getProperty(key) : null;
    }

    /**
     * 获取全部属性
     * @param filepath
     * @param key
     * @return
     */
    public static final HashMap<String,String> get(String filepath,String ...key){
        Properties props = read(filepath);
        if(null != props){
            final HashMap<String,String> map = new HashMap<>();
            for(String k:key){
                map.put(k,props.getProperty(k));
            }
            return map;
        }
        return null;
    }


}
