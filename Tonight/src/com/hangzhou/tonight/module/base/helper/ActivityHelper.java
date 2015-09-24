package com.hangzhou.tonight.module.base.helper;

import com.hangzhou.tonight.module.base.helper.model.TbarViewModel;

import android.content.Context;
import android.content.Intent;

public class ActivityHelper {
	/*** 标题KEY 值类型: string */
	public static final String KEY_COMMON_TITLE 	= "__common_title_";
	/*** 返回键KEY 值类型: boolean */
	public static final String KEY_COMMON_CANBACK 	= "__common_canback_";
	
	public static void startActivity(Context context,Class<?> cls){
		startActivity(context, cls,null);
	}
	
	public static void startActivity(Context context,Class<?> cls,TbarViewModel model){
		startActivity(context, cls, model, null);
	}
	
	public static void startActivity(Context context,Class<?> cls,TbarViewModel model,OnIntentCreateListener listener){
		Intent intent = new Intent();
		intent.setClass(context, cls);
		
		if(null != model){
			intent.putExtra(KEY_COMMON_TITLE, model.title);
			intent.putExtra(KEY_COMMON_CANBACK, model.canBack);
		}
		if(null != listener){ listener.onCreate(intent); }
		
		context.startActivity(intent);
	}
	
	public interface OnIntentCreateListener{
		public void onCreate(Intent intent);
	}
}
