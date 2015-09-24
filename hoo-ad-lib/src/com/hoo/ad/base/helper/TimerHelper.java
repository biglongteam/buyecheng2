package com.hoo.ad.base.helper;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Message;

/**
 * 定时器帮助类
 * 
 * @author hank
 */
public class TimerHelper {
	private TimerHelper helper = this;
	private Timer timer;
	private Handler handler;
	/*** 循环次数 */
	private long loop_time = 0;

	public TimerHelper() {
		timer = new Timer();
	}
	
	/**
	 * 获取一个实例
	 * @return
	 */
	public static TimerHelper newInstance(){
		return new TimerHelper();
	}
	
	/**
	 * 定时任务
	 * @param handler
	 * @param period
	 */
	public void schedule(Handler handler, long period) {
		this.handler = handler;

		timer.schedule(new TimerTask() {
			@Override public void run() {
				Message msg = new Message();
				loop_time++;
				msg.obj = loop_time;
				helper.handler.sendMessage(msg);
			}
		}, 0L, period);
	}
	
	/**
	 * 取消定时任务
	 */
	public void cancel(){
		timer.cancel();
	}

}
