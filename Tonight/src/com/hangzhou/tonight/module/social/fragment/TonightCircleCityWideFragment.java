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
 * 不夜城 - 同城
 * @author hank
 *
 */
public class TonightCircleCityWideFragment extends BEmptyListviewFragment {

	CollectionAdapter adapter;
	List<DataModel> listData = null;
	
	@Override protected void doListeners() {
		
	}
	
	@Override protected void doHandler() {

		listData = new ArrayList<DataModel>();
		
		String[] strs = {
				 "蜜桃"
				,"8090迷幻系小聚"
				,"19:01"
				,"已售220"};
		for(int i=0,len = 10;i<len;i++){
			DataModel m = new DataModel();
			m.name	 = strs[0];
			m.content= strs[1];
			m.time 	 = strs[2];
			m.age 	 = 23;
			m.good   = 5;
			m.comment= 6;
			listData.add(m);
		}
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
				convertView = View.inflate(getActivity(),R.layout.item_fragment_tonight_circle, null);
				holder = new ViewHolder(convertView);
			}
			holder = (ViewHolder) convertView.getTag();
			holder.tv_age.setText("" + model.age);
			holder.tv_comment.setText("评论 " + model.comment);
			holder.tv_name.setText(model.name);
			return convertView;
		}

		class ViewHolder {
			public ImageView iv_headphoto;
			public TextView tv_name, tv_content, tv_time, tv_age,tv_good,tv_comment;
			public ViewHolder(View view) {
				iv_headphoto= (ImageView)view.findViewById(R.id.tonight_circle_head_photo);
				tv_name 	= (TextView) view.findViewById(R.id.tonight_circle_name);
				tv_content 	= (TextView) view.findViewById(R.id.tonight_circle_content);
				tv_time 	= (TextView) view.findViewById(R.id.tonight_circle_time);
				tv_age 		= (TextView) view.findViewById(R.id.tonight_circle_age);
				tv_comment  = (TextView) view.findViewById(R.id.tonight_circle_comment);
				view.setTag(this);
			}
		}
	}

	class DataModel {
		String name, content, time, headphoto;
		int age,good,comment;
	}
}
