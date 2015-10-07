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
	
	/**
	 * 一维数组转二维数组
	 * @return
	 */
	public static String[][] array2TArray(Object[] objs,int index){
		int cell = index,row = (int)Math.ceil(Double.valueOf(objs.length) / index);
		String[][] strs = new String[row][cell];
		int pos = -1;
		for(int i=0,len = row;i<len;i++){
			for(int j=0,lenJ = cell;j<lenJ;j++){
				pos++;
				if(pos == objs.length){ break;}
				strs[i][j] = (String)objs[pos];
			}
		}
		return strs;
	}
}
