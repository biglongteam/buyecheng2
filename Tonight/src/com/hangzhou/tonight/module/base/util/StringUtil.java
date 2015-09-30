package com.hangzhou.tonight.module.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 * 
 * @author hank
 */
public class StringUtil {

	/**
	 * 是否包含中文
	 * @param str
	 * @return
	 */
	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) { return true; }
		return false;
	}
}
