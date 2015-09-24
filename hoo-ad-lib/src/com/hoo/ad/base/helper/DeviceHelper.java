package com.hoo.ad.base.helper;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * 设备相关的帮助类
 * @author hank
 *
 */
public class DeviceHelper {
	
	/**
	 * 获取状态栏高度
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context){
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
		    c   = Class.forName("com.android.internal.R$dimen");
		    obj = c.newInstance();
		    field = c.getField("status_bar_height");
		    x = Integer.parseInt(field.get(obj).toString());
		    sbar = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
		    e1.printStackTrace();
		}
		/*
		  该获取方式需在onCreate 后获取,否则为 0
		 Rect frame = new Rect();
		 getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
         sbar = frame.top;
		 */
		return sbar;
	}
	
	/**
	 * 调用系统打电话功能
	 * @param context
	 * @param phoneNum 电话号码
	 */
	public static void call(Context context,String phoneNum){
		Intent phoneIntent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + phoneNum));
		context.startActivity(phoneIntent);
	}
	
	/**
	 * 打开系统相册
	 * @param activity
	 * @param requestCode
	 * @param options
	 */
	public static void openAlbum(Activity activity,int requestCode){
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		
		activity.startActivityForResult(intent, requestCode);
	}
	
	/**
	 * 打开系统相机
	 * @param activity
	 * @param requestCode
	 */
	public static void openCamera(Activity activity,int requestCode){
		 Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
         activity.startActivityForResult(intent, requestCode);  
	}
	
	/**
	 * 手动隐藏软键盘
	 * @param activity
	 */
	public static void hideSoftKey(Activity activity){
		InputMethodManager manager = ((InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE));
		if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (activity.getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	
}
