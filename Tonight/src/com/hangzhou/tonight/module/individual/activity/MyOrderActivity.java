package com.hangzhou.tonight.module.individual.activity;

import java.util.List;

import com.hangzhou.tonight.module.base.TabActivity;
import com.hangzhou.tonight.module.individual.fragment.MyOrderFragment;
import com.hangzhou.tonight.module.individual.fragment.MyOrderUnPayFragment;

/**
 * 我的订单
 * @author hank
 *
 */
public class MyOrderActivity extends TabActivity {

	@Override public void onCreateTabs(List<TabModel> list) {
		TabModel tabModel = new TabModel();
		tabModel.title = "已支付";
		tabModel.fragment = new MyOrderFragment();
		list.add(tabModel);
		tabModel = new TabModel();
		tabModel.title = "未支付";
		tabModel.fragment = new MyOrderUnPayFragment();
		list.add(tabModel);
	}
	
}
