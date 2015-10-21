package com.hangzhou.tonight.module.message.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;









import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.text.Layout;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.manager.NoticeManager;
import com.hangzhou.tonight.model.Notice;
import com.hangzhou.tonight.module.base.dto.UserInfoDto;
import com.hangzhou.tonight.module.base.fragment.BEmptyListviewFragment;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;
import com.hangzhou.tonight.module.message.activity.ValidateMessageActivity;
import com.hangzhou.tonight.util.Base64Utils;
import com.hangzhou.tonight.util.HttpRequest;
import com.hangzhou.tonight.util.JsonUtils;
import com.hangzhou.tonight.util.PreferenceConstants;
import com.hangzhou.tonight.util.RC4Utils;

/**
 * 验证消息[listview实现,但需去除分割线,且设置高度,去除点击效果]
 * 
 * @author hank
 */
public class ValidateMessageFragment extends BEmptyListviewFragment {

	private static final int FREND=1;
	private static final int GROUP_APPRIES=2;
	private static final int GROUP_INVITE=3;
	private int tag;
	private String user_uid;
	private String user_nick;
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
			
		AsyncTaskUtil.postData(getActivity(), "getApplyList", params,
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
		String[] strs = { "学习", "天天向上", "智力", "时时下降", "凌晨一点", "写东东" };
		String[] strs1 = { "学习", "天天向上", "智力", "时时下降", "凌晨一点", "写东东" };
		String[] states={"1","9","9","9","0","1"};
		String[] types={"1","2","3","1","2","3"};
		String content = "今天，去了蜜桃酒吧，很不错的酒吧啊";
/*		for (int i = 0, len = strs.length; i < len; i++) {
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
		user_uid = String.valueOf(UserInfoDto.getUser(getActivity()).uid);
		user_nick = String.valueOf(UserInfoDto.getUser(getActivity()).nick);
		adapter = new EntityAdapter();
		mListView.setAdapter(adapter);
		//mListView.setDivider(new ColorDrawable(Color.parseColor("#00000000")));
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			DataModel model = listData.get(position);

			int state=Integer.parseInt(model.getState());
			final int type=Integer.parseInt(model.getType());
			String msg=model.getMsg();
			String title=model.getTitle();
			final String apply_id=model.getApply_id();
			final String uid=model.getUid();
			String nick=model.getNick();
			//String state=model.getState();

			//Notice notice = inviteNotices.get(position);
			ViewHolder hodler = null;
			if (convertView == null) {
				convertView = View.inflate(getActivity(),
						R.layout.item_message_fragment_validate_message_two, null);
				/*convertView = View.inflate(getActivity(),
						R.layout.item_message_fragment_validate_message, null);*/
				hodler = new ViewHolder(convertView);
			}
			//content数组，content[0]=昵称，content[1]=信息，content[2]=头像ID.
			String[] content=getContent(type,state,uid,apply_id,nick,title,msg);
			
			hodler = (ViewHolder) convertView.getTag();
			hodler.tv_username.setText(content[0]);
			hodler.tv_yours.setText(content[1]);
			
			//TODO 头像ID=content[2];
			
			
			/*if(!msg.equals("")){
				hodler.tv_content.setVisibility(View.VISIBLE);
				hodler.tv_content.setText("“"+msg+"”");
			}else{
				hodler.tv_content.setVisibility(View.GONE);
			}
			*/
			final boolean isUserId=user_uid.equals(uid);
			if(!isUserId&&type==1&&state==1){
				hodler.layout_validate.setVisibility(View.VISIBLE);
				hodler.agree.setVisibility(View.VISIBLE);
				hodler.refuse.setVisibility(View.VISIBLE);
				hodler.refuse.setClickable(true);
			}else if(isUserId&type==3&&state==1){
				hodler.layout_validate.setVisibility(View.VISIBLE);
				hodler.agree.setVisibility(View.VISIBLE);
				hodler.refuse.setVisibility(View.VISIBLE);
				hodler.refuse.setClickable(true);
			}else if(!isUserId&type==2&&state==1){
				hodler.layout_validate.setVisibility(View.VISIBLE);
				hodler.agree.setVisibility(View.VISIBLE);
				hodler.refuse.setVisibility(View.VISIBLE);
				hodler.refuse.setClickable(true);
				
			}else if(isUserId&type==3&&state!=1){
				hodler.layout_validate.setVisibility(View.VISIBLE);
				hodler.agree.setVisibility(View.INVISIBLE);
				hodler.refuse.setClickable(false);
			}else if(!isUserId&type==1&&state!=1){
				hodler.layout_validate.setVisibility(View.VISIBLE);
				hodler.agree.setVisibility(View.INVISIBLE);
				hodler.refuse.setClickable(false);
			}else if(!isUserId&type==2&&state!=1){
				hodler.layout_validate.setVisibility(View.VISIBLE);
				hodler.agree.setVisibility(View.INVISIBLE);
				hodler.refuse.setClickable(false);
			}else{
				hodler.layout_validate.setVisibility(View.GONE);
				hodler.refuse.setClickable(true);
			}
			
			if(hodler.refuse.isClickable()){
				hodler.refuse.setText("拒绝");
			}else{
				if(state==0){
					hodler.refuse.setText("已拒绝");
				}else if(state==9){
					hodler.refuse.setText("已同意");
				}
			}
			
			
			
			hodler.agree.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					agree(position,isUserId,type,apply_id,uid);
				}
			});
           hodler.refuse.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					refuse(position,isUserId,type,apply_id,uid);
				}
			});
			return convertView;
		}
		
		
		private String[] getContent(int type,int state,String uid,String apply_id,String nick,String title,String msg){
			String[] content=new String[3];
			
			if(user_uid.equals(uid)){
				content[0]=title;
				content[2]=apply_id;
				if(type==1){
					if(state==0){
						content[1]="拒绝了您的好友申请";
					}else if(state==9){
						content[1]="通过了您的好友申请";
					}
				}else if(type==2){
					if(state==0){
						content[1]="拒绝了您的加群申请";
					}else if(state==9){
						content[1]="通过了您的加群申请";
					}
				}else if(type==3){
					content[0]=msg;
					if(state==0){
						content[1]="邀请您加入群"+title;
					}else if(state==1){
						content[1]="邀请您加入群"+title;
					}else if(state==9){
						content[1]="邀请您加入群"+title;
					}
				}
			}else{
				content[0]=nick;
				content[2]=uid;
				if(type==1){
					if(state==0){
						content[1]="申请加您为好友";
					}else if(state==1){
						content[1]="申请加您为好友";
					}else if(state==9){
						content[1]="申请加您为好友";
					}
				}else if(type==2){
					if(state==0){
						content[1]="申请加入群";
					}else if(state==1){
						content[1]="申请加入群";
					}else if(state==9){
						content[1]="申请加入群";
					}
				}else if(type==3){
					if(state==0){
						content[1]="拒绝了加入群"+title+"的邀请";
					}else if(state==9){
						content[1]="接受加入群"+title+"的邀请";
					}
				}
			}
			
			
		
			return content;
		}

		class ViewHolder {
			TextView tv_username,tv_yours, tv_content;
			LinearLayout layout_validate;
			Button agree,refuse;
			public ViewHolder(View view) {
				this.tv_username = (TextView) view
						.findViewById(R.id.message_comment_username);
				this.tv_yours = (TextView) view
						.findViewById(R.id.message_comment_your);
				this.tv_content = (TextView) view
						.findViewById(R.id.message_comment_content);
				this.layout_validate = (LinearLayout) view
						.findViewById(R.id.validate_message_layout);
				this.agree = (Button) view
						.findViewById(R.id.validate_message_agree);
				this.refuse = (Button) view
						.findViewById(R.id.validate_message_refuse);
				view.setTag(this);
			}
		}
		
		private void agree(final int position,final boolean isUserId,final int type,final String apply_id,final String uid) {

			JSONObject parms = null;
			parms = new JSONObject();
			String urlName="";
			if(!isUserId&&type==1){
				parms.put("tuid", uid);		
				parms.put("nick", user_nick);	
				urlName="passFriend";
			}else if(!isUserId&&type==2){
				parms.put("tuid", uid);		
				parms.put("gid", apply_id);	
				urlName="passGroupApply";
			}else if(isUserId){
				parms.put("gid", apply_id);		
				parms.put("nick", user_nick);	
				urlName="passGroupInvite";
			}
			
		AsyncTaskUtil.postData(getActivity(), urlName, parms,
				new Callback() {

					@Override
					public void onSuccess(JSONObject result) {
						DataModel dataModel=listData.get(position);
						dataModel.setState("9");
						adapter.notifyDataSetChanged();
					}

					@Override
					public void onFail(String msg) {
						
					}
				});
			
			
			
			
			
		/*	
			new AsyncTask<Void, Void, String>() {

				@Override
				protected void onPreExecute() {
					super.onPreExecute();
				}

				@Override
				protected String doInBackground(Void... params) {

					Map<String, String> param =setParams(isUserId,type,apply_id,uid);
					return HttpRequest.submitPostData(PreferenceConstants.TONIGHT_SERVER,
							param, "UTF-8");
				}

				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					DataModel dataModel=listData.get(position);
					dataModel.setState("9");
					adapter.notifyDataSetChanged();
				}
			}.execute();*/
		}
		
		
		private Map<String, String> setParams(boolean isUserId,int type,String apply_id,String uid){
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> parms = new HashMap<String, Object>();
			ArrayList<Object> arry = new ArrayList<Object>();
			
			if(!isUserId&&type==1){
				parms.put("tuid", uid);		
				parms.put("nick", user_nick);	
				arry.add(0, "passFriend");
			}else if(!isUserId&&type==2){
				parms.put("tuid", uid);		
				parms.put("gid", apply_id);	
				arry.add(0, "passGroupApply");
			}else if(isUserId){
				parms.put("gid", apply_id);		
				parms.put("nick", user_nick);	
				arry.add(0, "passGroupInvite");
			}

			
			
			
			arry.add(1, user_uid);
			arry.add(2, parms);
			String data0 = RC4Utils.RC4("mdwi5uh2p41nd4ae23qy4",
					JsonUtils.list2json(arry));

			System.out.println("RC4加密后：   " + data0);
			
			String encoded1 = "";
			try {
				encoded1 = new String(Base64Utils.encode(
						data0.getBytes("ISO-8859-1"), 0, data0.length()));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			System.out.println("base64编码后：     " + encoded1);
			String decode = "";
			try {
				if(!encoded1.equals("")){
					decode = new String(
							Base64.decode(encoded1, Base64.DEFAULT),
							"ISO-8859-1");
				}		
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("base64解码后：" + decode);
			String data7 = RC4Utils.RC4("mdwi5uh2p41nd4ae23qy4", decode);
			System.out.println("RC4解密后：    " +data7);
			
			map.put("d", encoded1);
			
			return map;
			
		}
		
		private void refuse(final int position,final boolean isUserId,final int type,
				final String apply_id,final String uid) {

			
			JSONObject parms = null;
			parms = new JSONObject();
			String urlName="";
			if(!isUserId&&type==1){
				parms.put("tuid", uid);		
				parms.put("nick", user_nick);	
				urlName="refuseFriend";
			}else if(!isUserId&&type==2){
				parms.put("tuid", uid);		
				parms.put("gid", apply_id);	
				urlName="refuseGroupApply";
			}else if(isUserId){
				parms.put("gid", apply_id);		
				parms.put("nick", user_nick);	
				urlName="refuseGroupInvite";
			}
			
		AsyncTaskUtil.postData(getActivity(), urlName, parms,
				new Callback() {

					@Override
					public void onSuccess(JSONObject result) {
						
						DataModel dataModel=listData.get(position);
						dataModel.setState("0");
						adapter.notifyDataSetChanged();
					}

					@Override
					public void onFail(String msg) {
						
					}
				});
			
			
			
			
		/*	new AsyncTask<Void, Void, String>() {

				@Override
				protected void onPreExecute() {
					super.onPreExecute();
				}

				@Override
				protected String doInBackground(Void... params) {

					Map<String, String> param =setParams2(isUserId,type,apply_id,uid);
					return HttpRequest.submitPostData(PreferenceConstants.TONIGHT_SERVER,
							param, "UTF-8");
				}

				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					DataModel dataModel=listData.get(position);
					dataModel.setState("0");
					adapter.notifyDataSetChanged();
				}
			}.execute();*/
		}
		
		
		private Map<String, String> setParams2(boolean isUserId,int type,String apply_id,String uid){
			Map<String, String> map = new HashMap<String, String>();
			Map<String, Object> parms = new HashMap<String, Object>();
			ArrayList<Object> arry = new ArrayList<Object>();
			


			if(!isUserId&&type==1){
				parms.put("tuid", uid);		
				parms.put("nick", user_nick);	
				arry.add(0, "refuseFriend");
			}else if(!isUserId&&type==2){
				parms.put("tuid", uid);		
				parms.put("gid", apply_id);	
				arry.add(0, "refuseGroupApply");
			}else if(isUserId){
				parms.put("gid", apply_id);		
				parms.put("nick", user_nick);	
				arry.add(0, "refuseGroupInvite");
			}
			
			
			arry.add(1, user_uid);
			arry.add(2, parms);
			String data0 = RC4Utils.RC4("mdwi5uh2p41nd4ae23qy4",
					JsonUtils.list2json(arry));

			System.out.println("RC4加密后：   " + data0);
			
			String encoded1 = "";
			try {
				encoded1 = new String(Base64Utils.encode(
						data0.getBytes("ISO-8859-1"), 0, data0.length()));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			System.out.println("base64编码后：     " + encoded1);
			String decode = "";
			try {
				if(!encoded1.equals("")){
					decode = new String(
							Base64.decode(encoded1, Base64.DEFAULT),
							"ISO-8859-1");
				}		
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("base64解码后：" + decode);
			String data7 = RC4Utils.RC4("mdwi5uh2p41nd4ae23qy4", decode);
			System.out.println("RC4解密后：    " +data7);
			
			map.put("d", encoded1);
			
			return map;
			
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
