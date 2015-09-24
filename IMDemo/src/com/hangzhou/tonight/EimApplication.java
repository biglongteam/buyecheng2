package com.hangzhou.tonight;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.hangzhou.tonight.comm.AppException;
import com.hangzhou.tonight.manager.XmppConnectionManager;
import com.hangzhou.tonight.service.PresenceService;

/**
 * 
 * 完整的退出应用.
 * 
 * @author shimiso
 */
public class EimApplication extends Application {
	private List<Activity> activityList = new LinkedList<Activity>();
private String tag = "App";
	
	private static EimApplication instance;
	public static EimApplication getInstance(){
		return instance;
	}
	
	private ExecutorService es;
	
	public void execRunnable(Runnable r){
		es.execute(r);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		//
		Thread.setDefaultUncaughtExceptionHandler(AppException.getInstance());
		//
		es = Executors.newFixedThreadPool(3);
	}
	
	public void exit(){
		XmppConnectionManager.getInstance().disconnect();
		stopService(new Intent(this,PresenceService.class));
		for (Activity activity : activityList) {
			activity.finish();
		}
	}

	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

}
