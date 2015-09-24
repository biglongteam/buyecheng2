package com.hangzhou.tonight.module.base.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

public abstract class BFragment extends Fragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}
	
	protected void init(){
		doView();
		doListeners();
		doHandler();
	}
	
	protected abstract void doView();
	protected abstract void doListeners();
	protected abstract void doHandler();
	
	public View findViewById(int id){
		return getView().findViewById(id);
	}
	
	public <T extends View> T findView(int id){
		return (T) getView().findViewById(id);
	}
	
	public void onBackPressed(){}
	
	public Bundle getBundle(){
		Bundle b = getArguments();
		if(b == null){ b = getActivity().getIntent().getExtras();}
		if(b == null){ b = new Bundle();}
		return b;
	}
}
