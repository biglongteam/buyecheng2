package com.hangzhou.tonight.module.base.util;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * ListView 帮助类[待优化: 底部添加 加载中视图]
 * @author hank
 *
 */
public class ListViewHelper {
	
	OnScrollListener listener;
	
	Context  context;
	private ListView listview;
	
	private boolean stop;
	
	private ListViewHelper(Context context,ListView listview){
		this.context = context;
		this.listview= listview;
		init();
	}
	
	public static ListViewHelper newInstance(Context context,ListView listview){
		return new ListViewHelper(context,listview);
	}
	
	
	private void init(){
		
		listview.setOnScrollListener(new android.widget.AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            	if(null != listener){
            		switch (scrollState) {
	                    // 当不滚动时
	                    case android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
	                        if(stop){ return; }
	                    	// 判断滚动到底部
	                        if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
	                        	 listener.onScrollEnd();
	                        	 stop = true;
	                        }else if(view.getFirstVisiblePosition() == 0){
	                        	listener.onScrollTop();
	                        	stop = true;
	                        }
	                        break;
	                   default:
	                	   stop = false;
	                }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
               
            }
        });
	}
	
	public void setOnScrollListener(OnScrollListener listener){
		this.listener = listener;
	}
	
	public interface OnScrollListener{
		void onScrollTop();
		void onScrollEnd();
	}
}
