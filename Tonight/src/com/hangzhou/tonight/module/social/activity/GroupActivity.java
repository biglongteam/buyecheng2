package com.hangzhou.tonight.module.social.activity;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.BaseSingeFragmentActivity;
import com.hangzhou.tonight.module.base.TabActivity;
import com.hangzhou.tonight.module.base.CustomFragmentActivity.OnHandlerClickListener;
import com.hangzhou.tonight.module.base.helper.model.TbarViewModel;
import com.hangzhou.tonight.module.individual.fragment.MyOrderFragment;
import com.hangzhou.tonight.module.social.fragment.AddFriendFragment;
import com.hangzhou.tonight.module.social.fragment.CreateGroupFragment;
import com.hangzhou.tonight.module.social.fragment.GroupCityWideFragment;
import com.hangzhou.tonight.module.social.fragment.MyGroupFragment;

/**
 * 群组
 * 
 * @author hank
 * 
 */
public class GroupActivity extends TabActivity {
	
	@Override public void onCreateTabs(List<TabModel> list) {
		TabModel tabModel = new TabModel();
		tabModel.title = "同城";
		tabModel.fragment = new GroupCityWideFragment();
		list.add(tabModel);
		tabModel = new TabModel();
		tabModel.title = "我的";
		tabModel.fragment = new MyGroupFragment();
		list.add(tabModel);
	}
	
	@Override public void doHandlerView(View handler) {
		super.doHandlerView(handler);
		TextView tv = ((TextView)handler);
		tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.add), null, null, null);
		int left,right;
		left = right = getResources().getDimensionPixelSize(R.dimen.custom_actionbar_handler_padding);
		tv.setPadding(left, 0, right, 0);
		setOnClickListener(new OnHandlerClickListener() {
			@Override public void onClick(View handlerView) {
				BaseSingeFragmentActivity.startActivity(getContext(), CreateGroupFragment.class, new TbarViewModel("创建群组"));
			}
		});
	}
	
	public Context getContext(){return this;}
}
