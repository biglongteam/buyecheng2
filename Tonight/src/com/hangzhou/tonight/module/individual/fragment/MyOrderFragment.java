package com.hangzhou.tonight.module.individual.fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.constant.SysModuleConstant;
import com.hangzhou.tonight.module.base.helper.PicTextHelper;
import com.hangzhou.tonight.module.base.helper.ToastHelper;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.DateUtil;
import com.hangzhou.tonight.module.base.util.ViewUtil;
import com.hangzhou.tonight.module.base.util.ViewUtil.OnDoPicTextListener;
import com.hangzhou.tonight.module.base.util.inter.Callback;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 我的订单,依据状态码标识 已支付Paid、未支付
 * @author hank
 */
public class MyOrderFragment extends Fragment {
	private List<OrderModel> mOrderList;
	private ModelAdapter mAdapter;
	
	SwipeMenuListView swipeListview;
	/*** 全部、待付款,未消费,待评价*/
	public static final int STATE_ALL = 1,STATE_UNPAY = 2,STATE_UNBUY = 3,STATE_UNCOMMON = 4;
	
	public int PAY_STATE = STATE_ALL;
	
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_individual_myorder, container, false);
	}
	
	@Override public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());  

		swipeListview = (SwipeMenuListView) getView().findViewById(R.id.empty_swipe_listview);
		
		mOrderList = new ArrayList<OrderModel>();
		
		
		
		swipeListview.setDividerHeight(ViewUtil.dp2px(getActivity(), 12f));
		mAdapter = new ModelAdapter();
		swipeListview.setAdapter(mAdapter);
		
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				SwipeMenuItem commontItem = new SwipeMenuItem(getActivity().getApplicationContext());
				commontItem.setBackground(new ColorDrawable(Color.parseColor("#FFF8A11C")));
				commontItem.setWidth(ViewUtil.dp2px(getActivity().getBaseContext(),90));
				commontItem.setTitle("评价");
				commontItem.setTitleSize(20);
				commontItem.setTitleColor(Color.WHITE);
				menu.addMenuItem(commontItem);

				SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity().getApplicationContext());
				deleteItem.setBackground(new ColorDrawable(Color.parseColor("#FFEB416C")));
				deleteItem.setTitle("删除");
				deleteItem.setTitleSize(20);
				deleteItem.setTitleColor(Color.WHITE);
				
				deleteItem.setWidth(ViewUtil.dp2px(getActivity().getBaseContext(),90));
				menu.addMenuItem(deleteItem);
			}
		};
		swipeListview.setMenuCreator(creator);
		swipeListview.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override public void onMenuItemClick(final int position, SwipeMenu menu, int index) {
				if(index == 0){
					ToastHelper.show(getActivity(), "评价");//[TODO 这一块缺失]
				}else if(index == 1){
					JSONObject params = new JSONObject();
					params.put("order_id", mOrderList.get(position).order_id);
					AsyncTaskUtil.postData(getActivity(), "delOrder", params, new Callback() {
						@Override public void onSuccess(JSONObject result) {
							mOrderList.remove(position);
							mAdapter.notifyDataSetChanged();
						}
						@Override public void onFail(String msg) {}
					});
				}
			}
		});
		JSONObject params = new JSONObject();
		params.put("type", PAY_STATE);
		
		AsyncTaskUtil.postData(getActivity(), "getOrderList", params, new Callback() {
			
			@Override public void onSuccess(JSONObject result) {
				List<OrderModel> list = JSONArray.parseArray(result.getJSONArray("orders").toJSONString(), OrderModel.class);
				//filterData(list);
				mOrderList.addAll(list);
				mAdapter.notifyDataSetChanged();
			}
			
			@Override public void onFail(String msg) {
				if(SysModuleConstant.VALUE_DEV_MODEL){
					mOrderList.clear();
					List<OrderModel> list = new ArrayList<OrderModel>();
					OrderModel om = new OrderModel();
					om.order_id = 76534;
					om.state = STATE_ALL;
					om.title = "蜜桃酒吧迷情派对活动，100元单人代金券， 仅限活动当日使用…蜜桃酒吧迷情派对活动，200元单人代金券， 仅限活动当日使用…";
					list.add(om);
					
					om = new OrderModel();
					om.order_id = 789531;
					om.state = STATE_ALL;
					om.title = "蜜桃酒吧迷情派对活动，200元单人代金券， 仅限活动当日使用…蜜桃酒吧迷情派对活动，200元单人代金券， 仅限活动当日使用";
					list.add(om);
					
					om = new OrderModel();
					om.order_id = 78951;
					om.state = STATE_ALL;
					om.title = "蜜桃酒吧迷情派对活动，300元单人代金券， 仅限活动当日使用…蜜桃酒吧迷情派对活动，200元单人代金券， 仅限活动当日使用";
					list.add(om);
					
					om = new OrderModel();
					om.order_id = 789111;
					om.state = STATE_ALL;
					om.title = "蜜桃酒吧迷情派对活动，400元单人代金券， 仅限活动当日使用…蜜桃酒吧迷情派对活动，200元单人代金券， 仅限活动当日使用";
					list.add(om);
					
					om = new OrderModel();
					om.order_id = 909531;
					om.state = STATE_ALL;
					om.title = "蜜桃酒吧迷情派对活动，500元单人代金券， 仅限活动当日使用…蜜桃酒吧迷情派对活动，200元单人代金券， 仅限活动当日使用";
					list.add(om);
					
					om = new OrderModel();
					om.order_id = 8119531;
					om.state = STATE_ALL;
					om.title = "蜜桃酒吧迷情派对活动，600元单人代金券， 仅限活动当日使用…蜜桃酒吧迷情派对活动，200元单人代金券， 仅限活动当日使用";
					list.add(om);
					
					filterData(list);
					
					mOrderList.addAll(list);
					mAdapter.notifyDataSetChanged();
				}
			}
		},STATE_ALL == PAY_STATE);
	}
	
	private void filterData(List<OrderModel> data){
		Iterator<OrderModel> it = data.iterator();
		while(it.hasNext()){
			OrderModel om = it.next();
			if(om.state != PAY_STATE ){ it.remove();}
		}
	}
	
	class ModelAdapter extends BaseAdapter {

		@Override public int getCount() {
			return mOrderList.size();
		}

		@Override public OrderModel getItem(int position) {
			return mOrderList.get(position);
		}

		@Override public long getItemId(int position) {
			return position;
		}

		@Override public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(getActivity().getApplicationContext(),R.layout.item_individual_fragment_myorder, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			OrderModel item = mOrderList.get(position);
			holder.tv_title.setText(String.format(getResources().getString(R.string.order_message_ordernum), item.order_id + ""));
			holder.tv_content.setText(item.title);
			
			/*String content = ViewUtil.getPicTextMutil(item.title, item.img, true,new OnDoPicTextListener() {
				@Override public String onDoImg(String img) {
					return AsyncTaskUtil.IMG_BASE_PATH + "/act/" + img;
				}
			});*/
			//holder.tv_content.setText( Html.fromHtml(content,new PicTextHelper(),null));
			
			return convertView;
		}

		class ViewHolder {
			TextView tv_title;
			TextView tv_content;
			
			public ViewHolder(View view) {
				tv_title   = (TextView) view.findViewById(R.id.item_fragment_myorder_title);
				tv_content = (TextView) view.findViewById(R.id.item_fragment_myorder_content);
				view.setTag(this);
			}
		}
	}
	
	public static class OrderModel{
		int state,order_id,num;
		String img,title;//活动标题
		double price;
		public int getState() { return state; } public int getOrder_id() { return order_id; } public int getNum() { return num; } public String getImg() { return img; } public String getTitle() { return title; } public double getPrice() { return price; } public void setState(int state) { this.state = state; } public void setOrder_id(int order_id) { this.order_id = order_id; } public void setNum(int num) { this.num = num; } public void setImg(String img) { this.img = img; } public void setTitle(String title) { this.title = title; } public void setPrice(double price) { this.price = price; }

	}
}
