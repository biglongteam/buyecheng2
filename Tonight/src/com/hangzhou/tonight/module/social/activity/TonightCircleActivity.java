package com.hangzhou.tonight.module.social.activity;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.BaseSingeFragmentActivity;
import com.hangzhou.tonight.module.base.TabActivity;
import com.hangzhou.tonight.module.base.CustomFragmentActivity.OnHandlerClickListener;
import com.hangzhou.tonight.module.base.helper.ToastHelper;
import com.hangzhou.tonight.module.base.helper.model.TbarViewModel;
import com.hangzhou.tonight.module.social.fragment.CreateGroupFragment;
import com.hangzhou.tonight.module.social.fragment.TonightCircleCityWideFragment;
import com.hangzhou.tonight.module.social.fragment.TonightCircleMyFragment;

/**
 * 不夜圈
 * @author hank
 */
public class TonightCircleActivity extends TabActivity {
	@Override
	public void onCreateTabs(List<TabModel> list) {
		TabModel tabModel = new TabModel();
		tabModel.title = "同城";
		tabModel.fragment = new TonightCircleCityWideFragment();
		list.add(tabModel);
		tabModel = new TabModel();
		tabModel.title = "我的";
		tabModel.fragment = new TonightCircleMyFragment();
		list.add(tabModel);
	}
	
	@Override protected void setTab(int position) {
		if(tv!=null){
			tv.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
		}
		super.setTab(position);
	}
	TextView tv;
	@Override public void doHandlerView(View handler) {
		super.doHandlerView(handler);
		tv = ((TextView)handler);
		tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.add), null, null, null);
		int left,right;
		left = right = getResources().getDimensionPixelSize(R.dimen.custom_actionbar_handler_padding);
		tv.setPadding(left, 0, right, 0);
		setOnClickListener(new OnHandlerClickListener() {
			@Override public void onClick(View handlerView) {
				ToastHelper.show(getContext(), "0---");
			}
		});
		tv.setVisibility(View.GONE);
	}
	
	public Context getContext(){return this;}

}
