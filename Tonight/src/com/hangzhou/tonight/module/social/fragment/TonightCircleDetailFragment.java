package com.hangzhou.tonight.module.social.fragment;

import com.hangzhou.tonight.module.base.fragment.BFragment;
import com.hangzhou.tonight.module.base.helper.ToastHelper;

/**
 * 不夜城 - 动态详情
 * @author hank
 */
public class TonightCircleDetailFragment extends BFragment{

	@Override protected void doView() {
		
	}

	@Override protected void doListeners() {
	}

	@Override protected void doHandler() {
		String mid = getBundle().getString("mid");
		ToastHelper.show(getActivity(), mid);
	}
	
}
