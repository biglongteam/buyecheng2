package com.hangzhou.tonight.module.base.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 禁止左右滑动的 ViewPager
 * @author hank
 *
 */
public class NoSlidingViewPager extends ViewPager {

	boolean isCanSliding = false;
	
	public NoSlidingViewPager(Context context) {
		super(context);
	}
	
	public NoSlidingViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if(!isCanSliding){ return isCanSliding;}
		return super.onInterceptTouchEvent(arg0);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if(!isCanSliding){ return isCanSliding;}
		return super.onTouchEvent(arg0);
	}
}
