package com.hangzhou.tonight.module.social.fragment;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;

public class MyGroupFragment extends GroupCityWideFragment {

	
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
