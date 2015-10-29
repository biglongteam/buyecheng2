package com.hangzhou.tonight.module.individual.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.dto.UserInfoDto;
import com.hangzhou.tonight.module.base.dto.UserInfoDto.User;
import com.hangzhou.tonight.module.base.fragment.BFragment;
import com.hangzhou.tonight.module.base.helper.ToastHelper;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;
import com.hangzhou.tonight.util.MyPreference;
import com.hoo.base.util.ObjectUtil;

/**
 * 申请推广
 */
public class ApplyExtensionFragment extends BFragment {

	TextView tvNick,tvSex,tvPhone;
	Button bApplyExtension;
	TextView tvRealname;
	
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_individual_apply_extension, container, false);
	}
	
	@Override protected void doView() {
		tvNick = findView(R.id.apply_exteriion_nick);
		tvSex  = findView(R.id.apply_exteriion_sex);
		tvPhone= findView(R.id.apply_exteriion_phonenum);
		
		tvRealname = findView(R.id.apply_extension_realname);
		bApplyExtension = findView(R.id.apply_extension_submit);
		
	}

	@Override protected void doListeners() {
		bApplyExtension.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				String realName = tvRealname.getText().toString();
				if(ObjectUtil.isEmpty(realName)){ ToastHelper.show(getActivity(), "请填写真实姓名.");return; }
				
				JSONObject params = new JSONObject();
				params.put("realname", realName);
				AsyncTaskUtil.postData(getActivity(), "bApplyExtension", params, new Callback() {
					@Override public void onSuccess(JSONObject result) {
						ToastHelper.show(getActivity(), "操作成功.");
						onBackPressed();
					}
					
					@Override public void onFail(String msg) { }
				});
			}
		});
	}

	@Override protected void doHandler() {
		User u = UserInfoDto.getUser(getActivity());// MyPreference.getInstance(getActivity()).getUserSex();
		tvNick.setText(u.nick);
		tvSex.setText("1".equals(u.sex)?"男":"女");
		tvPhone.setText(u.phone);
	}

}
