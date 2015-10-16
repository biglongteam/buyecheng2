package com.hangzhou.tonight.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;

import android.util.Log;

import com.alibaba.sdk.android.oss.OSSService;
import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.model.AccessControlList;
import com.alibaba.sdk.android.oss.model.AuthenticationType;
import com.alibaba.sdk.android.oss.model.ClientConfiguration;
import com.alibaba.sdk.android.oss.model.TokenGenerator;
import com.alibaba.sdk.android.oss.util.OSSLog;
import com.alibaba.sdk.android.oss.util.OSSToolKit;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.comm.Constant;
import com.hangzhou.tonight.entity.BannerEntity;
import com.hangzhou.tonight.entity.NearByGroup;
import com.hangzhou.tonight.entity.NearByPeople;
import com.hangzhou.tonight.model.LoginConfig;
import com.hangzhou.tonight.service.IMChatService;
import com.hangzhou.tonight.service.IMContactService;
import com.hangzhou.tonight.service.IMSystemMsgService;
import com.hangzhou.tonight.service.ReConnectService;
import com.hangzhou.tonight.util.MyPreference;
import com.hoo.ad.base.helper.OsHelper;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;


public class BaseApplication extends Application {
	
	private static  BaseApplication instance = null;
	public static Context mContext;
	private Bitmap mDefaultAvatar;
	private static final String AVATAR_DIR = "avatar/";
	private static final String PHOTO_ORIGINAL_DIR = "photo/original/";
	private static final String PHOTO_THUMBNAIL_DIR = "photo/thumbnail/";
	private static final String STATUS_PHOTO_DIR = "statusphoto/";
	public Map<String, SoftReference<Bitmap>> mAvatarCache = new HashMap<String, SoftReference<Bitmap>>();
	public Map<String, SoftReference<Bitmap>> mPhotoOriginalCache = new HashMap<String, SoftReference<Bitmap>>();
	public Map<String, SoftReference<Bitmap>> mPhotoThumbnailCache = new HashMap<String, SoftReference<Bitmap>>();
	public Map<String, SoftReference<Bitmap>> mStatusPhotoCache = new HashMap<String, SoftReference<Bitmap>>();

	public List<NearByPeople> mNearByPeoples = new ArrayList<NearByPeople>();
	public  List<NearByGroup> mNearByGroups = new ArrayList<NearByGroup>();

	public static long sessionCreateTime = 0;
	public static String sessionId = null;
	public static List<BannerEntity> banners = new ArrayList<BannerEntity>();
	public static List<String> mEmoticons = new ArrayList<String>();
	public static Map<String, Integer> mEmoticonsId = new HashMap<String, Integer>();
	public static List<String> mEmoticons_Zem = new ArrayList<String>();
	public static List<String> mEmoticons_Zemoji = new ArrayList<String>();

	public MyPreference myPreference;
	public SharedPreferences preferences;
	public LocationClient mLocationClient;
	public double mLongitude;
	public double mLatitude;
	public String mCity="";

	
	
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		String processName = OsHelper.getProcessName(this, android.os.Process.myPid());
        if (processName != null) {
            boolean defaultProcess = processName.equals(OsHelper.getPackage(getBaseContext()));
            if (defaultProcess) { onCreateDefaultApp(); }
        }
		
	}
	
	
	//用于执行当前系统初始化工作
	public void onCreateDefaultApp(){
		instance = this;
		if (mContext == null) {
			mContext = this;
		}
		mDefaultAvatar = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_common_def_header);
	/*	for (int i = 1; i < 64; i++) {
			String emoticonsName = "[zem" + i + "]";
			int emoticonsId = getResources().getIdentifier("zem" + i,
					"drawable", getPackageName());
			mEmoticons.add(emoticonsName);
			mEmoticons_Zem.add(emoticonsName);
			mEmoticonsId.put(emoticonsName, emoticonsId);
		}
		for (int i = 1; i < 59; i++) {
			String emoticonsName = "[zemoji" + i + "]";
			int emoticonsId = getResources().getIdentifier("zemoji_e" + i,
					"drawable", getPackageName());
			mEmoticons.add(emoticonsName);
			mEmoticons_Zemoji.add(emoticonsName);
			mEmoticonsId.put(emoticonsName, emoticonsId);
		}
		
*/
		
		//startService(new Intent(getApplicationContext(), XXService.class));
		myPreference = MyPreference.getInstance(this);
		preferences = getSharedPreferences(Constant.LOGIN_SET, 0);
		// 获取当前用户位置
		mLocationClient = new LocationClient(getApplicationContext());
		mLocationClient.setAK("60b43d1a9513d904b6aa2948b27b4a20");
		/*LocationClientOption option = new LocationClientOption();

		option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
*/
		mLocationClient.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceivePoi(BDLocation arg0) {

			}

			@Override
			public void onReceiveLocation(BDLocation arg0) {
				mLongitude = arg0.getLongitude();
				mLatitude = arg0.getLatitude();
				Log.i("地理位置", "经度:" + mLongitude + ",纬度:" + mLatitude);
				List<Address> addList = null;			
				Geocoder ge = new Geocoder(getApplicationContext());
				try {
					addList = ge.getFromLocation(mLatitude, mLongitude, 1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (addList != null && addList.size() > 0) {
					for (int i = 0; i < addList.size(); i++) {
						Address ad = addList.get(i);
						mCity = ad.getLocality();
					}
				}
				System.out.println("您当前的城市是:\n" + mCity);
				if(mCity!=null&&!mCity.equals("")){
					if(mCity.endsWith("市"))
					mCity=mCity.replace("市", "");
				}
				myPreference.setLocation_j(mLongitude+"");
				myPreference.setLocation_w(mLatitude+"");
				myPreference.setCity(mCity);
				mLocationClient.stop();
			}
		});
		mLocationClient.start();
		mLocationClient.requestOfflineLocation();
		System.out.println("开始获取");
		
		File cacheDir = StorageUtils.getOwnCacheDirectory(
				getApplicationContext(), "tonght/Cache");
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				mContext).memoryCacheExtraOptions(480, 800)
				// default = device screen dimensions 推荐
				.diskCacheExtraOptions(480, 800, null)
				// .推荐diskCacheExtraOptions(480, 800, null)
				.threadPoolSize(3)
				// default 推荐1-5
				.threadPriority(Thread.NORM_PRIORITY - 2)
				// default
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				// default
				.denyCacheImageMultipleSizesInMemory()
				// 设置内存缓存不允许缓存一张图片的多个尺寸，默认允许。
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				// 使用强引用的缓存使用它，不过推荐使用weak与strong引用结合的UsingFreqLimitedMemoryCache或者使用全弱引用的WeakMemoryCache
				.memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
				// default
				.discCache(new UnlimitedDiscCache(cacheDir))
				// 自定义缓存路径
				.diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
				.diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
				.imageDownloader(new BaseImageDownloader(mContext)) // default
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
				.writeDebugLogs().build();
		ImageLoader.getInstance().init(config);
		
		
		/*----------------阿里云OSS初始化----------------*/
		initOssServer();
	}
	
	public static OSSService ossService;
	public static String bucketName;
	private String accessKey,screctKey;
	private void initOssServer(){
		    Context context = getBaseContext();
		    try {
				accessKey = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData.getString("com.alibaba.app.ossak");
				screctKey = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData.getString("com.alibaba.app.osssk");
				bucketName= context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData.getString("com.alibaba.app.ossbucketname");
		    } catch (NameNotFoundException e) {
				e.printStackTrace();
			}
            
		    // 开启Log
	        OSSLog.enableLog();

	        ossService = OSSServiceProvider.getService();

	        ossService.setApplicationContext(context);
	        ossService.setGlobalDefaultHostId("oss-cn-hangzhou.aliyuncs.com"); // 设置region host 即 endpoint
	        ossService.setGlobalDefaultACL(AccessControlList.PRIVATE); // 默认为private
	        ossService.setAuthenticationType(AuthenticationType.ORIGIN_AKSK); // 设置加签类型为原始AK/SK加签
	        ossService.setGlobalDefaultTokenGenerator(new TokenGenerator() { // 设置全局默认加签器
	            @Override
	            public String generateToken(String httpMethod, String md5, String type, String date,String ossHeaders, String resource) {
	                String content = httpMethod + "\n" + md5 + "\n" + type + "\n" + date + "\n" + ossHeaders + resource;
	                return OSSToolKit.generateToken(accessKey, screctKey, content);
	            }
	        });
	        ossService.setCustomStandardTimeWithEpochSec(System.currentTimeMillis() / 1000);

	        ClientConfiguration conf = new ClientConfiguration();
	        conf.setConnectTimeout(30 * 1000); 			// 设置全局网络连接超时时间，默认30s
	        conf.setSocketTimeout(30 * 1000); 			// 设置全局socket超时时间，默认30s
	        conf.setMaxConcurrentTaskNum(5); 			// 替换设置最大连接数接口，设置全局最大并发任务数，默认为6
	        conf.setIsSecurityTunnelRequired(false); 	// 是否使用https，默认为false
	        ossService.setClientConfiguration(conf);
	}
	
	public void startService() {
		// 好友联系人服务
		Intent server = new Intent(mContext, IMContactService.class);
		mContext.startService(server);
		// 聊天服务
		Intent chatServer = new Intent(mContext, IMChatService.class);
		mContext.startService(chatServer);
		// 自动恢复连接服务
		Intent reConnectService = new Intent(mContext, ReConnectService.class);
		mContext.startService(reConnectService);
		// 系统消息连接服务
		Intent imSystemMsgService = new Intent(mContext,
				IMSystemMsgService.class);
		mContext.startService(imSystemMsgService);
	}

	/**
	 * 
	 * 销毁服务.
	 * 
	 */
	public void stopService() {
		// 好友联系人服务
		Intent server = new Intent(mContext, IMContactService.class);
		mContext.stopService(server);
		// 聊天服务
		Intent chatServer = new Intent(mContext, IMChatService.class);
		mContext.stopService(chatServer);

		// 自动恢复连接服务
		Intent reConnectService = new Intent(mContext, ReConnectService.class);
		mContext.stopService(reConnectService);

		// 系统消息连接服务
		Intent imSystemMsgService = new Intent(mContext,
				IMSystemMsgService.class);
		mContext.stopService(imSystemMsgService);
	}
	
	
	public void saveLoginConfig(LoginConfig loginConfig) {
		preferences.edit()
				.putString(Constant.XMPP_HOST, loginConfig.getXmppHost())
				.commit();
		preferences.edit()
				.putInt(Constant.XMPP_PORT, loginConfig.getXmppPort()).commit();
		preferences
				.edit()
				.putString(Constant.XMPP_SEIVICE_NAME,
						loginConfig.getXmppServiceName()).commit();
		preferences.edit()
				.putString(Constant.USERNAME, loginConfig.getUsername())
				.commit();
		preferences.edit()
				.putString(Constant.PASSWORD, loginConfig.getPassword())
				.commit();
		preferences.edit()
				.putBoolean(Constant.IS_AUTOLOGIN, loginConfig.isAutoLogin())
				.commit();
		preferences.edit()
				.putBoolean(Constant.IS_NOVISIBLE, loginConfig.isNovisible())
				.commit();
		preferences.edit()
				.putBoolean(Constant.IS_REMEMBER, loginConfig.isRemember())
				.commit();
		preferences.edit()
				.putBoolean(Constant.IS_ONLINE, loginConfig.isOnline())
				.commit();
		preferences.edit()
				.putBoolean(Constant.IS_FIRSTSTART, loginConfig.isFirstStart())
				.commit();
	}

	public LoginConfig getLoginConfig() {
		LoginConfig loginConfig = new LoginConfig();
		String a = preferences.getString(Constant.XMPP_HOST, null);
		String b = getResources().getString(R.string.xmpp_host);
		loginConfig.setXmppHost(preferences.getString(Constant.XMPP_HOST,
				getResources().getString(R.string.xmpp_host)));
		loginConfig.setXmppPort(preferences.getInt(Constant.XMPP_PORT,
				getResources().getInteger(R.integer.xmpp_port)));
		loginConfig.setUsername(preferences.getString(Constant.USERNAME, null));
		loginConfig.setPassword(preferences.getString(Constant.PASSWORD, null));
		loginConfig.setXmppServiceName(preferences.getString(
				Constant.XMPP_SEIVICE_NAME,
				getResources().getString(R.string.xmpp_service_name)));
		loginConfig.setAutoLogin(preferences.getBoolean(Constant.IS_AUTOLOGIN,
				getResources().getBoolean(R.bool.is_autologin)));
		loginConfig.setNovisible(preferences.getBoolean(Constant.IS_NOVISIBLE,
				getResources().getBoolean(R.bool.is_novisible)));
		loginConfig.setRemember(preferences.getBoolean(Constant.IS_REMEMBER,
				getResources().getBoolean(R.bool.is_remember)));
		loginConfig.setFirstStart(preferences.getBoolean(
				Constant.IS_FIRSTSTART, true));
		return loginConfig;
	}

	
	
	

	
	public static BaseApplication getInstance() {
		return instance;
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		Log.e("BaseApplication", "onLowMemory");
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		Log.e("BaseApplication", "onTerminate");
	}

	public Bitmap getAvatar(String imageName) {
		if (mAvatarCache.containsKey(imageName)) {
			Reference<Bitmap> reference = mAvatarCache.get(imageName);
			if (reference.get() == null || reference.get().isRecycled()) {
				mAvatarCache.remove(imageName);
			} else {
				return reference.get();
			}
		}
		InputStream is = null;
		Bitmap bitmap = null;
		try {
			is = getAssets().open(AVATAR_DIR + imageName);
			bitmap = BitmapFactory.decodeStream(is);
			if (bitmap == null) {
				throw new FileNotFoundException(imageName + "is not find");
			}
			mAvatarCache.put(imageName, new SoftReference<Bitmap>(bitmap));
			return bitmap;
		} catch (Exception e) {
			return mDefaultAvatar;
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {

			}
		}
	}

	public Bitmap getPhotoOriginal(String imageName) {
		if (mPhotoOriginalCache.containsKey(imageName)) {
			Reference<Bitmap> reference = mPhotoOriginalCache.get(imageName);
			if (reference.get() == null || reference.get().isRecycled()) {
				mPhotoOriginalCache.remove(imageName);
			} else {
				return reference.get();
			}
		}
		InputStream is = null;
		Bitmap bitmap = null;
		try {
			is = getAssets().open(PHOTO_ORIGINAL_DIR + imageName);
			bitmap = BitmapFactory.decodeStream(is);
			if (bitmap == null) {
				throw new FileNotFoundException(imageName + "is not find");
			}
			mPhotoOriginalCache.put(imageName,
					new SoftReference<Bitmap>(bitmap));
			return bitmap;
		} catch (Exception e) {
			return null;
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {

			}
		}
	}

	public Bitmap getPhotoThumbnail(String imageName) {
		if (mPhotoThumbnailCache.containsKey(imageName)) {
			Reference<Bitmap> reference = mPhotoThumbnailCache.get(imageName);
			if (reference.get() == null || reference.get().isRecycled()) {
				mPhotoThumbnailCache.remove(imageName);
			} else {
				return reference.get();
			}
		}
		InputStream is = null;
		Bitmap bitmap = null;
		try {
			is = getAssets().open(PHOTO_THUMBNAIL_DIR + imageName);
			bitmap = BitmapFactory.decodeStream(is);
			if (bitmap == null) {
				throw new FileNotFoundException(imageName + "is not find");
			}
			mPhotoThumbnailCache.put(imageName, new SoftReference<Bitmap>(
					bitmap));
			return bitmap;
		} catch (Exception e) {
			return null;
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {

			}
		}
	}

	public Bitmap getStatusPhoto(String imageName) {
		if (mStatusPhotoCache.containsKey(imageName)) {
			Reference<Bitmap> reference = mStatusPhotoCache.get(imageName);
			if (reference.get() == null || reference.get().isRecycled()) {
				mStatusPhotoCache.remove(imageName);
			} else {
				return reference.get();
			}
		}
		InputStream is = null;
		Bitmap bitmap = null;
		try {
			is = getAssets().open(STATUS_PHOTO_DIR + imageName);
			bitmap = BitmapFactory.decodeStream(is);
			if (bitmap == null) {
				throw new FileNotFoundException(imageName + "is not find");
			}
			mStatusPhotoCache.put(imageName, new SoftReference<Bitmap>(bitmap));
			return bitmap;
		} catch (Exception e) {
			return null;
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {

			}
		}
	}
	
	//TODO 经纬度   SESSIONID
	
	
	
}
