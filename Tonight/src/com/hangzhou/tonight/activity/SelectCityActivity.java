package com.hangzhou.tonight.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.adapter.CityAdapter;
import com.hangzhou.tonight.base.BaseActivity;
import com.hangzhou.tonight.base.BaseApplication;
import com.hangzhou.tonight.entity.ActivesEntity;
import com.hangzhou.tonight.entity.City;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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


public class SelectCityActivity extends BaseActivity implements OnClickListener{

	
	BaseApplication myApplication;
	private Context mContext;
	String[] ALLCITIES = new String[] { "上海", "北京", "天津", "重庆", "西安市", "铜川市",
			"宝鸡市", "咸阳市", "渭南市", "延安市", "汉中市", "榆林市", "安康市", "商洛市", "杭州市",
			"宁波市", "温州市", "嘉兴市", "湖州市", "绍兴市", "金华市", "衢州市", "舟山市", "台州市",
			"丽水市", "石家庄市", "唐山市", "秦皇岛市", "邯郸市", "邢台市", "保定市", "张家口市", "承德市",
			"沧州市", "廊坊市", "衡水市", "沈阳市", "大连市", "鞍山市", "抚顺市", "本溪市", "丹东市",
			"锦州市", "营口市", "阜新市", "辽阳市", "盘锦市", "铁岭市", "朝阳市", "葫芦岛市", "济南市",
			"青岛市", "淄博市", "枣庄市", "东营市", "烟台市", "潍坊市", "济宁市", "泰安市", "威海市",
			"日照市", "莱芜市", "临沂市", "德州市", "聊城市", "滨州市", "菏泽市", "广州市", "深圳市",
			"珠海市", "汕头市", "韶关市", "佛山市", "江门市", "湛江市", "茂名市", "肇庆市", "惠州市",
			"梅州市", "汕尾市", "河源市", "阳江市", "清远市", "东莞市", "中山市", "潮州市", "揭阳市",
			"云浮市", "武汉市", "黄石市", "十堰市", "荆州市", "宜昌市", "襄樊市", "鄂州市", "荆门市",
			"孝感市", "黄冈市", "咸宁市", "随州市", "南京市", "无锡市", "徐州市", "常州市", "苏州市",
			"南通市", "连云港市", "淮安市", "盐城市", "扬州市", "镇江市", "泰州市", "宿迁市", "成都市",
			"自贡市", "攀枝花市", "泸州市", "德阳市", "绵阳市", "广元市", "遂宁市", "内江市", "乐山市",
			"南充市", "眉山市", "宜宾市", "广安市", "达州市", "雅安市", "巴中市", "资阳市",
			"阿坝藏族羌族自治州", "甘孜藏族自治州", "凉山彝族自治州"

	};

	ArrayList<City> cityLists = new ArrayList<City>();
	//ArrayList<String> hot_List = new ArrayList<String>();
	TextView tv_currentcity;
	private TextView tvBack,tvTitle;
	EditText et_search;
	TextView tv_result;
	FrameLayout fl_content;
	private String text;
	private int code;
	ArrayList<String> allList=new ArrayList<String>();

	public final String mPageName ="SelectCityActivity";
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
		setContentView(R.layout.layout_selectcity);
		mContext = this;
		myApplication = BaseApplication.getInstance();
		code = getIntent().getExtras().getInt("code");
		initCity();
		initViews() ;
		initEvents();
		init();

	}
	private void initCity() {
		File dbfile = new File(Environment.getExternalStorageDirectory() + "/citytonight/tonight.s3db");
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
		
		Cursor cursor = null;
		cursor = db.query("cfg_city", new String[]{"id","name"}, null, null, null, null, null);
		while(cursor.moveToNext()){
			City city = new City();
			city.setId(cursor.getString(0));
			city.setName(cursor.getString(1));
			cityLists.add(city);
		}
		
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
		tvTitle.setText("城市切换");
		tv_currentcity=(TextView) findViewById(R.id.tv_currentcity);
		tv_result=(TextView) findViewById(R.id.tv_result);
		fl_content =(FrameLayout) findViewById(R.id.fl_content);
	}
	@Override
	protected void init() {
		tv_currentcity.setText(myApplication.myPreference.getCity());
		 ListView mListView = (ListView) findViewById(R.id.lv);
		 /*ArrayList<City> names = new ArrayList<City>();

		 names.add(new City("北京市"));
		 names.add(new City("上海市"));
		 names.add(new City("杭州市"));
		 names.add(new City("鄭州市"));
		 names.add(new City("珠海市"));
		 names.add(new City("中山市"));
		 names.add(new City("廣州市"));
		 names.add(new City("沈阳市"));
		 */
		mListView.setAdapter(new CityAdapter(mApplication, mContext, cityLists));
		/**
		 * 快速查询ListView的条目点击事件
		 */
		mListView.setOnItemClickListener(new OnItemClickListener() {

			

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					
				City bean  = new City();
				TextView tv = (TextView) view.findViewById(R.id.tv_name);
				bean = (City) tv.getTag();
				Intent intent = new Intent(SelectCityActivity.this,PromotionActivity.class);
				intent.putExtra("name", bean.getName());
				intent.putExtra("id", bean.getId());
				setResult(code, intent);
				finish();

			}
		});
		
	}
	@Override
	protected void initEvents() {
		tvBack.setOnClickListener(this);
		tv_result.setOnClickListener(this);
		
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
