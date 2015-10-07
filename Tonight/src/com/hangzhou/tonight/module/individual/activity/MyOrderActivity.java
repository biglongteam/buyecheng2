package com.hangzhou.tonight.module.individual.activity;

import java.util.List;

import com.hangzhou.tonight.module.base.TabActivity;
import com.hangzhou.tonight.module.individual.fragment.MyOrderFragment;
import com.hangzhou.tonight.module.individual.fragment.MyOrderUnBuyFragment;
import com.hangzhou.tonight.module.individual.fragment.MyOrderUnCommonFragment;
import com.hangzhou.tonight.module.individual.fragment.MyOrderUnPayFragment;

/**
 * 我的订单
 * @author hank
 *
 */
public class MyOrderActivity extends TabActivity {

	@Override public void onCreateTabs(List<TabModel> list) {
		TabModel tabModel = new TabModel();
		tabModel.title = "全部";
		tabModel.fragment = new MyOrderFragment();
		list.add(tabModel);
		tabModel = new TabModel();
		tabModel.title = "待付款";
		tabModel.fragment = new MyOrderUnPayFragment();
		list.add(tabModel);
		tabModel = new TabModel();
		tabModel.title = "待评价";
		tabModel.fragment = new MyOrderUnCommonFragment();
		list.add(tabModel);
		tabModel = new TabModel();
		tabModel.title = "待消费";
		tabModel.fragment = new MyOrderUnBuyFragment();
		list.add(tabModel);
	}
	
}
