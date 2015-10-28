package com.hangzhou.tonight.module.individual.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.hangzhou.tonight.module.base.BaseSingeFragmentActivity;
import com.hangzhou.tonight.module.base.helper.ActivityHelper.OnIntentCreateListener;
import com.hangzhou.tonight.module.base.helper.model.TbarViewModel;

/**
 * 待评价
 * @author hank
 *
 */
public class MyOrderUnCommonFragment extends MyOrderFragment {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		PAY_STATE = STATE_UNCOMMON;
		HANDLER_TEXT = "评价";
		super.onActivityCreated(savedInstanceState);
	}
	
	public OnMenuItemClickListener getOnMenuItemClickListener(){
		return new OnMenuItemClickListener() {
			OrderModel om;
			@Override public void onMenuItemClick(final int position, SwipeMenu menu, int index) {
				om = mOrderList.get(position);
				if(index == 0){
					TbarViewModel model = new TbarViewModel(om.title + "-评价");
					BaseSingeFragmentActivity.startActivity(getActivity(), OrderEvaluationFragment.class, model,new OnIntentCreateListener() {
						@Override public void onCreate(Intent intent) {
							intent.putExtra("order_id", om.order_id);
						}
					});
				}else if(index == 1){
					deleteOrder(om, position);
				}
			}
		};
	}
}
