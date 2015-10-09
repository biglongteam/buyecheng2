package com.hangzhou.tonight.module.individual.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.BaseSingeFragmentActivity;
import com.hangzhou.tonight.module.base.helper.ActivityHelper;
import com.hangzhou.tonight.module.base.helper.ActivityHelper.OnIntentCreateListener;
import com.hangzhou.tonight.module.base.helper.ToastHelper;
import com.hangzhou.tonight.module.base.helper.model.TbarViewModel;

/**
 * 个人模块-扩展类
 * @author hank
 *
 */
public class ExtSingleFragmentActivity extends BaseSingeFragmentActivity {

	@Override public void doHandlerView(View handler) {
		super.doHandlerView(handler);
		TextView tv = ((TextView)handler);
		tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.add), null, null, null);
		int left,right;
		left = right = getResources().getDimensionPixelSize(R.dimen.custom_actionbar_handler_padding);
		tv.setPadding(left, 0, right, 0);
		setOnClickListener(new OnHandlerClickListener() {
			@Override public void onClick(View handlerView) {
				ToastHelper.show(getContext(), "添加个人动态");
			}
		});
		tv.setVisibility(View.VISIBLE);
	}
	
	public Context getContext(){return this;}
	
	public static void startActivity(Context context,final Class<?> fragmentCls,TbarViewModel model){
		startActivity(context, fragmentCls, model, null);
	}
	
	
	public static void startActivity(Context context,final Class<?> fragmentCls,TbarViewModel model,final OnIntentCreateListener listener){
		ActivityHelper.startActivity(context, ExtSingleFragmentActivity.class, 
				 model,new OnIntentCreateListener() {
				@Override public void onCreate(Intent intent) {
					if(null != listener){ listener.onCreate(intent);}
					intent.putExtra(BaseSingeFragmentActivity.KEY_FRAGMENT_CLASSNAME, fragmentCls.getName());
				}
			});
	}
}
