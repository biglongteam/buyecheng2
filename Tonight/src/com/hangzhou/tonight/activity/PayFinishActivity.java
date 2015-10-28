package com.hangzhou.tonight.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.hangzhou.tonight.PayActivity;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.adapter.OtherActsListAdapter;
import com.hangzhou.tonight.adapter.PinglunListAdapter;
import com.hangzhou.tonight.alipay.AliPayActivity;
import com.hangzhou.tonight.entity.ActivesInfo;
import com.hangzhou.tonight.entity.MerchantInfo;
import com.hangzhou.tonight.entity.OtherActsEntity;
import com.hangzhou.tonight.entity.ReviewsEntity;
import com.hangzhou.tonight.lianlianpay.activity.StandActivity;
import com.hangzhou.tonight.maintabs.TabItemActivity;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;
import com.hangzhou.tonight.util.Base64Utils;
import com.hangzhou.tonight.util.HttpRequest;
import com.hangzhou.tonight.util.IntentJumpUtils;
import com.hangzhou.tonight.util.JsonUtils;
import com.hangzhou.tonight.util.MyPreference;
import com.hangzhou.tonight.util.PreferenceConstants;
import com.hangzhou.tonight.util.RC4Utils;
import com.hangzhou.tonight.util.ScreenUtils;

/**
 * 
* @ClassName: OrderPromotionActivity 
* @Description: TODO(订单 支付完成  页面) 
* @author yanchao 
* @date 2015年9月26日 下午4:58:00 
*
 */
public class PayFinishActivity extends TabItemActivity implements OnClickListener{

	private Context mContext;
	private Button bt_submit;
	TextView tvTotalPrice,tv_totall;
	String order_id,act_name,expense,ticket_id="0",ticket_name="0";
	private ActivesInfo actInfo;
	int  type = 0,ticket;
	float pay_money,wallet_money;
	private TextView tvBack,tvTitle;
	Button rbzfb, rbwx ,rbyl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.pay_finish);
		order_id = getIntent().getStringExtra("order_id");
		act_name = getIntent().getStringExtra("actName");
		ticket_id = getIntent().getStringExtra("ticket_id");
		if(!ticket_id.equals("0")){
			ticket_name = getIntent().getStringExtra("ticket_name");
		}
		ticket = getIntent().getIntExtra("ticket", 0);
		pay_money = getIntent().getFloatExtra("pay_money", 0);
		
		initViews();
		initEvents();
		init();
	}
	
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_header_back:
			finish();
			break;
		}
		
	}

	


	@Override
	protected void init() {
		tvTotalPrice.setText(pay_money+"");
		tv_totall.setText(pay_money+"");
		
	}

	@Override
	protected void initViews() {

		tvBack = (TextView) findViewById(R.id.tv_header_back);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("支付订单");
		tv_totall = (TextView) findViewById(R.id.tv_totall);
		
	}

	@Override
	protected void initEvents() {
		tvBack.setOnClickListener(this);
	}
	

}
