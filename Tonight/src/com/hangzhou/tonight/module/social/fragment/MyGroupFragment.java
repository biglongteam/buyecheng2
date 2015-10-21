package com.hangzhou.tonight.module.social.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.module.base.constant.ActionConstant;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;

public class MyGroupFragment extends GroupCityWideFragment {

	BroadcastReceiver reciver = new BroadcastReceiver(){
		@Override public void onReceive(Context context, Intent intent) {
			if(ActionConstant.VALUE_ADD_GROUP_SUCCESS.equals(intent.getAction())){
				refreash();
			}
		}
		
	};
	
	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().registerReceiver(reciver, new IntentFilter(ActionConstant.VALUE_ADD_GROUP_SUCCESS));
	}
	
	@Override public void onDestroy() {
		if(null != reciver){ getActivity().unregisterReceiver(reciver); }
		super.onDestroy();
	}
	
	
	private void refreash(){
		listData.clear();
		page = 0;
		doPostData();
	};
	
	@Override protected void doPostData() {
		
		JSONObject params = new JSONObject();
		params.put("sort", 2);
		params.put("page", page);
		AsyncTaskUtil.postData(getActivity(), "getGroupList", params, new Callback() {
			
			@Override public void onSuccess(JSONObject result) {
				listData.addAll(JSONArray.parseArray(result.getString("groups"),DataModel.class));
				adapter.notifyDataSetChanged();
			}
			
			@Override public void onFail(String msg) {
				
			}
		});
	}
	
}
