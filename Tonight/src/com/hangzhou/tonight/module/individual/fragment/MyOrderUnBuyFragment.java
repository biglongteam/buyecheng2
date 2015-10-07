package com.hangzhou.tonight.module.individual.fragment;

import android.os.Bundle;

/**
 * 未消费
 * @author hank
 *
 */
public class MyOrderUnBuyFragment extends MyOrderUnPayFragment {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		PAY_STATE = STATE_UNPAY;
		super.onActivityCreated(savedInstanceState);
	}
	
}
