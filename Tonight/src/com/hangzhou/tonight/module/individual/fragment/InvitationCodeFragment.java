package com.hangzhou.tonight.module.individual.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.constant.SysModuleConstant;
import com.hangzhou.tonight.module.base.fragment.BFragment;
import com.hangzhou.tonight.module.base.helper.ToastHelper;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;
import com.hangzhou.tonight.util.PreferenceUtils;

/**
 * 我的邀请码
 * @author hank
 */
public class InvitationCodeFragment extends BFragment {
	
	EditText etMyCode,etGifts;
	Button bSubmit;
	
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_individual_invitation_code, container, false);
	}
	
	@Override protected void doView() {
		etMyCode = (EditText) findViewById(R.id.invitation_code_mine);
		etGifts  = (EditText) findViewById(R.id.indididual_code_friends_gifts);
		bSubmit  = (Button)   findViewById(R.id.individual_code_submit);
	}

	@Override protected void doListeners() {
		bSubmit.setOnClickListener(bSubmitClick);
	}
	
	OnClickListener bSubmitClick = new OnClickListener() {
		@Override public void onClick(View v) {
			String invitation = etGifts.getText().toString().trim();
			if(!TextUtils.isEmpty(invitation)){
				JSONObject params = new JSONObject();
				params.put("invitation", invitation);
				AsyncTaskUtil.postData(getActivity(), "getInvitationReward", params, new Callback() {
					@Override public void onSuccess(JSONObject result) {
						ToastHelper.show(getActivity(), "已成功领取.");
						etGifts.setText(null);
					}
					@Override public void onFail(String msg) {/*ToastHelper.show(getActivity(), "领取失败,请验证礼包码");*/}
				});
			}
		}
	};

	@Override protected void doHandler() {
		
		String code = PreferenceUtils.getPrefString(getActivity(), SysModuleConstant.KEY_INVITATION_CODE, null);
		if(null == code){
			AsyncTaskUtil.postData(getActivity(), "getSelfInvitation", null, new Callback() {
				@Override public void onSuccess(JSONObject result) {
					String code = result.getJSONObject("info").getString("invitation");
					doInvitationCode(code);
				}
				@Override public void onFail(String msg) {
					if(SysModuleConstant.VALUE_DEV_MODEL){ doInvitationCode("XS9Q01"); }
				}
			});
		}else{
			doInvitationCode(code);
		}
	}
	
	private void doInvitationCode(String code){
		if(code != null){
			PreferenceUtils.setPrefString(getActivity(), SysModuleConstant.KEY_INVITATION_CODE, code);
		}
		etMyCode.setText(code);
	}
}
