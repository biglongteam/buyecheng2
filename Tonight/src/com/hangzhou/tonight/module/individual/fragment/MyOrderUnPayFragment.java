package com.hangzhou.tonight.module.individual.fragment;

import android.os.Bundle;

/**
 * 未支付订单
 * @author hank
 *
 */
public class MyOrderUnPayFragment extends MyOrderFragment {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		PAY_STATE = STATE_UNPAY;
		super.onActivityCreated(savedInstanceState);
	}
	
}
