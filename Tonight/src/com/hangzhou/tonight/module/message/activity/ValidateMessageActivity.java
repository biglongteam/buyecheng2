package com.hangzhou.tonight.module.message.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.hangzhou.tonight.LoginActivity;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.base.BaseActivity;
import com.hangzhou.tonight.maintabs.MainActivity;
import com.hangzhou.tonight.util.Base64Utils;
import com.hangzhou.tonight.util.HttpRequest;
import com.hangzhou.tonight.util.JsonUtils;
import com.hangzhou.tonight.util.MD5Utils;
import com.hangzhou.tonight.util.MyPreference;
import com.hangzhou.tonight.util.PreferenceConstants;
import com.hangzhou.tonight.util.RC4Utils;

public class ValidateMessageActivity extends BaseActivity {
	
	 String nick,msg,uid,goupName,tuid;
	 int tag; 
	 TextView tv_username,tv_yours, tv_content;
	 Button btn_agree,btn_refuse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_message_fragment_validate_message);
		
	}

	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void initViews() {
		this.tv_username = (TextView) findViewById(R.id.message_comment_username);
		this.tv_yours = (TextView)findViewById(R.id.message_comment_your);
		this.tv_content = (TextView)findViewById(R.id.message_comment_content);
		this.btn_agree=(Button) findViewById(R.id.validate_message_agree);
		this.btn_refuse=(Button) findViewById(R.id.validate_message_refuse);
		String yours=getContent(tag,goupName);
		tv_username.setText(nick);
		tv_yours.setText(yours);
		tv_content.setText("“"+msg+"”");
	}

	@Override
	protected void init() {
        Intent intent=getIntent();
        tag=(Integer) intent.getExtras().get("tag");
        nick=intent.getExtras().get("nickname").toString();
        msg=intent.getExtras().get("msg").toString();
        uid=intent.getExtras().get("uid").toString();
        tuid=intent.getExtras().get("tuid").toString();
        if(tag==2||tag==3){
        	  goupName=intent.getExtras().get("goupName").toString();
        	
        }else{
        	goupName="";
        }
	}

	@Override
	protected void initEvents() {
		this.btn_agree.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				agree();
			}
		});
		
      this.btn_refuse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				refuse();
			}
		});

	}
	
	
	private String getContent(int tag,String goupName){
		String content="";
		if(tag==1){
				content="请求加您为好友";
		
		}else if(tag==2){
				content="申请加入群";	
		}
		return content;
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	
	private void agree() {

		new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
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
			}
		}.execute();
	}
	
	
	private Map<String, String> setParams(){
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> parms = new HashMap<String, Object>();
		ArrayList<Object> arry = new ArrayList<Object>();
		
		if(tag==1){
			parms.put("tuid", uid);		
			parms.put("nick", nick);	
			arry.add(0, "passFriend");
		}else if(tag==2){
			parms.put("tuid", uid);		
			parms.put("gid", nick);	
			arry.add(0, "passGroupApply");
		}else if(tag==3){
			parms.put("tuid", uid);		
			parms.put("nick", nick);	
			arry.add(0, "passGroupInvite");
		}

		
		
		
		arry.add(1, 0);
		arry.add(2, parms);
		String data0 = RC4Utils.RC4("mdwi5uh2p41nd4ae23qy4",
				JsonUtils.list2json(arry));

		System.out.println("RC4加密后：   " + data0);
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("base64解码后：" + decode);
		String data7 = RC4Utils.RC4("mdwi5uh2p41nd4ae23qy4", decode);
		System.out.println("RC4解密后：    " +data7);
		
		map.put("d", encoded1);
		
		return map;
		
	}
	
	private void refuse() {

		new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}

			@Override
			protected String doInBackground(Void... params) {

				Map<String, String> param =setParams2();
				return HttpRequest.submitPostData(PreferenceConstants.TONIGHT_SERVER,
						param, "UTF-8");
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
			}
		}.execute();
	}
	
	
	private Map<String, String> setParams2(){
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> parms = new HashMap<String, Object>();
		ArrayList<Object> arry = new ArrayList<Object>();
		
		if(tag==1){
			parms.put("tuid", uid);		
			parms.put("nick", nick);	
			arry.add(0, "refuseFriend");
		}else if(tag==2){
			parms.put("tuid", uid);		
			parms.put("gid", nick);	
			arry.add(0, "refuseGroupApply");
		}else if(tag==3){
			parms.put("tuid", uid);		
			parms.put("nick", nick);	
			arry.add(0, "refuseGroupInvite");
		}

		
		
		
		arry.add(1, 0);
		arry.add(2, parms);
		String data0 = RC4Utils.RC4("mdwi5uh2p41nd4ae23qy4",
				JsonUtils.list2json(arry));

		System.out.println("RC4加密后：   " + data0);
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("base64解码后：" + decode);
		String data7 = RC4Utils.RC4("mdwi5uh2p41nd4ae23qy4", decode);
		System.out.println("RC4解密后：    " +data7);
		
		map.put("d", encoded1);
		
		return map;
		
	}

}
