package com.hangzhou.tonight.module.social.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.hangzhou.tonight.module.base.constant.ActionConstant;
import com.hangzhou.tonight.util.MyPreference;

/**
 * 我的动态
 * @author hank
 */
public class TonightCircleMyFragment extends TonightCircleCityWideFragment {
	
	BroadcastReceiver refrash = new BroadcastReceiver(){
		@Override public void onReceive(Context context, Intent intent) {
			if(ActionConstant.VALUE_ADD_DYAMICS.equals(intent.getAction())){
				doPostData();
			}
		}
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IntentFilter filter = new IntentFilter(ActionConstant.VALUE_ADD_DYAMICS);
		getActivity().registerReceiver(refrash, filter);
	}
	
	@Override public int getSort() { return 3; }

	@Override public String getTuid() {
		return MyPreference.getInstance(getActivity()).getUserId();
	}
	
	@Override
	public void onDestroy() {
		if(null != refrash){
			getActivity().unregisterReceiver(refrash);
		}
		super.onDestroy();
	}
}
