package com.hoo.ad.base.activity;


import com.hoo.ad.base.R;
import com.hoo.ad.base.helper.TbarViewHelper;

import android.os.Bundle;

/**
 * 自定义Actionbar[仅封装逻辑,使用时需将tbarView引入]
 * 默认获取 的对象ID为 custom_actionbar_container
 * @author hank
 */
public abstract class BCustomActionbarActivity extends BActivity {
	
	protected void init(Bundle savedInstanceState) {
		doView();
		doActionbar();
		doListeners();
		doHandler(getBundle(savedInstanceState));
		doDefault();
	}
	
	protected void doDefault(){
		if(null == getTbarViewHelper().getTitle() && null != getTitle()){ getTbarViewHelper().setTitle(getTitle().toString());}
	}
	
	protected void doActionbar() {getTbarViewHelper();}
	
	TbarViewHelper tbarViewHelper;
	public TbarViewHelper getTbarViewHelper(){
		if(tbarViewHelper == null){
			tbarViewHelper = TbarViewHelper.newInstance(getActivity(), findViewById(R.id.custom_actionbar_container));
		}
		return tbarViewHelper;
	}
}
