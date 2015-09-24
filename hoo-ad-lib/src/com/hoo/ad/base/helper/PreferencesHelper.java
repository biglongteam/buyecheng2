package com.hoo.ad.base.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Preferences 本地存储帮助类
 * @author hank
 *
 */
public class PreferencesHelper {
	
	private static String STRING = "String", INTEGER = "Integer",BOOLEAN = "Boolean", FLOAT   = "Float",LONG    = "Long";
	
	/*** 保存在手机里面的文件名 */
	private static final String FILE_NAME = "com_hoo_ad_share_date";

	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 * 
	 * @param context
	 * @param key
	 * @param object 已支持: STRING INTEGER BOOLEAN FLOAT LONG
	 * 				   待支持: JSONObject JSONArray 等
	 */
	public static void put(Context context, String key, Object object) {

		SharedPreferences sp = getSharedPreferences(context);
		SharedPreferences.Editor editor = sp.edit();

		String type = object.getClass().getSimpleName();
		if (STRING.equals(type)) {
			editor.putString(key, (String) object);
		} else if (INTEGER.equals(type)) {
			editor.putInt(key, (Integer) object);
		} else if (BOOLEAN.equals(type)) {
			editor.putBoolean(key, (Boolean) object);
		} else if (FLOAT.equals(type)) {
			editor.putFloat(key, (Float) object);
		} else if (LONG.equals(type)) {
			editor.putLong(key, (Long) object);
		}

		editor.commit();
	}

	
	public static String getString(Context context,String key,String defValue){
		return getSharedPreferences(context).getString(key, defValue);
	}
	
	public static boolean getBoolean(Context context,String key,Boolean defValue){
		return getSharedPreferences(context).getBoolean(key, defValue);
	}
	
	public static float getFloat(Context context,String key,Float defValue){
		return getSharedPreferences(context).getFloat(key, defValue);
	}

	public static int getInt(Context context,String key,Integer defValue){
		return getSharedPreferences(context).getInt(key, defValue);
	}
	
	public static long getLong(Context context,String key,long defValue){
		return getSharedPreferences(context).getLong(key, defValue);
	}
	
	public static void remove(Context context,String key){
		Editor editor = getSharedPreferences(context).edit();
		editor.remove(key);
		editor.commit();
	}
	
	private static SharedPreferences getSharedPreferences(Context context){
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
	}
	
}
