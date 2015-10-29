package com.hangzhou.tonight.module.individual.fragment;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.hangzhou.tonight.module.individual.fragment.MyOrderFragment.OrderModel;

import android.os.Bundle;

/**
 * 未消费
 * @author hank
 *
 */
public class MyOrderUnBuyFragment extends MyOrderFragment {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		PAY_STATE = STATE_UNBUY;
		HANDLER_TEXT = "退款";
		super.onActivityCreated(savedInstanceState);
	}
	
	public OnMenuItemClickListener getOnMenuItemClickListener(){
		return new OnMenuItemClickListener() {
			OrderModel om;
			@Override public void onMenuItemClick(final int position, SwipeMenu menu, int index) {
				om = mOrderList.get(position);
				if(index == 0){
					
				}else if(index == 1){
					deleteOrder(om, position);
				}
			}
		};
	}
}
