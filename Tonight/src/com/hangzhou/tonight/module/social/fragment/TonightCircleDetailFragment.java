package com.hangzhou.tonight.module.social.fragment;


import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.fragment.BFragment;
import com.hangzhou.tonight.module.base.helper.PicTextHelper;
import com.hangzhou.tonight.module.base.helper.ToastHelper;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.DateUtil;
import com.hangzhou.tonight.module.base.util.ViewUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;
import com.hangzhou.tonight.util.MyPreference;

/**
 * 不夜城 - 动态详情
 * @author hank
 */
public class TonightCircleDetailFragment extends BFragment{
	public ImageView iv_headphoto;
	public TextView tv_name, tv_content, tv_time, tv_age,tv_good,tv_comment;
	
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_social_tonightcircle_detail, container, false);
	}

	@Override protected void doView() {
		iv_headphoto= (ImageView)findViewById(R.id.tonight_circle_head_photo);
		tv_name 	= (TextView) findViewById(R.id.tonight_circle_name);
		tv_content 	= (TextView) findViewById(R.id.tonight_circle_content);
		tv_time 	= (TextView) findViewById(R.id.tonight_circle_time);
		tv_age 		= (TextView) findViewById(R.id.tonight_circle_age);
		tv_comment  = (TextView) findViewById(R.id.tonight_circle_comment);
		tv_good     = (TextView) findViewById(R.id.tonight_circle_good);
	}

	@Override protected void doListeners() {
		tv_good.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				JSONObject params = new JSONObject();
				params.put("mid", mid);
				params.put("nick", MyPreference.getInstance(getActivity()).getUserName());
				AsyncTaskUtil.postData(getActivity(), "praiseMood", params, new Callback() {
					
					@Override
					public void onSuccess(JSONObject result) {
						ToastHelper.show(getActivity(), "成功点赞");
						if(result != null){
							tv_good	 .setText("赞 " + (result.getIntValue("pralse_num") + 1));
						}
					}
					@Override public void onFail(String msg) {
						ToastHelper.show(getActivity(), "已点赞");
					}
				});
				
			}
		});
	}

	String mid;
	JSONObject result;
	@Override protected void doHandler() {
		mid = getBundle().getString("mid");
		JSONObject params = new JSONObject();
		params.put("mid", mid);
		
		//获取心情
		AsyncTaskUtil.postData(getActivity(), "getMoodInfo", params, new Callback() {
			
			@Override public void onSuccess(JSONObject res) {
				tv_age.setVisibility(View.GONE);
				result = res.getJSONObject("moodInfo");
				String content = ViewUtil.getPicTextMutil(result.getString("content"), result.getString("url"), result.getIntValue("type")==2);
				tv_content.setText( Html.fromHtml(content,new PicTextHelper(),null));
				tv_good	 .setText("赞 " + result.getIntValue("pralse_num"));
				tv_comment.setText("评论 " + result.getIntValue("reply_num"));
				tv_name	 .setText(result.getString("nick"));
			}
			
			@Override public void onFail(String msg) {}
		});
		//获取回复
	}
	
}
