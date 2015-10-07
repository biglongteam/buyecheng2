package com.hangzhou.tonight.module.message.fragment;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.fragment.BEmptyListviewFragment;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;
import com.hangzhou.tonight.module.message.fragment.SystemMessageFragment.DataModel;

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
		/*String[] strs = {"学习天天向上智力时时下降","张三","李四","王五","黎明的拥抱","爱的旋律 Melody for Love","Full of Love","Street Centre Garden","激情之夜 Night of Passion","第一次约会"};
		for(String str : strs){
			GoodDataModel m = new GoodDataModel();
			m.username = str;
			//listData.add(m);
		}*/
		adapter = new GoodAdapter();
		mListView.setAdapter(adapter);
	}
	

	@Override
	protected void doPostData() {
		super.doPostData();
		JSONObject params =new JSONObject();
		params.put("page", page);
		params.put("time", time);
		AsyncTaskUtil.postData(getActivity(), "getSelfPraise", params, new Callback() {
			
			@Override public void onSuccess(JSONObject result) {
				try {
					List<GoodDataModel> list = JSONArray.parseArray(result.getString("praises"), GoodDataModel.class);
					if(list.size() > 0){ page++;}
					listData.addAll(list);
					adapter.notifyDataSetChanged();
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			
			@Override public void onFail(String msg) {
				/*if(SysModuleConstant.VALUE_DEV_MODEL){
					listData.clear();
					String[] strs = { "2012-01-10" ,"8090迷幻系小聚迷幻系小聚迷幻系小聚迷幻系小聚迷幻系小聚迷幻系小聚迷幻系小聚"};
					for(int i=0,len = 1;i<len;i++){
						DataModel m = new DataModel();
						m.time   = new Date().getTime();
						m.msg= strs[1];
						listData.add(m);
					}
					adapter.notifyDataSetChanged();
				}*/
			}
		});
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
			hodler.tv_username.setText(model.getNick());
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
		String uid,nick,type,url,mid,content,ptime,mood_state;

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

		public String getPtime() {
			return ptime;
		}

		public void setPtime(String ptime) {
			this.ptime = ptime;
		}

		public String getMood_state() {
			return mood_state;
		}

		public void setMood_state(String mood_state) {
			this.mood_state = mood_state;
		}
		
	}
}
