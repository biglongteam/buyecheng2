package com.hangzhou.tonight.module.message.fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.constant.SysModuleConstant;
import com.hangzhou.tonight.module.base.fragment.BEmptyListviewFragment;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.DateUtil;
import com.hangzhou.tonight.module.base.util.ViewUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;

/**
 * 系统消息
 * @author hank
 *
 */
public class SystemMessageFragment extends BEmptyListviewFragment {

	CollectionAdapter adapter;
	List<DataModel> listData = null;
	
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.empty_swipe_listview, container, false);
	}
	
	@Override
	protected void doView() {
		super.doView();
		mListView = (ListView) findViewById(R.id.empty_swipe_listview);
	}
	
	@Override protected void doListeners() {
		
	}
	
	@Override protected void doHandler() {

		listData = new ArrayList<DataModel>();
		
		adapter = new CollectionAdapter();
		mListView.setAdapter(adapter);
		SwipeMenuCreator creator = new SwipeMenuCreator() {
			@Override public void create(SwipeMenu menu) {
				menu.addMenuItem(getMenuItem());
			}
		};
		((SwipeMenuListView)mListView).setMenuCreator(creator);
		((SwipeMenuListView)mListView).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override public void onMenuItemClick(final int position, SwipeMenu menu, int index) {
				JSONObject params = new JSONObject();
				params.put("order_id", listData.get(position).msg_id);
				AsyncTaskUtil.postData(getActivity(), "delSystemMsg", params, new Callback() {
					@Override public void onSuccess(JSONObject result) {
						listData.remove(position);
						adapter.notifyDataSetChanged();
					}
					@Override public void onFail(String msg) {}
				});
			}
		});
	}
	
	@Override protected void doPostData() {
		super.doPostData();
		JSONObject params =new JSONObject();
		params.put("page", page);
		params.put("time", time);
		AsyncTaskUtil.postData(getActivity(), "getSystemMsg", params, new Callback() {
			
			@Override public void onSuccess(JSONObject result) {
				if(!result.containsKey("time")){ return; }
				time = result.getLongValue("time");
				List<DataModel> list = JSONArray.parseArray(result.getString("msgs"), DataModel.class);
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
		});
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
				convertView = View.inflate(getActivity(),R.layout.item_fragment_message_system_message, null);
				holder = new ViewHolder(convertView);
			}
			holder = (ViewHolder) convertView.getTag();
			
			holder.tv_time.setText(DateUtil.convertTimeToFormat(model.time));
			holder.tv_content.setText(model.msg);
			return convertView;
		}

		class ViewHolder {
			public TextView tv_time,tv_content;
			public ViewHolder(View view) {
				tv_content= (TextView) view.findViewById(R.id.system_message_content);
				tv_time   = (TextView) view.findViewById(R.id.system_message_time);
				view.setTag(this);
			}
		}
	}

	public static class DataModel {
		long msg_id,time;
		String msg;
		public long getMsg_id() {
			return msg_id;
		}
		public long getTime() {
			return time;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg_id(long msg_id) {
			this.msg_id = msg_id;
		}
		public void setTime(long time) {
			this.time = time;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		
	}

}
