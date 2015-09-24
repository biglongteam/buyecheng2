package com.hoo.ad.base.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * TextView帮助类
 * 
 * @author hank
 * 
 */
public class TextViewHelper {

	/**
	 * 用于解决中文换行和特殊统一字符
	 * @param text
	 * @return
	 */
	public static String toText(String text){
		String input = stringFilter(text);
		return replace(input);
	}
	
	/**
	 * 用于解决中文自动换行
	 * @param input
	 * @return
	 */
	public static String replace(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}
	
	/**
	 * 替换、过滤特殊字符  
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String stringFilter(String str) throws PatternSyntaxException {
		str = str.replaceAll("【", "[").replaceAll("】", "]")
				.replaceAll("！", "!");// 替换中文标号
		String regEx = "[『』]"; // 清除掉特殊字符
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	
}
