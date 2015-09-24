package com.hangzhou.tonight.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;



/**
 * activity 跳转 工具类
 * @author Administrator
 *
 */
public class IntentJumpUtils {

	
	public  static void nextActivity(Class<?> next,Activity activity){
		Intent intent = new Intent();
		intent.setClass(activity, next);
		activity.startActivity(intent);
		
	}
	
	
	
	@SuppressLint("NewApi")
	public  static void nextActivity(Class<?> next,Context mContext,Bundle b){
		
		Intent intent = new Intent(mContext, next);
		if (b != null) {
			intent.putExtras(b);
		}
		mContext.startActivity(intent, b);
		
	}
	
	
	/*@SuppressLint("NewApi")
	public  static void nextActivity(Class<?> next,Context mContext,Bundle b,int REQUEST){
		
		Intent intent = new Intent(mContext, CourseDetailActivity.class);
		if (b != null) {
			intent.putExtras(b);
		}
		start
		
	}*/
	
	public  static void nextActivity(Class<?> next,Activity activity,Bundle b){
		Intent intent = new Intent();
		intent.setClass(activity, next);
		if (b != null) {
			intent.putExtras(b);
		}
		activity.startActivity(intent);
		
	}
	
	public static void nextActivity(Class<?> next,Activity activity, Bundle b,int REQUEST ) {
		Intent intent = new Intent();
		intent.setClass(activity, next);
		if (b != null) {
			intent.putExtras(b);
		}
		activity.startActivityForResult(intent, REQUEST);
	}
}
