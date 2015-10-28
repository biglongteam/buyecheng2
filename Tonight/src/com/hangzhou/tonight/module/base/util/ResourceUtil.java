package com.hangzhou.tonight.module.base.util;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

/**
 * 用于操作android资源的工具类
 * @author hank
 */
public class ResourceUtil {
	
	/**
	 * 将对应path的内容以字符串形式输出
	 * @param context
	 * @param path 文件路径,如：html/json/cities.json
	 * @return
	 */
	public static String fromAssert(Context context,String path){
		InputStream is = null;
		StringBuffer sb = new StringBuffer();
		try {
			is = context.getAssets().open(path);
            byte[] buffer = new byte[1024];
            int byteCount=0;               
            while((byteCount = is.read(buffer)) != -1) {//循环从输入流读取 buffer字节        
                sb.append(new String(buffer));//,"utf-8"
            }
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(null != is){ is.close(); }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
