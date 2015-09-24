package com.hoo.ad.base.application;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Hashtable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Application;
import android.os.Process;

import com.hoo.ad.base.R;
import com.hoo.ad.base.helper.OsHelper;
import com.hoo.ad.base.helper.ToastHelper;
import com.hoo.ad.base.manager.ActivityManager;

public class BApplication extends Application {
	
	private static BApplication application;
	
	private static Map<Object,Object> map = new Hashtable<Object, Object>();

	public static BApplication getInstance() {
		synchronized (BApplication.class) {
			if (application == null) {
				application = new BApplication();
			}
		}
		return application;
	}
	
	private Timer timer;
	private TimerTask task;
	private boolean isQuit = false;
	private Timer getTimer(){
		if(null == timer){ timer = new Timer();}return timer;
	}
	
	public void exit(long delay,boolean isForce){
		if(isForce || isQuit){exit();return;}
		ToastHelper.show(getApplicationContext(), getApplicationContext().getResources().getString(R.string.exit_click_again));
		task = new TimerTask() { @Override public void run() { isQuit = false; } };
		isQuit = true;
		getTimer().schedule(task, delay);
	}
	
	
	public void exit(){
		ActivityManager.getInstance().unregistAll();
		Process.killProcess(Process.myPid());
		System.exit(0);
	}
	
	@Override public void onCreate() {
		super.onCreate();
		String processName = OsHelper.getProcessName(this, android.os.Process.myPid());
        if (processName != null) {
            boolean defaultProcess = processName.equals(OsHelper.getPackage(getBaseContext()));
            //TODO onCreate会根据不同的process,执行多次.故通过分支根据不同情况做相应处理
            if (defaultProcess) {
            	application = this;
            	onCreateDefaultProcess();
            	UncaughtExceptionHandler restartHandler = new UncaughtExceptionHandler() {
        			@Override public void uncaughtException(Thread thread, Throwable ex) {
        				doUncaughtExceptionHandler(thread, ex);
        			}
        		};
        		// 以下用来捕获程序崩溃异常
        		Thread.setDefaultUncaughtExceptionHandler(restartHandler);
            }
        }
	}
	
	/**
	 * 创建当前应用进程时
	 */
	public void onCreateDefaultProcess(){}
	
	/**
	 * 存放key-value[建议存放系统级别较高,不希望被系统kill掉的内容]
	 * @param key
	 * @param value[建议为简单的值]
	 */
	public void put(Object key,Object value){
		map.put(key, value);
	}
	
	/**
	 * 获取值
	 * @param key
	 * @return key对应的存储内容
	 */
	public Object get(Object key){
		return map.get(key);
	}
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		//TODO 切记: 如果非得存储 数据,一定记得:放到shareprefrence里,然后做全局初始化时重新赋值
	}
	
	public void doUncaughtExceptionHandler(Thread thread, Throwable ex){
		
	}
}
