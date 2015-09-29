package com.hangzhou.tonight.module.base.helper;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {
	
	public static void show(Context context,CharSequence text) {
		if(null != context)Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	
}
