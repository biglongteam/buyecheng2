package com.hangzhou.tonight.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * 记录用户名，密码之类的首选项
 *
 */
public class MyPreference {
	private static MyPreference preference = null;
	private SharedPreferences sharedPreference;
	private String packageName = "";
	private static final String LOGIN_NAME = "loginName"; //登录名
	private static final String PASSWORD = "password";  //密码
	private static final String HAS_LOGIN = "hasLogin";//是否处于登录状态
	private static final String HEAD_IMG_PATH = "headImgPath";//头像path
	private static final String USER_NAME = "userName";//用户昵称
	private static final String USER_BIRTH = "birth";//生日
	private static final String USER_ID = "userId";//user id
	private static final String USER_SEX = "userSex";
	private static final String USER_FAT = "userFat";
	private static final String USER_PRAISED = "praised";
	private static final String USER_GROUPS = "groups";
	private static final String USER_FRIENDS = "friends";
	private static final String TOKEN_ID = "token";//token
	private static final String TEL_NUMBER = "tel_number";//电话号码
	
	private static final String CITY_URL = "city_url";//
	private static final String CITY_COVSION = "city_covsion";//
	
	private static final String ISFIRSTCLICKPHOTO="isFirstClikcPhoto";
	
	private static final String ISSET="isSet";//是否设置过支付密码
	private static final String ISFRIST="isFrist";//是否 第一次 打开应用
	
	private static final String ISTIP="isTip";//是否有消息提示
	private static final String HISTORY_ONE = "history_1";
	private static final String HISTORY_TOW = "history_2";
	private static  final String HONG_BAOS = "hongbaos";
	private static final String CITY = "city";
	private static final String LAT = "location_w";
	private static final String LON = "location_j";
	//是否是第一次进入app
	private static final String ISFIRST="isFirst";
	
	public boolean getIsFirstClikcPhoto(){
		boolean isSet = sharedPreference.getBoolean(ISFIRSTCLICKPHOTO, true);
		return isSet;
	}
	
	
	public void setIsFirstClikcPhoto(boolean isFirstClikcPhoto){
		Editor editor = sharedPreference.edit();
		editor.putBoolean(ISFIRSTCLICKPHOTO, isFirstClikcPhoto);
		editor.commit();
	}
	
	public boolean getIsSet(){
		boolean isSet = sharedPreference.getBoolean(ISSET, true);
		return isSet;
	}
	
	
	public void setIsSet(boolean isSet){
		Editor editor = sharedPreference.edit();
		editor.putBoolean(ISSET, isSet);
		editor.commit();
	}
	
	public boolean getIsFirst(){
		boolean isFirst = sharedPreference.getBoolean(ISFIRST, true);
		return isFirst;
	}
	
	
	public void setIsFirst(boolean isFirst){
		Editor editor = sharedPreference.edit();
		editor.putBoolean(ISFIRST, isFirst);
		editor.commit();
	}
	
	public static synchronized MyPreference getInstance(Context context){
		if(preference == null)
			preference = new MyPreference(context);
		return preference;
	}
	
	
	public MyPreference(Context context){
		packageName = context.getPackageName() + "_preferences";
		sharedPreference = context.getSharedPreferences(
				packageName, context.MODE_PRIVATE);
	}
	
	
	public String getLoginName(){
		String loginName = sharedPreference.getString(LOGIN_NAME, "");
		return loginName;
	}
	
	
	public void setLoginName(String loginName){
		Editor editor = sharedPreference.edit();
		editor.putString(LOGIN_NAME, loginName);
		editor.commit();
	}
	public String getCityUrl(){
		String cityurl = sharedPreference.getString(CITY_URL, "");
		return cityurl;
	}
	
	
	public void setCityUrl(String cityurl){
		Editor editor = sharedPreference.edit();
		editor.putString(CITY_URL, cityurl);
		editor.commit();
	}
	
	public String getCityConvsion(){
		String convsion = sharedPreference.getString(CITY_COVSION, "0");
		return convsion;
	}
	
	
	public void setCityConvsion(String convsion){
		Editor editor = sharedPreference.edit();
		editor.putString(CITY_COVSION, convsion);
		editor.commit();
	}
	
	public String getHongBao(){
		String loginName = sharedPreference.getString(HONG_BAOS, "");
		return loginName;
	}
	
	
	public void setHongBao(String loginName){
		Editor editor = sharedPreference.edit();
		editor.putString(HONG_BAOS, loginName);
		editor.commit();
	}
	public String getPassword(){
		String password = sharedPreference.getString(PASSWORD, "");
		return password;
	}
	
	
	public void setPassword(String password){
		Editor editor = sharedPreference.edit();
		editor.putString(PASSWORD, password);
		editor.commit();
	}
	
	public boolean getHasLogin(){
		Boolean hasLogin = sharedPreference.getBoolean(HAS_LOGIN, false);
		return hasLogin;
	}
	
	public void setHasLogin(Boolean hasLogin){
		Editor edit = sharedPreference.edit();
		edit.putBoolean(HAS_LOGIN, hasLogin);
		edit.commit();
	}
	
	public String getHeadImgPath(){
		String headImgPath = sharedPreference.getString(HEAD_IMG_PATH, "");
		return headImgPath;
	}
	
	public void setHeadImgPath(String headImgPath){
		Editor edit = sharedPreference.edit();
		edit.putString(HEAD_IMG_PATH, headImgPath);
		edit.commit();
	}
	
	public String getUserId(){
		String userId = sharedPreference.getString(USER_ID, "0");
		return userId;
	}
	
	public void setUserId(String userId){
		Editor edit = sharedPreference.edit();
		edit.putString(USER_ID, userId);
		edit.commit();
	}
	
	public String getUserName(){
		String userName = sharedPreference.getString(USER_NAME, "游客");
		return userName;
	}
	
	public void setUserName(String userName){
		Editor edit = sharedPreference.edit();
		edit.putString(USER_NAME, userName);
		edit.commit();
	}
	
	public String getUserBirth(){
		String birth = sharedPreference.getString(USER_BIRTH, "1988-7-9");
		return birth;
	}
	public String getUserPraised(){
		String praised = sharedPreference.getString(USER_PRAISED, "");
		return praised;
	}
	public String getUserGroups(){
		String groups = sharedPreference.getString(USER_GROUPS, "1988-7-9");
		return groups;
	}
	public String getUserFrinds(){
		String frinds = sharedPreference.getString(USER_FRIENDS, "1988-7-9");
		return frinds;
	}
	
	public void setUserbirth(String birth){
		Editor edit = sharedPreference.edit();
		edit.putString(USER_BIRTH, birth);
		edit.commit();
	}
	public void setUserPraised(String birth){
		Editor edit = sharedPreference.edit();
		edit.putString(USER_PRAISED, birth);
		edit.commit();
	}
	public void setUserGroups(String birth){
		Editor edit = sharedPreference.edit();
		edit.putString(USER_GROUPS, birth);
		edit.commit();
	}
	public void setUserFrinds(String frinds){
		Editor edit = sharedPreference.edit();
		edit.putString(USER_FRIENDS, frinds);
		edit.commit();
	}
	
	public String getToken(){
		String token = sharedPreference.getString(TOKEN_ID, "123");
		return token;
	}
	
	public void setToken(String token){
		Editor edit = sharedPreference.edit();
		edit.putString(TOKEN_ID, token);
		edit.commit();
	}
	
	public String getTelNumber(){
		String token = sharedPreference.getString(TEL_NUMBER, "15801067749");
		return token;
	}
	
	public void setTelNumber(String telNumber){
		Editor edit = sharedPreference.edit();
		edit.putString(TEL_NUMBER, telNumber);
		edit.commit();
	}
	
	public String getHistory1(){
		String history1 = sharedPreference.getString(HISTORY_ONE, "");
		return history1;
	}
	
	public void setHistory1(String history1){
		Editor edit = sharedPreference.edit();
		edit.putString(HISTORY_ONE, history1);
		edit.commit();
	}
	public String getHistory2(){
		String history2 = sharedPreference.getString(HISTORY_TOW, "");
		return history2;
	}
	
	public void setHistory2(String history2){
		Editor edit = sharedPreference.edit();
		edit.putString(HISTORY_TOW, history2);
		edit.commit();
	}
	
	public String getCity(){
		String city = sharedPreference.getString(CITY, "");
		return city;
	}
	
	public void setCity(String city){
		Editor edit = sharedPreference.edit();
		edit.putString(CITY, city);
		edit.commit();
	}

	public String getLocation_w(){
		String location_w = sharedPreference.getString(LAT, "");
		return location_w;
	}
	
	public void setLocation_w(String location_w){
		Editor edit = sharedPreference.edit();
		edit.putString(LAT, location_w);
		edit.commit();
	}

	public String getLocation_j(){
		String location_j = sharedPreference.getString(LON, "");
		return location_j;
	}
	
	public void setLocation_j(String location_j){
		Editor edit = sharedPreference.edit();
		edit.putString(LON, location_j);
		edit.commit();
	}
	
	public String getUserSex(){
		String userSex = sharedPreference.getString(USER_SEX, "");
		return userSex;
	}
	
	public void setUserSex(String userSex){
		Editor edit = sharedPreference.edit();
		edit.putString(USER_SEX, userSex);
		edit.commit();
	}
	
	public String getUserFact(){
		String userFat = sharedPreference.getString(USER_FAT, "0");
		return userFat;
	}
	
	public void setUserFact(String userFat){
		Editor edit = sharedPreference.edit();
		edit.putString(USER_FAT, userFat);
		edit.commit();
	}
	
}


