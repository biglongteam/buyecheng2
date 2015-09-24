package com.hangzhou.tonight.activity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.ab.view.listener.AbOnItemClickListener;
import com.ab.view.sliding.AbSlidingPlayView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.LoginActivity;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.adapter.ActivesListAdapter;
import com.hangzhou.tonight.base.BaseApplication;
import com.hangzhou.tonight.base.Config;
import com.hangzhou.tonight.entity.ActivesEntity;
import com.hangzhou.tonight.entity.ActivesInfo;
import com.hangzhou.tonight.maintabs.TabItemActivity;
import com.hangzhou.tonight.util.Base64Utils;
import com.hangzhou.tonight.util.HttpRequest;
import com.hangzhou.tonight.util.JsonResolveUtils;
import com.hangzhou.tonight.util.JsonUtils;
import com.hangzhou.tonight.util.MyPreference;
import com.hangzhou.tonight.util.PreferenceConstants;
import com.hangzhou.tonight.util.RC4Utils;
import com.hangzhou.tonight.view.HeaderLayout;
import com.hangzhou.tonight.view.HeaderLayout.HeaderStyle;
import com.hangzhou.tonight.view.HeaderLayout.SearchState;
import com.hangzhou.tonight.view.HeaderLayout.onMiddleImageButtonClickListener;
import com.hangzhou.tonight.view.HeaderLayout.onSearchListener;
import com.hangzhou.tonight.view.HeaderSpinner;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
* @ClassName: 活动 详情主页 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author yanchao 
* @date 2015-8-30 下午5:17:58 
 */
public class PromotionDetailActivity extends TabItemActivity implements OnClickListener
{

	private Context mContext;
	private HeaderLayout mHeaderLayout;
	private HeaderSpinner mHeaderSpinner;
	//private PromotionListFragment mPeopleFragment;
	private Handler mHander;
	private TextView tvBack,tvTitle;
	private TextView tv_act_title,tv_act_time,tv_act_address,tv_neirong1,
	tv_neirong2,tv_neirong3,tv_neirong4,tv_neirong5,tv_neirong6,tv_xuzhi1,tv_charge;
	private int currentPage=1,//当期页码
            pageCount = 1,//总页数
            pageSize = 15;//每页数据量
	
	private RelativeLayout rl_fav,rl_share;
	private Button bt_lianxi,bt_goumai;
	private String act_id;
	private ActivesInfo actInfo;
	ImageLoader imageLoader;
	DisplayImageOptions options;
	private AbSlidingPlayView mAbSlidingPlayView;
	private String tel;
	private boolean isFav = false;
	private String fav;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_promotion_detail);
		mContext = this;
		act_id = getIntent().getStringExtra("id");
		initPopupWindow();
		initViews();
		initEvents();
		init();
		getDataDetail();
		
		
		mAbSlidingPlayView = (AbSlidingPlayView) findViewById(R.id.mAbSlidingPlayView);
		//mAbSlidingPlayView.setNavHorizontalGravity(Gravity.CENTER);
		// mAbSlidingPlayView.setParentHScrollView(menuLayout);
		

		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.kc_picture)// 正在加载
				.showImageForEmptyUri(R.drawable.kc_picture)// 空图片
				.showImageOnFail(R.drawable.kc_picture)// 错误图片
				.cacheOnDisk(true)//设置硬盘缓存
				.considerExifParams(true)
				
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		mAbSlidingPlayView.startPlay();
		// adapter = new MyAdapter();
		mAbSlidingPlayView.setOnItemClickListener(new AbOnItemClickListener() {

			@Override
			public void onClick(int position) {
				Toast.makeText(mContext, position + "", 1000).show();
			}
		});
		
	}
	
	@Override
	protected void initViews() {
		tvBack = (TextView) findViewById(R.id.tv_header_back);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		
		//设置 scrollView 定位到顶部
		LinearLayout ll_promotion_detail = (LinearLayout) findViewById(R.id.ll_promotion_detail);
		ll_promotion_detail.setFocusable(true);
		ll_promotion_detail.setFocusableInTouchMode(true);
		ll_promotion_detail.requestFocus();
		
		tv_act_title = (TextView) findViewById(R.id.tv_act_title);
		tv_act_time = (TextView) findViewById(R.id.tv_act_time);
		tv_act_address = (TextView) findViewById(R.id.tv_act_address);
		tv_neirong1 = (TextView) findViewById(R.id.tv_neirong1);
		/*tv_neirong2 = (TextView) findViewById(R.id.tv_neirong2);
		tv_neirong3 = (TextView) findViewById(R.id.tv_neirong3);
		tv_neirong4 = (TextView) findViewById(R.id.tv_neirong4);*/
		tv_xuzhi1 = (TextView) findViewById(R.id.tv_xuzhi1);
		tv_charge = (TextView) findViewById(R.id.tv_charge);
		//tv_xuzhi6 = (TextView) findViewById(R.id.tv_xuzhi6);
		rl_fav = (RelativeLayout) findViewById(R.id.rl_fav);
		rl_share = (RelativeLayout) findViewById(R.id.rl_share);
		bt_lianxi = (Button) findViewById(R.id.bt_lianxi);
		bt_goumai = (Button) findViewById(R.id.bt_goumai);
	}

	@Override
	protected void initEvents() {
		tvBack.setOnClickListener(this);
		rl_fav.setOnClickListener(this);
		rl_share.setOnClickListener(this);
		bt_lianxi.setOnClickListener(this);
		bt_goumai.setOnClickListener(this);

	}

	@Override
	protected void init() {
		mHander = new Handler();
		fav = MyPreference.getInstance(mContext).getUserFact();
		/*mPeopleFragment = new PromotionListFragment(mApplication, this, this);
		//mGroupFragment = new NearByGroupFragment(mApplication, this, this);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.nearby_layout_content, mPeopleFragment).commit();*/
	}

	private void initPopupWindow() {
		/*mPopupWindow = new NearByPopupWindow(this);
		mPopupWindow.setOnSubmitClickListener(new onSubmitClickListener() {

			@Override
			public void onClick() {
				mPeopleFragment.onManualRefresh();
			}
		});
		mPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				mHeaderSpinner.initSpinnerState(false);
			}
		});*/
	}

	/*public class OnSpinnerClickListener implements onSpinnerClickListener {

		@Override
		public void onClick(boolean isSelect) {
			if (isSelect) {
				mPopupWindow
						.showViewTopCenter(findViewById(R.id.nearby_layout_root));
			} else {
				mPopupWindow.dismiss();
			}
		}
	}*/

	public class OnSearchClickListener implements onSearchListener {

		@Override
		public void onSearch(EditText et) {
			String s = et.getText().toString().trim();
			if (TextUtils.isEmpty(s)) {
				showCustomToast("请输入搜索关键字");
				et.requestFocus();
			} else {
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(PromotionDetailActivity.this
								.getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
				putAsyncTask(new AsyncTask<Void, Void, Boolean>() {

					@Override
					protected void onPreExecute() {
						super.onPreExecute();
						mHeaderLayout.changeSearchState(SearchState.SEARCH);
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
						mHeaderLayout.changeSearchState(SearchState.INPUT);
						showCustomToast("未找到搜索的群");
					}
				});
			}
		}

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
				showLoadingDialog("正在请求,请稍后...");
			}

			@Override
			protected String doInBackground(Void... params) {

				Map<String, String> param =setParams(currentPage,0);
				
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
				
				//{"actInfo":{"act_id":"35","address":"杭州市江干区天城路88号","content":"3-4人畅爽套餐    1份     980元\n啤酒无限畅饮\n18:00到凌晨2:00，欢唱8小时\n门店价格：2480","des":"啤酒无限畅饮","endtime":"1470844800","img":"[\"0_0QQ%E6%88%AA%E5%9B%BE20150813101248.png\",\"0_1QQ%E6%88%AA%E5%9B%BE20150813101056.png\",\"0_2QQ%E6%88%AA%E5%9B%BE20150813101145.png\",\"0_3QQ%E6%88%AA%E5%9B%BE20150813101200.png\",\"0_4QQ%E6%88%AA%E5%9B%BE20150813101209.png\",\"0_7QQ%E6%88%AA%E5%9B%BE20150813101238.png\"]","lat":"30.293052","lon":"120.20591","name":"皇冠娱乐会所","phone":"15257128999","price":"0.00","review_num":"0","sales_num":"0","starttime":"1439395200","tip":"每张糯米券限20人使用，超出收费标准：超出收费标准：按照商家为标准，如有疑问请咨询商家\n每次消费不限使用糯米券张数\n包厢安排为：包厢安排为：小1包厢：3-4人，小2包厢：5-8人，中包厢：15-20人，大包厢；15-20人","title":"价值2480元15-20人欢唱套餐","value":"0.00"},"reviews":[],"s":1}
				JSONObject jsonObject = object.getJSONObject("actInfo"); 
				actInfo = JSON.parseObject(jsonObject.toString(), ActivesInfo.class);
				initData();
			}

			
		}.execute();
	}

	private Map<String, String> setParams(int currentPage,int flag){
		
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("act_id", act_id);
		ArrayList<Object> arry = new ArrayList<Object>();
		if(flag==0){
			arry.add(0, "getActInfo");
		}else {
			if(fav.equals("未收藏")){
				parms.put("state", 1);//state(1为收藏，0为取消收藏)act_id(活动ID)；state(1为收藏，0为取消收藏)
				
			}else {
				parms.put("state",0);//state(1为收藏，0为取消收藏)
			}
			arry.add(0, "setFavoriteAct");
		}
		
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

	public class OnMiddleImageButtonClickListener implements
			onMiddleImageButtonClickListener {

		@Override
		public void onClick() {
			mHeaderLayout.showSearch();
		}
	}

	
	private void initData() {
		tvTitle.setText(actInfo.getName());
		tv_act_title.setText(actInfo.getTitle());
		tv_act_time.setText(actInfo.getStarttime());
		tv_act_address.setText(actInfo.getAddress());
		tv_neirong1.setText(actInfo.getContent());
		tv_xuzhi1.setText(actInfo.getTip());
		tv_charge.setText("￥"+actInfo.getPrice());
		String imgs = actInfo.getImg();
		String []imgArray =  imgs.substring(1, imgs.length()-1).split(",");
		//String img = imgArray[0].substring(2, imgArray[0].length()-1);
		for(int i = 0;i<imgArray.length;i++){
			String  url = Config.ACT_IMG+imgArray[i].toString().substring(1, imgArray[i].length()-1);
			final View mView = View.inflate(PromotionDetailActivity.this,R.layout.play_view_item, null);
			ImageView mView2 = (ImageView) mView.findViewById(R.id.mPlayImage);
			imageLoader.displayImage(url, mView2,options);
			mAbSlidingPlayView.addView(mView);
			
			}
	}
	/*public class OnSwitcherButtonClickListener implements
			onSwitcherButtonClickListener {

		@Override
		public void onClick(SwitcherButtonState state) {
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.setCustomAnimations(R.anim.fragment_fadein,
					R.anim.fragment_fadeout);
			switch (state) {
			case LEFT:
				mHeaderLayout.init(HeaderStyle.TITLE_NEARBY_PEOPLE);
				ft.replace(R.id.nearby_layout_content, mPeopleFragment)
						.commit();
				break;

			case RIGHT:
				mHeaderLayout.init(HeaderStyle.TITLE_NEARBY_GROUP);
				ft.replace(R.id.nearby_layout_content, mGroupFragment).commit();
				break;
			}
		}

	}*/

	/**
	 * 分享功能
	 * 
	 * @param context
	 *            上下文
	 * @param activityTitle
	 *            Activity的名字
	 * @param msgTitle
	 *            消息标题
	 * @param msgText
	 *            消息内容
	 * @param imgPath
	 *            图片路径，不分享图片则传null
	 */
	public void shareMsg(String activityTitle, String msgTitle, String msgText,
			String imgPath) {
		
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, msgTitle);
		sendIntent.setType("text/plain");
		startActivity(sendIntent);
		/*Intent intent = new Intent(Intent.ACTION_SEND);
		if (imgPath == null || imgPath.equals("")) {
			intent.setType("text/plain"); // 纯文本
		} else {
			File f = new File(imgPath);
			if (f != null && f.exists() && f.isFile()) {
				intent.setType("image/jpg");
				Uri u = Uri.fromFile(f);
				intent.putExtra(Intent.EXTRA_STREAM, u);
			}
		}
		intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
		intent.putExtra(Intent.EXTRA_TEXT, msgText);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(Intent.createChooser(intent, activityTitle));*/
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_header_back:
			finish();
			break;
		case R.id.rl_fav://收藏
			setFavoriteAct();
			
			break;
		case R.id.rl_share://分享
			shareMsg(actInfo.getTitle(),actInfo.getTip(),actInfo.getContent(),actInfo.getImg());
			break;
		case R.id.bt_lianxi://
			if(tel!=null){
				tel = actInfo.getPhone();
			}else {
				showCustomToast("电话号码为空");
				return;
			}
			Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel://"+tel));    
            mContext.startActivity(intent); 
			break;
		case R.id.bt_goumai:// 
			//shareMsg(actInfo.getTitle(),actInfo.getTip(),actInfo.getContent(),actInfo.getImg());
			break;
		default:
			break;
		}
	}

	private void setFavoriteAct() {
		new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadingDialog("正在收藏,请稍后...");
			}

			@Override
			protected String doInBackground(Void... params) {

				Map<String, String> param =setParams(currentPage,1);
				
				return HttpRequest.submitPostData(PreferenceConstants.TONIGHT_SERVER,
						param, "UTF-8");
			}

			//[{"act_id":"46","address":"杭州市下城区再行路298号","des":"290元享价值总价值460元的雪花纯生啤酒套餐","endtime":"1451491200","img":"[\"0_0%E9%97%A8%E5%A4%B4.jpg\",\"0_1%E6%A0%BC%E5%B1%80.jpg\",\"0_2%E7%89%B9%E5%86%99.jpg\",\"0_3%E9%85%92%E6%B0%B4.jpg\",\"0_4%E5%A5%97%E9%A4%90.jpg\",\"0_5%E4%BA%BA%E7%89%A9.jpg\"]","lat":"30.312652","lon":"120.177033","mark":"0.0","name":"豪斯酒吧","price":"0.00","sales_num":"0","starttime":"1439827200","title":"畅销套餐","value":"0.00"}
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				com.alibaba.fastjson.JSONObject object = JSON.parseObject(result);
				
				if(fav.equals("未收藏")){
					showCustomToast(act_id);
					MyPreference.getInstance(mContext).setUserFact("已收藏");
				}else {
					MyPreference.getInstance(mContext).setUserFact("未收藏");
					showCustomToast("取消收藏");
				}
				//{"actInfo":{"act_id":"35","address":"杭州市江干区天城路88号","content":"3-4人畅爽套餐    1份     980元\n啤酒无限畅饮\n18:00到凌晨2:00，欢唱8小时\n门店价格：2480","des":"啤酒无限畅饮","endtime":"1470844800","img":"[\"0_0QQ%E6%88%AA%E5%9B%BE20150813101248.png\",\"0_1QQ%E6%88%AA%E5%9B%BE20150813101056.png\",\"0_2QQ%E6%88%AA%E5%9B%BE20150813101145.png\",\"0_3QQ%E6%88%AA%E5%9B%BE20150813101200.png\",\"0_4QQ%E6%88%AA%E5%9B%BE20150813101209.png\",\"0_7QQ%E6%88%AA%E5%9B%BE20150813101238.png\"]","lat":"30.293052","lon":"120.20591","name":"皇冠娱乐会所","phone":"15257128999","price":"0.00","review_num":"0","sales_num":"0","starttime":"1439395200","tip":"每张糯米券限20人使用，超出收费标准：超出收费标准：按照商家为标准，如有疑问请咨询商家\n每次消费不限使用糯米券张数\n包厢安排为：包厢安排为：小1包厢：3-4人，小2包厢：5-8人，中包厢：15-20人，大包厢；15-20人","title":"价值2480元15-20人欢唱套餐","value":"0.00"},"reviews":[],"s":1}
			}
			
		}.execute();
		
	}

}
