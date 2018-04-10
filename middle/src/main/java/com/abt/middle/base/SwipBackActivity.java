package com.abt.middle.base;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.abt.basic.arch.mvvm.BaseActivity;
import com.abt.basic.arch.mvvm.BaseFragment;
import com.abt.basic.arch.mvvm.IViewModel;
import com.abt.basic.log.LogHelper;
import com.abt.middle.swipback.SwipBackManager;
import com.bugtags.library.Bugtags;

/**
 * @描述：     @app activity基类
 * @作者：     @黄卫旗
 * @创建时间： @2018-02-01
 */
public abstract class SwipBackActivity<VM extends BaseObservable & IViewModel, V extends BaseFragment<VM>> extends BaseActivity {

    private final int LEFT_KEY_CODE = KeyEvent.KEYCODE_CAMERA;
    private final int RIGHT_KEY_CODE = KeyEvent.KEYCODE_BACK;

    private CountDown mCountDown;
    private boolean mShortPress = false;
    private volatile boolean mCounting = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(enableSwipBack()) SwipBackManager.addSwipBackList(this);
    }

    /**
     * 是否启动滑动删除
     */
    protected boolean enableSwipBack() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2
        Bugtags.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogHelper.d(TAG, "onKeyDown() keyCode = "+keyCode);
        if (keyCode == RIGHT_KEY_CODE || keyCode == LEFT_KEY_CODE) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                listenLongPress();
                listenShortPress(event);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /** 监听长按事件 */
    private void listenLongPress() {
        if (null != mCountDown && !mCounting) {
            mCountDown.cancel();
            mCountDown = null;
        } else if (mCountDown == null && !mCounting) {
            mCountDown = new CountDown(5 * 1000, 1000);
            mCountDown.start();
            mCounting = true;
        }
    }

    /** 监听短按事件 */
    private void listenShortPress(KeyEvent event) {
        event.startTracking();
        if (event.getRepeatCount() == 0) {
            mShortPress = true;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == RIGHT_KEY_CODE || keyCode == LEFT_KEY_CODE) {
            if (mShortPress) { // 处理短按事件
                handleShortPress();
            } else { // 处理长按事件，这里什么都不用做，
                // 等长按5秒后在线程中跳转到Launcher
                // Don't handle long press here
            }
            mShortPress = false;
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    /** 处理短按事件 */
    private void handleShortPress() {
        if (null != mCountDown && mCounting) {
            mCountDown.cancel();
            mCountDown = null;
            mCounting = false;
            LogHelper.d(TAG, "Count Down Cancel()");
        }
        // Toast.makeText(this, "mShortPress", Toast.LENGTH_LONG).show();
    }

    /** 处理长按返回Launcher倒计时 */
    private class CountDown extends CountDownTimer {

        public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            LogHelper.d(TAG, "CountDown millisInFuture = "+millisInFuture);
            LogHelper.d(TAG, "CountDown countDownInterval = "+countDownInterval);
        }

        @Override
        public void onTick(long l) {
            LogHelper.d(TAG, "onTick l = "+l);
        }

        @Override
        public void onFinish() {
            LogHelper.d(TAG, "onFinish()");
            Intent intent= new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            SwipBackActivity.this.startActivity(intent);
            SwipBackActivity.this.finish();
            LogHelper.d(TAG, "SwipBackActivity.this.finish()");
        }
    }

}
