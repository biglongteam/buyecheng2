package com.hoo.ad.base.helper;

import com.hoo.ad.base.constant.ActivityConstant;
import com.hoo.ad.base.helper.inter.OnIntentListener;
import com.hoo.ad.base.helper.model.ActivityHelperOpt;
import com.hoo.base.util.ObjectUtil;

import android.content.Context;
import android.content.Intent;


/**
 * activity帮助类
 * @author hank
 * @version 1.0
 */
public class ActivityHelper {
	
	/**
	 * 简单的启动activity操作
	 * @param packageContext
	 * @param cls
	 */
	public static void startActivity(Context packageContext,Class<?> cls){
		startActivity(packageContext, cls, "");
	}
	
	/**
	 * 简单的启动activity操作
	 * @param packageContext
	 * @param cls 待跳转的activity
	 * @param title 标题
	 */
	public static void startActivity(Context packageContext,Class<?> cls,String title){
		startActivity(packageContext, cls, title, null);
	}
	
	public static void startActivity(Context packageContext,Class<?> cls,OnIntentListener listener,ActivityHelperOpt opt){
		final String title = opt.getTitle(),backTip = opt.getBackTip(),url = opt.getUrl();
		final int bgColor  = opt.getBgColor();
		final boolean allowBack = opt.isAllowBack();
		final OnIntentListener _listener = listener;
		startActivity(packageContext, cls, new OnIntentListener() {
			@Override public void doCreate(Intent intent) { 
				if(!ObjectUtil.isEmpty(title))  { intent.putExtra(ActivityConstant.KEY_TITLE, title);   }
				if(bgColor != -1){ intent.putExtra(ActivityConstant.KEY_BGCOLOR, bgColor);}
				if(!ObjectUtil.isEmpty(backTip)){ intent.putExtra(ActivityConstant.KEY_BACKTIP, backTip);}
				if(!ObjectUtil.isEmpty(url))    { intent.putExtra(ActivityConstant.KEY_WEBVIEW_URL, url);}
				intent.putExtra(ActivityConstant.KEY_ALLOWBACK, allowBack);
				if(null != _listener){ _listener.doCreate(intent);}
		}});
	}
	
	public static void startActivity(Context packageContext,Class<?> cls,String title,OnIntentListener listener){
		ActivityHelperOpt opt = new ActivityHelperOpt();
		opt.setTitle(title);
		startActivity(packageContext, cls, listener, opt);
	}
	
	/**
	 * 启动activity操作
	 * @param packageContext
	 * @param cls 待跳转的activity
	 * @param listener 监听回调接口
	 */
	public static void startActivity(Context packageContext,Class<?> cls,OnIntentListener listener){
		Intent intent = new Intent();
		if(null != listener){ listener.doCreate(intent); }
		intent.setClass(packageContext, cls);
		packageContext.startActivity(intent);
	}
}
