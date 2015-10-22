package com.hangzhou.tonight.module.individual.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.CustomActionActivity;

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
