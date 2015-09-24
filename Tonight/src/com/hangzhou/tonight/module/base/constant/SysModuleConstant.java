package com.hangzhou.tonight.module.base.constant;

/**
 * 系统级别-module模块-常量维护类
 * @author hank
 *
 */
public class SysModuleConstant {
	/**
	 * 作为sp(注:sharedpreferences)存储用户登入信息
	 * 其值对应的格式,与login正常返回数据保持一致
	 * {
	 * "uid":"1000003",,"nick":"Moo","birth":"1989-05-08","sex":"1","phone":"13456941703"		//个人信息
	 * "favorite":["33","46"],			//收藏
	 * "praised":["1","32","35"],		//赞
	 * "groups":[{"gid":"100001","position":"9","time":"1428414109"},{"gid":"100010","position":"1","time":"1429599467"},{"gid":"100015","position":"1","time":"1433239722"},{"gid":"100016","position":"1","time":"1433240542"},{"gid":"100017","position":"1","time":"1433240752"}],
	 * "friends":["1000000","1000005","9000015"],"money":"0.00"}
	 */
	public static final String KEY_USERINFO = "_sys_module_userinfo";
	
	/*** 个人的邀请码[因为不会变,所以缓存]*/
	public static final String KEY_INVITATION_CODE = "_sys_module_invitation_code";
	/**
	 * 是否开发模式 TODO 发布时设为false 
	 */
	public static final boolean VALUE_DEV_MODEL = true;
	
	public static final String VALUE_EMPTY_TIP = "当前数据为可空:)";
	
}
