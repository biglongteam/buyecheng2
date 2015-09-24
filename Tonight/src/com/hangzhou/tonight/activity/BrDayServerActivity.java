package com.hangzhou.tonight.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.adapter.BrdayListAdapter;
import com.hangzhou.tonight.adapter.DriverListAdapter;
import com.hangzhou.tonight.base.Config;
import com.hangzhou.tonight.entity.ActivesInfo;
import com.hangzhou.tonight.entity.BrEventsEntity;
import com.hangzhou.tonight.entity.DriverEntity;
import com.hangzhou.tonight.maintabs.TabItemActivity;
import com.hangzhou.tonight.util.Base64Utils;
import com.hangzhou.tonight.util.HttpRequest;
import com.hangzhou.tonight.util.JsonUtils;
import com.hangzhou.tonight.util.PreferenceConstants;
import com.hangzhou.tonight.util.RC4Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
* @ClassName: BrDayServerActivity 
* @Description: TODO(生日宴会 策划) 
* @author yanchao 
* @date 2015-9-7 下午5:51:22 
*
 */
public class BrDayServerActivity extends TabItemActivity implements OnClickListener{

	
	private String seller_id;

	private TextView tvBack,tvTitle,tvName,tvDesc;
	RelativeLayout index_head;
	private BrEventsEntity actInfo;
	private List<BrEventsEntity> actInfos = new ArrayList<BrEventsEntity>();
	private Context mContext;
	private ListView xListView;
	private BrdayListAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_brday_server);
		mContext = this;
		seller_id = getIntent().getStringExtra("id");
		initViews();
		initEvents();
		init();
		getDataDetail();
	}
	@Override
	protected void init() {
		tvTitle.setText("活动策划");
	}

	@Override
	protected void initViews() {
		tvBack = (TextView) findViewById(R.id.tv_header_back);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		xListView = (ListView) findViewById(R.id.brday_list);
	}

	@Override
	protected void initEvents() {
		tvBack.setOnClickListener(this);
	}
	
/**
	
	* @Title: getDataList 
	
	* @Description: TODO(网络请求 活动列表数据) 
	
	* @param     设定文件 
	
	* @return void    返回类型 
	
	* @throws
	 */
	private void getDataDetail() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadingDialog("正在登录,请稍后...");
			}

			@Override
			protected String doInBackground(Void... params) {
				Map<String, String> param =setParams();
				return HttpRequest.submitPostData(PreferenceConstants.TONIGHT_SERVER,
						param, "UTF-8");
			}

			//[{"act_id":"46","address":"杭州市下城区再行路298号","des":"290元享价值总价值460元的雪花纯生啤酒套餐","endtime":"1451491200","img":"[\"0_0%E9%97%A8%E5%A4%B4.jpg\",\"0_1%E6%A0%BC%E5%B1%80.jpg\",\"0_2%E7%89%B9%E5%86%99.jpg\",\"0_3%E9%85%92%E6%B0%B4.jpg\",\"0_4%E5%A5%97%E9%A4%90.jpg\",\"0_5%E4%BA%BA%E7%89%A9.jpg\"]","lat":"30.312652","lon":"120.177033","mark":"0.0","name":"豪斯酒吧","price":"0.00","sales_num":"0","starttime":"1439827200","title":"畅销套餐","value":"0.00"}
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				com.alibaba.fastjson.JSONObject object = JSON
						.parseObject(result);
				
				//{"events":[{"title":"\u751f\u65e5\u6d3e\u5bf9","img":"http:\/\/tonimg.51tonight.com\/act\/49_0QQ%E6%88%AA%E5%9B%BE20150825222928.png","des":"\u63cf\u8ff0\u63cf\u8ff0\u63cf\u8ff0\u63cf\u8ff0\u63cf\u8ff0\u63cf\u8ff0\u63cf\u8ff0\u63cf\u8ff0\u63cf\u8ff0\u63cf\u8ff0\u63cf\u8ff0\u63cf\u8ff0","phone":"10086"},{"title":"\u8282\u65e5\u6d3e\u5bf9","img":"http:\/\/tonimg.51tonight.com\/act\/49_0QQ%E6%88%AA%E5%9B%BE20150825222928.png","des":"\u63cf\u8ff0\u63cf\u8ff0\u63cf\u8ff0\u63cf\u8ff0\u63cf\u8ff0\u63cf\u8ff0\u63cf\u8ff0\u63cf\u8ff0\u63cf\u8ff0\u63cf\u8ff0\u63cf\u8ff0\u63cf\u8ff0","phone":"10086"}],"s":1}9B%BE20150813101209.png\",\"0_7QQ%E6%88%AA%E5%9B%BE20150813101238.png\"]","lat":"30.293052","lon":"120.20591","name":"皇冠娱乐会所","phone":"15257128999","price":"0.00","review_num":"0","sales_num":"0","starttime":"1439395200","tip":"每张糯米券限20人使用，超出收费标准：超出收费标准：按照商家为标准，如有疑问请咨询商家\n每次消费不限使用糯米券张数\n包厢安排为：包厢安排为：小1包厢：3-4人，小2包厢：5-8人，中包厢：15-20人，大包厢；15-20人","title":"价值2480元15-20人欢唱套餐","value":"0.00"},"reviews":[],"s":1}
				JSONArray jsonArray = object.getJSONArray("events"); 
				actInfos = JSON.parseArray(jsonArray.toString(), BrEventsEntity.class);
				mAdapter = new BrdayListAdapter(mApplication, mContext,actInfos);
				xListView.setAdapter(mAdapter);
			}

		}.execute();
		
	}

	private Map<String, String> setParams(){
		
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("seller_id", seller_id);
		ArrayList<Object> arry = new ArrayList<Object>();
		arry.add(0, "getSellerEvent");
		arry.add(1, 0);
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

		default:
			break;
		}
		
	}

}
