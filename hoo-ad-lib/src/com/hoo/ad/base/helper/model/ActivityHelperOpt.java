package com.hoo.ad.base.helper.model;

public class ActivityHelperOpt {
	//标题
	private String title;
	//actionBar栏对应的颜色
	private int bgColor;
	//返回按钮对应的文字
	private String backTip;
	/*** 是否允许返回键*/
	private boolean allowBack;
	/**web url*/
	private String url;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getBgColor() {
		return bgColor;
	}
	public void setBgColor(int bgColor) {
		this.bgColor = bgColor;
	}
	public String getBackTip() {
		return backTip;
	}
	public void setBackTip(String backTip) {
		this.backTip = backTip;
	}
	
	public boolean isAllowBack() {
		return allowBack;
	}
	public void setAllowBack(boolean allowBack) {
		this.allowBack = allowBack;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
