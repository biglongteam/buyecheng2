package com.hangzhou.tonight.module.base.util.inter;

import com.alibaba.fastjson.JSONObject;

/**
 * 回调函数
 * @author hank
 */
public interface Callback {
	public void onSuccess(JSONObject result);
	public void onFail(String msg);
}
