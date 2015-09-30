package com.hangzhou.tonight.module.base.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.AsyncTask;

import com.hangzhou.tonight.module.base.dto.UserInfoDto;
import com.hangzhou.tonight.module.base.helper.ToastHelper;
import com.hangzhou.tonight.module.base.util.inter.Callback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.dialog.FlippingLoadingDialog;
import com.hangzhou.tonight.util.Base64Utils;
import com.hangzhou.tonight.util.HttpRequest;
import com.hangzhou.tonight.util.PreferenceConstants;
import com.hangzhou.tonight.util.RC4Utils;

public class AsyncTaskUtil {
	private static String password = "mdwi5uh2p41nd4ae23qy4";
	private static FlippingLoadingDialog dialog;
	private static Long uid = null;
	private static FlippingLoadingDialog getDialog(Context context){
		dialog = new FlippingLoadingDialog(context, "加载中...");
		return dialog;
	}
	private static void show(Context context){
		try{
			dismiss();
			dialog = getDialog(context);
			dialog.show();
		}catch(Exception e){}
	}
	private static void dismiss(){
		try{
			if(null != dialog){ dialog.dismiss(); dialog.cancel(); dialog = null;}
		}catch(Exception e){}
	}
	
	private static long getUid(Context context){
		if(null == uid){ uid = UserInfoDto.getUser(context).uid; }
		return uid;
	}
	
	public static void upload(){}
	
	public static void download(){}
	
	public static void postData(Context context,String method,JSONObject params,Callback callback){
		postData(context, method, params, callback, true);
	}
	
	public static void postData(final Context context,String method,JSONObject params,final Callback callback,final boolean showDef){
		if(params == null){ params = new JSONObject();}
		//汉字处理
		Object obj = null;
		for(String key : params.keySet()){
			obj = params.get(key);
			if(obj instanceof String){//复杂数据结构暂不处理
				String value = (String) obj;
				if(StringUtil.isContainChinese(value)){
					params.put(key, UnicodeUtil.gbEncoding(value));
				}
			}
		}
		
		RequestModel model = new RequestModel();
		model.methodName = method;
		model.params = params;
		model.uid = getUid(context);
		//if(showDef){ show(context); }
		new PostAsyncTask(){
			@Override protected void onPreExecute() {
				super.onPreExecute();
				if(showDef){ show(context); }
			}
			
			@Override protected void onPostExecute(CallbackModel result) {
				super.onPostExecute(result);
				dismiss();
				if(result.success){callback.onSuccess(result.resultSet);}
				else			  {callback.onFail(result.msg);	if(showDef){ ToastHelper.show(context, result.msg); }}
			}
		}.execute(model);
	}
	
	public static class PostAsyncTask extends AsyncTask<RequestModel, Void, CallbackModel>{
		
		@Override protected CallbackModel doInBackground(RequestModel... models) {
			RequestModel m = models[0];
			Map<String,String> param = new HashMap<String,String>();
			ArrayList<Object> array = new ArrayList<Object>();
			array.add(0, m.methodName);
			array.add(1, m.uid);		//uid>0  即需要传递用户是谁,TODO toaken 更合理
			array.add(2, m.params);
			String data = RC4Utils.RC4(password, JSONArray.toJSONString(array));//加密后 orign:JsonUtils.list2json(array)
			String encoded = null;
			try {
				encoded = new String(Base64Utils.encode(data.getBytes(PreferenceConstants.ISO88591), 0, data.length()));
			} catch (UnsupportedEncodingException e1) {
				encoded = null;
			}
			if(null != encoded){
				param.put("d", encoded);
			}
			
			CallbackModel model = new CallbackModel();
			try{
				String result = HttpRequest.submitPostData(PreferenceConstants.TONIGHT_SERVER, param,PreferenceConstants.ENCODE);
				JSONObject res= JSON.parseObject(result);
				boolean success = res.getIntValue("s") == 1;
				model.success = success;
				if(success){
					res.remove("s"); model.resultSet = res;
				}else{
					model.msg = res.getString("e");
				}
			}catch(Exception e){
				model.msg = e == null ? "未知空异常." : e.getMessage();
			}
			return model;
		}
	}
	
	/**
	 * 请求模型
	 * @author hank
	 */
	public static class RequestModel{
		public long uid;
		public String methodName;
		public JSONObject params;
	}
	
	/**
	 * 回调模型
	 * @author hank
	 */
	static class CallbackModel{
		public boolean success = false;
		public String msg;
		public JSONObject resultSet;
	}
}
