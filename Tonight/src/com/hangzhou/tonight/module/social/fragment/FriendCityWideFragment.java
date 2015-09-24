package com.hangzhou.tonight.module.social.fragment;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.fragment.BEmptyListviewFragment;

/**
 * 同城好友[地理位置获取在:MyPreference]
 * 【现在去掉同城好友界面,保留我的好友--MyFriend通过继承方式暂不删除该类】
 * @author hank
 *
 */
public class FriendCityWideFragment extends BEmptyListviewFragment {

	CollectionAdapter adapter;
	List<DataModel> listData = null;
	
	
	
	
	@Override protected void doListeners() {
		
	}
	
	@Override protected void doHandler() {

		listData = new ArrayList<DataModel>();
		
		adapter = new CollectionAdapter();
		mListView.setAdapter(adapter);

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
				convertView = View.inflate(getActivity(),R.layout.item_activity_social_friend, null);
				holder = new ViewHolder(convertView);
			}
			holder = (ViewHolder) convertView.getTag();
			holder.tv_name.setText(model.nick);
			return convertView;
		}

		class ViewHolder {
			public ImageView iv_photo;
			public TextView tv_name;
			public ViewHolder(View view) {
				iv_photo= (ImageView)view.findViewById(R.id.social_friend_headphoto);
				tv_name = (TextView) view.findViewById(R.id.social_friend_name);
				view.setTag(this);
			}
		}
	}

	public static class DataModel {
		String uid;
		String nick,headphoto;
		public String getUid() {
			return uid;
		}
		public String getNick() {
			return nick;
		}
		public String getHeadphoto() {
			return headphoto;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		public void setNick(String nick) {
			this.nick = nick;
		}
		public void setHeadphoto(String headphoto) {
			this.headphoto = headphoto;
		}
		
	}


}
