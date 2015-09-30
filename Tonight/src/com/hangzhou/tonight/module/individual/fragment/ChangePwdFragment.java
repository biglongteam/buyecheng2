package com.hangzhou.tonight.module.individual.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.fragment.BFragment;

/**
 * 修改密码
 * @author hank
 *
 */
public class ChangePwdFragment extends BFragment {

	EditText etOrgin,etNow,etNow2;
	Button bSubmit;
	
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_indididual_changepwd, container, false);
	}
	
	@Override protected void doView() {
		etOrgin = findView(R.id.indididual_changepwd_orgin);
		etNow   = findView(R.id.indididual_changepwd_now);
		etNow2  = findView(R.id.indididual_changepwd_now2);
		bSubmit = findView(R.id.individual_changepwd_submit);
	}

	@Override protected void doListeners() {
		bSubmit.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				if(validate()){
					submit();
				}
			}
		});
	}

	@Override protected void doHandler() {
		
	}

	public boolean validate(){
		return false;
	}
	
	public void submit(){
		
	}
}
