package com.hangzhou.tonight.module.message.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.hangzhou.tonight.module.base.util.ViewUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;

/**
 * 订单消息
 * @author hank
 */
public class OrderMessageFragment extends BEmptyListviewFragment {

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
				params.put("order_id", listData.get(position).order_id);
				AsyncTaskUtil.postData(getActivity(), "delOrderMsg", params, new Callback() {
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
		JSONObject params = new JSONObject();
		params.put("page", page);
		params.put("time", time);
		AsyncTaskUtil.postData(getActivity(), "getOrderMsg", params, new Callback() {
			
			@Override public void onSuccess(JSONObject result) {
				List<DataModel> list = JSONArray.parseArray(result.getString("orders"), DataModel.class);
				listData.addAll(list);
				adapter.notifyDataSetChanged();
			}
			
			@Override public void onFail(String msg) {
				/*if(SysModuleConstant.VALUE_DEV_MODEL){
					listData.clear();
					String[] strs = { "00001" ,"交易成功，订单生效"};
					for(int i=0,len = 1;i<len;i++){
						DataModel m = new DataModel();
						m.order_id = strs[0];
						m.result   = strs[1];
						listData.add(m);
					}
					adapter.notifyDataSetChanged();
				}*/
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
				convertView = View.inflate(getActivity(),R.layout.item_message_fragment_order_message, null);
				holder = new ViewHolder(convertView);
			}
			holder = (ViewHolder) convertView.getTag();
			int state = model.state,refund_state = model.refund_state;
			if(state == 9 && refund_state == 9){ model.result = "退款申请已处理.";}else
			if(state == 9 && refund_state != 9){ model.result = "订单已支付";	 }else 
			if(state == 0){ model.result = "订单已关闭";}else
			if(state == 1){ model.result = "订单未支付";}
			holder.tv_result.setText(model.result);
			holder.tv_ordernum.setText(String.format(getResources().getString(R.string.order_message_ordernum), model.order_id));
			return convertView;
		}

		class ViewHolder {
			public TextView tv_result,tv_ordernum;
			public ViewHolder(View view) {
				tv_ordernum = (TextView) view.findViewById(R.id.order_message_ordernum);
				tv_result   = (TextView) view.findViewById(R.id.order_message_result);
				view.setTag(this);
			}
		}
	}

	public static class DataModel {
		String order_id,result;
		int state = -1,refund_state = -1;
		public String getOrder_id() {
			return order_id;
		}
		public String getResult() {
			return result;
		}
		public int getState() {
			return state;
		}
		public int getRefund_state() {
			return refund_state;
		}
		public void setOrder_id(String order_id) {
			this.order_id = order_id;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public void setState(int state) {
			this.state = state;
		}
		public void setRefund_state(int refund_state) {
			this.refund_state = refund_state;
		}
		
	}

}
