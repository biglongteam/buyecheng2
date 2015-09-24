package com.hangzhou.tonight.module.social.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.BaseSingeFragmentActivity;
import com.hangzhou.tonight.module.base.helper.model.TbarViewModel;
import com.hangzhou.tonight.module.social.fragment.AddFriendFragment;
import com.hangzhou.tonight.module.social.fragment.MyFriendFragment;

public class FriendActivity extends BaseSingeFragmentActivity {
		
	@Override protected void doHandler() {
		Bundle bundle = getIntent().getExtras();
		Fragment fragment = new MyFriendFragment();
		if(null != bundle){ fragment.setArguments(bundle); }
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.common_tbar_framelayout, fragment).commit();
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
				BaseSingeFragmentActivity.startActivity(getContext(), AddFriendFragment.class, new TbarViewModel(getResources().getString(R.string.add_friend)));
			}
		});
	}
	
	public Context getContext(){return this;}
}
