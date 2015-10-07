package com.hangzhou.tonight.module.base.util;

import com.alibaba.fastjson.JSON;

import android.content.Context;

public class ViewUtil {
	
	public static int dp2px(Context context,float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
	}
	public static String getPicTextMutil(String content,String jsonArrayStr,boolean flag){
		return getPicTextMutil(content, jsonArrayStr, flag, null);
	}
	
	public static String getPicTextMutil(String content,String jsonArrayStr,boolean flag,OnDoPicTextListener listener){
		content = "<html><body><div>" + content + "</div>";
		String imgs = "";
		if(flag){
			Object[] urls = JSON.parseArray(jsonArrayStr).toArray();
			int index = 1;
			if(urls.length == 1){ index = 1;}
			else if(urls.length < 5){ index = 2;}else{index = 3;}
			String[][] strs = StringUtil.array2TArray(urls, index);
			String[]     ss = null;
			String src = null;
			for(int i=0,len = strs.length;i<len;i++){
				ss = strs[i];
				for(int j=0,lenj = ss.length ; j <lenj;j++){
					if(ss[j] == null){ break;}
					if(listener != null){
						src = listener.onDoImg(ss[j]);
					}else{
						src = AsyncTaskUtil.IMG_BASE_PATH + "/mood/" + ss[j] + ".jpg";
					}
					imgs += ("<div style='width:"+ (int)Math.floor(100/index) +"%;float:left;overflow:hidden; height:30em;margin:0.1em;'> " +
							"<img src='"+ src + "'>" + "</div>");
				}
			}
			if(imgs.length() > 0){
				content += ("<div style='width: 100%;'>" + imgs + "</div>");
			}
		}
		content += "</body></html>";
		return content;
	}
	
	public static interface OnDoPicTextListener{
		public String onDoImg(String img);
	}
}
