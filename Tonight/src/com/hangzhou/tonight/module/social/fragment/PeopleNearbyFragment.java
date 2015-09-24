package com.hangzhou.tonight.module.social.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.BaseSingeFragmentActivity;
import com.hangzhou.tonight.module.base.constant.SysModuleConstant;
import com.hangzhou.tonight.module.base.fragment.BEmptyListviewFragment;
import com.hangzhou.tonight.module.base.helper.ActivityHelper.OnIntentCreateListener;
import com.hangzhou.tonight.module.base.helper.model.TbarViewModel;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.DateUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;
import com.hangzhou.tonight.module.individual.fragment.IndividualInfomationFragment;
import com.hangzhou.tonight.module.social.fragment.FriendCityWideFragment.DataModel;
import com.hangzhou.tonight.util.MyPreference;

/**
 * 附近的人
 * @author hank
 *
 */
public class PeopleNearbyFragment extends BEmptyListviewFragment {

	CollectionAdapter adapter;
	List<DataModel> listData = null;
	
	@Override protected void doListeners() {
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
	
	@Override protected void doHandler() {

		listData = new ArrayList<DataModel>();
		adapter = new CollectionAdapter();
		mListView.setAdapter(adapter);

	}
	
	@Override protected void doPostData() {
		super.doPostData();
		JSONObject params = new JSONObject();
		params.put("lon", MyPreference.getInstance(getActivity()).getLocation_j());
		params.put("lat", MyPreference.getInstance(getActivity()).getLocation_w());
		AsyncTaskUtil.postData(getActivity(), "getAroundUser", params, new Callback() {
			@Override public void onSuccess(JSONObject result) {
				List<DataModel> list = JSONArray.parseArray(result.getString("userList"), DataModel.class);
				listData.addAll(list);
				adapter.notifyDataSetChanged();
			}
			
			@Override public void onFail(String msg) {
				if(SysModuleConstant.VALUE_DEV_MODEL){
					String[] strs = {
							 "蜜桃","今天好开心，开心!!!!开心！","100m"};
					for(int i=0,len = 10;i<len;i++){
						DataModel m = new DataModel();
						m.nick = strs[0] + i;
						m.sign = strs[1];
						m.distance = strs[2];
						m.birth = "1" + i;//应该是2001-1-2
						m.sex   = i % 2;
						listData.add(m);
					}
					adapter.notifyDataSetChanged();
				}
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
				convertView = View.inflate(getActivity(),R.layout.item_fragment_social_people_nearby, null);
				holder = new ViewHolder(convertView);
			}
			holder = (ViewHolder) convertView.getTag();
			holder.tv_name.setText(model.nick);
			holder.tv_content.setText(model.sign);
			holder.tv_distance.setText(model.distance);
			holder.tv_age.setText(""+DateUtil.getCurrentAgeByBirthdate(model.birth));
			holder.tv_age.setBackgroundDrawable(getResources().getDrawable(model.sex == 1 ? R.drawable.shape_module_sex_male : R.drawable.shape_module_sex_female));
			return convertView;
		}

		class ViewHolder {
			public ImageView iv_photo;
			public TextView tv_name,tv_distance,tv_content,tv_age;
			public ViewHolder(View view) {
				iv_photo= (ImageView)view.findViewById(R.id.social_people_nearby_head);
				tv_name = (TextView) view.findViewById(R.id.social_people_nearby_username);
				tv_content = (TextView) view.findViewById(R.id.social_people_nearby_content);
				tv_distance= (TextView) view.findViewById(R.id.social_people_nearby_distance);
				tv_age     = (TextView) view.findViewById(R.id.social_people_nearby_age);
				view.setTag(this);
			}
		}
	}

	public static class DataModel {
		String uid,nick,sign,distance,headphoto,birth;
		int sex;// 0 女  1 男
		public String getUid() { return uid; } public void setUid(String uid) { this.uid = uid; } public String getNick() { return nick; } public String getSign() { return sign; } public String getDistance() { return distance; } public String getHeadphoto() { return headphoto; } public String getBirth() { return birth; } public int getSex() { return sex; } public void setNick(String nick) { this.nick = nick; } public void setSign(String sign) { this.sign = sign; } public void setDistance(String distance) { this.distance = distance; } public void setHeadphoto(String headphoto) { this.headphoto = headphoto; } public void setBirth(String birth) { this.birth = birth; } public void setSex(int sex) { this.sex = sex; }

	}
}
