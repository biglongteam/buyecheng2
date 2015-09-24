package com.hangzhou.tonight.util;

import java.text.DecimalFormat;

import android.content.Context;
import android.location.Location;
import android.util.DisplayMetrics;

/**
 * @Description 坐标点(经、纬度)之间距离换算的工具类
 * @Created 2014-10-13
 * @author Ryu Zheng
 * 
 */
public class DistanceUtils {

	/** PI */
	static double DEF_PI = 3.14159265359;
	/** 2*PI */
	static double DEF_2PI = 6.28318530712;
	/** PI/180.0 */
	static double DEF_PI180 = 0.01745329252;
	/** radius of earth */
	static double DEF_R = 6370693.5;

	/**
	 * 百度坐标点之间距离换算的两种模式
	 * 
	 * @param lon1
	 *            经度1
	 * @param lat1
	 *            纬度1
	 * @param lon2
	 *            经度1
	 * @param lat2
	 *            纬度1
	 * @return
	 */
	public double getShortDistance(double lon1, double lat1, double lon2, double lat2) {
		double ew1, ns1, ew2, ns2;
		double dx, dy, dew;
		double distance;
		// 角度转换为弧度
		ew1 = lon1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lon2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;
		// 经度差
		dew = ew1 - ew2;
		// 若跨东经和西经180 度，进行调整
		if (dew > DEF_PI)
			dew = DEF_2PI - dew;
		else if (dew < -DEF_PI)
			dew = DEF_2PI + dew;
		dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
		dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
		// 勾股定理求斜边长
		distance = Math.sqrt(dx * dx + dy * dy);
		return distance;
	}

	/**
	 * 百度坐标点之间距离换算的两种模式
	 * 
	 * @param lon1
	 *            经度1
	 * @param lat1
	 *            纬度1
	 * @param lon2
	 *            经度2
	 * @param lat2
	 *            纬度2
	 * @return
	 */
	public double getLongDistance(double lon1, double lat1, double lon2, double lat2) {
		double ew1, ns1, ew2, ns2;
		double distance;
		// 角度转换为弧度
		ew1 = lon1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lon2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;
		// 求大圆劣弧与球心所夹的角(弧度)
		distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2) * Math.cos(ew1 - ew2);
		// 调整到[-1..1]范围内，避免溢出
		if (distance > 1.0)
			distance = 1.0;
		else if (distance < -1.0)
			distance = -1.0;
		// 求大圆劣弧长度
		distance = DEF_R * Math.acos(distance);
		return distance;
	}

	/**
	 * 计算两个GeoPoint的距离
	 * 
	 * @param lon1
	 *            经度1
	 * @param lat1
	 *            纬度1
	 * @param lon2
	 *            经度2
	 * @param lat2
	 *            纬度2
	 * @return
	 */
	public double getDistance(double lon1, double lat1, double lon2, double lat2) {
		float[] results = new float[1];
		Location.distanceBetween(lon1 * 0.000001, lat1 * 0.000001, lon2 * 0.000001, lat2 * 0.000001, results);
		return results[0];
	}

	/**
	 * 将距离数字格式化为距离字符串
	 * 
	 * @param distance
	 * @return
	 */
	public static String formatDistance(double distance) {
		double distanceFormat;

		DecimalFormat df = new DecimalFormat();

		// 定义要显示的数字的格式
		String style = "0.0";
		// 将格式应用于格式化器
		df.applyPattern(style);

		String dis = "";
		if (distance > 1000) {
			distanceFormat = distance / 1000;
			dis = df.format(distanceFormat) + "KM";
		} else {
			dis = df.format(distance) + "M";
		}
		return dis;
	}
	
	
	
	/**
	 * 获取屏幕宽度  （px）
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context){
		DisplayMetrics dm =context.getResources().getDisplayMetrics();  
        int w_screen = dm.widthPixels;  
        return w_screen;
	}
	
	/**
	 * 获取屏幕高度  （px）
	 * @param context
	 * @return
	 */
	
	public static int getScreenHeight(Context context){
		DisplayMetrics dm =context.getResources().getDisplayMetrics();  
        int h_screen = dm.heightPixels;
        return h_screen;
	}
	/**
	 * dip 转换 px
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 *  px 转换 dip
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}


}
