package com.hangzhou.tonight.module.message.fragment;

import java.util.ArrayList;
import java.util.List;




import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.manager.NoticeManager;
import com.hangzhou.tonight.model.Notice;
import com.hangzhou.tonight.module.base.fragment.BEmptyListviewFragment;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;
import com.hangzhou.tonight.module.message.activity.ValidateMessageActivity;

/**
 * 验证消息[listview实现,但需去除分割线,且设置高度,去除点击效果]
 * 
 * @author hank
 */
public class ValidateMessageFragment extends BEmptyListviewFragment {

	List<DataModel> listData = null;
	BaseAdapter adapter;
	private List<Notice> inviteNotices = new ArrayList<Notice>();
	private NoticeManager noticeManager;

	@Override
	protected void doListeners() {
	}

	@Override
	protected void doPostData() {
		super.doPostData();
		JSONObject params = null;
			params = new JSONObject();
			params.put("page", page);
			params.put("time",time);
			
		AsyncTaskUtil.postData(getActivity(), "getApplyList", null,
				new Callback() {

					@Override
					public void onSuccess(JSONObject result) {
						listData.addAll(JSONArray.parseArray(
								result.getString("applies"),
								DataModel.class));
						adapter.notifyDataSetChanged();
					}

					@Override
					public void onFail(String msg) {
						
					}
				});
	}

	@Override
	protected void doHandler() {
		listData = new ArrayList<DataModel>();
	/*	String[] strs = { "学习", "天天向上", "智力", "时时下降", "凌晨一点", "写东东" };
		String[] strs1 = { "学习", "天天向上", "智力", "时时下降", "凌晨一点", "写东东" };
		String[] states={"1","9","9","9","0","1"};
		String[] types={"1","2","3","1","2","3"};
		String content = "今天，去了蜜桃酒吧，很不错的酒吧啊";
		for (int i = 0, len = strs.length; i < len; i++) {
			String str = strs[i];
			String str1 = strs1[i];
			String state=states[i];
			String type=types[i];
			DataModel m = new DataModel();
			m.nick = str;
			m.msg = content;
			m.state=states[i];
			m.type=types[i];
			listData.add(m);
		}*/

		/*noticeManager = NoticeManager.getInstance(getActivity());
		inviteNotices = noticeManager.getNoticeListByTypeAndPage(
				Notice.ADD_FRIEND, Notice.All, 1, 10);*/
		adapter = new EntityAdapter();
		mListView.setAdapter(adapter);
		mListView.setDivider(new ColorDrawable(Color.parseColor("#00000000")));
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int posiiton,
					long arg3) {
				DataModel model = listData.get(posiiton);
				int state=Integer.parseInt(model.getState());
				int type=Integer.parseInt(model.getType());
				String msg=model.getMsg();
				String goupName=model.getTitle();
				String tuid=model.getApply_id();

				Intent intent =new Intent(getActivity(),ValidateMessageActivity.class);
				intent.putExtra("tag", type);
				intent.putExtra("uid", model.getUid());
				intent.putExtra("msg",msg);
				intent.putExtra("nickname", model.getNick());
				intent.putExtra("tuid", tuid);
				if(type==1&&state==1){
					startActivity(intent);
				}else if((type==2||type==3)&&state==1){
					intent.putExtra("goup", goupName);
					
					startActivity(intent);
				}
			}
		});
	}

	class EntityAdapter extends BaseAdapter {

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

			int state=Integer.parseInt(model.getState());
			int type=Integer.parseInt(model.getType());
			String msg=model.getMsg();
			String goupName=model.getTitle();
			//String state=model.getState();

			//Notice notice = inviteNotices.get(position);
			ViewHolder hodler = null;
			if (convertView == null) {
				convertView = View.inflate(getActivity(),
						R.layout.item_message_fragment_comment, null);
				/*convertView = View.inflate(getActivity(),
						R.layout.item_message_fragment_validate_message, null);*/
				hodler = new ViewHolder(convertView);
			}
			
			String yours=getContent(type,state,goupName);
			
			hodler = (ViewHolder) convertView.getTag();
			hodler.tv_username.setText(model.getNick());
			hodler.tv_yours.setText(yours);
			if(!msg.equals("")){
				hodler.tv_content.setVisibility(View.VISIBLE);
				hodler.tv_content.setText("“"+msg+"”");
			}else{
				hodler.tv_content.setVisibility(View.GONE);
			}
			
			
			
			return convertView;
		}
		
		
		private String getContent(int type,int state,String goupName){
			String content="";
			if(type==1){
				if(state==0){
					content="拒绝了您的好友申请";
				}else if(state==1){
					content="请求加您为好友";
				}else if(state==9){
					content="通过了您的好友申请";
				}
			}else if(type==2){
				if(state==0){
					content="拒绝了您的加群申请";
				}else if(state==1){
					content="申请加入群";
				}else if(state==9){
					content="通过了您的加群申请";
				}
			}else if(type==3){
				if(state==0){
					content="拒绝了加入群"+goupName+"的邀请";
				}else if(state==1){
					content="邀请您加入群"+goupName;
				}else if(state==9){
					content="接受加入群"+goupName+"的邀请";
				}
			}
			return content;
		}

		class ViewHolder {
			TextView tv_username,tv_yours, tv_content;

			public ViewHolder(View view) {
				this.tv_username = (TextView) view
						.findViewById(R.id.message_comment_username);
				this.tv_yours = (TextView) view
						.findViewById(R.id.message_comment_your);
				this.tv_content = (TextView) view
						.findViewById(R.id.message_comment_content);
				view.setTag(this);
			}
		}

		/*private void jsonContent(String content) {
			JSONObject object;
			User u = new User();
			String userName = "";
			String message = "";
			try {
				object = new JSONObject(content);
				userName = object.getString("");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
*/
	}

	public static class DataModel {
		// 用户名、消息内容
		String uid,nick,type,title,apply_id,msg,state,time;

		public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}

		public String getNick() {
			return nick;
		}
		
		

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public void setNick(String nick) {
			this.nick = nick;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getApply_id() {
			return apply_id;
		}

		public void setApply_id(String apply_id) {
			this.apply_id = apply_id;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}
		
	}

}
