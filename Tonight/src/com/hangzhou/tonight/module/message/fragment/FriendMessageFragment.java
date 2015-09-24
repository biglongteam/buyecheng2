package com.hangzhou.tonight.module.message.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.im.ChatActivity1;
import com.hangzhou.tonight.manager.ContacterManager;
import com.hangzhou.tonight.manager.MessageManager;
import com.hangzhou.tonight.manager.XmppConnectionManager;
import com.hangzhou.tonight.model.ChartHisBean;
import com.hangzhou.tonight.model.User;
import com.hangzhou.tonight.module.base.fragment.BEmptyListviewFragment;

/**
 * 好友消息
 * @author hank
 */
public class FriendMessageFragment extends BEmptyListviewFragment {

	List<DataModel> listData = null;
	BaseAdapter adapter;
	private List<ChartHisBean> inviteNotices = new ArrayList<ChartHisBean>();

	@Override protected void doListeners() {
		
	}

	@Override protected void doHandler() {
		listData = new ArrayList<DataModel>();
		String[] strs  = {"学习天天向上智力时时下降","凌晨一点写东东"};
		String[] times = {"昨天","7月1日"};
		String content = "今天，去了蜜桃酒吧，很不错的酒吧啊今天，去了蜜桃酒吧，很不错的酒吧啊今天，去了蜜桃酒吧，很不错的酒吧啊";
		for(int i=0,len = strs.length;i<len;i++){
			String str = strs[i];
			DataModel m = new DataModel();
			m.username  = str;
			m.content   = content;
			m.time      = times[i];
			listData.add(m);
		}
		
		inviteNotices = MessageManager.getInstance(getActivity())
				.getRecentContactsWithLastMsg();
		adapter = new EntityAdapter();
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				ChartHisBean notice =inviteNotices.get(position);
				String jid = notice.getFrom();
				User user = new User();
				user.setJID(jid);
				createChat(user);
			}
			
		});
	}
	
	
	
	/**
	 * 创建一个聊天
	 * 
	 * @param user
	 */
	protected void createChat(User user) {
		Intent intent = new Intent(getActivity(), ChatActivity1.class);
		intent.putExtra("to", user.getJID());
		startActivity(intent);
	}


	class EntityAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return inviteNotices.size();
		}

		@Override
		public Object getItem(int position) {
			return inviteNotices.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ChartHisBean notice = inviteNotices.get(position);
			//Integer ppCount = notice.getNoticeSum();
			ViewHolder hodler = null;
			if(convertView == null){
				convertView = View.inflate(getActivity(), R.layout.item_message_fragment_friend_message, null);
				hodler 		= new ViewHolder(convertView);
			}
			
			String jid = notice.getFrom();
			User u = ContacterManager.getNickname(jid, XmppConnectionManager
					.getInstance().getConnection());
			if (null == u) {
				u = new User();
				u.setJID(jid);
				u.setName(jid);
			}
			
			hodler = (ViewHolder) convertView.getTag();
			hodler.tv_username.setText(u.getName());
			hodler.tv_content .setText(notice.getContent());
			hodler.tv_time.setText(notice.getNoticeTime().substring(5, 16));
			return convertView;
		}
		
		
	/*	private void jsonContent(String content){
			JSONObject object;
			User u=new User();
			try {
				object = new JSONObject(content);
				u.setName(object.getString("uid"));
				nick = object.getString("nick");
				birth = object.getString("birth");
				sex = object.getString("sex");
				phone = object.getString("phone");
				money = object.getString("money");
				favorite = object.getString("favorite");
				praised = object.getString("praised");
				groups = object.getString("groups");
				friends = object.getString("friends");
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}*/
		
		class ViewHolder{
			TextView   tv_username,tv_content,tv_time;
			public ViewHolder(View view){
				this.tv_username= (TextView) view.findViewById(R.id.message_friend_message_username);
				this.tv_content = (TextView) view.findViewById(R.id.message_friend_message_content);
				this.tv_time    = (TextView) view.findViewById(R.id.message_friend_message_time);
				view.setTag(this);
			}
		}
	}
	
	class DataModel{
		String username,content,time;
	}
}
