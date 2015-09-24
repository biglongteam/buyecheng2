package com.hangzhou.tonight.module.social.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.fragment.BFragment;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.DateUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;
import com.hangzhou.tonight.util.StringUtil;
import com.hoo.ad.base.helper.ToastHelper;

public class CreateGroupFragment extends BFragment {

	Button button;
	TextView tvId,tvDate;
	EditText etProfile,etName,etLabel;
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_social_addgroup, container, false);
	}
	
	@Override protected void doView() {
		button = findView(R.id.social_creategroup);
		tvId   = findView(R.id.social_group_id);
		tvDate = findView(R.id.social_createdate);
		etName = findView(R.id.social_group_name);
		etLabel= findView(R.id.social_group_label);
		etProfile = findView(R.id.social_group_profile);
	}

	@Override protected void doListeners() {
		button.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				if(isVal()){ doCreateGroup(); }else{
					ToastHelper.show(getActivity(), "群昵称、群介绍均不可为空.");
				}
			}
		});
	}

	@Override protected void doHandler() {
		
	}
	
	
	private boolean isVal(){
		return StringUtil.notEmpty(etName.getText().toString()) && StringUtil.notEmpty(etProfile.getText().toString()) && StringUtil.notEmpty(etLabel.getText().toString());
	}

	private void doCreateGroup(){
		JSONObject params = new JSONObject();
		params.put("title", etName.getText().toString());
		params.put("intro", etProfile.getText().toString());
		//TODO lable city photo
		params.put("label", etLabel.getText().toString());
		params.put("city", 179);
		params.put("photo", new String[]{});
		AsyncTaskUtil.postData(getActivity(), "createGroup", params, new Callback() {
			
			@Override public void onSuccess(JSONObject result) {
				ToastHelper.show(getActivity(), "群创建成功!");
				tvId.setText(result.getString("gid"));
				tvDate.setText(DateUtil.getTodayDate());
				//TODO 邀请好友...
				button.setText("已创建");
				button.setEnabled(false);
			}
			
			@Override public void onFail(String msg) { }
		});
	}
	
}
