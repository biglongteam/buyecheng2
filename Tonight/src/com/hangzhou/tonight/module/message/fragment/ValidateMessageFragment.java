package com.hangzhou.tonight.module.message.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.manager.MessageManager;
import com.hangzhou.tonight.manager.NoticeManager;
import com.hangzhou.tonight.model.Notice;
import com.hangzhou.tonight.module.base.fragment.BEmptyListviewFragment;
import com.hangzhou.tonight.module.base.util.ViewUtil;

/**
 * 验证消息[listview实现,但需去除分割线,且设置高度,去除点击效果]
 * @author hank
 */
public class ValidateMessageFragment extends BEmptyListviewFragment {

	List<DataModel> listData = null;
	BaseAdapter adapter;
	private List<Notice> inviteNotices = new ArrayList<Notice>();
	private NoticeManager noticeManager;

	@Override protected void doListeners() {}

	@Override protected void doHandler() {
		listData = new ArrayList<DataModel>();
		String[] strs  = {"学习","天天向上","智力","时时下降","凌晨一点","写东东"};
		String content = "今天，去了蜜桃酒吧，很不错的酒吧啊";
		for(int i=0,len = strs.length;i<len;i++){
			String str = strs[i];
			DataModel m = new DataModel();
			m.username  = str;
			m.content   = content;
			listData.add(m);
		}
		noticeManager = NoticeManager.getInstance(getActivity());
		inviteNotices = noticeManager.getNoticeListByTypeAndPage(Notice.ADD_FRIEND,Notice.All,1,10);
		adapter = new EntityAdapter();
		mListView.setAdapter(adapter);
		mListView.setDivider(new ColorDrawable(Color.parseColor("#00000000")));
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
			//Notice notice = inviteNotices.get(position);
			ViewHolder hodler = null;
			if(convertView == null){
				convertView = View.inflate(getActivity(), R.layout.item_message_fragment_validate_message, null);
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
				this.tv_username= (TextView) view.findViewById(R.id.validate_message_username);
				this.tv_content = (TextView) view.findViewById(R.id.validate_message_content);
				view.setTag(this);
			}
		}
		
		
		
		
		
		
	}
	
	class DataModel{
		//用户名、消息内容
		String username,content;
	}

}
