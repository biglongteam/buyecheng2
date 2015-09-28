package com.hangzhou.tonight.module.social.fragment;

import com.hangzhou.tonight.util.MyPreference;

public class TonightCircleMyFragment extends TonightCircleCityWideFragment {
	
	@Override public int getSort() { return 3; }

	@Override public String getTuid() {
		return MyPreference.getInstance(getActivity()).getUserId();
	}
}
