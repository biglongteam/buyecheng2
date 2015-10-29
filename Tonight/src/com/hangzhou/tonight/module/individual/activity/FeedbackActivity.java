package com.hangzhou.tonight.module.individual.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.CustomActionActivity;
import com.hangzhou.tonight.module.base.helper.ToastHelper;
import com.hangzhou.tonight.module.base.util.RegexValidateUtil;

/**
 * 意见反馈
 * @author hank
 *
 */
public class FeedbackActivity extends CustomActionActivity {

	EditText etOptions,etContacts;
	@Override
	protected void doView() {
		setContentView(R.layout.activity_individual_feedback);
		etOptions  = findView(R.id.individual_feedback_options);
		etContacts = findView(R.id.individual_feedback_contacts);
	}
	
	@Override
	public void doHandlerView(View handler) {
		super.doHandlerView(handler);
		TextView tv = ((TextView)handler);
		int left,right;
		left = right = getResources().getDimensionPixelSize(R.dimen.custom_actionbar_handler_padding);
		tv.setPadding(left, 0, right, 0);
		
		setOnClickListener(new OnHandlerClickListener() {
			@Override public void onClick(View handlerView) {
				Editable options = etOptions.getText(),contacts = etContacts.getText();
				if(TextUtils.isEmpty(options)){
					ToastHelper.show(getActivity(), getResources().getString(R.string.input_your_option));return;
				}
				if(TextUtils.isEmpty(contacts)){
					ToastHelper.show(getActivity(), getResources().getString(R.string.input_your_contacts));return;
				}
				if(!RegexValidateUtil.checkCellphone(contacts.toString()) && !RegexValidateUtil.checkQQ(contacts.toString())){
					ToastHelper.show(getActivity(), "请按要求输入联系方式.");return;
				}
				//TODO 发送反馈信息
			}
		});
		tv.setText("发送");
	
	}

	@Override
	protected void doListeners() {

	}

	@Override
	protected void doHandler() {

	}

}
