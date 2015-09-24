package com.hangzhou.tonight.module.base.widget;

import com.hangzhou.tonight.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class TextViewWithDevider extends LinearLayout {

	private View $view;
	private TextView tvText;
	private Drawable drawableLeft,drawableRight;
	private boolean allowDefault = true;
	
	private CharSequence text;
	
	public TextViewWithDevider(Context context) {
		this(context,null);
	}
	
	public TextViewWithDevider(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.textview_with_deviderStyle);
	}
	
	@SuppressLint("Recycle")
	public TextViewWithDevider(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextViewWithDevider, defStyle, 0);
		text = a.getText(R.styleable.TextViewWithDevider_text);
		drawableLeft = a.getDrawable(R.styleable.TextViewWithDevider_drawableLeft);
		drawableRight= a.getDrawable(R.styleable.TextViewWithDevider_drawableRight);
		allowDefault = a.getBoolean(R.styleable.TextViewWithDevider_allow_default, true);
		a.recycle();
		
		$view  = LayoutInflater.from(context).inflate(R.layout.custom_textview_with_devider, this);
		tvText = (TextView) $view.findViewById(R.id.custom_textview);
		
		if(allowDefault){//允许默认行为
			if(null == drawableLeft){
				drawableLeft = context.getResources().getDrawable(R.drawable.empty_layer);  
			}
			if(null == drawableRight){
				drawableRight = context.getResources().getDrawable(R.drawable.chevron_right);
			}
		}
		tvText.setText(text);
		tvText.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,null,drawableRight,null); 
	}
	
}
