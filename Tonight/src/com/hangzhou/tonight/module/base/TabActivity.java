package com.hangzhou.tonight.module.base;

import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.util.ViewUtil;
import com.hangzhou.tonight.module.base.widget.NoSlidingViewPager;

/**
 * 默认顶部tab
 * @author hank
 *
 */
public abstract class TabActivity extends CustomFragmentActivity {
	
	RadioGroup mRadioGroup;
	NoSlidingViewPager mViewPager;
	ViewPagerFragmentAdapter adapter;
	int currentIndex = 0;
	List<TabModel> list = new LinkedList<TabModel>();
	
	@Override protected void doView() {
		setContentView(R.layout.activity_module_tab_top);
		mRadioGroup = (RadioGroup) findViewById(R.id.module_tab_radio_group);
		mViewPager  = (NoSlidingViewPager) findViewById(R.id.module_tab_view_pager);
	}

	@Override protected void doListeners() {
		mRadioGroup.setOnCheckedChangeListener(mRadioGroupCheckedChange);
		mViewPager.setOnPageChangeListener(mViewPagerPageChange);
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("InflateParams")
	@Override protected void doHandler() {
		
		onCreateTabs(list);
		
		List<Fragment> fragments = new LinkedList<Fragment>();
		
		RadioButton rb = null;
		for(int i=0,len = list.size();i<len;i++){
			rb = (RadioButton) LayoutInflater.from(this).inflate(R.layout.module_tab_radio_button, null);
			mRadioGroup.addView(rb);
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT,Gravity.CENTER);
			params.weight = 1.0f;
			rb.setLayoutParams(params);
			rb.setGravity(Gravity.CENTER);
			rb.setId(i);
			rb.setText(list.get(i).title);
			//分割线
			if(i != len - 1){
				View view = new View(this);
				mRadioGroup.addView(view);
				view.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFE5E5E5")));
				view.setLayoutParams(new LayoutParams(ViewUtil.dp2px(this, 1f), LayoutParams.MATCH_PARENT));
			}
			
			fragments.add(list.get(i).fragment);//bundle 暂时没想好
		}
		
		
	
		
		adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragments);
		mViewPager.setAdapter(adapter);
		
		mRadioGroup.check(currentIndex);
	}
	
	public void onCreateTabs(List<TabModel> list){}
	
	OnPageChangeListener mViewPagerPageChange = new OnPageChangeListener() {
		@Override public void onPageSelected(int arg0) { setTab(arg0); }
		@Override public void onPageScrolled(int arg0, float arg1, int arg2) {}
		@Override public void onPageScrollStateChanged(int arg0) {}
	};
	
	OnCheckedChangeListener mRadioGroupCheckedChange = new OnCheckedChangeListener() {
		@Override public void onCheckedChanged(RadioGroup group, int checkedId) {
			mViewPager.setCurrentItem(checkedId);
		}
	};

	protected void setTab(int position){
		currentIndex = position;
		//因为添加了分割线,所以当position 需要改
		position = position*2;
		RadioButton rb = (RadioButton) mRadioGroup.getChildAt(position);
		rb.setChecked(true);
	}
	
	public class ViewPagerFragmentAdapter extends FragmentPagerAdapter{
		
		private List<Fragment> list;
		public ViewPagerFragmentAdapter(FragmentManager fm,List<Fragment> list) {
			super(fm);
			this.list = list;
		}

		@Override public Fragment getItem(int arg0) {
			return list.get(arg0);
		}

		@Override public int getCount() {
			return list.size();
		}
		
	}
	
	public class TabModel{
		public String title;
		public Fragment fragment;
		//public Bundle bundle;
	}
}
