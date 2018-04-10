package com.abt.horizontal_loop_recycler.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.abt.horizontal_loop_recycler.R;

/**
 * @描述：     @FocusRelativeLayout
 * @作者：     @黄卫旗
 * @创建时间： @2018-03-14
 */
public class FocusRelativeLayout extends RelativeLayout {

    private Animation scaleSmallAnimation;
    private Animation scaleBigAnimation;

    public FocusRelativeLayout(Context context) {
        super(context);
    }

    public FocusRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);

        if (gainFocus) {
            getRootView().invalidate();
            zoomOut();
        } else {
            zoomIn();
        }
    }

    private void zoomIn() {
        if (scaleSmallAnimation == null) {
            scaleSmallAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scale_small);
        }
        startAnimation(scaleSmallAnimation);
    }

    private void zoomOut() {
        if (scaleBigAnimation == null) {
            scaleBigAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scale_big);
        }
        startAnimation(scaleBigAnimation);
    }
}