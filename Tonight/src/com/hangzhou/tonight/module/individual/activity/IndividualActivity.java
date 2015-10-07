package com.hangzhou.tonight.module.individual.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.BaseSingeFragmentActivity;
import com.hangzhou.tonight.module.base.CustomActionActivity;
import com.hangzhou.tonight.module.base.dto.UserInfoDto;
import com.hangzhou.tonight.module.base.dto.UserInfoDto.User;
import com.hangzhou.tonight.module.base.helper.ActivityHelper;
import com.hangzhou.tonight.module.base.helper.model.TbarViewModel;
import com.hangzhou.tonight.module.individual.fragment.ApplyExtensionFragment;
import com.hangzhou.tonight.module.individual.fragment.IndividualInfomationFragment;
import com.hangzhou.tonight.module.individual.fragment.InvitationCodeFragment;
import com.hangzhou.tonight.module.individual.fragment.MyCollectionFragment;
import com.hangzhou.tonight.module.individual.fragment.MyCommissionFragment;
import com.hangzhou.tonight.module.individual.fragment.SettingFragment;

/**
 * 个人[主界面]
 * 
 * @author hank
 */
public class IndividualActivity extends CustomActionActivity {
	//设置 订单  账户 收藏  邀请码 佣金
	View mSettings,mMyOrder,mMyAccount,mMyCollection,mInvitationCode,mMycommission,vInfoContainer;
	TextView tvNick,tvAccount;
	@Override protected void doView() {
		setContentView(R.layout.activity_individual_individual);
		
		tvNick    = (TextView) findViewById(R.id.individual_information_nick);
		tvAccount = (TextView) findViewById(R.id.individual_information_account);
		vInfoContainer = findViewById(R.id.individual_infomation_container);
		mSettings = findViewById(R.id.individual_settings);
		mMyOrder  = findViewById(R.id.individual_my_order);
		mMyAccount= findViewById(R.id.individual_my_account);
		mMyCollection = findViewById(R.id.individual_my_collection);
		mInvitationCode = findViewById(R.id.individual_invitation_code);
		mMycommission = findViewById(R.id.individual_my_commission);
	}

	@Override protected void doListeners() {
		vInfoContainer.setOnClickListener(itemViewClick);
		
		mSettings.setOnClickListener(itemViewClick);
		mMyOrder.setOnClickListener(itemViewClick);
		mMyAccount.setOnClickListener(itemViewClick);
		mMyCollection.setOnClickListener(itemViewClick);
		mInvitationCode.setOnClickListener(itemViewClick);
		mMycommission.setOnClickListener(itemViewClick);
	}
	
	@Override protected void doHandler() {
		setBackViewVisibility(View.GONE);
		User user = UserInfoDto.getUser(getActivity());
		tvNick.setText(user.nick);
		tvAccount.setText(String.format(getResources().getString(R.string.label_account), user.phone));
	
		//TODO  角色是经理 则显示  个人角色和等级在哪里获取?
		//mMycommission.setVisibility(View.GONE);
	}

	OnClickListener itemViewClick = new OnClickListener() {
		@Override public void onClick(View v) {
			if(v == mSettings){
				BaseSingeFragmentActivity.startActivity(getActivity(), SettingFragment.class, new TbarViewModel(getResources().getString(R.string.settings)));
			}else if(v == mMyOrder){
				ActivityHelper.startActivity(getActivity(),MyOrderActivity.class);
			}else if(v == mMyAccount){
				ActivityHelper.startActivity(getActivity(),MyAccountActivity.class);
			}else if(v == mMyCollection){
				BaseSingeFragmentActivity.startActivity(getActivity(), MyCollectionFragment.class, new TbarViewModel(getResources().getString(R.string.individual_my_collection)));
			}else if(v == mInvitationCode){
				BaseSingeFragmentActivity.startActivity(getActivity(), InvitationCodeFragment.class, new TbarViewModel(getResources().getString(R.string.individual_invitation_code)));
			}else if(v == vInfoContainer){
				BaseSingeFragmentActivity.startActivity(getActivity(), IndividualInfomationFragment.class, new TbarViewModel(getResources().getString(R.string.individual)));
			}else if(v == mMycommission){
				
				//TODO 如果未申请为 普通营销或者升级为销售经理等角色 
				BaseSingeFragmentActivity.startActivity(getActivity(), ApplyExtensionFragment.class, new TbarViewModel("申请推广"));
				//BaseSingeFragmentActivity.startActivity(getActivity(), MyCommissionFragment.class, new TbarViewModel(getResources().getString(R.string.individual_my_commission)));
			}
		}
	};
	
}
