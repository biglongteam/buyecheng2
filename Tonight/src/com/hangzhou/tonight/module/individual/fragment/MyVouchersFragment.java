package com.hangzhou.tonight.module.individual.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.hangzhou.tonight.module.base.helper.ToastHelper;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.ViewUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;

/**
 * 代金券
 * @author hank
 *
 */
public class MyVouchersFragment extends BFragment {

	private List<EntityModel> mListData;
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
		
		mListData = new ArrayList<EntityModel>();
		swipeListview.setDividerHeight(ViewUtil.dp2px(getActivity(), 12f));
		mAdapter = new ModelAdapter();
		swipeListview.setAdapter(mAdapter);
		
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				SwipeMenuItem openItem = new SwipeMenuItem(getActivity().getApplicationContext());
				openItem.setBackground(new ColorDrawable(Color.parseColor("#FFEB416C")));
				openItem.setWidth(ViewUtil.dp2px(getActivity().getBaseContext(),90));
				openItem.setTitle("退款");
				openItem.setTitleSize(20);
				openItem.setTitleColor(Color.WHITE);
				menu.addMenuItem(openItem);
			}
		};
		swipeListview.setMenuCreator(creator);
		swipeListview.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override public void onMenuItemClick(int position, SwipeMenu menu, int index) {
				ToastHelper.show(getActivity(), "暂无接口");
			}
		});
		
		AsyncTaskUtil.postData(getActivity(), "getCouponList", null, new Callback() {
			@Override public void onSuccess(JSONObject result) {
				List<EntityModel> list = JSONArray.parseArray(result.getString("coupons"), EntityModel.class);
				mListData.addAll(list);
				mAdapter.notifyDataSetChanged();
			}
			@Override public void onFail(String msg) {
				if(SysModuleConstant.VALUE_DEV_MODEL){
					EntityModel pm = new EntityModel();
					pm.coupon_name  = "蜜桃酒吧迷情派对";
					pm.money = "100元";
					pm.seller_name= "仅在活动期间使用，仅限一次";
					pm.code="2015-10-1~~2015-11-11";
					mListData.add(pm);
					
					pm = new EntityModel();
					pm.money = "100元";
					pm.coupon_name  = "测试内容";
					pm.seller_name= "相对内容";
					pm.code="2012-1-1~~2020-1-1";
					mListData.add(pm);
					mAdapter.notifyDataSetChanged();
				}
			}
		},false);
	}

	class ModelAdapter extends BaseAdapter {

		@Override public int getCount() {
			return mListData.size();
		}

		@Override public EntityModel getItem(int position) {
			return mListData.get(position);
		}

		@Override public long getItemId(int position) {
			return position;
		}

		@Override public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(getActivity().getApplicationContext(),R.layout.item_individual_fragment_myaccount_vouchers, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			EntityModel item = mListData.get(position);
			holder.tv_title.setText(item.coupon_name);
			holder.tv_content.setText(item.seller_name);
			holder.tv_amount.setText(item.money);
			holder.tv_time.setText(item.code);
			return convertView;
		}

		class ViewHolder {
			TextView tv_amount;
			TextView tv_title;
			TextView tv_content,tv_time;
			public ViewHolder(View view) {
				tv_amount  = (TextView) view.findViewById(R.id.item_fragment_myaccount_vouchers_amount);
				tv_title   = (TextView) view.findViewById(R.id.item_fragment_myaccount_vouchers_title);
				tv_content = (TextView) view.findViewById(R.id.item_fragment_myaccount_vouchers_content);
				tv_time    = (TextView) view.findViewById(R.id.item_fragment_myaccount_vouchers_time);
				view.setTag(this);
			}
		}
	}
	
	public static class EntityModel{
		String money;//金额
		String coupon_name;//代金券名称
		String seller_name;//商家名称
		String code;//TODO 接口中缺少 代金券时间段,且跟设计不符
		public String getMoney() {
			return money;
		}
		public String getCoupon_name() {
			return coupon_name;
		}
		public String getSeller_name() {
			return seller_name;
		}
		public String getCode() {
			return code;
		}
		public void setMoney(String money) {
			this.money = money;
		}
		public void setCoupon_name(String coupon_name) {
			this.coupon_name = coupon_name;
		}
		public void setSeller_name(String seller_name) {
			this.seller_name = seller_name;
		}
		public void setCode(String code) {
			this.code = code;
		}
		
	}

}
