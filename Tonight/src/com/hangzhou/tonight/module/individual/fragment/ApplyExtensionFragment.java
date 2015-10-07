package com.hangzhou.tonight.module.individual.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.fragment.BFragment;
import com.hangzhou.tonight.util.MyPreference;

/**
 * 申请推广
 */
public class ApplyExtensionFragment extends BFragment {

	TextView tvNick,tvSex,tvPhone;
	
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_individual_apply_extension, container, false);
	}
	
	@Override protected void doView() {
		tvNick = findView(R.id.apply_exteriion_nick);
		tvSex  = findView(R.id.apply_exteriion_sex);
		tvPhone= findView(R.id.apply_exteriion_phonenum);
	}

	@Override protected void doListeners() {

	}

	@Override protected void doHandler() {
		String sex = MyPreference.getInstance(getActivity()).getUserSex();
		tvNick.setText(MyPreference.getInstance(getActivity()).getUserName());
		tvSex.setText("1".equals(sex)?"男":"女");
		tvPhone.setText(MyPreference.getInstance(getActivity()).getTelNumber());
	}

}
