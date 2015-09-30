package com.hangzhou.tonight.util;

import java.util.Collection;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.XMPPError;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.hangzhou.tonight.IActivitySupport;
import com.hangzhou.tonight.LoginActivity;
import com.hangzhou.tonight.MainActivity1;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.base.BaseApplication;
import com.hangzhou.tonight.comm.Constant;
import com.hangzhou.tonight.manager.OfflineMsgManager;
import com.hangzhou.tonight.manager.XmppConnectionManager;
import com.hangzhou.tonight.model.LoginConfig;

/**
 * 
 * 登录异步任务.
 * 
 * @author shimiso
 */
public class LoginTask2 extends AsyncTask<String, Integer, Integer> {
	//private ProgressDialog pd;
	private Context context;
	private Activity activitySupport;
	private LoginConfig loginConfig;

	public LoginTask2(Activity activitySupport, LoginConfig loginConfig) {
		this.activitySupport = activitySupport;
		this.loginConfig = loginConfig;
		this.context = activitySupport.getApplicationContext();
	}

	@Override
	protected void onPreExecute() {
	/*	pd.setTitle("请稍等");
		pd.setMessage("正在登录...");
		pd.show();*/
		super.onPreExecute();
	}

	@Override
	protected Integer doInBackground(String... params) {
		return login();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
	}

	@Override
	protected void onPostExecute(Integer result) {
		//pd.dismiss();
		switch (result) {
		case Constant.LOGIN_SECCESS: // 登录成功
			Toast.makeText(context, "登陆成功", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			if (loginConfig.isFirstStart()) {// 如果是首次启动
				//intent.setClass(context, GuideViewActivity.class);
				//loginConfig.setFirstStart(false);
			} else {
				//intent.setClass(context, MainActivity1.class);
			}
			//intent.setClass(context, MainActivity1.class);
			//context.startActivity(intent);
			break;
		case Constant.LOGIN_ERROR_ACCOUNT_PASS:// 账户或者密码错误
			Toast.makeText(
					context,
					context.getResources().getString(
							R.string.message_invalid_username_password),
					Toast.LENGTH_SHORT).show();
			break;
		case Constant.SERVER_UNAVAILABLE:// 服务器连接失败
			Toast.makeText(
					context,
					context.getResources().getString(
							R.string.message_server_unavailable),
					Toast.LENGTH_SHORT).show();
			break;
		case Constant.LOGIN_ERROR:// 未知异常
			Toast.makeText(
					context,
					context.getResources().getString(
							R.string.unrecoverable_error), Toast.LENGTH_SHORT)
					.show();
			break;
		}
		super.onPostExecute(result);
	}

	// 登录
	private Integer login() {
		String username = MyPreference.getInstance(activitySupport).getUserId();
		String password = MyPreference.getInstance(activitySupport).getPassword();
		System.out.println("------"+username);
		System.out.println("------"+password);
		//String username = "1000000";
		//String password="ee3618e08444dba8ebd6e4dbd706b0c9";
		try {
			XMPPConnection connection = XmppConnectionManager.getInstance()
					.getConnection();
				if (connection.isConnected()) {// 首先判断是否还连接着服务器，需要先断开
						connection.disconnect();
				}
				System.out.println("-----1"+connection);
				
				//SmackConfiguration.setPacketReplyTimeout(30000);// 设置超时时间
			//	SmackConfiguration.setKeepAliveInterval(-1);
				//SmackConfiguration.setDefaultPingInterval(0);
				
			connection.connect();
			System.out.println("-----2"+connection);

		//	initServiceDiscovery();// 与服务器交互消息监听,发送消息需要回执，判断是否发送成功
			
			//if(connection.isConnected()){
				connection.login(username, password); // 登录
				System.out.println("-----3");
			//}	
			//TODO HANK 隐藏 error
			// OfflineMsgManager.getInstance(activitySupport).dealOfflineMsg(connection);//处理离线消息
			connection.sendPacket(new Presence(Presence.Type.available));
			if (loginConfig.isNovisible()) {// 隐身登录
				Presence presence = new Presence(Presence.Type.unavailable);
				Collection<RosterEntry> rosters = connection.getRoster()
						.getEntries();
				for (RosterEntry rosterEntry : rosters) {
					presence.setTo(rosterEntry.getUser());
					connection.sendPacket(presence);
				}
			}
			loginConfig.setUsername(username);
			//if (loginConfig.isRemember()) {// 保存密码
				loginConfig.setPassword(password);
			/*} else {
				loginConfig.setPassword("");
			}*/
			loginConfig.setOnline(true);
			BaseApplication.getInstance().saveLoginConfig(loginConfig);
			return Constant.LOGIN_SECCESS;
		} catch (Exception xee) {
			if (xee instanceof XMPPException) {
				XMPPException xe = (XMPPException) xee;
				final XMPPError error = xe.getXMPPError();
				int errorCode = 0;
				if (error != null) {
					errorCode = error.getCode();
				}
				if (errorCode == 401) {
					return Constant.LOGIN_ERROR_ACCOUNT_PASS;
				}else if (errorCode == 403) {
					return Constant.LOGIN_ERROR_ACCOUNT_PASS;
				} else {
					return Constant.SERVER_UNAVAILABLE;
				}
			} else {
				return Constant.LOGIN_ERROR;
			}
		}
	}
	
	
	
	/**
	 * 与服务器交互消息监听,发送消息需要回执，判断对方是否已读此消息
	 */
	private void initServiceDiscovery() {
		// register connection features
	/*	
		XMPPConnection connection = XmppConnectionManager.getInstance()
				.getConnection();
		
		
		ServiceDiscoveryManager sdm = ServiceDiscoveryManager
				.getInstanceFor(connection);
		if (sdm == null)
			sdm = new ServiceDiscoveryManager(connection);

		sdm.addFeature("http://jabber.org/protocol/disco#info");

		// reference PingManager, set ping flood protection to 10s
		PingManager.getInstanceFor(connection).setPingMinimumInterval(
				10 * 1000);
		// reference DeliveryReceiptManager, add listener

		DeliveryReceiptManager dm = DeliveryReceiptManager
				.getInstanceFor(connection);
		dm.enableAutoReceipts();
		dm.registerReceiptReceivedListener(new DeliveryReceiptManager.ReceiptReceivedListener() {
			public void onReceiptReceived(String fromJid, String toJid,
					String receiptId) {
				//changeMessageDeliveryStatus(receiptId, ChatConstants.DS_ACKED);// 标记为对方已读，实际上遇到了点问题，所以其实没有用上此状态
				System.out.println(receiptId);
			}
		});*/
	}
	
	
	
	
	
	
}
