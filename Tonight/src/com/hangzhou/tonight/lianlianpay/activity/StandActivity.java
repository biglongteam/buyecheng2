
package com.hangzhou.tonight.lianlianpay.activity;


import org.json.JSONException;
import org.json.JSONObject;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.activity.PayFinishActivity;
import com.hangzhou.tonight.alipay.AliPayActivity;
import com.hangzhou.tonight.lianlianpay.env.EnvConstants;
import com.hangzhou.tonight.lianlianpay.payutil.BaseHelper;
import com.hangzhou.tonight.lianlianpay.payutil.Constants;
import com.hangzhou.tonight.lianlianpay.payutil.Md5Algorithm;
import com.hangzhou.tonight.lianlianpay.payutil.MobileSecurePayer;
import com.hangzhou.tonight.lianlianpay.payutil.PayOrder;
import com.hangzhou.tonight.util.IntentJumpUtils;
import com.hangzhou.tonight.util.MyPreference;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 快捷支付
 * @author kristain
 *
 */
public class StandActivity extends Activity implements OnClickListener {

    // 支付验证方式 0：标准版本， 1：卡前置方式，接入时，只需要配置一种即可，Demo为说明用。可以在menu中选择支付方式。
    private int pay_type_flag = 0;
    private boolean is_preauth = false;
    float pay_money;
    String order_name,order_id;
    String oid_partner,sign_type,busi_partner,dt_order,notify_url,no_order,name_goods,info_order,risk_item,
    valid_order,money_order,sign;
    private Context mContext;
    PayOrder order = null;
   /* 
	String sign_type = result.getString("sign_type");
	String  = result.getString("busi_partner");;
	String  = result.getString("dt_order");
	String  = result.getString("notify_url");
    String  = result.getString("no_order");
    String  = result.getString("name_goods");
    String  = result.getString("info_order");
    String risk_item = result.getString("risk_item");
    String  = result.getString("valid_order");
    String  = result.getString("money_order");
    String  = result.getString("sign");*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.standpay);
        mContext = this;
        order_id = getIntent().getStringExtra("order_id");
        order_name = getIntent().getStringExtra("order_name");
        pay_money = getIntent().getFloatExtra("pay_money", 0);
        
        
        oid_partner = getIntent().getStringExtra("oid_partner");
        sign_type = getIntent().getStringExtra("sign_type");
        busi_partner = getIntent().getStringExtra("busi_partner");
        dt_order = getIntent().getStringExtra("dt_order");
        notify_url = getIntent().getStringExtra("notify_url");
        no_order = getIntent().getStringExtra("no_order");
        name_goods = getIntent().getStringExtra("name_goods");
        info_order = getIntent().getStringExtra("info_order");
        risk_item = getIntent().getStringExtra("risk_item");
        valid_order = getIntent().getStringExtra("valid_order");
        money_order = getIntent().getStringExtra("money_order");
        sign = getIntent().getStringExtra("sign");
        
       /* findViewById(R.id.jump_btn).setOnClickListener(this);
        findViewById(R.id.preauth_btn).setOnClickListener(this);*/
        
        
        order = constructGesturePayOrder();
        String content4Pay = BaseHelper.toJSONString(order);

        // 关键 content4Pay 用于提交到支付SDK的订单支付串，如遇到签名错误的情况，请将该信息帖给我们的技术支持
        Log.i(StandActivity.class.getSimpleName(), content4Pay);
        MobileSecurePayer msp = new MobileSecurePayer();
        if (is_preauth) {
            boolean bRet = msp.payPreAuth(content4Pay, mHandler,
                    Constants.RQF_PAY, StandActivity.this, false);
            Log.i(StandActivity.class.getSimpleName(), String.valueOf(bRet));
        } else {
            boolean bRet = msp.pay(content4Pay, mHandler,
                    Constants.RQF_PAY, StandActivity.this, false);
            Log.i(StandActivity.class.getSimpleName(), String.valueOf(bRet));
        }

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == findViewById(R.id.jump_btn)) {
            is_preauth = false;
        } else if (v == findViewById(R.id.preauth_btn)) {
            is_preauth = true;
        }
        PayOrder order = null;
        if (pay_type_flag == 0) {
            // 标准模式
            order = constructGesturePayOrder();
        } else if (pay_type_flag == 1) {
            // TODO 卡前置方式, 如果传入的是卡号，卡号必须大于等于14位
            if (TextUtils
                    .isEmpty(((EditText) findViewById(R.id.bankno))
                            .getText().toString())
                    && TextUtils
                            .isEmpty(((EditText) findViewById(R.id.agree_no))
                                    .getText().toString())) {
                Toast.makeText(StandActivity.this,
                        "卡前置模式，必须填入银行卡卡号或者协议号", Toast.LENGTH_LONG)
                        .show();
                return;
            }
            order = constructPreCardPayOrder();

        }
        order = constructGesturePayOrder();
        String content4Pay = BaseHelper.toJSONString(order);

        // 关键 content4Pay 用于提交到支付SDK的订单支付串，如遇到签名错误的情况，请将该信息帖给我们的技术支持
        Log.i(StandActivity.class.getSimpleName(), content4Pay);
        MobileSecurePayer msp = new MobileSecurePayer();
        if (is_preauth) {
            boolean bRet = msp.payPreAuth(content4Pay, mHandler,
                    Constants.RQF_PAY, StandActivity.this, false);
            Log.i(StandActivity.class.getSimpleName(), String.valueOf(bRet));
        } else {
            boolean bRet = msp.pay(content4Pay, mHandler,
                    Constants.RQF_PAY, StandActivity.this, false);
            Log.i(StandActivity.class.getSimpleName(), String.valueOf(bRet));
        }

    }

    private Handler mHandler = createHandler();

    private Handler createHandler() {
        return new Handler() {
            public void handleMessage(Message msg) {
                String strRet = (String) msg.obj;
                switch (msg.what) {
                    case Constants.RQF_PAY: {
                        JSONObject objContent = BaseHelper.string2JSON(strRet);
                        String retCode = objContent.optString("ret_code");
                        String retMsg = objContent.optString("ret_msg");

                        // 成功
                        if (Constants.RET_CODE_SUCCESS.equals(retCode)) {
                            // TODO 卡前置模式返回的银行卡绑定协议号，用来下次支付时使用，此处仅作为示例使用。正式接入时去掉
                            if (pay_type_flag == 1) {
                                TextView tv_agree_no = (TextView) findViewById(R.id.tv_agree_no);
                                tv_agree_no.setVisibility(View.VISIBLE);
                                tv_agree_no.setText(objContent.optString(
                                        "agreementno", ""));
                            }
                            BaseHelper.showDialog(StandActivity.this, "提示",
                                    "支付成功",
                                    android.R.drawable.ic_dialog_alert);
                            Bundle bundle = new Bundle();
        					bundle.putString("order_id", order_id);
        					bundle.putString("order_name", order_name);
        					bundle.putFloat("pay_money", pay_money);
        					//IntentJumpUtils.nextActivity(PayFinishActivity.class, StandActivity.this, bundle);
                            finish();
                        } else if (Constants.RET_CODE_PROCESS.equals(retCode)) {
                            // TODO 处理中，掉单的情形
                            String resulPay = objContent.optString("result_pay");
                            if (Constants.RESULT_PAY_PROCESSING
                                    .equalsIgnoreCase(resulPay)) {
                                BaseHelper.showDialog(StandActivity.this, "提示",
                                        objContent.optString("ret_msg") + "交易状态码："
                                                + retCode,
                                        android.R.drawable.ic_dialog_alert);
                            }

                        } else {
                            // TODO 失败
                            BaseHelper.showDialog(StandActivity.this, "提示", retMsg
                                    + "，交易状态码:" + retCode,
                                    android.R.drawable.ic_dialog_alert);
                            finish();
                        }
                    }
                        break;
                }
                super.handleMessage(msg);
            }
        };

    }

    private PayOrder constructGesturePayOrder() {
        SimpleDateFormat dataFormat = new SimpleDateFormat(
                "yyyyMMddHHmmss");
        Date date = new Date();
        String timeString = dataFormat.format(date);

        PayOrder order = new PayOrder();
        order.setOid_partner(oid_partner);
        order.setBusi_partner(busi_partner);
        order.setNo_order(no_order);
        order.setDt_order(dt_order);
        order.setName_goods(name_goods);
        order.setNotify_url(notify_url);
        order.setInfo_order(info_order);
        // TODO MD5 签名方式
        order.setSign_type(sign_type);
        // TODO RSA 签名方式
        // order.setSign_type(PayOrder.SIGN_TYPE_RSA);
        order.setValid_order(valid_order);

        order.setUser_id(MyPreference.getInstance(mContext).getUserId());
       // order.setId_no(((EditText) findViewById(R.id.idcard)).getText() .toString());
       // order.setAcct_name(((EditText) findViewById(R.id.name)).getText().toString());
        order.setMoney_order(money_order);

       /* int id = ((RadioGroup) findViewById(R.id.flag_modify_group))
                .getCheckedRadioButtonId();
        if (id == R.id.flag_modify_0) {
            order.setFlag_modify("0");
        } else if (id == R.id.flag_modify_1) {
            order.setFlag_modify("1");
        }*/
        // 风险控制参数
        order.setRisk_item(risk_item);

       /* String sign = "";
        if (is_preauth) {
            order.setOid_partner(EnvConstants.PARTNER_PREAUTH);
        } else {
            order.setOid_partner(EnvConstants.PARTNER);
        }
        String content = BaseHelper.sortParam(order);
        // TODO MD5 签名方式, 签名方式包括两种，一种是MD5，一种是RSA 这个在商户站管理里有对验签方式和签名Key的配置。
        if (is_preauth) {
            sign = Md5Algorithm.getInstance().sign(content, EnvConstants.MD5_KEY_PREAUTH);
        } else {
            sign = Md5Algorithm.getInstance().sign(content, EnvConstants.MD5_KEY);
        }*/
        // RSA 签名方式
        // sign = Rsa.sign(content, EnvConstants.RSA_PRIVATE);
        order.setSign(sign);
        return order;
    }

    private PayOrder constructPreCardPayOrder() {

        SimpleDateFormat dataFormat = new SimpleDateFormat(
                "yyyyMMddHHmmss");
        Date date = new Date();
        String timeString = dataFormat.format(date);
        PayOrder order = new PayOrder();
        order.setBusi_partner(oid_partner);
        order.setNo_order(no_order);
        order.setDt_order(dt_order);
        order.setName_goods(name_goods);
        order.setNotify_url(notify_url);
        // TODO MD5 签名方式
        order.setSign_type(sign_type);
        // TODO RSA 签名方式
        // order.setSign_type(PayOrder.SIGN_TYPE_RSA);
        order.setValid_order(valid_order);

        order.setUser_id(MyPreference.getInstance(mContext).getUserId());
       // order.setId_no(((EditText) findViewById(R.id.idcard)).getText() .toString());
       // order.setAcct_name(((EditText) findViewById(R.id.name)).getText().toString());
        order.setMoney_order(money_order);

        // 银行卡卡号，该卡首次支付时必填
        order.setCard_no(((EditText) findViewById(R.id.bankno))
                .getText().toString());
        // 银行卡历次支付时填写，可以查询得到，协议号匹配会进入SDK，
        order.setNo_agree(((EditText) findViewById(R.id.agree_no)).getText()
                .toString());

        int id = ((RadioGroup) findViewById(R.id.flag_modify_group))
                .getCheckedRadioButtonId();
        if (id == R.id.flag_modify_0) {
            order.setFlag_modify("0");
        } else if (id == R.id.flag_modify_1) {
            order.setFlag_modify("1");
        }
        // 风险控制参数
        order.setRisk_item(risk_item);

      //  String sign = "";
        // TODO 商户号
        if (is_preauth) {
            order.setOid_partner(EnvConstants.PARTNER_PREAUTH);
        } else {
            order.setOid_partner(EnvConstants.PARTNER);
        }
        String content = BaseHelper.sortParam(order);
        // TODO MD5 签名方式, 签名方式包括两种，一种是MD5，一种是RSA 这个在商户站管理里有对验签方式和签名Key的配置。
       /* if (is_preauth) {
            sign = Md5Algorithm.getInstance().sign(content, EnvConstants.MD5_KEY_PREAUTH);
        } else {
            sign = Md5Algorithm.getInstance().sign(content, EnvConstants.MD5_KEY);
        }*/
        // RSA 签名方式
        // sign = Rsa.sign(content, EnvConstants.RSA_PRIVATE);
        order.setSign(sign);
        return order;
    }

   /* private String constructRiskItem() {
        JSONObject mRiskItem = new JSONObject();
        try {
            mRiskItem.put("user_info_bind_phone", "13958069593");
            mRiskItem.put("user_info_dt_register", "201407251110120");
            mRiskItem.put("frms_ware_category", "4.0");
            mRiskItem.put("request_imei", "1122111221");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mRiskItem.toString();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.stand_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.menu_1 == item.getItemId()) {
            findViewById(R.id.layout_precard).setVisibility(View.GONE);
            pay_type_flag = 0;
        } else if (R.id.menu_2 == item.getItemId()) {
            pay_type_flag = 1;
            findViewById(R.id.layout_precard).setVisibility(View.VISIBLE);
        }

        return super.onOptionsItemSelected(item);
    }
}
