package com.hangzhou.tonight.module.social.activity;

import java.util.List;

import com.hangzhou.tonight.module.base.TabActivity;
import com.hangzhou.tonight.module.social.fragment.TonightCircleCityWideFragment;
import com.hangzhou.tonight.module.social.fragment.TonightCircleMyFragment;

/**
 * 不夜圈
 * @author hank
 */
public class TonightCircleActivity extends TabActivity {
	@Override
	public void onCreateTabs(List<TabModel> list) {
		TabModel tabModel = new TabModel();
		tabModel.title = "同城";
		tabModel.fragment = new TonightCircleCityWideFragment();
		list.add(tabModel);
		tabModel = new TabModel();
		tabModel.title = "我的";
		tabModel.fragment = new TonightCircleMyFragment();
		list.add(tabModel);
	}
}
