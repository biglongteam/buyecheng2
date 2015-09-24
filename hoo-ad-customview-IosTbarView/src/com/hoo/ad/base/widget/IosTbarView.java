package com.hoo.ad.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 仿照IOS的TbarView视图组件
 * 有关支持:tbar 查询、右侧菜单或者多按钮的问题.请通过popwindow等方式扩展解决
 * @author hank
 *
 */
public class IosTbarView extends RelativeLayout {

	View view;
	TextView tvTitle,tvBack,tvHandle;
	String back,title,handleTitle;
	boolean hideBack;
	Drawable backIcon,handleIcon;
	public IosTbarView(Context context) {
		this(context, null);
	}

	public IosTbarView(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.IosTbarViewStyle);
	}

	public IosTbarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		view = View.inflate(context, R.layout.widget_ios_tbar_view, this);
		//注意: actionbar本身若是居中,则需要计算两端宽度
		tvBack  = (TextView) view.findViewById(R.id.custom_actionbar_back);
		tvTitle = (TextView) view.findViewById(R.id.custom_actionbar_title);
		tvHandle= (TextView) view.findViewById(R.id.custom_actionbar_handle);
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IosTbarViewStyle, defStyle, 0);
		
		title= a.getString(R.styleable.IosTbarViewStyle_actionbarTitle);
		hideBack   = a.getBoolean(R.styleable.IosTbarViewStyle_hideBack, false);
		back       = a.getString(R.styleable.IosTbarViewStyle_backTitle);
		backIcon   = a.getDrawable(R.styleable.IosTbarViewStyle_backIcon);
		handleTitle= a.getString(R.styleable.IosTbarViewStyle_handleTitle);
		handleIcon = a.getDrawable(R.styleable.IosTbarViewStyle_handleIcon);
		
		a.recycle();
		
		init();
	}
	
	private void init(){
		if(null != title){ setTitle(title); }
		if(null != back) { setBackText(back);}
		if(null != backIcon){ tvBack.setCompoundDrawablesWithIntrinsicBounds(backIcon, null, null, null); }
		if(null != handleTitle){ tvHandle.setText(handleTitle); }
		if(null != handleIcon){ tvHandle.setCompoundDrawablesWithIntrinsicBounds(handleIcon, null, null, null);}
		if(hideBack){ tvBack.setVisibility(View.GONE);}
		//在初始化时变更 中间title的margin属性么?
	}
	
	/**
	 * 设置顶部栏标题
	 * @param text
	 */
	public void setTitle(CharSequence text){
		tvTitle.setText(text);
	}
	
	/**
	 * 获取顶部栏标题
	 * @return
	 */
	public CharSequence getTtitle(){
		return tvTitle.getText();
	}
	
	/**
	 * 设置返回文字
	 * @param text
	 */
	public void setBackText(CharSequence text){
		tvBack.setText(text);
	}
	/**
	 * 设置背景色
	 * @param color 颜色值
	 */
	public void setBackgroundColor(int color){
		view.setBackgroundColor(color);
	}
	
}
