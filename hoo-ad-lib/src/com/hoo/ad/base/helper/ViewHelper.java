package com.hoo.ad.base.helper;

import java.util.concurrent.atomic.AtomicInteger;

import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * 视图组件帮助类
 * @author hank
 */
public class ViewHelper {
	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(10);// 原来是1

	/**
	 * 生成 VIEW ID[功能来自api 11],抽离主要为了向下兼容
	 * @return next id
	 */
	public static int generateViewId() {
		for (;;) {
			final int result = sNextGeneratedId.get();// aapt-generated IDs have the high byte nonzero; clamp to the range under that.
			int newValue = result + 1;
			if (newValue > 0x00FFFFFF){ newValue = 1; }// Roll over to 1, not 0.
			if (sNextGeneratedId.compareAndSet(result, newValue)) {
				return result;
			}
		}
	}
	
	/**
	 * 判断当前触发点是否在editText焦点上
	 * @param v
	 * @param event
	 * @return
	 */
	public static boolean isFocusEditText(View v,MotionEvent event){
		if (v != null && (v instanceof EditText)) {  
	        int[] leftTop = { 0, 0 };  
	        //获取输入框当前的location位置  
	        v.getLocationInWindow(leftTop);  
	        int left = leftTop[0];  
	        int top = leftTop[1];  
	        int bottom = top + v.getHeight();  
	        int right = left + v.getWidth();  
	        if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {  
	            // 点击的是输入框区域，保留点击EditText的事件  
	            return false;  
	        } else {  
	            return true;  
	        }  
	    }  
	    return false;
	}
	
}
