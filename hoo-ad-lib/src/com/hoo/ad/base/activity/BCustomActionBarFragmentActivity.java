package com.hoo.ad.base.activity;

import android.os.Bundle;

import com.hoo.ad.base.R;
import com.hoo.ad.base.helper.TbarViewHelper;

/**
 * 自定义FragmentActivity[添加TbarView 处理]
 * @author hank
 */
public abstract class BCustomActionBarFragmentActivity extends BFragmentActivity {
	
	@Override protected void init(Bundle bundle) {
		doView();
		doActionbar();
		doListeners();
		doHandler(getBundle(bundle));
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
