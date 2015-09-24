package com.hoo.ad.base.activity;

import com.hoo.ad.base.manager.ActivityManager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * FragmentActivity 基类
 * @author hank
 *
 */
public class BFragmentActivity extends FragmentActivity {
	
	private Bundle $bundle;
	
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityManager.getInstance().regist(getActivity());
		beforeInit();
		init(null != $bundle ? $bundle : savedInstanceState);
		afterInit();
	}
	
	protected void beforeInit() {}
	
	protected void init(Bundle bundle) {
		doView();
		doListeners();
		doHandler($bundle = getBundle(bundle));
	}
	
	/**
	 * 执行视图操作
	 */
	protected void doView(){};
	/**
	 * 执行监听操作
	 */
	protected void doListeners(){};
	
	/**
	 * 执行功能操作
	 * @param bundle
	 */
	protected void doHandler(Bundle bundle){};
	
	protected void afterInit(){}
	
	protected Bundle getBundle(Bundle bundle){
		Bundle b =  getIntent().getExtras();
		if(bundle == null){ bundle = b; }
		if(bundle == null){ bundle = new Bundle();}
		return bundle;
	}
	
	//TODO 尝试  未验证   http://coolxing.iteye.com/blog/1279447
	@Override protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//if(null == ($bundle = getBundle(null))){ return; }
		//outState.putAll($bundle);
		/*for(String key : $bundle.keySet()){
			Object object = $bundle.get(key);
			if(object instanceof String) { outState.putString(key, (String)object);}else
			if(object instanceof Integer){ outState.putInt(key, (Integer)object);  }else
			if(object instanceof Boolean){ outState.putBoolean(key, (Boolean)object);}
		}*/
	}
	
	@Override protected void onDestroy() {
		super.onDestroy();
		ActivityManager.getInstance().unregist(getActivity());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	public Activity getActivity(){
		return this;
	}
	
	public Context getContext(){
		return this;
	}
}
