package com.hangzhou.tonight.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow.OnDismissListener;

import com.alibaba.fastjson.JSON;
import com.hangzhou.tonight.LoginActivity;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.adapter.ActivesListAdapter;
import com.hangzhou.tonight.adapter.DriverListAdapter;
import com.hangzhou.tonight.entity.ActivesEntity;
import com.hangzhou.tonight.entity.BannerEntity;
import com.hangzhou.tonight.entity.DriverEntity;
import com.hangzhou.tonight.entity.NearByPeople;
import com.hangzhou.tonight.maintabs.TabItemActivity;
import com.hangzhou.tonight.util.Base64Utils;
import com.hangzhou.tonight.util.HttpRequest;
import com.hangzhou.tonight.util.IntentJumpUtils;
import com.hangzhou.tonight.util.JsonResolveUtils;
import com.hangzhou.tonight.util.JsonUtils;
import com.hangzhou.tonight.util.PreferenceConstants;
import com.hangzhou.tonight.util.RC4Utils;
import com.hangzhou.tonight.util.ScreenUtils;
import com.hangzhou.tonight.view.HeaderLayout;
import com.hangzhou.tonight.view.HeaderLayout.HeaderStyle;
import com.hangzhou.tonight.view.HeaderLayout.SearchState;
import com.hangzhou.tonight.view.HeaderLayout.onMiddleImageButtonClickListener;
import com.hangzhou.tonight.view.HeaderLayout.onSearchListener;
import com.hangzhou.tonight.view.HeaderSpinner;
import com.hangzhou.tonight.view.HeaderSpinner.onSpinnerClickListener;
import com.hangzhou.tonight.view.XListView;
import com.hangzhou.tonight.view.XListView.IXListViewListener;

/**
 * @ClassName:带代驾  主页
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author yanchao
 * @date 2015-9-70 下午5:17:58
 * 
 */
public class DriverServerActivity extends TabItemActivity implements
		OnClickListener {

	private Context mContext;
	private ListView xListView;
	private TextView tvBack,tvTitle;
	RelativeLayout index_head;
	private DriverListAdapter mAdapter;
	public List<DriverEntity> mActives = new ArrayList<DriverEntity>();
	private String seller_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_djia_server);
		mContext = this;
		seller_id = getIntent().getStringExtra("id");
		initViews();
		initEvents();
		init();
		getDataList();
	}

	@Override
	protected void initViews() {
		tvBack = (TextView) findViewById(R.id.tv_header_back);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		xListView = (ListView) findViewById(R.id.daijia_list);
	
	}

	@Override
	protected void initEvents() {
		tvBack.setOnClickListener(this);
		//xListView.setOnItemClickListener(new ItemtClickListener());
	}

	@Override
	protected void init() {
		tvTitle.setText("代驾预约");
	}

	private class ItemtClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
				/*ActivesEntity bean  = new ActivesEntity();
				TextView tv = (TextView) view.findViewById(R.id.tv_title);
				bean = (ActivesEntity) tv.getTag();
				Bundle bundle = new Bundle();
				bundle.putString("id", bean.getAct_id());
				IntentJumpUtils.nextActivity(PromotionDetailActivity.class, DriverServerActivity.this, bundle);*/
		}
	}
	
	

	/**
	 * 
	 * @Title: getDataList
	 * 
	 * @Description: TODO(网络请求 活动列表数据)
	 * 
	 * @param 设定文件
	 * 
	 * @return void 返回类型
	 * 
	 * @throws
	 */
	private void getDataList() {
		new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadingDialog("正在登录,请稍后...");
			}

			@Override
			protected String doInBackground(Void... params) {

				Map<String, String> param = setParams();

				return HttpRequest.submitPostData(
						PreferenceConstants.TONIGHT_SERVER, param, "UTF-8");
			}

			// [{"act_id":"46","address":"杭州市下城区再行路298号","des":"290元享价值总价值460元的雪花纯生啤酒套餐","endtime":"1451491200","img":"[\"0_0%E9%97%A8%E5%A4%B4.jpg\",\"0_1%E6%A0%BC%E5%B1%80.jpg\",\"0_2%E7%89%B9%E5%86%99.jpg\",\"0_3%E9%85%92%E6%B0%B4.jpg\",\"0_4%E5%A5%97%E9%A4%90.jpg\",\"0_5%E4%BA%BA%E7%89%A9.jpg\"]","lat":"30.312652","lon":"120.177033","mark":"0.0","name":"豪斯酒吧","price":"0.00","sales_num":"0","starttime":"1439827200","title":"畅销套餐","value":"0.00"}
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				
				com.alibaba.fastjson.JSONObject object = JSON
						.parseObject(result);
				
				com.alibaba.fastjson.JSONArray jsonArray = object.getJSONArray("drivers");
				mActives = JSON.parseArray(jsonArray.toString(), DriverEntity.class);
			/*	JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(result);
					JSONArray jsonArray = jsonObject.getJSONArray("drivers");
					mActives = resolveUtils(jsonArray);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/

				mAdapter = new DriverListAdapter(mApplication, mContext,mActives);
				xListView.setAdapter(mAdapter);
			}
		}.execute();

	}


	private Map<String, String> setParams() {

		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("seller_id", seller_id);
		ArrayList<Object> arry = new ArrayList<Object>();
		arry.add(0, "getSellerDriver");
		arry.add(1, 0);
		arry.add(2, parms);
		String data0 = RC4Utils.RC4("mdwi5uh2p41nd4ae23qy4",
				JsonUtils.list2json(arry));
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
			if (!encoded1.equals("")) {
				decode = new String(Base64.decode(encoded1, Base64.DEFAULT),
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
		case R.id.tv_header_back://切换城市
			
			finish();
			break;
		default:
			break;
		}
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
}
