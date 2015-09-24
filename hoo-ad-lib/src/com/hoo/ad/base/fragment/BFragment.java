package com.hoo.ad.base.fragment;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

//http://www.yrom.net/blog/2013/03/10/fragment-switch-not-restart/

public abstract class BFragment extends Fragment {

	private Bundle $bundle;
	
	@Override public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		doView();
		doListeners();
		doHandler($bundle = getBundle(savedInstanceState));
	}
	
	public View findViewById(int id){
		return getView().findViewById(id);
	}
	
	protected void doView(){}
	protected void doListeners(){};
	protected void doHandler(Bundle bundle){};
	
	
	Map<String,OnFireListeners> listenerMap = new HashMap<String,OnFireListeners>();
	
	public void fireListener(String eventName){
		OnFireListeners listener = listenerMap.get(eventName);
		if(null != listener){ listener.doHandler(); }
	}
	
	public void addListener(String eventName,OnFireListeners listener){
		listenerMap.put(eventName, listener);
	}
	
	public interface OnFireListeners{
		void doHandler();
	}
	
	public Bundle getBundle(Bundle bundle){
		bundle = bundle == null ? getArguments() : bundle;
		if(bundle == null){
			bundle = getActivity().getIntent().getExtras();
		}
		if(null == bundle){ bundle = new Bundle();}
		return bundle;
	}
	
	@Override public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//if(null == ($bundle = getBundle(null))){ return; }
		//outState.putAll($bundle);//TODO 测试 未验证 http://coolxing.iteye.com/blog/1279447
	}
}