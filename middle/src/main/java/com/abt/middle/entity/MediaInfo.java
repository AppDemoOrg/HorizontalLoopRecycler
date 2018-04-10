package com.abt.middle.entity;

import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.icu.util.GregorianCalendar;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.abt.basic.utils.DateStyle;
import com.abt.basic.utils.DateUtil;
import com.abt.middle.base.SwipBackApplication;

import java.io.File;
import java.io.Serializable;
import java.util.Locale;

public class MediaInfo implements Serializable{
    private static final String TAG = "MediaInfo";
    public File file;
    public boolean selected;
    public float transcodingProgress = -1;
    public String dayTag;
    public String monthTag;
    public boolean invalid;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public MediaInfo(File f, boolean s) {
        file         = f;
        selected    = s;
        dayTag      = getDayTag();
        monthTag    = getMonthTag();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private final String getDayTag(){
        String filename  =  file.getName();
        int headIdStrLen = 6;
        if (filename.length() >= headIdStrLen) {
            String mStr = filename.substring(0, headIdStrLen);
            try {
                final int time = Integer.parseInt(mStr);
                return getDayShotTime(time);
            } catch (NumberFormatException e) {
            }
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private final String getMonthTag(){
        String filename  =  file.getName();
        int headIdStrLen = 4;
        if (filename.length() >= headIdStrLen) {
            String mStr = filename.substring(0, headIdStrLen);
            try {
                final int time = Integer.parseInt(mStr);
                return getMonthShotTime(time);
            } catch (NumberFormatException e) {
            }
        }
        return null;
    }

    public final String getDuration(){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
            mmr.setDataSource(file.getAbsolutePath());
            String duration        = mmr.extractMetadata(
                    MediaMetadataRetriever.METADATA_KEY_DURATION);
            int time               = Integer.parseInt(duration);
            if(time == 0){
                invalid           = true;
            }
            mmr.release();
            return DateUtil.longToString(time/1000);
        }catch (Exception e){
            if(!isPhoto()){
                invalid           = true;
            }
            return "";
        }
    }

    public final Bitmap getFirstFrame(){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        Bitmap bitmap = null;
        try {
            mmr.setDataSource(file.getAbsolutePath());
            bitmap    = mmr.getFrameAtTime();
            mmr.release();
        }catch (Exception e){
        }
        return bitmap;
    }

    /**
     * 判断是否为图片类型
     * @return
     */
    public final boolean isPhoto(){
        final String filename = file.getName();
        if (filename.toLowerCase().endsWith(".jpg") ||
                filename.toLowerCase().endsWith(".jpeg")){
            return true;
        }
        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private final String getDayShotTime(int time){
        GregorianCalendar date                 = new GregorianCalendar(time / 10000 + 2000,
                (time / 100) % 100 - 1, time % 100);
        final SimpleDateFormat simpleDateFormat= getDayFormat();
        return simpleDateFormat.format(date);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private final String getMonthShotTime(int time){
        GregorianCalendar date                 = new GregorianCalendar(time / 100 + 2000,
                time % 100, 0);
        final SimpleDateFormat simpleDateFormat= getMonthFormat();
        return simpleDateFormat.format(date);
    }

    private final SimpleDateFormat getDayFormat(){
        final SwipBackApplication application = (SwipBackApplication) SwipBackApplication.getAppContext();
        if(application.isZh()){
            return new SimpleDateFormat(DateStyle.MM_DD_CN.getValue().toString(),Locale.CHINESE);
        }else{
            return new SimpleDateFormat(DateStyle.DD_MMMM.getValue().toString(), Locale.ENGLISH);
        }
    }

    private final SimpleDateFormat getMonthFormat(){
        final SwipBackApplication application = (SwipBackApplication) SwipBackApplication.getAppContext();
        if(application.isZh()){
            return new SimpleDateFormat(DateStyle.YYYY_MM_CN.getValue().toString(),Locale.CHINESE);
        }else{
            return new SimpleDateFormat(DateStyle.MMMM_YYYY.getValue().toString(), Locale.ENGLISH);
        }
    }
}