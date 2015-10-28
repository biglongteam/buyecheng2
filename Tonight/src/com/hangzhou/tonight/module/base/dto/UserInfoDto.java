package com.hangzhou.tonight.module.base.dto;

import java.util.logging.Logger;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.LoginActivity;
import com.hangzhou.tonight.module.base.constant.SysModuleConstant;
import com.hangzhou.tonight.module.base.util.UnicodeUtil;
import com.hangzhou.tonight.util.MyPreference;
import com.hangzhou.tonight.util.PreferenceUtils;

/**
 * 用户数据传输对象
 * @author hank
 */
public class UserInfoDto {
	private static Logger logger = Logger.getLogger("userInfoDto");
	/**
	 * 保存用户信息[TODO 注意:如果登录入口不止一个的话,请登入后重新调用]
	 * @param context
	 * @param result 登录返回数据
	 */
	public static void save(Context context,String result){
		//TODO 将用户信息保存到sp中,放到内存中极易丢失,如果为了安全考虑，可以加密、也可以在系统退出前清除[hank]
		result = UnicodeUtil.decodeUnicode(result);
		PreferenceUtils.setPrefString(context, SysModuleConstant.KEY_USERINFO, result);
	}
	
	/**
	 * 获取当前登录用户 JSON 对象
	 * @param context
	 * @return 
	 * { 
	 * "uid":"1000003",
	 * "nick":"Moo",
	 * "birth":"1989-05-08",
	 * "sex":"1",
	 * "phone":"13456941703", //个人信息部分
	 * "favorite":["33","46"], //收藏 
	 * "praised":["1","32","35"], //赞 
	 * "groups":[{"gid":"100001","position":"9","time":"1428414109"}], 
	 * "friends":["1000000"],
	 * "money":"0.00"
	 * }
	 */
	public static JSONObject getUserInfo(Context context){
		String result = PreferenceUtils.getPrefString(context, SysModuleConstant.KEY_USERINFO, null);
		return JSON.parseObject(result);
	}
	
	static User u = null;
	public static User getUser(Context context){
		if(u == null){ u = new User(); }
		//JSONObject json = getUserInfo(context);
		u.uid  = Long.parseLong(MyPreference.getInstance(context).getUserId());
		u.nick = UnicodeUtil.decodeUnicode(MyPreference.getInstance(context).getUserName());
		u.birth= MyPreference.getInstance(context).getUserBirth();
		u.sex  = MyPreference.getInstance(context).getUserSex();
		u.phone= MyPreference.getInstance(context).getTelNumber();
		return u;
	}
	
	public static class User{
		public long uid;
		public String nick,birth,sex,phone;
	}
}
