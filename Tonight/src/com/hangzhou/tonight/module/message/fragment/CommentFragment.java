package com.hangzhou.tonight.module.message.fragment;

import java.util.ArrayList;
import java.util.List;




import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.constant.SysModuleConstant;
import com.hangzhou.tonight.module.base.fragment.BEmptyListviewFragment;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;

/**
 * 个人被评论列表
 * @author hank
 *
 */
public class CommentFragment extends BEmptyListviewFragment {

	List<DataModel> listData = null;
	BaseAdapter adapter;

	@Override 
	protected void doListeners() {
		
	}

	@Override
	protected void doHandler() {
		listData = new ArrayList<DataModel>();

		/*String[] strs  = {"学习天天向上智力时时下降","张三","李四","王五","黎明的拥抱","爱的旋律 Melody for Love","Full of Love","Street Centre Garden","激情之夜 Night of Passion","第一次约会","凌晨一点写东东"};
		String content = "今天，去了蜜桃酒吧，很不错的酒吧啊今天，去了蜜桃酒吧，很不错的酒吧啊今天，去了蜜桃酒吧，很不错的酒吧啊";
		for(String str : strs){
			DataModel m = new DataModel();
			m.username = str;
			m.content = content;
			//listData.add(m);
		}*/

		adapter = new EntityAdapter();
		mListView.setAdapter(adapter);
	}

	/*@Override
	protected void doPostData() {
		super.doPostData();
		JSONObject params =new JSONObject();
		params.put("page", page);
		params.put("time", time);
		AsyncTaskUtil.postData(getActivity(), "getSelfReply", params, new Callback() {
			
			@Override public void onSuccess(JSONObject result) {
				List<DataModel> list = JSONArray.parseArray(result.getString("replys"), DataModel.class);
				if(list.size() > 0){ page++;}
				listData.addAll(list);
				adapter.notifyDataSetChanged();
			}
			
			@Override public void onFail(String msg) {
				if(SysModuleConstant.VALUE_DEV_MODEL){
					listData.clear();
					String[] strs = { "2012-01-10" ,"8090迷幻系小聚迷幻系小聚迷幻系小聚迷幻系小聚迷幻系小聚迷幻系小聚迷幻系小聚"};
					for(int i=0,len = 1;i<len;i++){
						DataModel m = new DataModel();
						m.time   = new Date().getTime();
						m.msg= strs[1];
						listData.add(m);
					}
					adapter.notifyDataSetChanged();
				}
			}
		});*/
	

	@Override 
	protected void doPostData() {
		loadData();
	}
	
	int  page = 0;
	long time = 0;
	private void loadData(){
		page++;
		JSONObject params = new JSONObject();
		params.put("page", page);
		AsyncTaskUtil.postData(getActivity(), "getSelfReply", params, new Callback() {
			@Override
			public void onSuccess(JSONObject result) {
				time = result.getLongValue("time");
				try {
					listData.addAll(JSONArray.parseArray(result.getString("replys"), DataModel.class));
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			
			@Override 
			public void onFail(String msg) {
				if(SysModuleConstant.VALUE_DEV_MODEL){
					String[] strs = {"凌晨风暴","Moomo"};
					String[] content = {"中国光棍危机2020年或全面爆发：光棍男性上千万","印度拟在中印边境增设新司令部及超过40所哨站"};
					for(int i=0,len = strs.length;i<len;i++){
						DataModel m = new DataModel();
						m.nick = strs[i];
						m.content = content[i];
						listData.add(m);
					}
					adapter.notifyDataSetChanged();
				}
			}
		});
		

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

			hodler.tv_username.setText(model.getNick());
			hodler.tv_content .setText(model.getContent());

			hodler.tv_username.setText(model.nick);
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

		String rid,uid,nick,to_uid,to_nick,msg,ptime,mid,
		content,type,url,mood_state;

		public String getRid() {
			return rid;
		}

		public void setRid(String rid) {
			this.rid = rid;
		}

		public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}

		public String getNick() {
			return nick;
		}

		public void setNick(String nick) {
			this.nick = nick;
		}

		public String getTo_uid() {
			return to_uid;
		}

		public void setTo_uid(String to_uid) {
			this.to_uid = to_uid;
		}

		public String getTo_nick() {
			return to_nick;
		}

		public void setTo_nick(String to_nick) {
			this.to_nick = to_nick;
		}

	

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public String getPtime() {
			return ptime;
		}

		public void setPtime(String ptime) {
			this.ptime = ptime;
		}

		public String getMid() {
			return mid;
		}

		public void setMid(String mid) {
			this.mid = mid;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getMood_state() {
			return mood_state;
		}

		public void setMood_state(String mood_state) {
			this.mood_state = mood_state;
		}
		
	
	}

}
