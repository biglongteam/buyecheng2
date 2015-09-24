package com.hangzhou.tonight.module.social.activity;

import android.view.View;
import android.view.View.OnClickListener;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.BaseSingeFragmentActivity;
import com.hangzhou.tonight.module.base.CustomActionActivity;
import com.hangzhou.tonight.module.base.helper.ActivityHelper;
import com.hangzhou.tonight.module.base.helper.model.TbarViewModel;
import com.hangzhou.tonight.module.social.fragment.PeopleNearbyFragment;

/**
 * 发现
 * @author hank
 *
 */
public class FindActivity extends CustomActionActivity {

	View vCircle,vGroup,vFriend,vAround;
	
	@Override protected void doView() {
		setContentView(R.layout.activity_social_find);
		vCircle = findViewById(R.id.social_find_circle);
		vGroup  = findViewById(R.id.social_find_group);
		vFriend = findViewById(R.id.social_find_friend);
		vAround = findViewById(R.id.social_find_around);
	}

	@Override protected void doListeners() {
		vCircle.setOnClickListener(viewClick);
		vGroup .setOnClickListener(viewClick);
		vFriend.setOnClickListener(viewClick);
		vAround.setOnClickListener(viewClick);
	}

	@Override protected void doHandler() {
		setBackViewVisibility(View.GONE);
	}
	
	OnClickListener viewClick = new OnClickListener() {
		@Override public void onClick(View v) {
			if(v == vCircle){
				ActivityHelper.startActivity(getActivity(), TonightCircleActivity.class);
			}else if(v == vGroup){
				ActivityHelper.startActivity(getActivity(), GroupActivity.class);
			}else if(v == vFriend){
				ActivityHelper.startActivity(getActivity(), FriendActivity.class);
			}else if(v == vAround){
				BaseSingeFragmentActivity.startActivity(getActivity(), PeopleNearbyFragment.class, new TbarViewModel(getResources().getString(R.string.around_people)));
			}
		}
	};
}
