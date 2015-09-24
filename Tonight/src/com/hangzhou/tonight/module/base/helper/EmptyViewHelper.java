package com.hangzhou.tonight.module.base.helper;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hangzhou.tonight.R;

public class EmptyViewHelper {
	private ListView mListView;
	private View emptyView;
	private Context mContext;
	private String mEmptyText;
	private TextView mTextView;
	
	public EmptyViewHelper(ListView listView) {
		mListView = listView;
		mContext = listView.getContext();
		initEmptyView();
	}
	
	public EmptyViewHelper(ListView listView, String text) {
		mListView = listView;
		mContext = listView.getContext();
		mEmptyText = text;
		initEmptyView();
	}

	private void initEmptyView() {
		emptyView = View.inflate(mContext, R.layout.base_module_empty_view, null);
		((ViewGroup)mListView.getParent()).addView(emptyView);
		mListView.setEmptyView(emptyView);
		if (!TextUtils.isEmpty(mEmptyText)) {
			(mTextView = ((TextView)emptyView.findViewById(R.id.empty_view))).setText(mEmptyText);
		}
	}
	

}
