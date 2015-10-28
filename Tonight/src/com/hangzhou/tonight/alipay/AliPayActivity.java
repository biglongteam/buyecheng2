package com.hangzhou.tonight.alipay;

import java.net.URLEncoder;

import com.alipay.android.app.sdk.AliPay;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.activity.PayFinishActivity;
import com.hangzhou.tonight.activity.PayTypeActivity;
import com.hangzhou.tonight.util.IntentJumpUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class AliPayActivity  extends Activity {


	private static final int RQF_PAY = 1;

	private static final int RQF_LOGIN = 2;
	String info,order_name,order_id;
	float pay_money;
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.pay_result);
	        info = getIntent().getStringExtra("info");
	        order_id = getIntent().getStringExtra("order_id");
	        order_name = getIntent().getStringExtra("order_name");
	        pay_money = getIntent().getFloatExtra("pay_money", 0);
	    	/*String sign = Rsa.sign(info, Keys.PRIVATE);
	    	sign = URLEncoder.encode(sign);
	    	info += "&sign=\"" + sign + "\"&" + getSignType();*/
	    	final String orderInfo = info;
	    	new Thread() {
	    		public void run() {
	    			AliPay alipay = new AliPay(AliPayActivity.this, mHandler);
	    			
	    			//设置为沙箱模式，不设置默认为线上环境
	    			//alipay.setSandBox(true);
	    			String result = alipay.pay(orderInfo);
	    			Message msg = new Message();
	    			msg.what = RQF_PAY;
	    			msg.obj = result;
	    			mHandler.sendMessage(msg);
	    		}
	    	}.start();
	    }
	 Handler mHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				Result result = new Result((String) msg.obj);

				switch (msg.what) {
				case RQF_PAY:
				case RQF_LOGIN: {
					/*Toast.makeText(AliPayActivity.this, result.getResult(),
							Toast.LENGTH_SHORT).show();*/
					Bundle bundle = new Bundle();
					bundle.putString("order_id", order_id);
					bundle.putString("order_name", order_name);
					bundle.putFloat("pay_money", pay_money);
					//IntentJumpUtils.nextActivity(PayFinishActivity.class, AliPayActivity.this, bundle);
					finish();

				}
					break;
				default:
					break;
				}
			};
		};
	
		
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}
}
