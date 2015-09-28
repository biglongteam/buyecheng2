package com.hangzhou.tonight;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.hangzhou.tonight.base.BaseActivity;
import com.hangzhou.tonight.base.BaseApplication;
import com.hangzhou.tonight.base.Config;
import com.hangzhou.tonight.entity.BannerEntity;
import com.hangzhou.tonight.maintabs.MainActivity;
import com.hangzhou.tonight.module.social.fragment.MyFriendFragment;
import com.hangzhou.tonight.util.Base64Utils;
import com.hangzhou.tonight.util.HttpRequest;
import com.hangzhou.tonight.util.JsonResolveUtils;
import com.hangzhou.tonight.util.JsonUtils;
import com.hangzhou.tonight.util.MD5Utils;
import com.hangzhou.tonight.util.MyPreference;
import com.hangzhou.tonight.util.PreferenceConstants;
import com.hangzhou.tonight.util.RC4Utils;
import com.hangzhou.tonight.util.SystemUiHider;
import com.hangzhou.tonight.view.JazzyViewPager;
import com.hangzhou.tonight.view.JazzyViewPager.TransitionEffect;
import com.hangzhou.tonight.view.OutlineContainer;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class WelcomeActivity extends BaseActivity {
  
    private String account ;
    private String password  ;

    private Context mContext;
    private JazzyViewPager mJazzy;
    private Button but_start;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
        mContext = this;
        openTonight();
        if(MyPreference.getInstance(mContext).getIsFirst()){
        	setupJazziness(TransitionEffect.Tablet);
        }else {
        	login();
        	
        	/*MyPreference.getInstance(WelcomeActivity.this).setUserId("9000043");
        	Intent intent = new Intent(WelcomeActivity.this, MainActivity.class); 
        	startActivity(intent);
			finish();*/
        }
		
    }

    private void openTonight(){
    	putAsyncTask2(new AsyncTask<Void, Void, String>() {

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
				
				try {
					com.alibaba.fastjson.JSONObject object = JSON
							.parseObject(result);
					com.alibaba.fastjson.JSONObject object1 = object.getJSONObject("config");
					String version = object1.getString("version");
					String cityUrl = object1.getString("url");
					
					/*if(Integer.parseInt(MyPreference.getInstance(mContext).getCityConvsion())<Integer.parseInt(version)){
						
					}else {
						
					}*/
					MyPreference.getInstance(mContext).setCityUrl(cityUrl);
					MyPreference.getInstance(mContext).setCityConvsion(version);
					
					com.alibaba.fastjson.JSONArray jsonArray = object.getJSONArray("banner");
					BaseApplication.banners = JSON.parseArray(jsonArray.toString(), BannerEntity.class);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		});
    }
    
    
	private Map<String, String> setParams(){
		Map<String, String> map = new HashMap<String, String>();
		ArrayList<Object> arry = new ArrayList<Object>();
		arry.add(0, "openTonight");
		arry.add(1, 0);
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

		/*try {
			String data8 = new String(Base64.decode(
					encoded1.getBytes(), Base64.DEFAULT), "ISO-8859-1");
			System.out.println("base64解码后：" + data8);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		// String data7=HloveyRC4(decode,"mdwi5uh2p41nd4ae23qy4");
		String data7 = RC4Utils.RC4("mdwi5uh2p41nd4ae23qy4", decode);
		System.out.println("RC4解密后：    " +data7);
		
		map.put("d", encoded1);
		return map;
		
	}

	
	
	

	private void login() {
		/*
		 * if ((!validateAccount()) || (!validatePwd())) { return; }
		 */

		new AsyncTask<Void, Void, String>() {

			private String uid;
			private String nick;
			private String birth;
			private String sex;
			private String phone;
			private String money;
			private String favorite;
			private String praised;
			private String groups;
			private String friends;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				//showLoadingDialog("正在登录,请稍后...");
			}

			@Override
			protected String doInBackground(Void... params) {

				Map<String, String> param =setLoginParams();
				return HttpRequest.submitPostData(PreferenceConstants.TONIGHT_SERVER,
						param, "UTF-8");
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				/*
				 * if (result) { Intent intent = new Intent(WelcomeActivity.this,
				 * MainActivity.class); startActivity(intent); finish(); } else
				 * { showCustomToast("账号或密码错误,请检查是否输入正确"); }
				 */
				boolean success=dealResult(result);
				if(success){
					JSONObject object;
					
					
					 /*"uid": "9000034",
					    "favorite": [],
					    "praised": [],
					    "groups": [],
					    "friends": [],
					    "nick": "nickname",
					    "birth": "1988-09-09",
					    "sex": "1",
					    "phone": "15225095589",
					    "money": "0.00",
					    "paypass": "0",
					    "s": 1*/
					try {
						object = new JSONObject(result);
						uid = object.getString("uid");
						nick = object.getString("nick");
						birth = object.getString("birth");
						sex = object.getString("sex");
						phone = object.getString("phone");
						money = object.getString("money");
						favorite = object.getString("favorite");
						praised = object.getString("praised");
						groups = object.getString("groups");
						friends = object.getString("friends");
						
					} catch (JSONException e) {
						e.printStackTrace();
					}

					MyPreference.getInstance(WelcomeActivity.this).setUserId(uid);
					MyPreference.getInstance(WelcomeActivity.this).setPassword(password);
					MyPreference.getInstance(WelcomeActivity.this).setLoginName(account);
					MyPreference.getInstance(WelcomeActivity.this).setTelNumber(phone);
					MyPreference.getInstance(WelcomeActivity.this).setUserSex(sex);
					MyPreference.getInstance(WelcomeActivity.this).setUserName(nick);
					MyPreference.getInstance(WelcomeActivity.this).setUserbirth(birth);
					
					if(!favorite.contains(",")){
						MyPreference.getInstance(WelcomeActivity.this).setUserFact("0");
					}else{
						MyPreference.getInstance(WelcomeActivity.this).setUserFact(favorite);
					}
					
					MyPreference.getInstance(WelcomeActivity.this).setUserPraised(praised);
					MyPreference.getInstance(WelcomeActivity.this).setUserGroups(groups);
					MyPreference.getInstance(WelcomeActivity.this).setUserFrinds(friends);
					Intent intent = new Intent(WelcomeActivity.this, MainActivity.class); 
					
					
					/*uid；
					nick(⽤用户昵称)；
					birth(如2014-1-1)；
					sex(1男,0⼥女)；
					phone；
					money(余额)；
					favorite(收藏的活动ID数组)；
					praised(点赞过的动态ID数组)；
					groups(加⼊入的群组的map){gid，position(群内
					位置，1:群主,9:普通成员)，time(加群时间)}；
					friends(好友uid数组)；
					*/
					startActivity(intent);
					finish();
				}else{
					showCustomToast("账号或密码错误,请检查是否输入正确");
				}
			}
		}.execute();
	}
	
	
	
	
	private Map<String, String> setLoginParams(){
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> parms = new HashMap<String, Object>();
		
		
		
		String psw = "sq"+password;
		String pw = MD5Utils.md5(psw).substring(0, 27);
		String dd = pw+"ton";
		String mPassword = MD5Utils.md5(dd);
		
		//parms.put("id", 1000003);
		//parms.put("password", "9d2b201382a3a8cf1342c1be422594d5");
		/*登录密码= md5(substr(md5("sq".$password), 0,27).”ton”);
		如密码为51tonight则为: md5(“sq51tonight”)=“16d88e6ba9fbbbf04c5ca181ba6f16f7”，之后截取32位的前27位，得“16d88e6ba9fbbbf04c5ca181ba6”，然后该字
		符串再拼接ton之后再md5，即md5(“16d88e6ba9fbbbf04c5ca181ba6ton”)所得即为登录密码“7084f8139b3ecb942258288336f792c3”;
		其中md5采⽤用32位⼩小写*/
		
		/*parms.put("id",1000195);		
		parms.put("password", mPassword);*/
		parms.put("id",account);		
		parms.put("password", mPassword);
		ArrayList<Object> arry = new ArrayList<Object>();
		arry.add(0, "userLogin");
		arry.add(1, 0);
		arry.add(2, parms);
		String data0 = RC4Utils.RC4("mdwi5uh2p41nd4ae23qy4",
				JsonUtils.list2json(arry));
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
		map.put("d", encoded1);
		return map;
		
	}
	
	
	
private boolean dealResult(String result){
		
		boolean success=JsonResolveUtils.resolveuserResult(result);
		
		System.out.println("结果    " +success);
		
		return success;
	}
    private void loadActivity(){
    	
    	Intent intent = new Intent(WelcomeActivity.this,
    			MainActivity.class);
    	
    	if(TextUtils.isEmpty(password)||TextUtils.isEmpty(account)){
    		intent = new Intent(WelcomeActivity.this,
    				LoginActivity.class);	
    	}
    	
    	
    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    	startActivity(intent);
		WelcomeActivity.this.finish();
    }


	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void init() {
		
		/*account ="9000043";
	   password = "123456" ;*/
		account = MyPreference.getInstance(mContext).getLoginName();
		password = MyPreference.getInstance(mContext).getPassword();	
	}
	
	
	
	private void setupJazziness(TransitionEffect effect) {
		mJazzy = (JazzyViewPager) findViewById(R.id.jazzy_pager);
		mJazzy.setTransitionEffect(effect);
		mJazzy.setAdapter(new MainAdapter());
		mJazzy.setPageMargin(20);
		mJazzy.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				if(arg0==4){
					
					but_start.setVisibility(View.VISIBLE);
					final ScaleAnimation scaleAnimation=new ScaleAnimation(0.000001f, 1f, 0.000001f, 1f, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
			        scaleAnimation.setDuration(1000);
			        scaleAnimation.setStartOffset(500);
			        but_start.startAnimation(scaleAnimation);
					but_start.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent =new Intent(WelcomeActivity.this,LoginActivity.class);						
							startActivity(intent);
							MyPreference.getInstance(mContext).setIsFirst(false);
							finish();
							
						}
					});
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	
	private int [] imageArray = new int []{R.drawable.welcome_a,R.drawable.welcome_b,R.drawable.welcome_c,R.drawable.welcome_d,R.drawable.welcome_e};
	private class MainAdapter extends PagerAdapter {
		
		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			
			View v =View.inflate(WelcomeActivity.this, R.layout.item_welcome, null);			
			ImageView iv =(ImageView) v.findViewById(R.id.iv_welcome);
			iv.setBackgroundResource(imageArray[position]);
			but_start = (Button) v.findViewById(R.id.but_start);
			
			
			container.addView(v);
			mJazzy.setObjectForPosition(v, position);
			return v;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object obj) {
			container.removeView(mJazzy.findViewFromObject(position));
		}
		@Override
		public int getCount() {
			return 5;
		}
		@Override
		public boolean isViewFromObject(View view, Object obj) {
			if (view instanceof OutlineContainer) {
				return ((OutlineContainer) view).getChildAt(0) == obj;
			} else {
				return view == obj;
			}
		}		
	}
}
