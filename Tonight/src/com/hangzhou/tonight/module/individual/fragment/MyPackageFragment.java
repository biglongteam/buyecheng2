package com.hangzhou.tonight.module.individual.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
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
import com.hangzhou.tonight.module.base.fragment.BFragment;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.DateUtil;
import com.hangzhou.tonight.module.base.util.ViewUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;
/**
 * 我的账户-礼包
 * @author hank
 *
 */
public class MyPackageFragment extends BFragment {

	private List<PackageModel> mListData;
	private ModelAdapter mAdapter;
	
	SwipeMenuListView swipeListview;
	
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_individual_myorder, container, false);
	}
	
	@Override
	protected void doView() {
		
	}

	@Override
	protected void doListeners() {
		
	}

	@Override
	protected void doHandler() {
		swipeListview = (SwipeMenuListView) getView().findViewById(R.id.empty_swipe_listview);
		
		mListData = new ArrayList<PackageModel>();
		
		swipeListview.setDividerHeight(ViewUtil.dp2px(getActivity(), 12f));
		mAdapter = new ModelAdapter();
		swipeListview.setAdapter(mAdapter);
		
		AsyncTaskUtil.postData(getActivity(), "getTicketList", null, new Callback() {
			
			@Override public void onSuccess(JSONObject result) {
				List<PackageModel> list = JSONArray.parseArray(result.getString("tickets"), PackageModel.class);
				mListData.addAll(list);
				mAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void onFail(String msg) {
				if(SysModuleConstant.VALUE_DEV_MODEL){
					mListData.clear();
					PackageModel pm = new PackageModel();
					pm.ticket_id  = "蜜桃酒吧感恩礼包";
					pm.money = "100元";
					pm.name= "仅在活动期间使用，仅限一次";
					mListData.add(pm);
					
					pm = new PackageModel();
					pm.money = "100元";
					pm.ticket_id  = "测试内容";
					pm.name= "相对内容";
					mListData.add(pm);
					mAdapter.notifyDataSetChanged();
				}
			}
		});
	}

	class ModelAdapter extends BaseAdapter {

		@Override public int getCount() {
			return mListData.size();
		}

		@Override public PackageModel getItem(int position) {
			return mListData.get(position);
		}

		@Override public long getItemId(int position) {
			return position;
		}

		@Override public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(getActivity().getApplicationContext(),R.layout.item_individual_fragment_myaccount_package, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			PackageModel item = mListData.get(position);
			holder.tv_title.setText("礼包号:"+item.ticket_id);
			holder.tv_content.setText(item.name);
			holder.tv_amount.setText(item.money +"元");
			holder.tv_time.setText(DateUtil.formatDate(item.starttime) + "——" + DateUtil.formatDate(item.endtime));
			return convertView;
		}

		class ViewHolder {
			TextView tv_amount;
			TextView tv_title;
			TextView tv_content;
			TextView tv_time;
			public ViewHolder(View view) {
				tv_amount  = (TextView) view.findViewById(R.id.item_fragment_myaccount_package_amount);
				tv_title   = (TextView) view.findViewById(R.id.item_fragment_myaccount_package_title);
				tv_content = (TextView) view.findViewById(R.id.item_fragment_myaccount_package_content);
				tv_time    = (TextView) view.findViewById(R.id.item_fragment_myaccount_package_time);
				
				view.setTag(this);
			}
		}
	}
	public static class PackageModel{
		String money;
		String ticket_id;
		String name;
		long starttime,endtime;
		public String getMoney() {
			return money;
		}
		public String getTicket_id() {
			return ticket_id;
		}
		public String getName() {
			return name;
		}
		public long getStarttime() {
			return starttime;
		}
		public long getEndtime() {
			return endtime;
		}
		public void setMoney(String money) {
			this.money = money;
		}
		public void setTicket_id(String ticket_id) {
			this.ticket_id = ticket_id;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setStarttime(long starttime) {
			this.starttime = starttime;
		}
		public void setEndtime(long endtime) {
			this.endtime = endtime;
		}
		
		
	}
}
