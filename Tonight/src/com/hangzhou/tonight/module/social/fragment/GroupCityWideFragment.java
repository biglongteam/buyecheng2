package com.hangzhou.tonight.module.social.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.BaseSingeFragmentActivity;
import com.hangzhou.tonight.module.base.constant.SysModuleConstant;
import com.hangzhou.tonight.module.base.fragment.BEmptyListviewFragment;
import com.hangzhou.tonight.module.base.helper.ActivityHelper.OnIntentCreateListener;
import com.hangzhou.tonight.module.base.helper.model.TbarViewModel;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;
import com.hangzhou.tonight.module.social.fragment.TonightCircleCityWideFragment.CollectionAdapter;
import com.hangzhou.tonight.module.social.fragment.TonightCircleCityWideFragment.DataModel;
import com.hangzhou.tonight.module.social.fragment.TonightCircleCityWideFragment.CollectionAdapter.ViewHolder;

/**
 * 群组-同城
 * @author hank
 *
 */
public class GroupCityWideFragment extends BEmptyListviewFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_social_group, container, false);
	}
	
	CollectionAdapter adapter;
	List<DataModel> listData = null;
	
	@Override protected void doListeners() {
		mListView.setOnItemClickListener(new OnItemClickListener() {
			DataModel dm;
			@Override public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				dm = listData.get(position);
				TbarViewModel model = new TbarViewModel(dm.title);
				BaseSingeFragmentActivity.startActivity(getActivity(), GroupDetailFragment.class, model, new OnIntentCreateListener() {
					@Override public void onCreate(Intent intent) {
						intent.putExtra("gid", dm.gid);
					}
				});
			}
		});
	}
	
	@Override protected void doHandler() {

		listData = new ArrayList<DataModel>();
		
		/*String[] strs = {
				 "蜜桃"
				,"8090迷幻系小聚"
				,"19:01"
				,"已售220"};
		for(int i=0,len = 10;i<len;i++){
			DataModel m = new DataModel();
			m.title = strs[0];
			m.label = strs[1];
			listData.add(m);
		}*/
		adapter = new CollectionAdapter();
		mListView.setAdapter(adapter);

	}
	boolean flag = false;
	@Override protected void doPostData() {
		super.doPostData();
		if(flag){ return; }
		JSONObject params = new JSONObject();
		params.put("sort", 1);
		params.put("page", page);
		params.put("city_id", SysModuleConstant.getCityId(getActivity()));
		AsyncTaskUtil.postData(getActivity(), "getGroupList", params, new Callback() {
			
			@Override public void onSuccess(JSONObject result) {
				if(result.getIntValue("nomore")==1){flag = true; }else{page++;}
				listData.addAll(JSONArray.parseArray(result.getString("groups"),DataModel.class));
				adapter.notifyDataSetChanged();
			}
			
			@Override public void onFail(String msg) {
				
			}
		});
	}
	
	
	class CollectionAdapter extends BaseAdapter {

		@Override public int getCount() {
			return listData.size();
		}

		@Override public Object getItem(int position) {
			return listData.get(position);
		}

		@Override public long getItemId(int position) {
			return 0;
		}

		@Override public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			DataModel model = listData.get(position);
			if (convertView == null) {
				convertView = View.inflate(getActivity(),R.layout.item_fragment_social_group, null);
				holder = new ViewHolder(convertView);
			}
			holder = (ViewHolder) convertView.getTag();
			holder.tv_title.setText(model.title);
			holder.tv_label.setText(model.intro);
			return convertView;
		}

		class ViewHolder {
			public ImageView iv_photo;
			public TextView tv_title, tv_label;
			public ViewHolder(View view) {
				iv_photo= (ImageView)view.findViewById(R.id.social_group_photo);
				tv_title= (TextView) view.findViewById(R.id.social_group_title);
				tv_label= (TextView) view.findViewById(R.id.social_group_label);
				view.setTag(this);
			}
		}
	}

	public static class DataModel {
		String gid,title, intro,headphoto;
		public String getGid() { 			return gid; 		} 		public void setGid(String gid) { 			this.gid = gid; 		} 		public String getTitle() { 			return title; 		} 		public String getIntro() { 			return intro; 		} 		public String getHeadphoto() { 			return headphoto; 		} 		public void setTitle(String title) { 			this.title = title; 		} 		public void setIntro(String intro) { 			this.intro = intro; 		} 		public void setHeadphoto(String headphoto) { 			this.headphoto = headphoto; 		} 		
	}

}
