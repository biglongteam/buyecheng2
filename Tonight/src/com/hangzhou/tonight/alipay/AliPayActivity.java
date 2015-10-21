package com.hangzhou.tonight.alipay;

import java.net.URLEncoder;

import com.alipay.android.app.sdk.AliPay;
import com.hangzhou.tonight.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class AliPayActivity  extends Activity {


	private static final int RQF_PAY = 1;

	private static final int RQF_LOGIN = 2;
	String info;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.pay_result);
	        info = getIntent().getStringExtra("info");
	    	/*String sign = Rsa.sign(info, Keys.PRIVATE);
	    	sign = URLEncoder.encode(sign);
	    	info += "&sign=\"" + sign + "\"&" + getSignType();*/
	    	final String orderInfo = info;
	    	new Thread() {
	    		public void run() {
	    			AliPay alipay = new AliPay(AliPayActivity.this, mHandler);
	    			
	    			//设置为沙箱模式，不设置默认为线上环境
	    			alipay.setSandBox(true);
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
					Toast.makeText(AliPayActivity.this, result.getResult(),
							Toast.LENGTH_SHORT).show();
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
