/**
 * 
 */
package com.hangzhou.tonight.module.social.fragment;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TableLayout.LayoutParams;

import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;
import com.hangzhou.tonight.util.MyPreference;
import com.hangzhou.tonight.util.StringUtil;
import com.hoo.ad.base.helper.ToastHelper;

/**
 * 群组详细
 * @author hank
 */
public class GroupDetailFragment extends CreateGroupFragment {

	boolean inGroup;
	String gid;
	JSONObject json;
	
	@Override protected void doListeners() {
		button.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				if(inGroup){
					//TODO 发消息
					ToastHelper.show(getActivity(), "发消息");
				}else{
					//加入群组
					applyInGroup();
				}
			}
		});
	}
	
	@Override protected void doHandler() {
		
		button.setVisibility(View.GONE);
		
		gid = getBundle().getString("gid");
		
		
		JSONObject params = new JSONObject();
		params.put("gid", gid);
		AsyncTaskUtil.postData(getActivity(), "getGroupInfo", params, new Callback() {
			
			@Override public void onSuccess(JSONObject result) {
				//gid(群组ID)，title(群名称)，photo(群照⽚)，label(群标签)，num(群⼈数)，total_num(总⼈数上限)，intro(群介绍)，created_at(创建时间)，owner(群主uid)，nick(群主昵称)
				json = result.getJSONObject("groupInfo");
				boolean isOwner = MyPreference.getInstance(getActivity()).getUserId().equals(json.getString("owner"));
				inGroup = result.containsKey("inGroup") ? result.getInteger("inGroup") == 1 : false;
				
				tvId.setText(json.getString("gid"));
				etName.setText(json.getString("title"));
				etLabel.setText(json.getString("label"));
				etProfile.setText(json.getString("intro"));
				tvDate.setText(json.getString("created_at"));
				
				if(!isOwner){
					etName.setEnabled(false);
					etLabel.setEnabled(false);
					etProfile.setEnabled(false);
				}
				button.setText(inGroup ? "发消息":"加入群组");
				button.setVisibility(View.VISIBLE);
			}
			
			@Override public void onFail(String msg) {}
		});
	}
	
	
	AlertDialog adInGroup;TextView tvInGroup;EditText etValMsg;
	private void applyInGroup(){
		if(adInGroup == null){
			View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_custom_edittext,null);
			adInGroup = new AlertDialog.Builder(getActivity()).create();
			adInGroup.show();
			Window w = adInGroup.getWindow();
			w.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			w.setContentView(view);
			w.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); 
			w.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
			
			tvInGroup = (TextView) view.findViewById(R.id.title);
			etValMsg  = (EditText) view.findViewById(R.id.edit);
			Button ok = (Button)view.findViewById(R.id.btn_ok),cancel = (Button)view.findViewById(R.id.btn_cancel);
			ok.setOnClickListener(new OnClickListener() {
				@Override public void onClick(View v) {
					String content = etValMsg.getText().toString();
					if(!StringUtil.empty(content)){
						adInGroup.dismiss();
						
						JSONObject params = new JSONObject();
						params.put("tuid", gid);
						params.put("msg", content);
						params.put("nick", MyPreference.getInstance(getActivity()).getUserName());
						AsyncTaskUtil.postData(getActivity(), "applyGroup", params, new Callback() {
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
					adInGroup.dismiss();
				}
			});
		}else{
			adInGroup.show();
		}
		tvInGroup.setText("你将添加" + json.getString("title"));
		etValMsg.setHint("请输入验证消息");
		String content = etValMsg.getText().toString();
		etValMsg.setText(content);
		if(!StringUtil.empty(content)){ etValMsg.setSelection(content.length()); }
	}
}
