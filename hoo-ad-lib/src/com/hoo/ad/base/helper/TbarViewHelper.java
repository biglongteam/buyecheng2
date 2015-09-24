package com.hoo.ad.base.helper;

import com.hoo.ad.base.R;
import com.hoo.base.util.ObjectUtil;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TbarViewHelper {
	
	private Activity activity;
	private View actionbarView;
	
	private TextView tvBack;
	private TextView tvTitle;
	private View     handler;
	
	private String title;
	
	private TbarViewHelper(Activity activity,View tbarView){
		this.activity = activity;
		this.actionbarView = tbarView;
	}
	
	public static TbarViewHelper newInstance(Activity context,View tbarView){
		TbarViewHelper helper = new TbarViewHelper(context, tbarView);
		helper.init();
		return helper;
	}
	
	private void init(){
		if(null != actionbarView){
			tvBack = (TextView) actionbarView.findViewById(R.id.custom_actionbar_back);
			tvTitle= (TextView) actionbarView.findViewById(R.id.custom_actionbar_title);
			handler= actionbarView.findViewById(R.id.custom_actionbar_handle);
			
			tvBack.setOnClickListener(new OnClickListener() {
				@Override public void onClick(View v) { activity.onBackPressed(); }
			});
			
			doHandlerView(handler);
			handler.setOnClickListener(new OnClickListener() {
				@Override public void onClick(View v) {
					if(null != handlerClick){ handlerClick.onClick(handler); }
				}
			});
			
			title = ObjectUtil.isEmpty(tvTitle.getText().toString()) ? null : tvTitle.getText().toString();
		}
	}
	
	public void setTitle(String title) {
		this.title = title;
		if(tvTitle != null){ tvTitle.setText(title);}
	}
	
	public String getTitle(){
		return title;
	}
	
	/**
	 * 获取tbarView栏高度
	 * @return
	 */
	public int getTbarViewHeight(){
		return actionbarView.getHeight();
	}
	/**
	 * 设置背景
	 * @param background
	 */
	@SuppressWarnings("deprecation")
	public void setBackground(Drawable background){
		actionbarView.setBackgroundDrawable(background);
	}
	
	/*** 隐藏返回键 */
	public void hideBackView(){ handleBackView(View.GONE); }
	
	public void showBackView(){ handleBackView(View.VISIBLE); }
	
	private void handleBackView(int visibility){
		if(null != tvBack){ tvBack.setVisibility(visibility);}
	}
	
	/**
	 * 用于自定义对handle的操作
	 * @param handle
	 */
	public void doHandlerView(View handle){}
	
	OnHandlerClickListener handlerClick = null;
	public void setOnClickListener(OnHandlerClickListener handlerClick){
		this.handlerClick = handlerClick;
	}
	
	public interface OnHandlerClickListener{
		public void onClick(View handlerView);
	}
	
	
}
