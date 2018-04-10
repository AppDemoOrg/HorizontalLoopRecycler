package com.abt.middle.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.abt.basic.utils.ResourceUtil;
import com.abt.middle.R;

public class BatteryView extends View {

	private int mProgress = 50;
	private int mPowerLeftPadding;
	private int mPowerRightPadding;
	private int mPowerWidth;

	private Bitmap mBatteryBackground;
	private Bitmap mBatteryLevel;
	private Bitmap mBatteryCharge;
	private boolean mIsCharge 	    = false;
	public BatteryView(Context context) {
		this(context, null);
	}

	public BatteryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray ta                   = context.obtainStyledAttributes(attrs, R.styleable.BatteryView);
		Drawable db 					= ta.getDrawable(R.styleable.BatteryView_batteryBackground);
		BitmapDrawable bd			    = null;
		if(null != db){
			bd 	    					= (BitmapDrawable) db;
			mBatteryBackground 		= bd.getBitmap();
		}


		db 								= ta.getDrawable(R.styleable.BatteryView_batteryLevel);
		if(null != db){
			bd 	    					= (BitmapDrawable) db;
			mBatteryLevel 				= bd.getBitmap();
		}


		db 								= ta.getDrawable(R.styleable.BatteryView_batteryChargeIcon);
		if(null != db){
			bd 	    					= (BitmapDrawable) db;
			mBatteryCharge 			= bd.getBitmap();
		}

		ta.recycle();
		mPowerLeftPadding = ResourceUtil.getDimensionPixelOffset(R.dimen.dp_2);
		mPowerRightPadding = ResourceUtil.getDimensionPixelOffset(R.dimen.dp_2);
		mPowerWidth = mBatteryBackground.getWidth() - mPowerLeftPadding - mPowerRightPadding;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if(!mIsCharge) {
			if(null != mBatteryBackground && null != mBatteryLevel){
				canvas.drawBitmap(mBatteryBackground, 0, 0, null);
				canvas.save();
				canvas.clipRect(0, 0, calculateClipRight(mProgress), getHeight());
				canvas.drawBitmap(mBatteryLevel, 0, 0, null);
				canvas.restore();
			}
		}else {
			canvas.drawBitmap(mBatteryCharge, 0, 0, null);
			canvas.save();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);

		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width = widthSize;
		int height = heightSize;
		if (MeasureSpec.AT_MOST == widthMode) {
			width = mBatteryBackground.getWidth();
		}
		if (MeasureSpec.AT_MOST == heightMode) {
			height = mBatteryBackground.getHeight();
		}
		setMeasuredDimension(width, height);

	}

	/**
	 * 
	 * @param mProgress
	 *            between 0 and 100
	 */
	public void setProgress(int mProgress) {
		if (mProgress < 0 || mProgress > 100) {
			return;
		}
		this.mProgress = mProgress;
		postInvalidate();
	}

	public final void setBatteryCharge(boolean isCharge){
		mIsCharge = isCharge;
		postInvalidate();
	}

	private int calculateClipRight(int progress) {
		return mPowerWidth * progress / 100 + mPowerLeftPadding;
	}
}
