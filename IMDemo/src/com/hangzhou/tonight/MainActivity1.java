package com.hangzhou.tonight;



import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;
import org.jivesoftware.smackx.packet.VCard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hangzhou.tonight.comm.Constant;
import com.hangzhou.tonight.im.ContacterMainActivity;
import com.hangzhou.tonight.manager.MessageManager;
import com.hangzhou.tonight.manager.UserManager;
import com.hangzhou.tonight.manager.XmppConnectionManager;
import com.hangzhou.tonight.model.IMMessage;
import com.hangzhou.tonight.model.LoginConfig;
import com.hangzhou.tonight.model.MainPageItem;
import com.hangzhou.tonight.util.DateUtil;
import com.hangzhou.tonight.util.StringUtil;

/**
 * 
 * 主页面.
 * 
 * @author shimiso
 */
public class MainActivity1 extends ActivitySupport implements OnClickListener{
	private GridView gridview;
	private List<MainPageItem> list;
	//private MainPageAdapter adapter;
	private ImageView iv_status;
	private ContacterReceiver receiver = null;
	private TextView usernameView;
	private UserManager userManager;
	private LoginConfig loginConfig;
	private ImageView userimageView;
	
	private Chat chat = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
	}

	@Override
	protected void onPause() {
		// 卸载广播接收器
		unregisterReceiver(receiver);
		super.onPause();
	}

	@Override
	protected void onResume() {
		// 注册广播接收器
		IntentFilter filter = new IntentFilter();
		// 好友请求
		filter.addAction(Constant.ROSTER_SUBSCRIPTION);
		filter.addAction(Constant.NEW_MESSAGE_ACTION);
		filter.addAction(Constant.ACTION_SYS_MSG);

		filter.addAction(Constant.ACTION_RECONNECT_STATE);
		registerReceiver(receiver, filter);

		/*if (getUserOnlineState()) {
			iv_status.setImageDrawable(getResources().getDrawable(
					R.drawable.status_online));
		} else {
			iv_status.setImageDrawable(getResources().getDrawable(
					R.drawable.status_offline));
		}
*/
		super.onResume();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { // resultCode为回传的标记
		case 1:
			setUserView();
			break;
		default:
			break;
		}
	}

	private void setUserView() {
		String jid = StringUtil.getJidByName(loginConfig.getUsername(),
				loginConfig.getXmppServiceName());
		VCard vCard = userManager.getUserVCard(jid);
		InputStream is = userManager.getUserImage(jid);
		if (is != null) {
			Bitmap bm = BitmapFactory.decodeStream(is);
			userimageView.setImageBitmap(bm);
		}
		if (vCard.getFirstName() != null) {
			usernameView.setText(vCard.getFirstName()
					+ (StringUtil.notEmpty(vCard.getOrganization()) ? " - "
							+ vCard.getOrganization() : ""));
		} else {
			usernameView.setText(loginConfig.getUsername()
					+ (StringUtil.notEmpty(vCard.getOrganization()) ? " - "
							+ vCard.getOrganization() : ""));
		}

	}

	private void init() {
		userManager = UserManager.getInstance(this);
		loginConfig = getLoginConfig();
		gridview = (GridView) findViewById(R.id.gridview);
		iv_status = (ImageView) findViewById(R.id.iv_status);
		userimageView = (ImageView) findViewById(R.id.userimage);
		usernameView = (TextView) findViewById(R.id.username);
		setUserView();
		userimageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//Intent intent = new Intent();
				//intent.setClass(context, UserInfoActivity.class);
				//startActivityForResult(intent, 1);
			}
		});
		// 初始化广播
		receiver = new ContacterReceiver();
		
		
		String to="9000000@115.29.246.139";
		chat = XmppConnectionManager.getInstance().getConnection()
				.getChatManager().createChat(to, null);
		
		
		
		
		
		Button button=(Button) findViewById(R.id.button);
		Button button1=(Button) findViewById(R.id.button1);
		Button button2=(Button) findViewById(R.id.button2);
		button.setOnClickListener(this);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		/*button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Intent intent = new Intent();
				intent.setClass(context, ContacterMainActivity.class);
				startActivity(intent);
			}
		});
*/
		//loadMenuList();
		/*adapter = new MainPageAdapter(this);
		adapter.setList(list);
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final Intent intent = new Intent();
				switch (position) {
				case 0:// 我的联系人
					intent.setClass(context, ContacterMainActivity.class);
					startActivity(intent);
					break;
				case 1:// 我的消息
					intent.setClass(context, MyNoticeActivity.class);
					startActivity(intent);
					break;
				case 2:// 企业通讯录
					break;
				case 3:// 个人通讯录
					break;
				case 4:// 我的邮件
					break;
				case 5:// 网络收藏夹
					break;
				case 6:// 个人文件夹
					break;
				}
			}
		});*/
	}

	/**
	 * 
	 * 加载菜单.
	 * 
	 * @author shimiso
	 * @update 2012-5-16 下午7:15:21
	 */
	protected void loadMenuList() {
		/*list = new ArrayList<MainPageItem>();
		list.add(new MainPageItem("我的联系人", R.drawable.mycontacts));
		list.add(new MainPageItem("我的消息", R.drawable.mynotice));
		list.add(new MainPageItem("企业通讯录", R.drawable.e_contact));
		list.add(new MainPageItem("个人通讯录", R.drawable.p_contact));
		list.add(new MainPageItem("邮件", R.drawable.email));
		list.add(new MainPageItem("单点登录", R.drawable.sso));
		list.add(new MainPageItem("个人文件夹", R.drawable.p_folder));
		list.add(new MainPageItem("我的笔记", R.drawable.mynote));
		list.add(new MainPageItem("我的签到", R.drawable.signin));
		list.add(new MainPageItem("我的工作日报", R.drawable.mydaily));
		list.add(new MainPageItem("我的日程", R.drawable.mymemo));
		list.add(new MainPageItem("设置", R.drawable.set));*/
	}

	@Override
	protected void onRestart() {
		//adapter.notifyDataSetChanged();
		super.onRestart();
	}

	private class ContacterReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (Constant.ROSTER_SUBSCRIPTION.equals(action)) {
				//adapter.notifyDataSetChanged();
			} else if (Constant.NEW_MESSAGE_ACTION.equals(action)) {
				// 添加小气泡
				//adapter.notifyDataSetChanged();
			} else if (Constant.ACTION_RECONNECT_STATE.equals(action)) {
				boolean isSuccess = intent.getBooleanExtra(
						Constant.RECONNECT_STATE, false);
				handReConnect(isSuccess);
			} else if (Constant.ACTION_SYS_MSG.equals(action)) {
				//adapter.notifyDataSetChanged();
			}

		}
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_page_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		switch (item.getItemId()) {
		case R.id.menu_relogin:
			intent.setClass(context, LoginActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.menu_exit:
			isExit();
			break;
		}
		return true;
	}
*/
	@Override
	public void onBackPressed() {
		isExit();
	}

	/**
	 * 处理冲连接返回状态，连接成功 改变头像 ，失败
	 * 
	 * @param isSuccess
	 */
	private void handReConnect(boolean isSuccess) {
		// 成功了连接
		if (Constant.RECONNECT_STATE_SUCCESS == isSuccess) {
			/*iv_status.setImageDrawable(getResources().getDrawable(
					R.drawable.status_online));*/
			 Toast.makeText(context, "网络恢复,用户已上线!", Toast.LENGTH_LONG).show();
		} else if (Constant.RECONNECT_STATE_FAIL == isSuccess) {// 失败
			/*iv_status.setImageDrawable(getResources().getDrawable(
					R.drawable.status_offline));*/
			 Toast.makeText(context, "网络断开,用户已离线!", Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	/*	int id = v.getId();
		switch (id) {
		case R.id.button:
			Intent intent = new Intent();
			intent.setClass(context, ContacterMainActivity.class);
			startActivity(intent);
			break;
		case R.id.button1:
			try {
				sendMessage("1000004@115.29.246.139","del_friend","{'uid7':1000003}");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.button2:

			break;
		case R.id.button3:

			break;

		default:
			break;
		}*/
	}
	
	
	protected void sendMessage(String from,String tp,String messageContent) throws Exception {
		String time = DateUtil.date2Str(Calendar.getInstance(),
				Constant.MS_FORMART);
		Message message = new Message();
		message.setFrom(from);
		message.setTp(tp);
		message.setSt("1900:08:08");
		message.setType(Type.normal);
		//message.setFrom("1000000@115.29.246.139");
		message.setProperty(IMMessage.KEY_TIME, time);
		message.setBody(messageContent);
		ChatManager ch=XmppConnectionManager.getInstance().getConnection()
		.getChatManager();
		chat.sendMessage(message);
		Log.d("发送信息", message.toXML());
		IMMessage newMessage = new IMMessage();
		newMessage.setMsgType(1);
		newMessage.setFromSubJid(chat.getParticipant());
		newMessage.setContent(messageContent);
		newMessage.setTime(time);
		//newMessage.setPath(path);
		MessageManager.getInstance(context).saveIMMessage(newMessage);
		// MChatManager.message_pool.add(newMessage);


	}
	
	
	
	
	
	
}
