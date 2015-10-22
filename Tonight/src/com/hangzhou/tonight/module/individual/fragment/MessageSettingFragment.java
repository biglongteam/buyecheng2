package com.hangzhou.tonight.module.individual.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.fragment.BFragment;

/**
 * 消息设置
 * @author hank
 *
 */
public class MessageSettingFragment extends BFragment {

	ToggleButton tgVibration,tgVoice;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_individual_message_setting, container, false);
	}
	
	@Override
	protected void doView() {
		tgVibration = findView(R.id.message_setting_vibration);
		tgVoice     = findView(R.id.message_setting_voice)	;
	}

	@Override
	protected void doListeners() {
		tgVibration.setOnCheckedChangeListener(occ);
		tgVoice.setOnCheckedChangeListener(occ);
	}

	OnCheckedChangeListener occ = new OnCheckedChangeListener() {
		@Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			//进行判断,设置sp对应的值
		}
	};
	
	@Override protected void doHandler() {
		//读取本地sp  进行初始值设置
		
	}

}
