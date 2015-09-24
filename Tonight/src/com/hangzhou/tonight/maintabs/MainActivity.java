package com.hangzhou.tonight.maintabs;

import java.lang.reflect.Field;

import android.app.TabActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.hangzhou.tonight.LoginActivity;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.activity.MerchantsHomeListActivity;
import com.hangzhou.tonight.activity.PromotionActivity;
import com.hangzhou.tonight.base.BaseApplication;
import com.hangzhou.tonight.manager.XmppConnectionManager;
import com.hangzhou.tonight.model.LoginConfig;
import com.hangzhou.tonight.module.individual.activity.IndividualActivity;
import com.hangzhou.tonight.module.message.activity.MessageMainActivity;
import com.hangzhou.tonight.module.social.activity.FindActivity;
import com.hangzhou.tonight.service.IConnectionStatusCallback;
import com.hangzhou.tonight.service.XXService;
import com.hangzhou.tonight.util.L;
import com.hangzhou.tonight.util.LoginTask2;
import com.hangzhou.tonight.util.PreferenceConstants;
import com.hangzhou.tonight.util.PreferenceUtils;
import com.hangzhou.tonight.util.T;
import com.hangzhou.tonight.util.XMPPHelper;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements IConnectionStatusCallback{

	private XXService mXxService;
	
	private TabHost mTabHost;
	private LoginConfig loginConfig;

	private FrameLayout layout1, layout2, layout3, layout4,layout5;

	private ImageView tab_home,  tab_bang,tab_act, tab_message,tab_persone;
	private TextView tab_home_text_click, tab_home_text, tab_bang_text,
			tab_bang_text_click;
	
	
	ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mXxService = ((XXService.XXBinder) service).getService();
			mXxService.registerConnectionStatusCallback(MainActivity.this);
			// 开始连接xmpp服务器
			if (!mXxService.isAuthenticated()) {
				String usr = PreferenceUtils.getPrefString(MainActivity.this,
						PreferenceConstants.ACCOUNT, "");
				String password = PreferenceUtils.getPrefString(
						MainActivity.this, PreferenceConstants.PASSWORD, "");
				mXxService.Login(usr, password);
				// mTitleNameView.setText(R.string.login_prompt_msg);
				// setStatusImage(false);
				// mTitleProgressBar.setVisibility(View.VISIBLE);
			} else {
				/*mTitleNameView.setText(XMPPHelper
						.splitJidAndServer(PreferenceUtils.getPrefString(
								MainActivity.this, PreferenceConstants.ACCOUNT,
								"")));
				setStatusImage(true);
				mTitleProgressBar.setVisibility(View.GONE);*/
				XMPPHelper.splitJidAndServer(PreferenceUtils.getPrefString(
						MainActivity.this, PreferenceConstants.ACCOUNT,
						""));
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mXxService.unRegisterConnectionStatusCallback();
			mXxService = null;
		}

	};

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//startService(new Intent(MainActivity.this, XXService.class));
		setContentView(R.layout.activity_main);
		initViews();
		/*BaseApplication application=BaseApplication.getInstance();	
		startService();
		
	    loginConfig = application.getLoginConfig();
	   // XmppConnectionManager.getInstance().init();
		LoginTask2 loginTask = new LoginTask2(MainActivity.this, loginConfig);
		loginTask.execute();*/
			
		init();	
	//	initTabs();
	}
	
	
	
	private void init(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				XmppConnectionManager.getInstance().init();
				BaseApplication application=BaseApplication.getInstance();	
				application.startService();					
			    loginConfig = application.getLoginConfig();
				LoginTask2 loginTask = new LoginTask2(MainActivity.this, loginConfig);
				loginTask.execute();			
			}
		}).start();
	}
	
	
	
	private void initViews() {
		mTabHost = getTabHost();
		Intent intent1 = new Intent();
		intent1.setClass(MainActivity.this, MerchantsHomeListActivity.class);
		
		Intent intent2 = new Intent();
		intent2.setClass(MainActivity.this, FindActivity.class);
		
		Intent intent3 = new Intent();
		intent3.setClass(MainActivity.this, PromotionActivity.class);
		
		Intent intent4 = new Intent();
		intent4.setClass(MainActivity.this, MessageMainActivity.class);
		
		Intent intent5 = new Intent();
		intent5.setClass(MainActivity.this, IndividualActivity.class);
		
		layout1 = (FrameLayout) findViewById(R.id.frame_home);//首页
		layout2 = (FrameLayout) findViewById(R.id.frame_forum);//发现
		layout3 = (FrameLayout) findViewById(R.id.frame_core);//活动
		layout4 = (FrameLayout) findViewById(R.id.frame_message);//消息
		layout5 = (FrameLayout) findViewById(R.id.frame_persional);//个人

		layout1.setOnClickListener(l);
		layout2.setOnClickListener(l);
		layout3.setOnClickListener(l);
		layout4.setOnClickListener(l);
		layout5.setOnClickListener(l);

		tab_home = (ImageView) findViewById(R.id.tab_home_click);
		tab_bang = (ImageView) findViewById(R.id.tab_bang);
		tab_act = (ImageView) findViewById(R.id.tab_act);
		tab_message = (ImageView) findViewById(R.id.tab_message);
		tab_persone = (ImageView) findViewById(R.id.tab_person);
		
		
		//http://www.cnblogs.com/liuqxFuture/p/3343675.html
		//http://blog.csdn.net/icewst/article/details/8084691
		 	try{
	           Field current = mTabHost.getClass().getDeclaredField("mCurrentTab");
	           current.setAccessible(true);
	           current.setInt(mTabHost, 0);
	       }catch (Exception e){
	           e.printStackTrace();
	       }
		 	mTabHost.addTab(mTabHost.newTabSpec("1").setIndicator("1").setContent(intent1));
		 	mTabHost.addTab(mTabHost.newTabSpec("2").setIndicator("2").setContent(intent2));
		 	mTabHost.addTab(mTabHost.newTabSpec("3").setIndicator("3").setContent(intent3));
		 	mTabHost.addTab(mTabHost.newTabSpec("4").setIndicator("4").setContent(intent4));
		 	mTabHost.addTab(mTabHost.newTabSpec("5").setIndicator("5").setContent(intent5));
	       //mCurrentTab恢复到－1状态
	       try{
	           Field current = mTabHost.getClass().getDeclaredField("mCurrentTab");
	           current.setAccessible(true);
	           current.set(mTabHost, -1);
	       }catch (Exception e){ e.printStackTrace();  }
		
	       mTabHost.setCurrentTab(2);//默认中间的
	}
	
	OnClickListener l = new OnClickListener() {

		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (arg0 == layout1) {
				mTabHost.setCurrentTabByTag("1");
				tab_home.setImageResource(R.drawable.tab_home_click);
				tab_bang.setImageResource(R.drawable.find);
				tab_act.setImageResource(R.drawable.tab_ask);
				tab_message.setImageResource(R.drawable.tab_message);
				tab_persone.setImageResource(R.drawable.tab_person);

			} else if (arg0 == layout2) {

				tab_act.setImageResource(R.drawable.tab_ask);
				tab_home.setImageResource(R.drawable.tab_home);
				tab_bang.setImageResource(R.drawable.findsel);
				tab_message.setImageResource(R.drawable.tab_message);
				tab_persone.setImageResource(R.drawable.tab_person);

				mTabHost.setCurrentTabByTag("2");

			} else if(arg0 == layout3){
				tab_act.setImageResource(R.drawable.tab_ask_click);
				tab_home.setImageResource(R.drawable.tab_home);
				tab_bang.setImageResource(R.drawable.find);
				tab_message.setImageResource(R.drawable.tab_message);
				tab_persone.setImageResource(R.drawable.tab_person);
				mTabHost.setCurrentTabByTag("3");
			} else if(arg0 == layout4){
				tab_act.setImageResource(R.drawable.tab_ask);
				tab_home.setImageResource(R.drawable.tab_home);
				tab_bang.setImageResource(R.drawable.find);
				tab_message.setImageResource(R.drawable.tab_message_sel);
				tab_persone.setImageResource(R.drawable.tab_person);
				mTabHost.setCurrentTabByTag("4");
			}else if(arg0 == layout5){
				mTabHost.setCurrentTabByTag("5");
				tab_act.setImageResource(R.drawable.tab_ask);
				tab_home.setImageResource(R.drawable.tab_home);
				tab_bang.setImageResource(R.drawable.find);
				tab_message.setImageResource(R.drawable.tab_message);
				tab_persone.setImageResource(R.drawable.tab_person_click);
			}
		}
	};

	/*private void initTabs() {
		LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

		View nearbyView = inflater.inflate(
				R.layout.common_bottombar_tab_business, null);
		TabHost.TabSpec nearbyTabSpec = mTabHost.newTabSpec(
				BusinessActivity.class.getName()).setIndicator(nearbyView);
		nearbyTabSpec.setContent(new Intent(MainActivity.this,
				BusinessActivity.class));
		mTabHost.addTab(nearbyTabSpec);
		
		View sessionListView = inflater.inflate(
				R.layout.common_bottombar_tab_chat, null);
		TabHost.TabSpec sessionListTabSpec = mTabHost.newTabSpec(
				SessionListActivity.class.getName()).setIndicator(
				sessionListView);
		sessionListTabSpec.setContent(new Intent(MainActivity.this,
				SessionListActivity.class));
		mTabHost.addTab(sessionListTabSpec);		

		View nearbyFeedsView = inflater.inflate(
				R.layout.common_bottombar_tab_promotion, null);
		TabHost.TabSpec nearbyFeedsTabSpec = mTabHost.newTabSpec(
				PromotionActivity.class.getName()).setIndicator(
				nearbyFeedsView);
		nearbyFeedsTabSpec.setContent(new Intent(MainActivity.this,
				PromotionActivity.class));
		mTabHost.addTab(nearbyFeedsTabSpec);
		

		View contactView = inflater.inflate(
				R.layout.common_bottombar_tab_discover, null);
		TabHost.TabSpec contactTabSpec = mTabHost.newTabSpec(
				DiscoverActivity.class.getName()).setIndicator(contactView);
		contactTabSpec.setContent(new Intent(MainActivity.this,
				DiscoverActivity.class));
		mTabHost.addTab(contactTabSpec);

		View userSettingView = inflater.inflate(
				R.layout.common_bottombar_tab_profile, null);
		TabHost.TabSpec userSettingTabSpec = mTabHost.newTabSpec(
				ChatActivity.class.getName()).setIndicator(
				userSettingView);
		userSettingTabSpec.setContent(new Intent(MainActivity.this,
				UserSettingActivity.class));
		mTabHost.addTab(userSettingTabSpec);
		
	}*/
	
	
	@Override
	protected void onResume() {
		super.onResume();
		/*LoginTask2 loginTask = new LoginTask2(MainActivity.this, loginConfig);
		loginTask.execute();*/
	}
	
	
	/**
	 * 连续按两次返回键就退出
	 */
	private long firstTime;

	@Override
	public void onBackPressed() {
		if (System.currentTimeMillis() - firstTime < 3000) {
			finish();
			//T.showShort(this, R.string.press_again_backrun);
		} else {
			firstTime = System.currentTimeMillis();
			T.showShort(this, R.string.press_again_backrun);
		}
	}

	
	@Override
	protected void onPause() {
		super.onPause();
		unbindXMPPService();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
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
		bindService(new Intent(MainActivity.this, XXService.class),
				mServiceConnection, Context.BIND_AUTO_CREATE
						+ Context.BIND_DEBUG_UNBIND);
	}

	@Override
	public void connectionStatusChanged(int connectedState, String reason) {
		switch (connectedState) {
		case XXService.CONNECTED:
			/*mTitleNameView.setText(XMPPHelper.splitJidAndServer(PreferenceUtils
					.getPrefString(MainActivity.this,
							PreferenceConstants.ACCOUNT, "")));
			mTitleProgressBar.setVisibility(View.GONE);
			// mTitleStatusView.setVisibility(View.GONE);
			setStatusImage(true);*/
			T.showLong(this, "连接成功");
			break;
		case XXService.CONNECTING:
			/*mTitleNameView.setText(R.string.login_prompt_msg);
			mTitleProgressBar.setVisibility(View.VISIBLE);
			mTitleStatusView.setVisibility(View.GONE);*/
			
			break;
		case XXService.DISCONNECTED:
			/*mTitleNameView.setText(R.string.login_prompt_no);
			mTitleProgressBar.setVisibility(View.GONE);
			mTitleStatusView.setVisibility(View.GONE);
			T.showLong(this, reason);*/
			break;

		default:
			break;
		}
	}

	
	
}
