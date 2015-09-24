package com.hoo.ad.base.helper;


import com.hoo.ad.base.R;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * listView帮助类
 * @author hank
 */
public class ListViewHelper {
	
	private ListView mListView;
	private View emptyView;
	private Context mContext;
	private String mEmptyText;
	
	public ListViewHelper(ListView listView) {
		mListView = listView;
		mContext = listView.getContext();
		initEmptyView();
	}
	
	public ListViewHelper(ListView listView, String text) {
		mListView = listView;
		mContext = listView.getContext();
		mEmptyText = text;
		initEmptyView();
	}

	private void initEmptyView() {
		emptyView = View.inflate(mContext, R.layout.base_helper_empty_view, null);
		((ViewGroup)mListView.getParent()).addView(emptyView);
		mListView.setEmptyView(emptyView);
		if (!TextUtils.isEmpty(mEmptyText)) {
			((TextView)emptyView.findViewById(R.id.base_helper_empty_view_textview)).setText(mEmptyText);
		}
	}
	
}
