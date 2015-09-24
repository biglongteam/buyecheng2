package com.hoo.ad.base.manager;

import java.util.Stack;

import android.app.Activity;

/**
 * activity管理类
 * @author hank
 */
public class ActivityManager {
	
	private static Stack<Activity> stacks = new Stack<Activity>();
	private static ActivityManager single;
	private ActivityManager() {}

	/**
	 * 得到ActivityManager唯一实例
	 * @return
	 */
	public static ActivityManager getInstance() {
		synchronized (ActivityManager.class) {
			if (single == null) { single = new ActivityManager(); }
		}
		return single;
	}
	
	/**
	 * 注册一个activity实例对象(唯一,重复的自动unRegister)
	 * @param activity
	 */
	public void registUnique(Activity activity) {
		if (activity != null) {
			Stack<Activity> delStacks = new Stack<Activity>();
			for (Activity ac : stacks) {
				if (activity.getClass().getName().endsWith(ac.getClass().getName())) {
					delStacks.add(ac);
				}
			}
			for (Activity ac : delStacks) {
				unregist(ac);
			}

			stacks.add(activity);
		}
	}
	
	/**
	 * 注册
	 * @param activity
	 */
	public void regist(Activity activity){
		stacks.add(activity);
	}

	/**
	 * 注销一个activity实例对象,并销毁
	 * @param activity
	 */
	public void unregist(Activity activity) {
		if (activity != null) {
			stacks.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 注销
	 * @param cls
	 */
	public void unregist(Class<?> cls) {
		Stack<Activity> delStacks = new Stack<Activity>();
		for (Activity activity : stacks) {
			if (activity.getClass().equals(cls)) {
				delStacks.add(activity);
			}
		}
		for (Activity activity : delStacks) {
			unregist(activity);
		}
	}

	/**
	 * 注销全部activity实例对象,并销毁
	 */
	public void unregistAll() {
		for (Activity activity : stacks) {
			if (activity != null) {
				activity.finish();
			}
		}
		stacks.clear();
	}

	/**
	 * 得到最后加入到Activity对象
	 * @return
	 */
	public Activity getLast() {
		if (stacks.isEmpty()) {
			return null;
		}
		return stacks.lastElement();
	}
}
