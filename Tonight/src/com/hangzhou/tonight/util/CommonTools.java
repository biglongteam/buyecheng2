package com.hangzhou.tonight.util;


import java.io.File;

import com.hangzhou.tonight.base.BaseApplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


/**
 * 
 * @version V_1.0.0
 * 
 */
public class CommonTools {
	
	static boolean isCheck = false;
	static boolean isSdCard = false;
	private static String TAG = "CommonTools";
    /** 检查SD卡是否存在 */
    public static boolean checkSdCard() {
//        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
    	if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
        	isSdCard = true;        	
        }
        else {
        	isSdCard = false;
        }
        return isSdCard;	
    }
    
    /** 获取SD卡路径 */
    public static String getSdCardPathOld(){
        if(checkSdCard()){
//            return Environment.getExternalStorageDirectory().getAbsolutePath();
            return Environment.getExternalStorageDirectory().toString() ;
        }else {
            return null;
        }
    }
    
    /**
     * 获得机身路径
     * @param context
     * @return
     */
    public static String getDataPath(){
    	// 	public static final String SDCARD = Environment.getExternalStorageDirectory().toString();
//    	return Environment.getDataDirectory()+ "/data/"+BaseApplication.getInstance().getPackageName();
    	return Environment.getDataDirectory()+ "/data/";
    }
    
    

    public static String getPath() {
    	if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return android.os.Environment.getExternalStorageDirectory().getPath();
        }
    	else {
    		return  Environment.getDataDirectory()+ "/data/"+BaseApplication.getInstance().getPackageName();
    	}
    }
    
	/**
	 * 短暂显示Toast消息
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShortToast(Context context, String message) {
	    
	    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	public static void showShortToast(Context context, int resId){
		Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
	}
	
	public static void showLongToast(Context context, String message) {
	    
	    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
	
	public static void hideShortToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).cancel();
	}

    /**
     * 根据手机的分辨率dip 的单位转成px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从px 的单位转成dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * 
     * @param pxValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(float pxValue, float fontScale) {
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * 
     * @param spValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(float spValue, float fontScale) {
        return (int) (spValue * fontScale + 0.5f);
    }

	/**
	 * 获取手机状态栏高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		java.lang.reflect.Field field = null;
		int x = 0;
		int statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
			return statusBarHeight;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusBarHeight;
	}
	
    /**获取 VersionName*/
    public static String getVersionName(Context context)
    {
       // 获取packagemanager的实例
       PackageManager packageManager = context.getPackageManager();
       // getPackageName()是你当前类的包名，0代表是获取版本信息
       PackageInfo packInfo = null;
       try {
           packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
       } catch (NameNotFoundException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       String version = packInfo.versionName;
       return version;
    }
    /**获取VersionCode*/
    public static int getVersionCode(Context context)
    {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return packInfo.versionCode;
    }
    
    /**
     * 获取包名
     * @param cxt
     * @return
     */
    private static String getPkgName(Context cxt) {
        PackageManager pManager = cxt.getPackageManager();
        PackageInfo pkgInfo = null;
        try {
            pkgInfo = pManager.getPackageInfo(cxt.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return pkgInfo.packageName;
    }
    
    /**
     * 卸载指定应用包
     * @param cxt
     */
    public static void uninstallApp(Context cxt) {
        String pkgName = getPkgName(cxt);
        Uri data = Uri.fromParts("package", pkgName, null);
        Intent intent = new Intent(Intent.ACTION_DELETE, data);
        cxt.startActivity(intent);
    }
    
    public static void showSoftInput(View view) {
        // TODO Auto-generated method stub
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);

    }
    
    public static void hideInputFromWindow(Activity activity) {
        // TODO Auto-generated method stub
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()){
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_NOT_ALWAYS); 
        }
    }
    /**
     * 换算视频秒数
     * @param seconds
     * @return
     */
    public static String millsecondsToStr(int seconds){
    	seconds = seconds / 1000;
		String result = "";
		int min = 0, second = 0;
		min = seconds / 60;
	//	second = seconds - min * 60 + 1;
		second = seconds - min * 60;
		if (min < 10) {
			result += "0" + min + ":";
		} else {
			result += min + ":";
		}
		if (second < 10) {
			result += "0" + second;
		} else {
			result += second;
		}
		return result;
    }
    /**
     * 检测当前网络是否连接且可用
     * @param context
     * @return
     */
    public static boolean checkNetConnect(Context context){
    	ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
    	if(manager != null){
    		NetworkInfo info = manager.getActiveNetworkInfo();
    		if(info != null && info.isConnected()){//当前网络已连接
    			if(info.getState() == NetworkInfo.State.CONNECTED){//当前网络可用
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * 检测当前网络类型是否是WiFi
     * @param context
     */
	public static boolean checkWifiStatus(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager != null) {
			NetworkInfo info = manager.getActiveNetworkInfo();
			if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI) {
				return true;
			}
		}
		return false;
	}
	
}
