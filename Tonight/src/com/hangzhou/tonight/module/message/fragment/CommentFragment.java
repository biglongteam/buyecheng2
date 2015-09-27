package com.hangzhou.tonight.module.message.fragment;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.fragment.BEmptyListviewFragment;
/**
 * 个人被评论列表
 * @author hank
 *
 */
public class CommentFragment extends BEmptyListviewFragment {

	List<DataModel> listData = null;
	BaseAdapter adapter;

	@Override protected void doListeners() {
		
	}

	@Override protected void doHandler() {
		listData = new ArrayList<DataModel>();
		String[] strs  = {"学习天天向上智力时时下降","张三","李四","王五","黎明的拥抱","爱的旋律 Melody for Love","Full of Love","Street Centre Garden","激情之夜 Night of Passion","第一次约会","凌晨一点写东东"};
		String content = "今天，去了蜜桃酒吧，很不错的酒吧啊今天，去了蜜桃酒吧，很不错的酒吧啊今天，去了蜜桃酒吧，很不错的酒吧啊";
		for(String str : strs){
			DataModel m = new DataModel();
			m.username = str;
			m.content = content;
			//listData.add(m);
		}
		adapter = new EntityAdapter();
		mListView.setAdapter(adapter);
	}

	class EntityAdapter extends BaseAdapter{

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
			DataModel model = listData.get(position);
			ViewHolder hodler = null;
			if(convertView == null){
				convertView = View.inflate(getActivity(), R.layout.item_message_fragment_comment, null);
				hodler 		= new ViewHolder(convertView);
			}
			hodler = (ViewHolder) convertView.getTag();
			hodler.tv_username.setText(model.username);
			hodler.tv_content .setText(model.content);
			return convertView;
		}
		
		class ViewHolder{
			TextView   tv_username,tv_content;
			public ViewHolder(View view){
				this.tv_username= (TextView) view.findViewById(R.id.message_comment_username);
				this.tv_content = (TextView) view.findViewById(R.id.message_comment_content);
				view.setTag(this);
			}
		}
	}
	
	class DataModel{
		String username,content;
	}

}
