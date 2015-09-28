package com.hangzhou.tonight.activity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.adapter.ActivesListAdapter;
import com.hangzhou.tonight.adapter.CityAdapter;
import com.hangzhou.tonight.adapter.LibaoListAdapter;
import com.hangzhou.tonight.base.BaseActivity;
import com.hangzhou.tonight.base.BaseApplication;
import com.hangzhou.tonight.entity.ActivesEntity;
import com.hangzhou.tonight.entity.ActivesInfo;
import com.hangzhou.tonight.entity.City;
import com.hangzhou.tonight.entity.LiBaoEntity;
import com.hangzhou.tonight.util.Base64Utils;
import com.hangzhou.tonight.util.HttpRequest;
import com.hangzhou.tonight.util.JsonUtils;
import com.hangzhou.tonight.util.MyPreference;
import com.hangzhou.tonight.util.PreferenceConstants;
import com.hangzhou.tonight.util.RC4Utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;


public class SelectLibaoActivity extends BaseActivity implements OnClickListener{

	
	BaseApplication myApplication;
	private Context mContext;
	private LibaoListAdapter mAdapter;

	ArrayList<LiBaoEntity> libaoLists = new ArrayList<LiBaoEntity>();
	private TextView tvBack,tvTitle;
	EditText et_search;
	TextView tv_result;
	FrameLayout fl_content;
	private String text;
	private int code;

	public final String mPageName ="SelectCityActivity";
	private ListView xListView;
	@Override
	public void onResume() {
		super.onResume();
	}
	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_select_libao);
		mContext = this;
		myApplication = BaseApplication.getInstance();
		code = getIntent().getExtras().getInt("code");
		initCity();
		initViews() ;
		initEvents();
		init();

	}
	private void initCity() {
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK&&tv_result.isShown()  ){
			tv_result.setVisibility(View.GONE);
			fl_content.setVisibility(View.VISIBLE);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void initViews() {
		tvBack = (TextView) findViewById(R.id.tv_header_back);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("我的礼包");
		tv_result=(TextView) findViewById(R.id.tv_result);
		fl_content =(FrameLayout) findViewById(R.id.fl_content);
		xListView = (ListView) findViewById(R.id.lv);
	}
	@Override
	protected void init() {
		getLibao();
		
	}
	
	
	/**
	 * 
	* @Title: getLibao 
	* @Description: TODO(获取礼包列表 ) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void getLibao() {
		new AsyncTask<Void, Void, String>() {

			
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadingDialog("正在请求,请稍后...");
			}

			@Override
			protected String doInBackground(Void... params) {

				Map<String, String> param =setParams();
				
				return HttpRequest.submitPostData(PreferenceConstants.TONIGHT_SERVER,
						param, "UTF-8");
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				com.alibaba.fastjson.JSONObject object = JSON
						.parseObject(result);
				com.alibaba.fastjson.JSONArray jsonArray = object.getJSONArray("tickets");
				/*com.alibaba.fastjson.JSONObject object = JSON.parseObject(json.toString());
				com.alibaba.fastjson.JSONArray jsonArray = object.getJSONArray("list");
				mCourseLists = JSON.parseArray(jsonArray.toString(),CourseBean.class);*/
				libaoLists = (ArrayList<LiBaoEntity>) JSON.parseArray(jsonArray.toString(), LiBaoEntity.class);
				if(libaoLists.size()==0){
					showCustomToast("您还没有可用礼包");
					return;
				}
			/*	JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(result);
					JSONArray jsonArray = jsonObject.getJSONArray("tickets");
					libaoLists = resolveUtils(jsonArray);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/

				//JSONArray array = new JSONArray(result);

				// mActives =
				// JSON.parseArray(jsonArray.toString(),ActivesEntity.class);
				mAdapter = new LibaoListAdapter(mApplication, mContext,libaoLists);
				xListView.setAdapter(mAdapter);
			}

			
		}.execute();
	}
	
	
	@Override
	protected void initEvents() {
		tvBack.setOnClickListener(this);
		tv_result.setOnClickListener(this);
		
		
		xListView.setAdapter(new CityAdapter(mApplication, mContext, libaoLists));
		/**
		 * 快速查询ListView的条目点击事件
		 */
		xListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					
				LiBaoEntity bean  = new LiBaoEntity();
				TextView tv = (TextView) view.findViewById(R.id.tv_libao_name);
				bean = (LiBaoEntity) tv.getTag();
				Intent intent = new Intent(SelectLibaoActivity.this,OrderPromotionActivity.class);
				intent.putExtra("name", bean.getName());
				intent.putExtra("money", bean.getMoney());
				intent.putExtra("id", bean.getTicket_id());
				setResult(code, intent);
				finish();

			}
		});
		
	}
	
	
private Map<String, String> setParams(){
		
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> parms = new HashMap<String, Object>();
		ArrayList<Object> arry = new ArrayList<Object>();
		arry.add(0, "getTicketList");
		arry.add(1, MyPreference.getInstance(mContext).getUserId());
		arry.add(2, parms);
		String data0 = RC4Utils.RC4("mdwi5uh2p41nd4ae23qy4",JsonUtils.list2json(arry));
		String encoded1 = "";
		try {
			encoded1 = new String(Base64Utils.encode(
					data0.getBytes("ISO-8859-1"), 0, data0.length()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("base64编码后：     " + encoded1);
		String decode = "";
		try {
			if(!encoded1.equals("")){
				decode = new String(
						Base64.decode(encoded1, Base64.DEFAULT),
						"ISO-8859-1");
			}		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String data7 = RC4Utils.RC4("mdwi5uh2p41nd4ae23qy4", decode);
		map.put("d", encoded1);
		return map;
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_header_back:
			finish();
			break;
		case R.id.tv_result:
			if(!text.equals("亲，暂无该地区服务")){
				tv_result.setVisibility(View.GONE);
				fl_content.setVisibility(View.VISIBLE);
			}
			break;
		default:
			break;
		}
	}

}
