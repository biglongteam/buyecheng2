package com.hangzhou.tonight.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.adapter.OtherActsListAdapter;
import com.hangzhou.tonight.adapter.PinglunListAdapter;
import com.hangzhou.tonight.entity.ActivesInfo;
import com.hangzhou.tonight.entity.MerchantInfo;
import com.hangzhou.tonight.entity.OtherActsEntity;
import com.hangzhou.tonight.entity.ReviewsEntity;
import com.hangzhou.tonight.maintabs.TabItemActivity;
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
public class OrderPromotionActivity extends TabItemActivity implements OnClickListener{

	
	private Context mContext;
	/** 增加 操作 */
	private static final int OPERATION_TYPE_ADD = 0;
	/** 减少 操作 */
	private static final int OPERATION_TYPE_SUB = 1;
	/** 门票数量"减少"按钮 */
	ImageView ivCounterSub;
	/** 门票数量"增加"按钮 */
	ImageView ivCounterAdd;
	private Button bt_submit;
	private TextView tvCounter;//商品 数量
	TextView tvTotalPrice,tvPricezhekou,tvPhone,tv_youhuiquan;
	RelativeLayout rl_youhuiquan;
	String act_id,phone,expense,ticket_id = "0",ticket_name;
	private ActivesInfo actInfo;
	int  count = 1;
	float amount = 200,totalPrice = 200,coursePrice,zekou,unitPrice;
	private int ticket = 0;
	private TextView tvBack,tvTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_promotion);
		mContext = this;
		actInfo = (ActivesInfo) getIntent().getSerializableExtra("actInfo");
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
		case R.id.iv_counter_sub:
			
			changeCourseCount(OPERATION_TYPE_SUB);
			
			break;

		case R.id.iv_counter_add:
			changeCourseCount(OPERATION_TYPE_ADD);
			break;
		case R.id.rl_youhuiquan:
			Bundle bundle = new Bundle();
			bundle.putInt("code", 1003);
			IntentJumpUtils.nextActivity(SelectLibaoActivity.class, OrderPromotionActivity.this,bundle, 1003);
			break;
		case R.id.bt_submit:
			checkDates();
			submitOrder();
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
		if(totalPrice<=0){
			showCustomToast("金额有误，请选择商品");
			return;
		}
		if(totalPrice<=0){
			showCustomToast("金额有误，请选择商品");
			return;
		}
		if(phone==null){
			showCustomToast("您的电话不能为空！");
			return;
		}
	}



	/**
	 * 计算 课程总价 
	 * courseCount 课时数
	 * coursePrice 对应单价 
	 */
	
	private void calculateCourseTotalPrice(float courseCount,float coursePrice,int addOrReduceType) {
		
		//tvCoursePrice.setText(coursePrice+"元/ 小时");
		if(addOrReduceType==OPERATION_TYPE_SUB){
			totalPrice = (float) (coursePrice*courseCount);
		}else{
			totalPrice = (float) (courseCount*coursePrice);
		}
		
		tvTotalPrice.setText(totalPrice + "元");
		tvPricezhekou.setText(totalPrice - ticket + "元");
		//tvAllPrice.setText(totalPrice + "元");
	}
	/**
	 * 添加 或减少 课时
	 * @param operationTypeAdd
	 */

	private void changeCourseCount(int addOrReduceType) {
		
			
		//减少 课时
		if(addOrReduceType==OPERATION_TYPE_SUB){
			
			
			if (Integer.valueOf(tvCounter.getText().toString()) > 1) {
				int numReduce = Integer.valueOf(tvCounter.getText().toString())-1;
						
				count = numReduce;
				tvCounter.setText(count + "");
			}else {
				// 如果数量小于1，则减少按钮的背景设置为灰色，且数字为1
				tvCounter.setText(count + "");
				//ivCounterSub.setClickable(false);
			}
			
			calculateCourseTotalPrice(count,unitPrice,addOrReduceType);
		}
		
		//增加 课时
		if(addOrReduceType==OPERATION_TYPE_ADD){
			if (Integer.valueOf(tvCounter.getText().toString()) <99) {
				int numAdd = Integer.valueOf(tvCounter.getText().toString())
						+1;
				count = numAdd;
				tvCounter.setText(count + "");
			}else {
				// 如果数量大于 最大课时限度 ，则增加按钮的背景设置为灰色，且数字为count
				tvCounter.setText(count + "");
				//ivCounterSub.setClickable(false);
			}
			calculateCourseTotalPrice(count,unitPrice,addOrReduceType);
		}
		
		// 将新的数量更新到购票车的数据库表中
		//updateTicketQuantityInCartTable(0, quantity);

		// 计算总价，改变页面上总价的数字
		//calculateTicketTotalPrice();
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
		new AsyncTask<Void, Void, String>() {

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
				String order_id = jsonObject.getString("order_id"); 
				
				try {
					
					
					/*order_id(订单ID)；
					type(1:⽀支付宝 2：微信⽀支付 3：银⾏行卡)；
					wallet_money(使⽤用钱包的⾦金额数)；
					pay_money(使⽤用⽀支付的⾦金额数)；
					title(订单名称，订单名称=活动名+“ — ”+代⾦金券名)*/
					Bundle bundle = new Bundle();
					
					
					bundle.putString("order_id", order_id);
					bundle.putString("actName", actInfo.getName());
					if(!ticket_id.equals("0")){
					bundle.putString("ticket_id", ticket_id);
					bundle.putString("ticket_name", ticket_name);
					}else {
						bundle.putString("ticket_id", "0");
					}
					bundle.putInt("wallet_money", ticket);
					bundle.putFloat("pay_money", totalPrice-ticket);
					bundle.putFloat("ticket_price", totalPrice);
					
					IntentJumpUtils.nextActivity(PayTypeActivity.class, OrderPromotionActivity.this, bundle);
				} catch (Exception e) {
				}
			}
		}.execute();
		
	}


private Map<String, String> setParams(){
		
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> parms = new HashMap<String, Object>();
		
		/*act_id(活动ID)；
		num(数量)；
		amount(订单总⾦金额);
		ticket_id(礼包ID，如不使⽤用礼包则为0)；
		expense(实际⽀支付⾦金额)；
		phone(⼿手机号)*/
		//parms.put("uid", MyPreference.getInstance(mContext).getUserId());
		parms.put("act_id", actInfo.getAct_id());
		parms.put("num",count);
		parms.put("amount", totalPrice+"");
		if(!ticket_id.equals("0")){
			parms.put("ticket_id", ticket_id);
		}else {
			parms.put("ticket_id", "0");
		}
		parms.put("expense", totalPrice-ticket+"");
		parms.put("phone", actInfo.getPhone());
		ArrayList<Object> arry = new ArrayList<Object>();
		arry.add(0, "createOrder");
		arry.add(1, MyPreference.getInstance(mContext).getUserId());
		arry.add(2, parms);
		String data0 = RC4Utils.RC4("mdwi5uh2p41nd4ae23qy4",JsonUtils.list2json(arry));
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
		String price = actInfo.getPrice();
		if(price!=null){
			unitPrice = Float.parseFloat(price);
			totalPrice=unitPrice;
			tvTotalPrice.setText(totalPrice+"元");
		}else {
			tvTotalPrice.setText("0元");
		}
		
		phone = actInfo.getPhone();
		if(ticket>0){
			tvPricezhekou.setText(Integer.parseInt(actInfo.getPrice())-ticket+"元");
		}else{
			tvPricezhekou.setText(actInfo.getPrice()+"元");
		}
		
		tvPhone.setText(actInfo.getPhone());
		
	}

	@Override
	protected void initViews() {

		tvBack = (TextView) findViewById(R.id.tv_header_back);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("提交订单");
		ivCounterSub = (ImageView) findViewById(R.id.iv_counter_sub);
		ivCounterAdd = (ImageView) findViewById(R.id.iv_counter_add);
		tvTotalPrice = (TextView) findViewById(R.id.tv_order_pricecount);
		tvPricezhekou = (TextView) findViewById(R.id.tv_order_pricezhekou);
		tvCounter = (TextView) findViewById(R.id.tv_counter_txt);
		tvPhone = (TextView) findViewById(R.id.tv_order_phone);
		tv_youhuiquan = (TextView) findViewById(R.id.tv_order_youhuiquan);
		rl_youhuiquan = (RelativeLayout) findViewById(R.id.rl_youhuiquan);
		bt_submit = (Button) findViewById(R.id.bt_submit);
		
	}

	@Override
	protected void initEvents() {
		tvBack.setOnClickListener(this);
		ivCounterSub.setOnClickListener(this);
		ivCounterAdd.setOnClickListener(this);
		rl_youhuiquan.setOnClickListener(this);
		ivCounterSub.setOnClickListener(this);
		ivCounterSub.setOnClickListener(this);
		rl_youhuiquan.setOnClickListener(this);
		bt_submit.setOnClickListener(this);
	}
	
	@Override
	protected void onActivityResult( int requestCode,int resultCode,
            Intent imageReturnIntent) {
		 if (resultCode != 1003)
	            return;
		
		 if(requestCode==1003){
			ticket_id =  imageReturnIntent.getExtras().getString("id");
			ticket_name = imageReturnIntent.getExtras().getString("name");
			String money = imageReturnIntent.getExtras().getString("money");
			tv_youhuiquan.setText("-"+ticket_name);
			if(!ticket_id.equals("0")){
				ticket  = Integer.parseInt(money);
			}
		 }
		
	}

}
