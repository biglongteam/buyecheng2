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
import com.hangzhou.tonight.wxpay.PayActivity;

/**
 * 
* @ClassName: OrderPromotionActivity 
* @Description: TODO(订单 详情 页面) 
* @author yanchao 
* @date 2015年9月26日 下午4:58:00 
*
 */
public class PayTypeActivity extends TabItemActivity implements OnClickListener{

	private Context mContext;
	private Button bt_submit;
	TextView tvTotalPrice;
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
		setContentView(R.layout.pay_type);
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
		case R.id.rl_youhuiquan:
			Bundle bundle = new Bundle();
			bundle.putInt("code", 1003);
			IntentJumpUtils.nextActivity(SelectLibaoActivity.class, PayTypeActivity.this,bundle, 1003);
			break;
		case R.id.bt_submit:
			checkDates();
			submitOrder();
			break;
			
		case R.id.rb_zfb:
			rbzfb.setBackgroundResource(R.drawable.autoplay_c);
			rbwx.setBackgroundResource(R.drawable.autoplay);
			rbyl.setBackgroundResource(R.drawable.autoplay);
			type = 1;
			break;
		case R.id.rb_wx:
			rbzfb.setBackgroundResource(R.drawable.autoplay);
			rbwx.setBackgroundResource(R.drawable.autoplay_c);
			rbyl.setBackgroundResource(R.drawable.autoplay);
			type = 2;
			break;
		case R.id.rb_yl:
			rbzfb.setBackgroundResource(R.drawable.autoplay);
			rbwx.setBackgroundResource(R.drawable.autoplay);
			rbyl.setBackgroundResource(R.drawable.autoplay_c);
			type = 3;
			break;
		}
		
	}

	
	/**
	 * 
	* @Title: checkDates 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void checkDates() {
		if(type<=0){
			showCustomToast("请选择支付方式");
			return;
		}
		if(pay_money<=0){
			showCustomToast("金额有误，请选择商品");
			return;
		}
	}


		/**
		 * 
		* @Title: submitOrder 
		* @Description: TODO(提交订单) 
		* @param     设定文件 
		* @return void    返回类型 
		* @throws
		 */
	private void submitOrder() {
		
		JSONObject params = new JSONObject();
		params.put("order_id", order_id);
		params.put("type", type);
		if(!ticket_id.equals("0")){
			params.put("wallet_money", wallet_money+"");
			params.put("title", act_name+"-"+ticket_name);
		}else {
			params.put("wallet_money", "0");
			params.put("title", "ceshi");
		}
		params.put("pay_money", pay_money+"");
		AsyncTaskUtil.postData(mContext, "prePayOrder", params, new Callback() {
			@Override public void onSuccess(JSONObject result) {
				
				try {
					
					Bundle bundle = new Bundle();
					if(type==1){//跳转到支付宝
						String info = result.getString("info");
						bundle.putString("info", info);
						IntentJumpUtils.nextActivity(AliPayActivity.class, PayTypeActivity.this, bundle);
					}else if(type==2){//跳转到微信 
						
						String appid = result.getString("appid");
						String noncestr = result.getString("appid");
						String packagestr = result.getString("package");;
						String partnerid = result.getString("partnerid");
						String prepayid = result.getString("prepayid");
					    String sign = result.getString("sign");
					    int timestamp = result.getInteger("timestamp");
						bundle.putString("appid", appid);
						bundle.putString("partnerid", partnerid);
						bundle.putString("prepayid", prepayid);
						bundle.putString("package", packagestr);
						bundle.putString("noncestr", noncestr);
						bundle.putString("sign", sign);
						bundle.putInt("timestamp", timestamp);
						IntentJumpUtils.nextActivity(PayActivity.class, PayTypeActivity.this, bundle);
					}else if(type==3){//跳转到银联
						
						/*银⾏行卡⽀支付时返回oid_partner，sign_type，
						busi_partner，dt_order，notify_url，
						no_order，name_goods，info_order，
						risk_item，valid_order，money_order，
						sign*/
						
						String oid_partner = result.getString("oid_partner");
						String sign_type = result.getString("sign_type");
						String busi_partner = result.getString("busi_partner");;
						String dt_order = result.getString("dt_order");
						String notify_url = result.getString("notify_url");
					    String no_order = result.getString("no_order");
					    String name_goods = result.getString("name_goods");
					    String info_order = result.getString("info_order");
					    String risk_item = result.getString("risk_item");
					    String valid_order = result.getString("valid_order");
					    String money_order = result.getString("money_order");
					    String sign = result.getString("sign");
					    
					    
					    bundle.putString("oid_partner", oid_partner);
						bundle.putString("sign_type", sign_type);
						bundle.putString("busi_partner", busi_partner);
						bundle.putString("dt_order", dt_order);
						bundle.putString("notify_url", notify_url);
						bundle.putString("money_order", money_order);
						bundle.putString("valid_order", valid_order);
						bundle.putString("risk_item", risk_item);
						bundle.putString("info_order", info_order);
						bundle.putString("name_goods", name_goods);
						bundle.putString("no_order", no_order);
						bundle.putString("sign", sign);
						IntentJumpUtils.nextActivity(StandActivity.class, PayTypeActivity.this, bundle);
						
					}
					/*⽀支付宝⽀支付时返回info(调⽤用⽀支付请求所需参
数)；
微信⽀支付时返回appid，partnerid，
prepayid，package，noncestr，timestamp，
sign；参数说明参考微信⽀支付api⽂文档
银⾏行卡⽀支付时返回oid_partner，sign_type，
busi_partner，dt_order，notify_url，
no_order，name_goods，info_order，
risk_item，valid_order，money_order，
sign；具体参考连连⽀支付相关demo
仅礼包或者余额⽀支付时，如成功则返回
wallet_pay=1
					*/
					//IntentJumpUtils.nextActivity(PayActivity.class, PayTypeActivity.this, bundle);
				} catch (Exception e) {
				}
				
			}
			@Override public void onFail(String msg) {}
		});
		
		/*new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadingDialog("正在提交,请稍后...");
			}

			@Override
			protected String doInBackground(Void... params) {

				Map<String, String> param =setParams();
				
				return HttpRequest.submitPostData(PreferenceConstants.TONIGHT_SERVER,
						param, "UTF-8");
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				JSONObject jsonObject = JSON.parseObject(result);
				String s = jsonObject.getString("s"); 
				if(s.equals("0")){
					String e = jsonObject.getString("e");
					showCustomToast(e);
					return;
				}
				try {
					
					Bundle bundle = new Bundle();
					if(type==1){//跳转到支付宝
						
					}else if(type==2){//跳转到微信 
						String partnerid = 
						bundle.putString("partnerid", partnerid);
						bundle.putString("prepayid", prepayid);
						bundle.putString("package", package);
						bundle.putString("noncestr", noncestr);
						bundle.putString("timestamp", timestamp);
						IntentJumpUtils.nextActivity(PayActivity.class, PayTypeActivity.this, bundle);
					}else if(type==3){//跳转到银联
						
					}
					⽀支付宝⽀支付时返回info(调⽤用⽀支付请求所需参
数)；
微信⽀支付时返回appid，partnerid，
prepayid，package，noncestr，timestamp，
sign；参数说明参考微信⽀支付api⽂文档
银⾏行卡⽀支付时返回oid_partner，sign_type，
busi_partner，dt_order，notify_url，
no_order，name_goods，info_order，
risk_item，valid_order，money_order，
sign；具体参考连连⽀支付相关demo
仅礼包或者余额⽀支付时，如成功则返回
wallet_pay=1
					
					//IntentJumpUtils.nextActivity(PayActivity.class, PayTypeActivity.this, bundle);
				} catch (Exception e) {
				}
			}
		}.execute();
		*/
	}


private Map<String, String> setParams(){
		
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		
		/*order_id(订单ID)；
		type(1:⽀支付宝 2：微信⽀支付 3：银⾏行卡)；
		wallet_money(使⽤用钱包的⾦金额数)；
		pay_money(使⽤用⽀支付的⾦金额数)；
		title(订单名称，订单名称=活动名+“ — ”+代⾦金券名)*/
		params.put("order_id", order_id);
		params.put("type", type);
		if(!ticket_id.equals("0")){
			params.put("wallet_money", wallet_money+"");
			params.put("title", act_name+"-"+ticket_name);
		}else {
			params.put("wallet_money", "0");
			params.put("title", act_name);
		}
		params.put("pay_money", pay_money+"");
		
		ArrayList<Object> arry = new ArrayList<Object>();
		arry.add(0, "prePayOrder");
		arry.add(1, MyPreference.getInstance(mContext).getUserId());
		arry.add(2, params);
		String data0 = RC4Utils.RC4("mdwi5uh2p41nd4ae23qy4",com.alibaba.fastjson.JSONArray.toJSONString(arry)/*JsonUtils.list2json(arry)*/);
		String encoded1 = "";
		try {
			encoded1 = new String(Base64Utils.encode(
					data0.getBytes("ISO-8859-1"), 0, data0.length()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("base64编码后：     " + encoded1);
		String decode = "";
		try {
			if(!encoded1.equals("")){
				decode = new String(
						Base64.decode(encoded1, Base64.DEFAULT),
						"ISO-8859-1");
			}		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String data7 = RC4Utils.RC4("mdwi5uh2p41nd4ae23qy4", decode);
		map.put("d", encoded1);
		return map;
		
	}
	@Override
	protected void init() {
		tvTotalPrice.setText(pay_money+"");
		
	}

	@Override
	protected void initViews() {

		tvBack = (TextView) findViewById(R.id.tv_header_back);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("支付订单");
		tvTotalPrice = (TextView) findViewById(R.id.tv_money);
		rbzfb = (Button) findViewById(R.id.rb_zfb);
		rbwx = (Button) findViewById(R.id.rb_wx);
		rbyl = (Button) findViewById(R.id.rb_yl);
		bt_submit = (Button) findViewById(R.id.bt_submit);
		
	}

	@Override
	protected void initEvents() {
		tvBack.setOnClickListener(this);
		rbzfb.setOnClickListener(this);
		rbwx.setOnClickListener(this);
		rbyl.setOnClickListener(this);
		bt_submit.setOnClickListener(this);
	}
	

}
