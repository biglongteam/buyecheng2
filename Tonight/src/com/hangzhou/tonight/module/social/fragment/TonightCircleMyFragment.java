package com.hangzhou.tonight.module.social.fragment;

import com.hangzhou.tonight.util.MyPreference;

/**
 * 我的动态
 * @author hank
 */
public class TonightCircleMyFragment extends TonightCircleCityWideFragment {
	
	@Override public int getSort() { return 3; }

	@Override public String getTuid() {
		return MyPreference.getInstance(getActivity()).getUserId();
	}
}
