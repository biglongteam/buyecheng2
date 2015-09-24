package com.hoo.ad.base.helper;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {

	/**
	 * 显示一个默认的toast[short]
	 * @param context
	 * @param text
	 */
	public static void show(Context context, CharSequence text){
		show(context, text, Toast.LENGTH_SHORT);
	}
	
	/**
	 * 显示一个长时间的toast
	 * @param context
	 * @param text
	 */
	public static void showLong(Context context, CharSequence text){
		show(context, text,Toast.LENGTH_LONG);
	}
	
	private static void show(Context context, CharSequence text,int duration){
		if(context != null){ Toast.makeText(context, text, duration).show(); } 
	}
	
}
