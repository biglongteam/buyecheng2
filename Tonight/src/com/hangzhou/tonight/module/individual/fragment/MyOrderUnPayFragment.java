package com.hangzhou.tonight.module.individual.fragment;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.hangzhou.tonight.module.individual.fragment.MyOrderFragment.OrderModel;

import android.os.Bundle;

/**
 * 未支付订单
 * @author hank
 *
 */
public class MyOrderUnPayFragment extends MyOrderFragment {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		PAY_STATE = STATE_UNPAY;
		HANDLER_TEXT = "付款";
		super.onActivityCreated(savedInstanceState);
	}
	
	public OnMenuItemClickListener getOnMenuItemClickListener(){
		return new OnMenuItemClickListener() {
			OrderModel om;
			@Override public void onMenuItemClick(final int position, SwipeMenu menu, int index) {
				om = mOrderList.get(position);
				if(index == 0){
					/*TbarViewModel model = new TbarViewModel(om.title + "-评价");
					BaseSingeFragmentActivity.startActivity(getActivity(), OrderEvaluationFragment.class, model,new OnIntentCreateListener() {
						@Override public void onCreate(Intent intent) {
							intent.putExtra("order_id", om.order_id);
						}
					});*/
				}else if(index == 1){
					deleteOrder(om, position);
				}
			}
		};
	}
}
