package com.hangzhou.tonight.module.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hangzhou.tonight.R;

/**
 * 自定义actionTbarView
 * @author hank
 *
 */
public class TbarView extends RelativeLayout{

	private View $view;
	TextView tvBack,tvTitle;
	View vAction;
	
	public TbarView(Context context) {
		this(context,null);
	}
	
	public TbarView(Context context, AttributeSet attrs) {
		this(context, attrs,R.attr.tbarviewStyle);
	}
	
	public TbarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		$view = LayoutInflater.from(context).inflate(R.layout.custom_tbarview, this);
		
		/*
		 需要做的有:  
		  tbar背景色
		  title\backtitle 文字 颜色 大小
		  action向外暴露
		  
		  */
		
		
		tvBack = (TextView) $view.findViewById(R.id.custom_actionbar_back);
		tvTitle= (TextView) $view.findViewById(R.id.custom_actionbar_title);
		
		
	}

	public void setTitle(CharSequence text){ tvTitle.setText(text); }
	public CharSequence getTitle(){ return tvTitle.getText(); }
	
}
