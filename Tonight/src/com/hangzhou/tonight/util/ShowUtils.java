package com.hangzhou.tonight.util;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

public class ShowUtils {
	
	/**
	 * 
	* @Title: dimssHint 
	* @Description: TODO(点击输入框 hint消失) 
	* @param @param et    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public  static void dimssHint(EditText et){
		et.setOnFocusChangeListener(new OnFocusChangeListener() {
		    public void onFocusChange(View v, boolean hasFocus) {
		        EditText _v=(EditText)v;
		        if (!hasFocus) {// 失去焦点
		            _v.setHint(_v.getTag().toString());
		        } else {
		            String hint=_v.getHint().toString();
		            _v.setTag(hint);
		            _v.setHint("");
		        }
		    }
		});
	}

}
