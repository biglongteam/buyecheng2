package com.hangzhou.tonight;


import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;






import org.json.JSONException;
import org.json.JSONObject;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alipay.android.app.util.Utils;
import com.hangzhou.tonight.activity.EditPassActivity;
import com.hangzhou.tonight.base.BaseActivity;
import com.hangzhou.tonight.maintabs.MainActivity;
import com.hangzhou.tonight.service.IConnectionStatusCallback;
import com.hangzhou.tonight.service.XXService;
import com.hangzhou.tonight.util.Base64Utils;
import com.hangzhou.tonight.util.FileUtils;
import com.hangzhou.tonight.util.HttpRequest;
import com.hangzhou.tonight.util.JsonResolveUtils;
import com.hangzhou.tonight.util.JsonUtils;
import com.hangzhou.tonight.util.L;
import com.hangzhou.tonight.util.MD5Utils;
import com.hangzhou.tonight.util.MyPreference;
import com.hangzhou.tonight.util.PreferenceConstants;
import com.hangzhou.tonight.util.PreferenceUtils;
import com.hangzhou.tonight.util.RC4Utils;
import com.hangzhou.tonight.util.ShowUtils;
import com.hangzhou.tonight.util.T;
import com.hangzhou.tonight.view.HandyTextView;
import com.hangzhou.tonight.view.HeaderLayout;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

public class LoginActivity extends BaseActivity implements OnClickListener,
		IConnectionStatusCallback {

	public static final String LOGIN_ACTION = "com.way.action.LOGIN";
	private static final int LOGIN_OUT_TIME = 0;

	private HeaderLayout mHeaderLayout;
	private Button mLoginBtn;
	private HandyTextView mRegisterBtn,tv_edit_pass;
	private EditText mAccountEt;
	private EditText mPasswordEt;

	private ImageView ivSina,ivQq,ivWeant;
	private XXService mXxService;
	private String mAccount;
	private String mPassword;
	private FileOutputStream mOutputStream;
	private URL mUrl;
	private File mFile;
	private Context mContext;
	 // 整个平台的Controller, 负责管理整个SDK的配置、操作等处理
    private UMSocialService mController = UMServiceFactory
            .getUMSocialService("com.umeng.login");
	ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mXxService = ((XXService.XXBinder) service).getService();
			mXxService.registerConnectionStatusCallback(LoginActivity.this);
			// 开始连接xmpp服务器
			if (!mXxService.isAuthenticated()) {
				String usr = PreferenceUtils.getPrefString(LoginActivity.this,
						PreferenceConstants.ACCOUNT, "");
				String password = PreferenceUtils.getPrefString(
						LoginActivity.this, PreferenceConstants.PASSWORD,
						"");
				//mXxService.Login(usr, password);
				// mTitleNameView.setText(R.string.login_prompt_msg);
				// setStatusImage(false);
				// mTitleProgressBar.setVisibility(View.VISIBLE);
			} else {
				/*
				 * mTitleNameView.setText(XMPPHelper
				 * .splitJidAndServer(PreferenceUtils.getPrefString(
				 * MainActivity.this, PreferenceConstants.ACCOUNT, "")));
				 * setStatusImage(true);
				 * mTitleProgressBar.setVisibility(View.GONE);
				 */
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mXxService.unRegisterConnectionStatusCallback();
			mXxService = null;
		}

	};
	
	
	
	@Override
	public void connectionStatusChanged(int connectedState, String reason) {
		if (connectedState == XXService.CONNECTED) {
			save2Preferences();
			//startActivity(new Intent(this, MainActivity.class));
			//finish();
		} else if (connectedState == XXService.DISCONNECTED)
			T.showLong(LoginActivity.this, getString(R.string.request_failed)
					+ reason);
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		// startService(new Intent(LoginActivity.this, XXService.class));
		//bindXMPPService();
		mContext = this;
		initViews();
		init();
		initEvents();
		
		
		 //参数1为当前Activity， 参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1104785217","D1R5KR1UJUOxRQrB");
		qqSsoHandler.addToSocialSDK();
		
		UMWXHandler wxHandler =new UMWXHandler(this, "wx17271e32d3c796f0", "396caedd6352a115a83de18ef48045ab");
		wxHandler.addToSocialSDK();
		
		//设置新浪SSO handler
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		mController.getConfig().setSinaCallbackUrl("https://api.weibo.com/oauth2/default.html");

		SnsPostListener mSnsPostListener  = new SnsPostListener() {

	        @Override
	    public void onStart() {

	    }

	    @Override
	    public void onComplete(SHARE_MEDIA platform, int stCode,
	        SocializeEntity entity) {
	      if (stCode == 200) {
//	        Toast.makeText(LoginActivity.this, "分享成功", Toast.LENGTH_SHORT)
//	            .show();
	      } else {
//	        Toast.makeText(LoginActivity.this,
//	            "分享失败 : error code : " + stCode, Toast.LENGTH_SHORT)
//	            .show();
	      }
	    }
	  };
	  mController.registerListener(mSnsPostListener);
		
	}

	private void unbindXMPPService() {
		try {
			unbindService(mServiceConnection);
			L.i(LoginActivity.class, "[SERVICE] Unbind");
		} catch (IllegalArgumentException e) {
			L.e(LoginActivity.class, "Service wasn't bound!");
		}
	}

	private void bindXMPPService() {
		L.i(LoginActivity.class, "[SERVICE] Unbind");
		Intent mServiceIntent = new Intent(this, XXService.class);
		mServiceIntent.setAction(LOGIN_ACTION);
		bindService(mServiceIntent, mServiceConnection,
				Context.BIND_AUTO_CREATE + Context.BIND_DEBUG_UNBIND);
	}

	@Override
	protected void initViews() {
		
		mAccountEt = (EditText) findViewById(R.id.login_et_account);
		mPasswordEt = (EditText) findViewById(R.id.login_et_pwd);
		ShowUtils.dimssHint(mAccountEt);
		ShowUtils.dimssHint(mPasswordEt);
		mLoginBtn = (Button) findViewById(R.id.login_btn_login);
		mRegisterBtn = (HandyTextView) findViewById(R.id.login_btn_register);
		tv_edit_pass = (HandyTextView) findViewById(R.id.tv_edit_pass);
		ivQq = (ImageView) findViewById(R.id.im_qq);
		ivWeant = (ImageView) findViewById(R.id.im_wechat);
		ivSina = (ImageView) findViewById(R.id.im_sina);

	}

	@Override
	protected void init() {
		/*mAccount=MyPreference.getInstance(LoginActivity.this).getLoginName();
		mPassword=MyPreference.getInstance(LoginActivity.this).getPassword();;
		mAccount="1000003";
		mPassword="9d2b201382a3a8cf1342c1be422594d5";
		if (!TextUtils.isEmpty(mAccount))
			mAccountEt.setText(mAccount);
		if (!TextUtils.isEmpty(mPassword))
			mPasswordEt.setText(mPassword);*/
	}

	@Override
	protected void initEvents() {
		mLoginBtn.setOnClickListener(LoginActivity.this);
		mRegisterBtn.setOnClickListener(LoginActivity.this);
		tv_edit_pass.setOnClickListener(LoginActivity.this);
		ivQq.setOnClickListener(this);
		ivWeant.setOnClickListener(this);
		ivSina.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindXMPPService();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn_login:
			
			login();
			/*if(validateAccount()){
				
			}*/
			
			break;
		case R.id.login_btn_register:
			Intent intent2 = new Intent(LoginActivity.this,
					RegisterActivity.class);
			startActivity(intent2);

			/*
			 * Intent intent2=new Intent(Intent.ACTION_SEND);
			 * intent2.setType("image/*");
			 * intent2.putExtra(Intent.EXTRA_SUBJECT, "分享");
			 * intent2.putExtra(Intent.EXTRA_TEXT, "终于可以了!!!");
			 * intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 * startActivity(Intent.createChooser(intent2, getTitle()));
			 */
			// shareMsg("分享", "试试分享功能", "分享吧", "mnt/sdcard/1.jpg");
			break;
			
		case R.id.tv_edit_pass:
			Intent intent3 = new Intent(LoginActivity.this,
					EditPassActivity.class);
			startActivity(intent3);

			/*
			 * Intent intent2=new Intent(Intent.ACTION_SEND);
			 * intent2.setType("image/*");
			 * intent2.putExtra(Intent.EXTRA_SUBJECT, "分享");
			 * intent2.putExtra(Intent.EXTRA_TEXT, "终于可以了!!!");
			 * intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 * startActivity(Intent.createChooser(intent2, getTitle()));
			 */
			// shareMsg("分享", "试试分享功能", "分享吧", "mnt/sdcard/1.jpg");
			break;
			
		case R.id.im_sina:
			Toast.makeText(this, "暂未开通", 1000).show();
			login(SHARE_MEDIA.SINA,2);
			break;
		case R.id.im_qq:
			Toast.makeText(this, "暂未开通", 1000).show();
			login(SHARE_MEDIA.QQ,3);
			break;
		case R.id.im_wechat:
			//Toast.makeText(this, "暂未开通", 1000).show();
			login(SHARE_MEDIA.WEIXIN,1);
			break;

		default:
			break;
		}
	}
	
	
	
	
	
	/**
     * 授权。如果授权成功，则获取用户信息</br>
	 * @param i 
     */
    private void login(final SHARE_MEDIA platform, final int i) {
    	
    	mController.doOauthVerify(this, platform,new UMAuthListener() {
            private String openid;
			@Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
            	 Toast.makeText(LoginActivity.this, "授权错误",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onComplete(Bundle value, SHARE_MEDIA platform) {
            	
            	Toast.makeText(LoginActivity.this, "授权成功",Toast.LENGTH_SHORT).show();
            	
                if (value != null && !TextUtils.isEmpty(value.getString("uid"))) {
                    if(i==3){
                    	//此时是qq登录，需要从value中获得openid作为唯一标记
                    	openid = value.getString("openid");
                    	// Toast.makeText(LoginActivity.this, "授权成功...openid"+openid,Toast.LENGTH_SHORT).show();
                    }
                   // Toast.makeText(LoginActivity.this, "授权成功",Toast.LENGTH_SHORT).show();
                    getUserInfo(platform,openid,i);
                    
                } else {
                    Toast.makeText(LoginActivity.this, "授权失败",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancel(SHARE_MEDIA platform) {
            	 Toast.makeText(LoginActivity.this, "授权取消",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onStart(SHARE_MEDIA platform) {
            	 Toast.makeText(LoginActivity.this, "授权开始",Toast.LENGTH_SHORT).show();
            }
    	});
    }
    
    /**
     * 获取授权平台的用户信息</br>
     * @param openid 
     * @param i 
     */
    private void getUserInfo(SHARE_MEDIA platform, final String openid, final int i) {
    	mController.getPlatformInfo(this, platform, new UMDataListener() {
    		String onlyId;
    		String head_img;
    		String screen_name;
    		String sex;
    	    @Override
    	    public void onStart() {
    	        Toast.makeText(LoginActivity.this, "获取平台数据开始", Toast.LENGTH_SHORT).show();
    	    }                                              
    	    @Override
    	        public void onComplete(int status, Map<String, Object> info) {
    	            if(status == 200 && info != null){
    	                StringBuilder sb = new StringBuilder();
    	                Set<String> keys = info.keySet();
    	                for(String key : keys){
    	                   sb.append(key+"="+info.get(key).toString()+"\r\n");
    	                }
    	                if(i==1){
    	                	//微信登录，获得unionid
    	                	onlyId=info.get("unionid").toString();
    	                	head_img=info.get("headimgurl").toString();
    	                	screen_name=info.get("nickname").toString();
    	                	sex=info.get("sex").toString();
    	                	//Toast.makeText(LoginActivity.this, "登录...onlyId"+onlyId,Toast.LENGTH_SHORT).show();
    	                	isBind(onlyId,1);
    	                	 // isBund(String.valueOf(2),onlyId,head_img,screen_name,sex);
    	                }else if(i==2){
    	                	//新浪微博登录，获得uid wb_uid；access_token(
    	                	
    	                	onlyId=info.get("uid").toString();
    	                	head_img=info.get("profile_image_url").toString();
    	                	screen_name=info.get("screen_name").toString();
    	                	sex=info.get("gender").toString();
    	                	isBind(onlyId,2);
    	                	
    	                	
    	                	//Toast.makeText(LoginActivity.this, "登录...onlyId"+onlyId,Toast.LENGTH_SHORT).show();
    	                	//isBund(String.valueOf(3),onlyId,head_img,screen_name,sex);
    	                }else{
    	                	//qq登录，获得openid
    	                	onlyId=openid;
    	                	head_img=info.get("profile_image_url").toString();
    	                	screen_name=info.get("screen_name").toString();
    	                	sex=info.get("gender").toString();
    	                	if("男".equals(sex)){
    	                		sex="1";
    	                	}else{
    	                		sex="0";
    	                	}
    	                	isBind(onlyId,3);
    	                	//Toast.makeText(LoginActivity.this, "登录...onlyId"+onlyId,Toast.LENGTH_SHORT).show();
    	                	//isBund(String.valueOf(1),onlyId,head_img,screen_name,sex);
    	                }
    	                
    	               /* String path =Environment.getExternalStorageDirectory().getAbsolutePath();
    	                File file =new File(path, "abcsina.txt");
						try {
							OutputStream os =new FileOutputStream(file);
							os.write(sb.toString().getBytes("utf-8"));
							os.flush();
							os.close();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
						
    	              
						
    	                Log.d("TestData",sb.toString());
    	               
    	            }else{
    	               Log.d("TestData","发生错误："+status);
    	           }
    	        }
    	});
    }

    
    /*第三方登陆判断是否绑定过账号
	 *  status：登陆方式 1QQ 2 微信  3微博
		   uid:第三方登陆返回的唯一标识

	 * */
   /* public void isBund(final String i,final String uid,final String head_img,final String nickname,final String sex){
    	String str="http://112.126.72.250/ut_app/index.php?m=User&a=tpos_login";
    	RequestParams params =new RequestParams();
    	params.put("status", i);
    	params.put("uid", uid);
    //	Utils.showToast(LoginActivity.this, i+"onSuccess..."+uid);
    	HttpUtils.post(str, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				Utils.showToast(LoginActivity.this, "onSuccess...");
				Gson gson =new Gson();
				LoginBean loginBean = gson.fromJson(arg2, LoginBean.class);
				if(loginBean!=null&loginBean.code.equals("0")){
					//登录成功，需要绑定账号
					Intent intent =new Intent(LoginActivity.this,ThirdLoginActivity.class);
					intent.putExtra("uid", uid);
					intent.putExtra("status",i);
					intent.putExtra("head_img", head_img);
					intent.putExtra("nickname", nickname);
					intent.putExtra("sex", sex);
					//YoutiApplication.getInstance().myPreference.setHeadImgPath(head_img);
					startActivity(intent);
					finish();
					
				}else if(loginBean.code.equals("1")){
					//登录成功，已经绑定账号
					//System.out.println();
					String userId = loginBean.list.user_id;
					((YoutiApplication) getApplication()).myPreference.setUserId(userId);
					((YoutiApplication) getApplication()).myPreference.setHasLogin(true);
					//Intent intent =new Intent(LoginActivity.this,PersonCenterActivity.class);
					//startActivity(intent);
					requestData(userId);
					
				}else{
					Utils.showToast(LoginActivity.this, "第三方登录失败");
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				Utils.showToast(LoginActivity.this, "网络连接失败");
			}
		});
    }*/
    
    
    private void isBind(final String uid,final int type) {
		/*
		 * if ((!validateAccount()) || (!validatePwd())) { return; }
		 */

		new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadingDialog("正在登录,请稍后...");
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
				/*
				 * if (result) { Intent intent = new Intent(LoginActivity.this,
				 * MainActivity.class); startActivity(intent); finish(); } else
				 * { showCustomToast("账号或密码错误,请检查是否输入正确"); }
				 */
				System.out.println("用户登录结果：      " +result);
				boolean success=dealResult(result);
				if(success){
					JsonResolveUtils.resolveuserLogin(result);
					Intent intent = new Intent(LoginActivity.this, MainActivity.class); 
					startActivity(intent);
					finish();
				}else{
					showCustomToast("账号或密码错误,请检查是否输入正确");
				}
			}
		}.execute();
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
				showLoadingDialog("正在登录,请稍后...");
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
				/*
				 * if (result) { Intent intent = new Intent(LoginActivity.this,
				 * MainActivity.class); startActivity(intent); finish(); } else
				 * { showCustomToast("账号或密码错误,请检查是否输入正确"); }
				 */
				System.out.println("用户登录结果：      " +result);
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

					MyPreference.getInstance(LoginActivity.this).setUserId(uid);
					MyPreference.getInstance(LoginActivity.this).setPassword(mPassword);
					MyPreference.getInstance(LoginActivity.this).setLoginName(mAccount);;
					MyPreference.getInstance(LoginActivity.this).setTelNumber(phone);
					MyPreference.getInstance(LoginActivity.this).setUserSex(sex);
					MyPreference.getInstance(LoginActivity.this).setUserName(nick);
					MyPreference.getInstance(LoginActivity.this).setUserbirth(birth);
					
					if(!favorite.contains(",")){
						MyPreference.getInstance(LoginActivity.this).setUserFact("0");
					}else{
						MyPreference.getInstance(LoginActivity.this).setUserFact(favorite);
					}
					
					MyPreference.getInstance(LoginActivity.this).setUserPraised(praised);
					MyPreference.getInstance(LoginActivity.this).setUserGroups(groups);
					MyPreference.getInstance(LoginActivity.this).setUserFrinds(friends);
					Intent intent = new Intent(LoginActivity.this, MainActivity.class); 
					
					
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
	
	
	private Map<String, String> setParams(){
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> parms = new HashMap<String, Object>();
		mAccount = mAccountEt.getText().toString().trim();
		//mPassword = mPasswordEt.getText().toString().trim();
		/*mAccount="1000003";
		mPassword="9d2b201382a3a8cf1342c1be422594d5";*/
		String psw = "sq"+mPasswordEt.getText().toString().trim();
		String pw = MD5Utils.md5(psw).substring(0, 27);
		String dd = pw+"ton";
		mPassword = MD5Utils.md5(dd);
		
		//parms.put("id", 1000003);
		//parms.put("password", "9d2b201382a3a8cf1342c1be422594d5");
		
		/*登录密码= md5(substr(md5("sq".$password), 0,27).”ton”);
		如密码为51tonight则为: md5(“sq51tonight”)=“16d88e6ba9fbbbf04c5ca181ba6f16f7”，之后截取32位的前27位，得“16d88e6ba9fbbbf04c5ca181ba6”，然后该字
		符串再拼接ton之后再md5，即md5(“16d88e6ba9fbbbf04c5ca181ba6ton”)所得即为登录密码“7084f8139b3ecb942258288336f792c3”;
		其中md5采⽤用32位⼩小写*/
		parms.put("id", mAccount);		
		parms.put("password", mPassword);
		ArrayList<Object> arry = new ArrayList<Object>();
		arry.add(0, "userLogin");
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
	
	
	private boolean dealResult(String result){
		
		boolean success=JsonResolveUtils.resolveuserResult(result);
		
		System.out.println("结果    " +success);
		
		return success;
	}

	private boolean validateAccount() {
		mAccount = null;
		if (FileUtils.isNull(mAccountEt)) {
			showCustomToast("请输入您的手机号码");
			mAccountEt.requestFocus();
			return false;
		}
		String account = mAccountEt.getText().toString().trim();
		if (FileUtils.matchPhone(account)) {
			if (account.length() < 3) {
				showCustomToast("账号格式不正确");
				mAccountEt.requestFocus();
				return false;
			}
			if (Pattern.compile("(\\d{3,})|(\\+\\d{3,})").matcher(account)
					.matches()) {
				mAccount = account;
				return true;
			}
		}
		/*
		 * if (matchEmail(account)) { mAccount = account; return true; }
		 */
		/*
		 * if (matchMoMo(account)) { mAccount = account; return true; }
		 */
		showCustomToast("账号格式不正确");
		mAccountEt.requestFocus();
		return false;
	}

	private boolean validatePwd() {
		mPassword = null;
		String pwd = mPasswordEt.getText().toString().trim();
		if (pwd.length() < 4) {
			showCustomToast("密码不能小于4位");
			mPasswordEt.requestFocus();
			return false;
		}
		/*
		 * if (pwd.length() > 16) { showCustomToast("密码不能大于16位");
		 * mPasswordEt.requestFocus(); return false; }
		 */
		mPassword = MD5Utils.Md5(pwd);
		return true;
	}

	public void shareMsg(String activityTitle, String msgTitle, String msgText,
			String imgPath) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		if (imgPath == null || imgPath.equals("")) {
			intent.setType("text/plain"); // 纯文本
		} else {
			File f = new File(imgPath);
			if (f != null && f.exists() && f.isFile()) {
				intent.setType("image/jpg");
				Uri u = Uri.fromFile(f);
				intent.putExtra(Intent.EXTRA_STREAM, u);
			}
		}
		intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
		intent.putExtra(Intent.EXTRA_TEXT, msgText);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(Intent.createChooser(intent, activityTitle));
	}

	
	private void save2Preferences() {
		PreferenceUtils.setPrefString(this, PreferenceConstants.ACCOUNT,
				mAccount);
		PreferenceUtils.setPrefString(this, PreferenceConstants.PASSWORD,
				mPassword);

	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    /**使用SSO授权必须添加如下代码 */  
	    UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
	    if(ssoHandler != null){
	       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
	    }
	}
	

}
