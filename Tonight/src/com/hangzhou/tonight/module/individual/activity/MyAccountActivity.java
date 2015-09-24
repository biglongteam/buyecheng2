package com.hangzhou.tonight.module.individual.activity;

import java.util.List;

import com.hangzhou.tonight.module.individual.fragment.MyPackageFragment;
import com.hangzhou.tonight.module.individual.fragment.MyVouchersFragment;

/**
 * 我的账户
 * 
 * @author hank
 * 
 */
public class MyAccountActivity extends MyOrderActivity {
	
	@Override public void onCreateTabs(List<TabModel> list) {
		TabModel tabModel = new TabModel();
		tabModel.title = "代金券";
		tabModel.fragment = new MyVouchersFragment();
		list.add(tabModel);
		tabModel = new TabModel();
		tabModel.title = "礼包";
		tabModel.fragment = new MyPackageFragment();
		list.add(tabModel);
	}
	
}
