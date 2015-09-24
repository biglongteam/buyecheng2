package com.hangzhou.tonight.module.social.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.module.base.BaseSingeFragmentActivity;
import com.hangzhou.tonight.module.base.helper.ActivityHelper.OnIntentCreateListener;
import com.hangzhou.tonight.module.base.helper.model.TbarViewModel;
import com.hangzhou.tonight.module.base.widget.TbarView;
import com.hangzhou.tonight.module.individual.fragment.IndividualInfomationFragment;
import com.hangzhou.tonight.module.social.fragment.FriendCityWideFragment.CollectionAdapter;
import com.hangzhou.tonight.module.social.fragment.FriendCityWideFragment.DataModel;

/**
 * 搜索结果界面
 * @author hank
 *
 */
public class SearchResultFragment extends MyFriendFragment {

	@Override protected void doHandler() {
		
		listData = new ArrayList<DataModel>();
		
		adapter = new CollectionAdapter();
		mListView.setAdapter(adapter);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {
			DataModel dm;
			@Override public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				dm = listData.get(position);
				TbarViewModel model = new TbarViewModel();
				model.title = dm.getNick() + "-详细信息";
				BaseSingeFragmentActivity.startActivity(getActivity(), IndividualInfomationFragment.class, model, new OnIntentCreateListener() {
					@Override public void onCreate(Intent intent) {
						intent.putExtra("uid", dm.uid);
					}
				});
			}
		});
	}
	
	@Override protected void doPostData() {
		JSONArray array = JSON.parseArray(getBundle().getString("friends"));
		DataModel dm;JSONObject json;
		for(int i=0,len = array.size();i<len;i++){
			dm = new DataModel();
			json = array.getJSONObject(i);
			dm.uid = json.getString("uid");
			dm.nick = json.getString("nick");
			listData.add(dm);
		}
		adapter.notifyDataSetChanged();
	}
}
