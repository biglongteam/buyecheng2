package com.hangzhou.tonight.module.base.fragment;

import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.constant.SysModuleConstant;
import com.hangzhou.tonight.module.base.helper.EmptyViewHelper;
import com.hangzhou.tonight.module.base.util.ViewUtil;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * 空 ListView
 * @author hank
 */
public abstract class BEmptyListviewFragment extends BFragment {
	
	protected ListView mListView;
	
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.empty_listview, container, false);
	}
	
	@Override protected void doView() {
		mListView = (ListView) getView().findViewById(R.id.common_tbar_listview);
	}
	
	@Override protected void init() {
		super.init();
		new EmptyViewHelper(mListView,SysModuleConstant.VALUE_EMPTY_TIP);
		if(mListView instanceof SwipeMenuListView){
			mListView.setDivider(new ColorDrawable(getResources().getColor(R.color.divider_listview)));
			mListView.setDividerHeight(1);
		}
		doPostData();
	}
	
	protected int page = 1;
	protected long time = 0;
	/*** 执行post数据*/
	protected void doPostData() {}
	
	public SwipeMenuItem getMenuItem(){
		SwipeMenuItem menuItem = new SwipeMenuItem(getActivity().getApplicationContext());
		menuItem.setBackground(new ColorDrawable(Color.parseColor("#FFEB416C")));
		menuItem.setWidth(ViewUtil.dp2px(getActivity().getBaseContext(),90));
		menuItem.setTitle("删除");
		menuItem.setTitleSize(20);
		menuItem.setTitleColor(Color.WHITE);
		return menuItem;
	}
}
