package com.hangzhou.tonight.module.message.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.adapter.NoticeAdapter;
import com.hangzhou.tonight.comm.Constant;
import com.hangzhou.tonight.manager.NoticeManager;
import com.hangzhou.tonight.model.Notice;
import com.hangzhou.tonight.module.base.BaseSingeFragmentActivity;
import com.hangzhou.tonight.module.base.CustomActionActivity;
import com.hangzhou.tonight.module.base.helper.model.TbarViewModel;
import com.hangzhou.tonight.module.message.fragment.CommentFragment;
import com.hangzhou.tonight.module.message.fragment.FriendMessageFragment;
import com.hangzhou.tonight.module.message.fragment.GoodFragment;
import com.hangzhou.tonight.module.message.fragment.OrderMessageFragment;
import com.hangzhou.tonight.module.message.fragment.SystemMessageFragment;
import com.hangzhou.tonight.module.message.fragment.ValidateMessageFragment;

/**
 * 消息主界面
 * 
 * @author hank
 */
public class MessageMainActivity extends CustomActionActivity{

	View vGood,vComment,vFriendMessage,vValidateMessage,vOrderMessage,vSystemMessage;
	private List<Notice> inviteNotices = new ArrayList<Notice>();
	private ContacterReceiver receiver = null;
	private NoticeManager noticeManager;
	 private SharedPreferences sharedPreferences;
	@Override protected void doView() {
		setContentView(R.layout.activity_message_main);
		vGood    = findViewById(R.id.message_main_good);
		vComment = findViewById(R.id.message_main_comment);
		vFriendMessage = findViewById(R.id.message_main_friend_message);
		
		vOrderMessage = findViewById(R.id.message_main_order_message);
		vSystemMessage = findViewById(R.id.message_main_system_message);
		vValidateMessage = findViewById(R.id.message_main_validate_message);
		
		
		if(sharedPreferences==null){
			sharedPreferences=getSharedPreferences("notice", Activity.MODE_PRIVATE);
		}
		int state_add_friend=sharedPreferences.getInt("notice="+Notice.ADD_FRIEND, Notice.READ);
		int state_system=sharedPreferences.getInt("notice="+Notice.SYS_MSG, Notice.READ);
		int state_chat=sharedPreferences.getInt("notice="+Notice.CHAT_MSG, Notice.READ);
		int state_parise=sharedPreferences.getInt("notice="+Notice.PARISE_MSG, Notice.READ);
		int state_reply=sharedPreferences.getInt("notice="+Notice.REPLY_MSG, Notice.READ);
		int state_order=sharedPreferences.getInt("notice="+Notice.ORDER_MSG, Notice.READ);
		
		//addPoint(vFriendMessage);
		
	}
	
	
	private void addPoint(View view){
		Drawable nav_up=getResources().getDrawable(R.drawable.shape_message_point);  
		nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());  
		TextView tvText = (TextView) view.findViewById(R.id.custom_textview);
		tvText.setCompoundDrawables(null, null, nav_up, null);
	}
	
	
	@Override protected void onResume() {
		super.onResume();
		
		// 注册广播接收器
		IntentFilter filter = new IntentFilter();
		// 好友请求
		filter.addAction(Constant.ROSTER_SUBSCRIPTION);
		filter.addAction(Constant.ACTION_SYS_MSG);
		registerReceiver(receiver, filter);
		
	}
	
	@Override protected void onPause() {
		// 卸载广播接收器
		unregisterReceiver(receiver);
		super.onPause();
	}

	@Override protected void doListeners() {
		vGood   .setOnClickListener(itemClick);
		vComment.setOnClickListener(itemClick);
		vFriendMessage.setOnClickListener(itemClick);
		vValidateMessage.setOnClickListener(itemClick);
		vOrderMessage.setOnClickListener(itemClick);
		vSystemMessage.setOnClickListener(itemClick);
	}

	@Override protected void doHandler() {
		setBackViewVisibility(View.GONE);
		init();
	}
	
	private void init() {
		receiver = new ContacterReceiver();
		noticeManager = NoticeManager.getInstance(getActivity());
	}
	
	OnClickListener itemClick = new OnClickListener() {
		@Override public void onClick(View v) {
			if(v == vGood){
				BaseSingeFragmentActivity.startActivity(getActivity(), GoodFragment.class, new TbarViewModel(getResources().getString(R.string.good)));
			}else if(v == vComment){
				BaseSingeFragmentActivity.startActivity(getActivity(), CommentFragment.class, new TbarViewModel(getResources().getString(R.string.comment)));
			}else if(v == vFriendMessage){
				BaseSingeFragmentActivity.startActivity(getActivity(), FriendMessageFragment.class, new TbarViewModel(getResources().getString(R.string.friend_message)));
			}else if(v == vValidateMessage){
				BaseSingeFragmentActivity.startActivity(getActivity(), ValidateMessageFragment.class, new TbarViewModel(getResources().getString(R.string.validate_message)));
			}else if(v == vOrderMessage){
				BaseSingeFragmentActivity.startActivity(getActivity(), OrderMessageFragment.class, new TbarViewModel(getResources().getString(R.string.order_message)));
			}else if(v == vSystemMessage){
				BaseSingeFragmentActivity.startActivity(getActivity(), SystemMessageFragment.class, new TbarViewModel(getResources().getString(R.string.system_message)));
			}
		}
	};
	
	
	private class ContacterReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Notice notice = (Notice) intent.getSerializableExtra("notice");
			// String action = intent.getAction();
			Toast.makeText(MessageMainActivity.this, "有新消息"+notice, Toast.LENGTH_LONG).show();
		}
	}
	
	
	
	
	
}
