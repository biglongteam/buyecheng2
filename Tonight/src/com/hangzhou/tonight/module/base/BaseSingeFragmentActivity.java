package com.hangzhou.tonight.module.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.fragment.BFragment;
import com.hangzhou.tonight.module.base.helper.ActivityHelper;
import com.hangzhou.tonight.module.base.helper.ActivityHelper.OnIntentCreateListener;
import com.hangzhou.tonight.module.base.helper.model.TbarViewModel;

/**
 * 支持单一Fragment嵌入的情况,参数依托activity传入
 * @author hank
 *
 */
public class BaseSingeFragmentActivity extends CustomFragmentActivity {
	
	public static final String KEY_FRAGMENT_CLASSNAME = "__cls_name_";
	Fragment fragment;
	
	@Override protected void doView() {
		setContentView(R.layout.common_tbar_framelayout);
		
	}

	@Override protected void doListeners() {}

	@Override protected void doHandler() {
		Bundle bundle = getIntent().getExtras();
		if(null == bundle){ return; }
		
		String className= bundle.getString(KEY_FRAGMENT_CLASSNAME);
		String    title = bundle.getString(ActivityHelper.KEY_COMMON_TITLE);
		boolean canback = bundle.getBoolean(ActivityHelper.KEY_COMMON_CANBACK);
		
		setTitle(title);
		if(!canback){ setBackViewVisibility(View.GONE); }
		try {
			Class<?> cls = Class.forName(className);
			fragment = (Fragment) cls.newInstance();
			fragment.setArguments(bundle);
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.common_tbar_framelayout, fragment).commit();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}

	public static void startActivity(Context context,final Class<?> fragmentCls,TbarViewModel model){
		/*ActivityHelper.startActivity(context, BaseSingeFragmentActivity.class, 
				 model,new OnIntentCreateListener() {
				@Override public void onCreate(Intent intent) {
					intent.putExtra(BaseSingeFragmentActivity.KEY_FRAGMENT_CLASSNAME, fragmentCls.getName());
				}
			});*/
		startActivity(context, fragmentCls, model, null);
	}
	
	public static void startActivity(Context context,final Class<?> fragmentCls,TbarViewModel model,final OnIntentCreateListener listener){
		ActivityHelper.startActivity(context, BaseSingeFragmentActivity.class, 
				 model,new OnIntentCreateListener() {
				@Override public void onCreate(Intent intent) {
					if(null != listener){ listener.onCreate(intent);}
					intent.putExtra(BaseSingeFragmentActivity.KEY_FRAGMENT_CLASSNAME, fragmentCls.getName());
				}
			});
	}
	
	@Override
	public void onBackPressed() {
		if(fragment instanceof BFragment){
			BFragment bf = (BFragment)fragment;
			bf.onBackPressed();
		}
		super.onBackPressed();
	}
	
}
