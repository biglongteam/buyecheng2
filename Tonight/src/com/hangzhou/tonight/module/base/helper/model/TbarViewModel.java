package com.hangzhou.tonight.module.base.helper.model;

/**
 * actionbarde TbarView 视图
 * @author hank
 */
public class TbarViewModel{
	public String title;
	public boolean canBack = true;
	public TbarViewModel(){super();};
	public TbarViewModel(String title) {
		this(); this.title = title;
	}
	public TbarViewModel(String title, boolean canBack) {
		this(title); this.canBack = canBack;
	}
}
