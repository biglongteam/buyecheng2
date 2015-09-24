package com.hangzhou.tonight.module.social.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.BaseSingeFragmentActivity;
import com.hangzhou.tonight.module.base.TabActivity.TabModel;
import com.hangzhou.tonight.module.base.fragment.BFragment;
import com.hangzhou.tonight.module.base.helper.ActivityHelper.OnIntentCreateListener;
import com.hangzhou.tonight.module.base.helper.ToastHelper;
import com.hangzhou.tonight.module.base.helper.model.TbarViewModel;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;

/**
 * 添加好友
 * @author hank
 *
 */
public class AddFriendFragment extends BFragment {

	View vWeChat,vQq,wAddressBook;
	EditText etSearch;
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_social_add_friend, container, false);
	}
	
	@Override protected void doView() {
		etSearch	 = (EditText) findViewById(R.id.socail_add_friend_search);
		wAddressBook = findViewById(R.id.social_add_friend_address_book);
		vWeChat      = findViewById(R.id.social_add_friend_wechat);
		vQq          = findViewById(R.id.social_add_friend_qq);
	}

	@Override protected void doListeners() {
		etSearch.setOnKeyListener(new OnKeyListener() {
			@Override public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
					String account = etSearch.getText().toString().trim();
					searchFriend(account);
					return true;
				}
				return false;
			}
		});
		wAddressBook.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				BaseSingeFragmentActivity.startActivity(getActivity(), ContactsFragment.class, new TbarViewModel("点击选择好友"));
			}
		});
	}

	//账号查询和手机号数组查询-->跳转到查询界面
	private void searchFriend(String account){
		//ToastHelper.show(getActivity(), account);//个人
		JSONObject params = new JSONObject();
		params.put("searchUser", account);
		AsyncTaskUtil.postData(getActivity(), "searchUser", params, new Callback() {
			@Override public void onSuccess(final JSONObject result) {
				//跳转到好友list界面
				TbarViewModel model = new TbarViewModel();
				model.title = "搜索结果";
				BaseSingeFragmentActivity.startActivity(getActivity(), SearchResultFragment.class, model,new OnIntentCreateListener() {
					@Override public void onCreate(Intent intent) {
						intent.putExtra("friends", JSON.toJSONString(result.getJSONArray("userList")));
					}
				});
			}
			
			@Override public void onFail(String msg) {}
		});
	}
	
	@Override protected void doHandler() {
		vWeChat .setVisibility(View.GONE);
		vQq		.setVisibility(View.GONE);
	}

}
