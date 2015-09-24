package com.hoo.ad.base.helper;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 系统进程相关的帮助类
 * 
 * @author hank
 */
public class OsHelper {
	
	/**
	 * @return 如果指定的进程不存在,则返回null
	 */
	public static String getProcessName(Context cxt, int pid) {
		ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
		if (runningApps == null) {
			return null;
		}
		for (RunningAppProcessInfo procInfo : runningApps) {
			if (procInfo.pid == pid) {
				return procInfo.processName;
			}
		}
		return null;
	}
	
	/**
	 * 版本名
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
	    return getPackageInfo(context).versionName;
	}
	 
	/**
	 * 版本号
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
	    return getPackageInfo(context).versionCode;
	}
	
	/**
	 * 获取当前应用包名
	 * @param context
	 * @return
	 */
	public static String getPackage(Context context){
		return getPackageInfo(context).packageName;
	}
	 
	private static PackageInfo getPackageInfo(Context context) {
	    PackageInfo pi = null;
	    try {
	        PackageManager pm = context.getPackageManager();
	        pi = pm.getPackageInfo(context.getPackageName(),PackageManager.GET_CONFIGURATIONS);
	        return pi;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return pi;
	}
}
