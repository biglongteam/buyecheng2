package com.hangzhou.tonight.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.PopupWindow;

public class PopWindowCoursePg {
	
    private Context context;
	private int screenWidth;
	private int screenHeigh;
	private int xOff,yOff;
	String gravity;
	private static PopupWindow popupWindow;
	View view;
	View downview;
	
	public PopWindowCoursePg(Context context, int screenWidth, int screenHeigh,
			View view, View downview) {
		super();
		this.context = context;
		this.screenWidth = screenWidth;
		this.screenHeigh = screenHeigh;
		this.view = view;
		this.downview = downview;
		init();
	}

	
	
	
	@SuppressLint("NewApi")
	private void init(){
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
		popupWindow = new PopupWindow(view, screenWidth, screenHeigh);
		ColorDrawable cd = new ColorDrawable(-0000); 
		popupWindow.setBackgroundDrawable(cd);//去掉后点击外围 pop不会消失
		popupWindow.setFocusable(true);   
		popupWindow.setOutsideTouchable(true);//设置外部能点击
		
		popupWindow.showAsDropDown(downview,20,0);
		
		//popupWindow.setAnimationStyle(R.style.PopupAnimation);
		//popupWindow.update();
		/*ListView listView = (ListView) view.findViewById(R.id.lv_city_sights);
		adapter = new ExpandableSightListAdapter(context, lists);
		listView.setAdapter(adapter);*/
		  
		// 重写onKeyListener  
		 view.setOnKeyListener(new OnKeyListener() {  
		    
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {  
					popupWindow.dismiss();  
					popupWindow = null;  
		            return true;  
		         }  
		         return false;
			}  
		 });  
		 
		//setting popupWindow d点击消失
		 popupWindow.setTouchInterceptor(new View.OnTouchListener() {  
	         @Override
			public boolean onTouch(View v, MotionEvent event) {  
	             /****   如果点击了popupwindow的外部，popupwindow也会消失 ****/ 
	             if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {  
	            	popupWindow.dismiss();  
	                 return false;   
	             }  
	             return false;  
	         }  
		 });
		 
		 
		/* listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					Sight sight = null;
					// 判断是否是TextView
					if (view instanceof TextView) {
						sight = (Sight) view.getTag();
					} else {
						TextView tv = (TextView) view.findViewById(R.id.sight_list_item_name);
						sight = (Sight) tv.getTag();
					}
					if (sight == null)
						return;

					// 跳转到景点 下载详情页面
					Intent intent = new Intent(context, GuideDownLoadActivity.class);
					intent.putExtra(Constants.EXTRA_KEY_OBJECT, sight);
					
					context.startActivity(intent);
				//contextoverridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				}
			});*/
	}
	
	public static void colsePop(){
		popupWindow.dismiss();
	}

}
