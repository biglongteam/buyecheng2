package com.hangzhou.tonight.module.individual.fragment;

import com.hangzhou.tonight.LoginActivity;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.BaseSingeFragmentActivity;
import com.hangzhou.tonight.module.base.fragment.BFragment;
import com.hangzhou.tonight.module.base.helper.ActivityHelper;
import com.hangzhou.tonight.module.base.helper.model.TbarViewModel;
import com.hangzhou.tonight.util.MyPreference;

import android.view.View;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class SettingFragment extends BFragment {
	
	
	View vSetting,vShare,vChangePwd;
	Button bExit;
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_individual_setting, container, false);
	}
	@Override
	protected void doView() {
		vSetting = findViewById(R.id.individual_setting_message);
		vShare   = findViewById(R.id.individual_setting_share_software);
		vChangePwd = findViewById(R.id.individual_setting_change_password);
		bExit   = findView(R.id.individual_setting_exit);
		
	}
	
	@Override
	protected void doListeners() {
		vChangePwd.setOnClickListener(clickListener);
		bExit.setOnClickListener(clickListener);
	}
	
	@Override
	protected void doHandler() {
		vSetting.setVisibility(View.GONE);
		vShare.setVisibility(View.GONE);
	}
	
	OnClickListener clickListener = new OnClickListener() {
		@Override public void onClick(View v) {
			if(v == bExit){
				MyPreference.getInstance(getActivity()).setLoginName(null);
				MyPreference.getInstance(getActivity()).setPassword(null);
				ActivityHelper.startActivity(getActivity(), LoginActivity.class);
				getActivity().finish();
				//TODO 需要清空栈
			}else if(v == vChangePwd){
				BaseSingeFragmentActivity.startActivity(getActivity(), ChangePwdFragment.class, new TbarViewModel("修改密码"));
			}
		}	
	};
	
	
}
