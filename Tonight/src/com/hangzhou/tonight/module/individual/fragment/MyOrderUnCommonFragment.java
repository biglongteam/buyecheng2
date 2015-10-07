package com.hangzhou.tonight.module.individual.fragment;

import android.os.Bundle;

/**
 * 待评价
 * @author hank
 *
 */
public class MyOrderUnCommonFragment extends MyOrderUnPayFragment {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		PAY_STATE = STATE_UNCOMMON;
		super.onActivityCreated(savedInstanceState);
	}
}
