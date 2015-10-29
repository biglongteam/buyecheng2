package com.hangzhou.tonight.module.individual.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.fragment.BFragment;
import com.hangzhou.tonight.module.base.helper.ToastHelper;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;

/**
 * 订单评价
 * @author hank
 */
public class OrderEvaluationFragment extends BFragment {

	long orderId;
	RatingBar   rbMark;
	EditText etContent;
	Button bSubmit;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_individual_order_evaluation, container, false);
	}
	
	@Override
	protected void doView() {
		rbMark = findView(R.id.review_mark);
		etContent = findView(R.id.review_content);
		bSubmit   = findView(R.id.review_submit);
	}

	@Override
	protected void doListeners() {
		bSubmit.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				String content = etContent.getText().toString();
				if(TextUtils.isEmpty(content)){
					ToastHelper.show(getActivity(), "输入您要评论的内容.");return;
				}
				JSONObject params = new JSONObject();
				params.put("mark", (int)(rbMark.getRating() == 0f ? 1 : rbMark.getRating()));
				params.put("content", content);
				params.put("order_id", orderId);
				params.put("img", new String[]{"stag_0__w_480__uid_1000022__h_640__time_20150506235224-e"});
				AsyncTaskUtil.postData(getActivity(), "reviewOrder", params, new Callback() {
					@Override public void onSuccess(JSONObject result) {
						
					}
					@Override public void onFail(String msg) {}
				});
			}
		});
	}

	@Override protected void doHandler() {
		orderId = getBundle().getInt("order_id");
		//查询评论   修改评论
	}

}
