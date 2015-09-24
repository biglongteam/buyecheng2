package com.hoo.ad.base.activity;

import com.hoo.ad.base.helper.ActivityHelper;
import com.hoo.ad.base.manager.ActivityManager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * Activity基类扩展
 * @author hank
 *
 */
public abstract class BActivity extends Activity {
	
	private Bundle $bundle;
	
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityManager.getInstance().regist(getActivity());
		
		beforeInit();
		init(null != $bundle ? $bundle : savedInstanceState);
		afterInit();
		
	}
	protected void beforeInit(){}
	
	protected void afterInit(){};
	
	protected void init(Bundle savedInstanceState){
		doView();
		doListeners();
		doHandler($bundle = getBundle(savedInstanceState));
	}
	
	/**
	 * 获取一个非空 bundle对象
	 * @param bundle
	 * @return
	 */
	protected Bundle getBundle(Bundle bundle){
		Bundle b =  getIntent().getExtras();
		if(bundle == null){ bundle = b; }
		if(bundle == null){ bundle = new Bundle();}
		return bundle;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
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
	
	@Override protected void onDestroy() {
		super.onDestroy();
		ActivityManager.getInstance().unregist(getActivity());
	}
	
	/**
	 * 获取Activity对象
	 * @return
	 */
	public BActivity getActivity(){
		return this;
	}
	
	/**
	 * 获取Context
	 * @return
	 */
	public Context getContext(){
		return this;
	}
	
	/**
	 * 启动一个activity
	 * @param cls
	 */
	public void startActivity(Class<?> cls){
		ActivityHelper.startActivity(getContext(), cls);
	}
	
	/**
	 * 简单封装findViewById
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getViewById(int id) {
		return (T) super.findViewById(id);
	}
}
