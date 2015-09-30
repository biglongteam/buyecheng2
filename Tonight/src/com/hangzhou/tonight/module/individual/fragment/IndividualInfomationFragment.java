package com.hangzhou.tonight.module.individual.fragment;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.im.ChatActivity1;
import com.hangzhou.tonight.manager.XmppConnectionManager;
import com.hangzhou.tonight.model.User;
import com.hangzhou.tonight.module.base.fragment.BFragment;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.DateUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;
import com.hangzhou.tonight.util.MyPreference;
import com.hangzhou.tonight.util.StringUtil;
import com.hoo.ad.base.helper.DeviceHelper;
import com.hoo.ad.base.helper.ToastHelper;

public class IndividualInfomationFragment extends BFragment {

	View vDynamic,vNick,vSign,vBrith,vSex,vOcc;
	TextView tvDynamic,tvNick,tvSign,tvConstellation,tvBrith,tvId,tvSex,tvOcc,tvSt;
	ImageView ivHeadAdd;
	Button bAddfriend;
	
	JSONObject orginJson = new JSONObject();
	Map<String,ComponentModel> map = new HashMap<String,ComponentModel>();
	boolean isFriend = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_individual_infomation, container, false);
	}
	
	@Override
	protected void doView() {
		
		bAddfriend = findView(R.id.information_addfriend);
		ivHeadAdd = findView(R.id.individual_head_add);
		
		vDynamic = findView(R.id.information_dynamic_container);
		tvDynamic= findView(R.id.information_dynamic);
		vNick  = findView(R.id.information_nickname_container);
		tvNick = findView(R.id.information_nickname);
		vSign  = findView(R.id.information_sign_container);
		tvSign = findView(R.id.information_sign);
		tvConstellation = findView(R.id.information_constellation);
		tvBrith = findView(R.id.information_brithday);
		vBrith  = findView(R.id.information_brithday_container);
		tvId    = findView(R.id.information_id);
		vSex    = findView(R.id.information_sex_container);
		tvSex   = findView(R.id.information_sex);
		vOcc    = findViewById(R.id.information_occupation_container);
		tvOcc   = findView(R.id.information_occupation);
		tvSt    = findView(R.id.information_signtime);
	}

	@Override protected void doListeners() {
		
	}
	OnClickListener bAddfriendClick = new OnClickListener() {
		@Override public void onClick(View v) {
			if(MyPreference.getInstance(getActivity()).getUserId().equals(uid)){ 
				ToastHelper.show(getActivity(), "当前用户是你自己.");return;
			}
			if(isFriend){
				User user = new User();
				user.setJID(uid +"@"+XmppConnectionManager.getInstance().getConnection().getServiceName());
				Intent intent = new Intent(getActivity(), ChatActivity1.class);
				intent.putExtra("to", user.getJID());
				startActivity(intent);
			}else{
				addFriend();
			}
		}
	};

	String uid;
	@Override protected void doHandler() {
		uid =getBundle().getString("uid");
		
		if(uid == null){
			bAddfriend.setVisibility(View.GONE);
		}else{
			ivHeadAdd.setVisibility(View.GONE);
			bAddfriend.setOnClickListener(bAddfriendClick);
		}
		
		
		map.put("nick",new ComponentModel("昵称","nick", vNick, tvNick, Type.TEXT, true));
		map.put("mood_count", new ComponentModel(null,"mood_count", vDynamic, tvDynamic, Type.DYNA, false));
		map.put("sign", new ComponentModel("个人签名","sign", vSign, tvSign, Type.TEXT, true));
		map.put("constellation", new ComponentModel(null,"constellation", null, tvConstellation, Type.TEXT, false));
		map.put("birth", new ComponentModel("生日","birth", vBrith, tvBrith, Type.BRITH, true));
		map.put("uid", new ComponentModel(null,"uid", null, tvId, Type.TEXT, false));
		map.put("sex", new ComponentModel(null,"sex", vSex, tvSex, Type.SEX, true));
		map.put("career", new ComponentModel("职业","career", vOcc, tvOcc, Type.TEXT, true));
		map.put("created_at", new ComponentModel(null,"created_at", null, tvSt, Type.TEXT, false));
		
		JSONObject params = null;
		if(uid != null){
			params = new JSONObject();
			params.put("tuid", uid);
		}
		AsyncTaskUtil.postData(getActivity(),uid == null? "getSelfInfo" : "getUserInfo", params, new Callback() {
			
			@Override public void onSuccess(JSONObject result) {
				JSONObject json = result.getJSONObject("userInfo");
				orginJson = result.getJSONObject("userInfo");
				json.put("constellation", DateUtil.getConstellation(json.getString("birth"), "yyyy-MM-dd"));
				json.put("sex", json.get("sex")== null ?"未知" : (json.getInteger("sex")== 1 ?"男":"女"));
				ComponentModel cm = null;
				for(String key : map.keySet()){
					cm = map.get(key);
					cm.getTextView().setText(json.getString(key));
					if(cm.isAllowChange()){
						cm.getContainer().setTag(cm);
						cm.getContainer().setOnClickListener(containerClick);
					}
					if(uid != null){
						View v = cm.getContainer();
						if(v == null){continue;}
						View tv = v.findViewWithTag("tip"),iv = v.findViewWithTag("img");
						if(null != tv){tv.setVisibility(View.GONE);}
						if(null != iv){iv.setVisibility(View.GONE);}
					}
				}
				if(json.containsKey("isfriend") && json.getInteger("isfriend")==1){
					isFriend = true;
					bAddfriend.setText("发消息");
				}
			}
			
			@Override public void onFail(String msg) {
				
			}
		});
	}

	
	AlertDialog mAlertDialog;TextView tvTitle;EditText etContent;
	OnClickListener containerClick  = new OnClickListener() {
		private TextView tvTemp;
		ComponentModel cm;
		@Override public void onClick(View v) {
			cm = (ComponentModel) v.getTag();
			
			tvTemp = cm.getTextView();
			if(Type.BRITH.equals(cm.getType())){
				Calendar calendar = Calendar.getInstance();
				String date = tvTemp.getText().toString();
				if(!StringUtil.empty(date)){ DateUtil.setDate(calendar, date, DateUtil.pattern_df); }
				final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), null, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
				datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成", new DialogInterface.OnClickListener() {
		            @Override public void onClick(DialogInterface dialog, int which) {
		            	DatePicker datePicker = datePickerDialog.getDatePicker();
		                int year = datePicker.getYear();
		                int month = datePicker.getMonth();
		                int day = datePicker.getDayOfMonth();
		                tvTemp.setText(DateUtil.getFormatDate(year, month, day, DateUtil.pattern_df));
		                cm.value = tvTemp.getText().toString();
		                String cons = DateUtil.getConstellation(tvTemp.getText().toString(), DateUtil.pattern_df);
		                tvConstellation.setText(cons);
		                //doUpdate(cm);
		            }
		        });
				datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
		            @Override public void onClick(DialogInterface dialog, int which) {
		            	datePickerDialog.dismiss();datePickerDialog.cancel();
		            }
		        });
				datePickerDialog.setTitle("请选择");
				datePickerDialog.show();
			}else if(Type.SEX.equals(cm.getType())){
				Builder bd = new AlertDialog.Builder(getActivity());
				bd.setItems(new String[]{"女","男"}, new DialogInterface.OnClickListener() {
					@Override public void onClick(DialogInterface arg0, int position) {
						tvTemp.setText(position == 1 ? "男" : "女");
						cm.value = position;
						//doUpdate(cm);
					}
				});
				bd.create().show();
			}else if(Type.TEXT.equals(cm.getType())){
				if(null == mAlertDialog){
					View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_custom_edittext,null);
					mAlertDialog = new AlertDialog.Builder(getActivity()).create();
					mAlertDialog.show();
					Window w = mAlertDialog.getWindow();
					w.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
					w.setContentView(view);
					w.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); 
					w.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
					
					tvTitle = (TextView) view.findViewById(R.id.title);
					etContent = (EditText) view.findViewById(R.id.edit);
					Button ok = (Button)view.findViewById(R.id.btn_ok),cancel = (Button)view.findViewById(R.id.btn_cancel);
					ok.setOnClickListener(new OnClickListener() {
						@Override public void onClick(View v) {
							tvTemp.setText(etContent.getText().toString());
							cm.value = tvTemp.getText().toString();
							mAlertDialog.dismiss();
							DeviceHelper.hideSoftKey(getActivity());
							//doUpdate(cm);
						}
					});
					cancel.setOnClickListener(new OnClickListener() {
						@Override public void onClick(View v) {
							mAlertDialog.dismiss();
						}
					});
				}else{
					mAlertDialog.show();
				}
				tvTitle.setText(cm.getCnName());
				etContent.setHint("请输入" + cm.getCnName());
				String content = tvTemp.getText().toString();
				etContent.setText(content);
				if(!StringUtil.empty(content)){
					etContent.setSelection(content.length());
				}
			}else if(Type.DYNA.equals(cm.getType())){
				//TODO 动态跳转到哪里?
			}
		}
	};
	
	AlertDialog adAddfriend;TextView tvAddfriend;EditText etAddfriend;
	private void addFriend(){
		if(adAddfriend == null){
			View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_custom_edittext,null);
			adAddfriend = new AlertDialog.Builder(getActivity()).create();
			adAddfriend.show();
			Window w = adAddfriend.getWindow();
			w.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			w.setContentView(view);
			w.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); 
			w.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
			
			tvAddfriend = (TextView) view.findViewById(R.id.title);
			etAddfriend = (EditText) view.findViewById(R.id.edit);
			Button ok = (Button)view.findViewById(R.id.btn_ok),cancel = (Button)view.findViewById(R.id.btn_cancel);
			ok.setOnClickListener(new OnClickListener() {
				@Override public void onClick(View v) {
					String content = etAddfriend.getText().toString();
					if(!StringUtil.empty(content)){
						adAddfriend.dismiss();
						JSONObject params = new JSONObject();
						params.put("tuid", uid);
						params.put("msg", content);
						params.put("nick", MyPreference.getInstance(getActivity()).getUserName());
						AsyncTaskUtil.postData(getActivity(), "applyFriend", params, new Callback() {
							
							@Override public void onSuccess(JSONObject result) {
								ToastHelper.show(getActivity(), "申请成功,等待回复.");getActivity().onBackPressed();
							}
							@Override public void onFail(String msg) {}
						});
					}
					
				}
			});
			cancel.setOnClickListener(new OnClickListener() {
				@Override public void onClick(View v) {
					adAddfriend.dismiss();
				}
			});
		}else{
			adAddfriend.show();
		}
		tvAddfriend.setText("你将添加" + orginJson.getString("nick"));
		etAddfriend.setHint("请输入验证消息");
		String content = etAddfriend.getText().toString();
		etAddfriend.setText(content);
		if(!StringUtil.empty(content)){ etAddfriend.setSelection(content.length()); }
	}
	
	
	private void doUpdate(ComponentModel cm){
		
		JSONObject params = new JSONObject();
		for(String key : map.keySet()){
			orginJson.put(key, map.get(key).value);
			if("nick".equals(key)||"photo".equals(key)||"birth".equals(key)||"sex".equals(key)||"sign".equals(key)||"career".equals(key)){
				params.put(key, orginJson.get(key));
			}
		}
		//TODO 什么参数错误?
		AsyncTaskUtil.postData(getActivity(), "editUserInfo", params, new Callback() {
			@Override public void onSuccess(JSONObject result) {
				ToastHelper.show(getActivity(), "个人信息已修改.");
			}
			
			@Override public void onFail(String msg) { }
		});
		
		
	}
	
	@Override public void onBackPressed() {
		if(uid == null){ doUpdate(null); }
		super.onBackPressed();
	}
	
	
	
	/**
	 * 组件模型
	 * @author hank
	 */
	public static class ComponentModel{
		private String cnName;
		private String filedName;
		private View container;
		private TextView textView;
		private String type;
		private boolean allowChange;
		public Object value;
		public ComponentModel(String cnName,String filedName, View container, TextView textView, String type, boolean allowChange) { 			super(); 	this.cnName = cnName;		this.filedName = filedName; 			this.container = container; 			this.textView = textView; 			this.type = type; 			this.allowChange = allowChange; 		}
		public String getFiledName() { 			return filedName; 		} 		public void setFiledName(String filedName) { 			this.filedName = filedName; 		}		public View getContainer() { return container; } public TextView getTextView() { return textView; } public String getType() { return type; } public boolean isAllowChange() { return allowChange; } public void setContainer(View container) { this.container = container; } public void setTextView(TextView textView) { this.textView = textView; } public void setType(String type) { this.type = type; } public void setAllowChange(boolean allowChange) { this.allowChange = allowChange; }public String getCnName() { 			return cnName; 		} 		public Object getValue() { 			return value; 		} 		public void setCnName(String cnName) { 			this.cnName = cnName; 		} 		public void setValue(Object value) { 			this.value = value; 		} 		
	}
	
	public static class Type{
		public static final String DYNA = "D",TEXT = "T",BRITH ="B",SEX = "S";
	}
	
}
