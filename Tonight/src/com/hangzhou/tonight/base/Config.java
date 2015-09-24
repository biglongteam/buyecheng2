package com.hangzhou.tonight.base;

import com.hangzhou.tonight.util.CommonTools;


/**
 * 

* @ClassName: Config 

* @Description: TODO(常用配置 信息) 

* @author A18ccms a18ccms_gmail_com 

* @date 2015年9月6日 下午6:28:59 

*
 */
public class Config {
	
	public static String   ACT_IMG = "http://tonimg.51tonight.com/act/";
	
	public static String   SEL_IMG = "http://tonimg.51tonight.com/seller/";
	
    /**
     * 机身的路径
     */
    public static final String DATA_PATH = CommonTools.getSdCardPathOld();
    
	
	public static final String   CITY_PATH = DATA_PATH+"/tonight.zip/";

}
