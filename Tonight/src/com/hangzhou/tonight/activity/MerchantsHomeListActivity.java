package com.hangzhou.tonight.activity;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

import com.ab.view.listener.AbOnItemClickListener;
import com.ab.view.sliding.AbSlidingPlayView;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.adapter.ActivesListAdapter;
import com.hangzhou.tonight.adapter.MerchantListAdapter;
import com.hangzhou.tonight.base.BaseApplication;
import com.hangzhou.tonight.entity.ActivesEntity;
import com.hangzhou.tonight.entity.MerchantEntity;
import com.hangzhou.tonight.maintabs.TabItemActivity;
import com.hangzhou.tonight.util.Base64Utils;
import com.hangzhou.tonight.util.HttpRequest;
import com.hangzhou.tonight.util.IntentJumpUtils;
import com.hangzhou.tonight.util.JsonUtils;
import com.hangzhou.tonight.util.MyPreference;
import com.hangzhou.tonight.util.PreferenceConstants;
import com.hangzhou.tonight.util.RC4Utils;
import com.hangzhou.tonight.util.ScreenUtils;
import com.hangzhou.tonight.view.HeaderLayout;
import com.hangzhou.tonight.view.HeaderSpinner;
import com.hangzhou.tonight.view.XListView;
import com.hangzhou.tonight.view.HeaderLayout.HeaderStyle;
import com.hangzhou.tonight.view.HeaderLayout.SearchState;
import com.hangzhou.tonight.view.HeaderLayout.onMiddleImageButtonClickListener;
import com.hangzhou.tonight.view.HeaderLayout.onSearchListener;
import com.hangzhou.tonight.view.XListView.IXListViewListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**

* @ClassName: MerchantsHomeListActivity 

* @Description: TODO(商家 首页) 

* @author A18ccms a18ccms_gmail_com 

* @date 2015年9月2日 下午4:55:35 

*
 */
public class MerchantsHomeListActivity extends TabItemActivity implements
OnClickListener, IXListViewListener{

	/** 全局的LayoutInflater对象，已经完成初始化. */
	public LayoutInflater mInflater;
	private Context mContext;
	private XListView xListView;
	private HeaderSpinner mHeaderSpinner;
	// private PromotionListFragment mPeopleFragment;
	private Handler mHander;

	private ImageView imgRq,imgjl,imgJg,imgHp;
	private int currentPage = 1,// 当期页码
			pageCount = 1,// 总页数
			pageSize = 15;// 每页数据量
	private TextView tvCity,et_key;
	private MerchantListAdapter mAdapter;
	public List<MerchantEntity> mActives = new ArrayList<MerchantEntity>();
	public List<MerchantEntity> mActiveMore = new ArrayList<MerchantEntity>();

	private PopupWindow mPopupWindow;
	LinearLayout sortlist;
	private Button btRq,btJl,btJg,btHp,btQx;
	private String cityId;
	private String cityName;
	int type = 1;
	RelativeLayout index_head;
	private int sort = 1;
	String key;
	ImageView imSerach;
	ImageLoader imageLoader;
	DisplayImageOptions options;
	private AbSlidingPlayView mAbSlidingPlayView;
	private boolean flagLoadMore = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_merchant_home);
		mContext = this;
		mInflater = LayoutInflater.from(this);
		initPopupWindow();
		initViews();
		initEvents();
		init();
		getDataList(currentPage,cityId,key,type,sort);
	}
	@Override
	protected void initViews() {
		tvCity = (TextView) findViewById(R.id.tv_city);
		
		et_key = (EditText) findViewById(R.id.et_key);
		et_key.requestFocus();
		index_head = (RelativeLayout) findViewById(R.id.index_head);
		imSerach = (ImageView) findViewById(R.id.title_search);
		View mPlayViews = mInflater.inflate(R.layout.merchant_header, null);
		xListView = (XListView) findViewById(R.id.merchant_list);
		xListView.addHeaderView(mPlayViews);
		mAbSlidingPlayView = (AbSlidingPlayView) findViewById(R.id.mAbSlidingPlayView);
		//mAbSlidingPlayView.setNavHorizontalGravity(Gravity.CENTER);
		// mAbSlidingPlayView.setParentHScrollView(menuLayout);
		sortlist = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.sort_list, null);
		btRq = (Button) sortlist.findViewById(R.id.bt_rq);
		btJg = (Button) sortlist.findViewById(R.id.bt_jg);
		btHp = (Button) sortlist.findViewById(R.id.bt_hp);
		btJl = (Button) sortlist.findViewById(R.id.bt_jl);
		btQx = (Button) sortlist.findViewById(R.id.bt_qx);
		
		
		imgRq = (ImageView) mPlayViews.findViewById(R.id.img_qb);
		imgJg = (ImageView) mPlayViews.findViewById(R.id.img_yzh);
		imgHp = (ImageView) mPlayViews.findViewById(R.id.img_myb);
		imgjl = (ImageView) mPlayViews.findViewById(R.id.img_ktv);
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.kc_picture)// 正在加载
				.showImageForEmptyUri(R.drawable.kc_picture)// 空图片
				.showImageOnFail(R.drawable.kc_picture)// 错误图片
				.cacheOnDisk(true)//设置硬盘缓存
				.considerExifParams(true)
				
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		mAbSlidingPlayView.startPlay();
		mAbSlidingPlayView.setParentListView(xListView);
		// adapter = new MyAdapter();
		mAbSlidingPlayView.setOnItemClickListener(new AbOnItemClickListener() {

			@Override
			public void onClick(int position) {
				Toast.makeText(mContext, position + "", 1000).show();
			}
		});
	}

	@Override
	protected void initEvents() {
		tvCity.setOnClickListener(this);
		imgRq.setOnClickListener(this);
		imgJg.setOnClickListener(this);
		imgHp.setOnClickListener(this);
		imgjl.setOnClickListener(this);
		btRq.setOnClickListener(this);
		btJg.setOnClickListener(this);
		btHp.setOnClickListener(this);
		btJl.setOnClickListener(this);
		btQx.setOnClickListener(this);
		
		imSerach.setOnClickListener(this);
		xListView.setPullLoadEnable(true);
		xListView.setPullRefreshEnable(true);
		xListView.setXListViewListener(this);
		xListView.setOnItemClickListener(new ItemtClickListener());
	}
	@Override
	protected void init() {
		mHander = new Handler();
		//tvTitle.setText("不夜程");
		tvCity.setText(MyPreference.getInstance(this).getCity());
		
		for(int i = 0;i<BaseApplication.banners.size();i++){
			
		final View mView = View.inflate(MerchantsHomeListActivity.this,R.layout.play_view_item, null);
		ImageView mView2 = (ImageView) mView.findViewById(R.id.mPlayImage);
		imageLoader.displayImage(BaseApplication.banners.get(i).getImg(), mView2,options);
		mAbSlidingPlayView.addView(mView);
		
		}
		
	}
	private void initPopupWindow() {
		/*
		 * mPopupWindow = new NearByPopupWindow(this);
		 * mPopupWindow.setOnSubmitClickListener(new onSubmitClickListener() {
		 * 
		 * @Override public void onClick() { mPeopleFragment.onManualRefresh();
		 * } }); mPopupWindow.setOnDismissListener(new OnDismissListener() {
		 * 
		 * @Override public void onDismiss() {
		 * mHeaderSpinner.initSpinnerState(false); } });
		 */
	}

	
	private class ItemtClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			
			MerchantEntity bean  = new MerchantEntity();
				TextView tv = (TextView) view.findViewById(R.id.tv_merchant_name);
				bean = (MerchantEntity) tv.getTag();
				Bundle bundle = new Bundle();
				bundle.putString("id", bean.getSeller_id());
				bundle.putString("name", bean.getName());
				/*bundle.putString(Constants.KEY_ID, bean.getCoach_id());
				bundle.putString(Constants.KEY_CHAT_TEL, bean.getCoach_tel());
				bundle.putString(Constants.KEY_CHAT_USERNAME, bean.getCoach_name());
				bundle.putString(Constants.KEY_CHAT_AVATAR, bean.getHead_img());
				
				bundle.putString("sign", bean.getSign());
				bundle.putString("project", bean.getVal());*/
				IntentJumpUtils.nextActivity(MerchantDetailActivity.class, MerchantsHomeListActivity.this, bundle);
			

		}
	}
	
	
	/*
	 * public class OnSpinnerClickListener implements onSpinnerClickListener {
	 * 
	 * @Override public void onClick(boolean isSelect) { if (isSelect) {
	 * mPopupWindow .showViewTopCenter(findViewById(R.id.nearby_layout_root)); }
	 * else { mPopupWindow.dismiss(); } } }
	 */

	public class OnSearchClickListener implements onSearchListener {

		@Override
		public void onSearch(EditText et) {
			String s = et.getText().toString().trim();
			if (TextUtils.isEmpty(s)) {
				showCustomToast("请输入搜索关键字");
				et.requestFocus();
			} else {
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(MerchantsHomeListActivity.this.getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
				putAsyncTask(new AsyncTask<Void, Void, Boolean>() {

					@Override
					protected void onPreExecute() {
						super.onPreExecute();
						//mHeaderLayout.changeSearchState(SearchState.SEARCH);
					}

					@Override
					protected Boolean doInBackground(Void... params) {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return false;
					}

					@Override
					protected void onPostExecute(Boolean result) {
						super.onPostExecute(result);
						//mHeaderLayout.changeSearchState(SearchState.INPUT);
						showCustomToast("未找到搜索的群");
					}
				});
			}
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
	private void getDataList(final int currentPage,String cityId,String key,final int type,final int sort) {
		new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadingDialog("正在加载,请稍后...");
			}

			@Override
			protected String doInBackground(Void... params) {

				Map<String, String> param = setParams(currentPage);

				return HttpRequest.submitPostData(
						PreferenceConstants.TONIGHT_SERVER, param, "UTF-8");
			}

			// [{"act_id":"46","address":"杭州市下城区再行路298号","des":"290元享价值总价值460元的雪花纯生啤酒套餐","endtime":"1451491200","img":"[\"0_0%E9%97%A8%E5%A4%B4.jpg\",\"0_1%E6%A0%BC%E5%B1%80.jpg\",\"0_2%E7%89%B9%E5%86%99.jpg\",\"0_3%E9%85%92%E6%B0%B4.jpg\",\"0_4%E5%A5%97%E9%A4%90.jpg\",\"0_5%E4%BA%BA%E7%89%A9.jpg\"]","lat":"30.312652","lon":"120.177033","mark":"0.0","name":"豪斯酒吧","price":"0.00","sales_num":"0","starttime":"1439827200","title":"畅销套餐","value":"0.00"}
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				/*
				 * String str = null ; try { str = URLEncoder.encode(result,
				 * "utf-8"); } catch (UnsupportedEncodingException e) { // TODO
				 * Auto-generated catch block e.printStackTrace(); }
				 */
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(result);
					JSONArray jsonArray = jsonObject.getJSONArray("sellers");
					mActives = resolveUtils(jsonArray);
					if(mAdapter==null){
						mAdapter = new MerchantListAdapter(mContext,mActives);
						xListView.setAdapter(mAdapter);
						mAdapter.notifyDataSetChanged();
					}else{
						if(flagLoadMore){
							mAdapter.addAndRefreshListView(mActives);
							if(mActives.size()==0){
								Toast.makeText(mContext, "没有更多数据...", 1000).show();
							}
						}else{
							mAdapter.refreshListView(mActives);
						}
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}.execute();

	}

	private List<MerchantEntity> resolveUtils(
			JSONArray jsonArray) throws JSONException {

		List<MerchantEntity> list = new ArrayList<MerchantEntity>();
		if (jsonArray != null) {
			MerchantEntity people = null;
			JSONObject object = null;
			for (int i = 0; i < jsonArray.length(); i++) {
				object = jsonArray.getJSONObject(i);

				String imgs = object.getString("img");
				String []imgArray =  imgs.split(",");
				String img = imgArray[0].substring(2, imgArray[0].length()-1);
				/*String img = null;
				try {
					img = URLEncoder.encode(imgStr, "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				String value = object.getString("value");
				String price =  object.getString("price");
				String seller_id = object.getString("seller_id");
				String name = object.getString("name");
				String title = object.getString("title");
				String sales_num = object.getString("sales_num");
				String address = object.getString("address");
				String lon = object.getString("lon");
				String lat = object.getString("lat");

				people = new MerchantEntity(seller_id, name, title, img,value,price, sales_num, address, lon, lat);
				/*people = new MerchantEntity(act_id, title, des, img, value,
						price, starttime, endtime, sales_num, name, address,
						lon, lat);*/
				list.add(people);
			}

		}
		return list;
	}

	private Map<String, String> setParams(int currentPage) {

		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("city", 179);
		parms.put("page", currentPage);
		ArrayList<Object> arry = new ArrayList<Object>();
		arry.add(0, "getSellerList");
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

	public class OnMiddleImageButtonClickListener implements
			onMiddleImageButtonClickListener {

		@Override
		public void onClick() {
			//mHeaderLayout.showSearch();
		}
	}

	/*
	 * public class OnSwitcherButtonClickListener implements
	 * onSwitcherButtonClickListener {
	 * 
	 * @Override public void onClick(SwitcherButtonState state) {
	 * FragmentTransaction ft = getSupportFragmentManager() .beginTransaction();
	 * ft.setCustomAnimations(R.anim.fragment_fadein, R.anim.fragment_fadeout);
	 * switch (state) { case LEFT:
	 * mHeaderLayout.init(HeaderStyle.TITLE_NEARBY_PEOPLE);
	 * ft.replace(R.id.nearby_layout_content, mPeopleFragment) .commit(); break;
	 * 
	 * case RIGHT: mHeaderLayout.init(HeaderStyle.TITLE_NEARBY_GROUP);
	 * ft.replace(R.id.nearby_layout_content, mGroupFragment).commit(); break; }
	 * }
	 * 
	 * }
	 */

	@Override
	public void onBackPressed() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_city:
			Bundle bundle = new Bundle();
			bundle.putInt("code", 1002);
			IntentJumpUtils.nextActivity(SelectCityActivity.class, MerchantsHomeListActivity.this,bundle, 1002);
			break;
		case R.id.title_search:
			if(mPopupWindow!=null&&mPopupWindow.isShowing()){
				return;
			}
			key = et_key.getText().toString();
			if(!key.equals("")){
				getDataList(currentPage,cityId,key,type,sort);
			}else{
				
				mPopupWindow = new PopupWindow(sortlist, 450, LayoutParams.WRAP_CONTENT);
				mPopupWindow.showAsDropDown(index_head, ScreenUtils.getScreenWidth(mContext)/5, 200);
			}
			
			break;
			
			case R.id.bt_rq:
				mPopupWindow.dismiss();
				sort = 1;
				getDataList(currentPage,cityId,key,type,sort);
				break;
			case R.id.bt_jg:
				sort = 5;
				mPopupWindow.dismiss();
				getDataList(currentPage,cityId,key,type,sort);
				break;
			case R.id.bt_hp:
				mPopupWindow.dismiss();
				getDataList(currentPage,cityId,key,type,sort);
				break;
			case R.id.bt_jl:
				sort = 2;
				mPopupWindow.dismiss();
				getDataList(currentPage,cityId,key,type,sort);
				break;
			case R.id.bt_qx:
				mPopupWindow.dismiss();
				break;
			
		case R.id.img_qb:
			type = 1;
			getDataList(currentPage,cityId,key,type,sort);
			break;
		case R.id.img_yzh:
			type = 2;
			getDataList(currentPage,cityId,key,type,sort);
			break;
		case R.id.img_myb:
			type = 3;
			getDataList(currentPage,cityId,key,type,sort);
			break;
		case R.id.img_ktv:
			type = 4;
			getDataList(currentPage,cityId,key,type,sort);
			break;
		default:
			break;
		}

	}

	
	@Override
	protected void onActivityResult( int requestCode,int resultCode,
            Intent imageReturnIntent) {
		 if (resultCode != 1002)
	            return;
		
		 if(requestCode==1002){
			cityId =  imageReturnIntent.getExtras().getString("id");
			cityName = imageReturnIntent.getExtras().getString("name");
			tvCity.setText(cityName);
			getDataList(currentPage,cityId,key,type,sort);
			
		 }
	}
	
	protected void loadMore() {
		currentPage++;// 每次 点击加载更多，请求页码 +1
		flagLoadMore = true;
		getDataList(currentPage,cityId,key,type,sort);
	}

	@Override
	public void onRefresh() {
		mHander.postDelayed(new Runnable() {// 处理耗时 操作
					@Override
					public void run() {
						Date currentTime = new Date();
						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyy-MM-dd H:mm:ss");
						String dateString = formatter.format(currentTime);
						flagLoadMore = false;
						currentPage = 1;
						getDataList(currentPage,cityId,key,type,sort);
						xListView.stopRefresh();
						xListView.stopLoadMore();
						xListView.setRefreshTime(dateString);
					}
				}, 1000);

	}

	@Override
	public void onLoadMore() {
		mHander.postDelayed(new Runnable() {// 处理耗时 操作
					@Override
					public void run() {
						loadMore();
						xListView.stopRefresh();
						xListView.stopLoadMore();
					}
				}, 200);

	}
}