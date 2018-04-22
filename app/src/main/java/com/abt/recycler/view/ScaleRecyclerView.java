package com.abt.recycler.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

/**
 * @描述：     @ScaleRecyclerView
 * @作者：     @黄卫旗
 * @创建时间： @2018-03-14
 */
public class ScaleRecyclerView extends RecyclerView {

    private static final String TAG = ScaleRecyclerView.class.getSimpleName();
    private int mSelectedPosition = 0;
    private int mTargetPos;
    private int mLastX = 0;
    private float mPxPerMillSecond = 0;//用于设置自动平移时候的速度
    private Scroller mScroller;

    public ScaleRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public ScaleRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScaleRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        //启用子视图排序功能
        mScroller = new Scroller(context);
        setChildrenDrawingOrderEnabled(true);
        //smoothToCenter(2+5*1000);
    }

    @Override
    public void onDraw(Canvas c) {
        mSelectedPosition = getChildAdapterPosition(getFocusedChild());
        super.onDraw(c);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int index) {
        int position = mSelectedPosition;
        if (position < 0) {
            return index;
        } else {
            if (index == childCount - 1) {
                if (position > index) {
                    position = index;
                }
                return position;
            }
            if (index == position) {
                return childCount - 1;
            }
        }
        return index;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //computeScrollOffset返回true表示滚动还在继续，持续时间应该就是startScroll设置的时间
        if (mScroller!=null && mScroller.computeScrollOffset()) {
            com.abt.basic.log.LogHelper.d(TAG, "getCurrX = " + mScroller.getCurrX());
            scrollBy(mLastX - mScroller.getCurrX(), 0);
            mLastX = mScroller.getCurrX();
            postInvalidate();//让系统继续重绘，则会继续重复执行computeScroll
        }
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy, int duration) {
        if (duration>0) {
            mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy,duration);
        } else {
            //设置mScroller的滚动偏移量
            mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        }
        /**
         * 重绘整个view，重绘过程会调用到computeScroll()方法
         */
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    /**
     * @param start 滑动起始位置
     * @param end 滑动结束位置
     */
    private void autoAdjustScroll(int start, int end) {
        int duration = 0;
        if (mPxPerMillSecond != 0) {
            duration = (int)((Math.abs(end - start)/ mPxPerMillSecond));
        }
        com.abt.basic.log.LogHelper.d(TAG, "duration:" + duration);
        mLastX = start;
        if (duration>0) {
            mScroller.startScroll(start, 0, end - start, 0, duration);
        } else {
            mScroller.startScroll(start, 0, end - start, 0);
        }
        postInvalidate();
    }

    /**
     * 将指定item平滑移动到整个view的中间位置
     * @param position
     */
    public void smoothToCenter(int position) {
        int parentWidth = getWidth();//获取父视图的宽度
        int childCount = getChildCount();//获取当前视图可见子view的总数
        //获取可视范围内的选项的头尾位置
        int firstVisiblePosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        int lastVisiblePosition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        int count = (getLayoutManager()).getItemCount();//获取item总数
        mTargetPos = Math.max(0, Math.min(count - 1, position));//获取目标item的位置（参考listview中的smoothScrollToPosition方法）

        View targetChild = getChildAt(mTargetPos-firstVisiblePosition);//获取目标item在当前可见视图item集合中的位置
        View firstChild = getChildAt(0);//当前可见视图集合中的最左view
        View lastChild = getChildAt(childCount-1);//当前可见视图集合中的最右view
        if (firstChild != null) {
            com.abt.basic.log.LogHelper.i(TAG, "first-->left:" + firstChild.getLeft() + "   right:" + firstChild.getRight());
            com.abt.basic.log.LogHelper.i(TAG, "last-->left:" + lastChild.getLeft() + "   right:" + lastChild.getRight());
        }

        int childLeftPx = targetChild.getLeft();//子view相对于父view的左边距
        int childRightPx = targetChild.getRight();//子view相对于父view的右边距
        com.abt.basic.log.LogHelper.i(TAG, "target-->left:" + targetChild.getLeft() + "  right:" + targetChild.getRight());

        int childWidth = targetChild.getWidth();
        int centerLeft = parentWidth/2-childWidth/2;//计算子view居中后相对于父view的左边距
        int centerRight = parentWidth/2+childWidth/2;//计算子view居中后相对于父view的右边距
        com.abt.basic.log.LogHelper.i(TAG,"rv width:"+parentWidth+"   item width:"+childWidth+"   centerleft:"+centerLeft+"   centerRight:"+centerRight);

        if (childLeftPx>centerLeft) {//子view左边距比居中view大（说明子view靠父view的右边，此时需要把子view向左平移
            //平移的起始位置就是子view的左边距，平移的距离就是两者之差
            mLastX = childLeftPx;
            mScroller.startScroll(childLeftPx,0,centerLeft-childLeftPx,0,600);//600为移动时长，可自行设定
            postInvalidate();
        } else if (childRightPx<centerRight) {
            mLastX = childRightPx;
            mScroller.startScroll(childRightPx,0,centerRight-childRightPx,0,600);
            postInvalidate();
        }
    }
}