package com.hangzhou.tonight.module.social.activity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.CustomActionActivity;
import com.hangzhou.tonight.module.base.alioss.GetAndUploadFile;
import com.hangzhou.tonight.module.base.constant.ActionConstant;
import com.hangzhou.tonight.module.base.constant.SysModuleConstant;
import com.hangzhou.tonight.module.base.helper.ToastHelper;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.ImageViewUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;
import com.hoo.base.util.ObjectUtil;

/**
 * 添加个人动态
 * @author hank
 */
public class AddDynamicsActivity extends CustomActionActivity{

	List<String> imgs = new ArrayList<String>();
	ImageView ivImgs;
	EditText tvContent;
	LinearLayout vContainer;
	@Override protected void doView() {
		setContentView(R.layout.activity_social_add_dynamic);
		tvContent = findView(R.id.add_dynamic_text_content);
		vContainer= findView(R.id.add_dynamic_add_imgs_container);
		ivImgs    = findView(R.id.add_dynamic_add_imgs);
	}

	@Override protected void doListeners() {
		ivImgs.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, 1000);
			}
		});
	}

	@Override protected void doHandler() {
		
	}

	@Override public void doHandlerView(View handler) {
		 TextView tv = (TextView) handler;
		 tv.setText("发布");
		 int left,right;
		 left = right = getResources().getDimensionPixelSize(R.dimen.custom_actionbar_handler_padding);
		 tv.setPadding(left, 0, right, 0);
		 setOnClickListener(new OnHandlerClickListener() {
			@Override public void onClick(View handlerView) {
				String content = tvContent.getText().toString();
				if(ObjectUtil.isEmpty(content)){
					ToastHelper.show(getActivity(), "心情不可为空.");return;
				}
				int type = imgs.size() == 0 ? 1 : 2;//1 纯文字 2带图片
				JSONObject params = new JSONObject();
				params.put("type", type);
				params.put("city", SysModuleConstant.getCityId(getActivity()));
				params.put("content", content);
				if(imgs.size() > 0){ params.put("url", imgs); }
				AsyncTaskUtil.postData(getActivity(), "setMood", params, new Callback() {
					@Override public void onSuccess(JSONObject result) {
						Intent intent = new Intent();
						intent.setAction(ActionConstant.VALUE_ADD_DYAMICS);
						intent.putExtra("result", true);
						sendBroadcast(intent);
						ToastHelper.show(getActivity(), "心情发布成功."); onBackPressed();
					}
					@Override public void onFail(String msg) {}
				});
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK){
			if(requestCode == 1000){
				Uri uri = data.getData();  
	            ContentResolver cr = getActivity().getContentResolver();  
	            try {  
	                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));  
	                ImageView ivHead = new ImageView(getActivity());
	                ivHead.setImageBitmap(ImageViewUtil.centerSquareScaleBitmap(bitmap,60)); 
	                
	                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)getResources().getDimension(R.dimen.refresh_header_height), (int)getResources().getDimension(R.dimen.refresh_header_height));  
	                lp.setMargins(5, 0, 5, 0);  
	                vContainer.addView(ivHead,lp);
	                //TODO 命名规则??
	                new GetAndUploadFile(getActivity()).resumableUpload(uri.getPath(), "user/head.jpg");
	            } catch (FileNotFoundException e) {  
	                
	            }
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
