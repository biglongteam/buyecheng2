package com.hangzhou.tonight.module.message.fragment;

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
 * 赞列表界面
 * @author hank
 *
 */
public class GoodFragment extends BEmptyListviewFragment {

	
	List<GoodDataModel> listData = null;
	BaseAdapter adapter;

	@Override protected void doListeners() {
		
	}

	@Override protected void doHandler() {
		listData = new ArrayList<GoodDataModel>();
		String[] strs = {"学习天天向上智力时时下降","张三","李四","王五","黎明的拥抱","爱的旋律 Melody for Love","Full of Love","Street Centre Garden","激情之夜 Night of Passion","第一次约会"};
		for(String str : strs){
			GoodDataModel m = new GoodDataModel();
			m.username = str;
			//listData.add(m);
		}
		adapter = new GoodAdapter();
		mListView.setAdapter(adapter);
	}

	class GoodAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return listData.size();
		}

		@Override
		public Object getItem(int position) {
			return listData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			GoodDataModel model = listData.get(position);
			ViewHolder hodler = null;
			if(convertView == null){
				convertView = View.inflate(getActivity(), R.layout.item_message_fragment_good, null);
				hodler 		= new ViewHolder(convertView);
			}
			hodler = (ViewHolder) convertView.getTag();
			hodler.tv_username.setText(model.username);
			return convertView;
		}
		
		class ViewHolder{
			ImageView  iv_head_url;
			TextView   tv_username;
			public ViewHolder(View view){
				this.iv_head_url = (ImageView) view.findViewById(R.id.message_good_head);
				this.tv_username = (TextView) view.findViewById(R.id.message_good_username);
				view.setTag(this);
			}
		}
	}
	
	class GoodDataModel{
		String username,headUrl;
	}
}
