package com.hangzhou.tonight;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.base.BaseActivity;
import com.hangzhou.tonight.base.BaseApplication;
import com.hangzhou.tonight.base.Config;
import com.hangzhou.tonight.entity.BannerEntity;
import com.hangzhou.tonight.maintabs.MainActivity;
import com.hangzhou.tonight.module.social.fragment.MyFriendFragment;
import com.hangzhou.tonight.util.Base64Utils;
import com.hangzhou.tonight.util.HttpRequest;
import com.hangzhou.tonight.util.JsonUtils;
import com.hangzhou.tonight.util.MyPreference;
import com.hangzhou.tonight.util.PreferenceConstants;
import com.hangzhou.tonight.util.PreferenceUtils;
import com.hangzhou.tonight.util.RC4Utils;
import com.hangzhou.tonight.util.SystemUiHider;
import com.hangzhou.tonight.view.HeaderLayout.SearchState;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class WelcomeActivity extends BaseActivity {
  
    private static final boolean AUTO_HIDE = true;  
    private static final int AUTO_HIDE_DELAY_MILLIS = 5000;  
    private static final boolean TOGGLE_ON_CLICK = true;  
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;
   
    private SystemUiHider mSystemUiHider;
    private String account;
    private String password ;

    private Context mContext;
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
        mContext = this;
        openTonight();
        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);
        final View adsView = findViewById(R.id.fullscreen_imageview);
       
		MyPreference.getInstance(mContext).getCityConvsion();
		
        
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedLoadActivity(AUTO_HIDE_DELAY_MILLIS);
    }
    
    
   /* private void init(){
		account = PreferenceUtils.getPrefString(this,
				PreferenceConstants.ACCOUNT, "");
		password = PreferenceUtils.getPrefString(this,
				PreferenceConstants.PASSWORD, "");
	}*/
    
    
   
    private void openTonight(){
    	putAsyncTask2(new AsyncTask<Void, Void, String>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
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
				
				try {
					com.alibaba.fastjson.JSONObject object = JSON
							.parseObject(result);
					com.alibaba.fastjson.JSONObject object1 = object.getJSONObject("config");
					String version = object1.getString("version");
					String cityUrl = object1.getString("url");
					
					/*if(Integer.parseInt(MyPreference.getInstance(mContext).getCityConvsion())<Integer.parseInt(version)){
						
					}else {
						
					}*/
					MyPreference.getInstance(mContext).setCityUrl(cityUrl);
					MyPreference.getInstance(mContext).setCityConvsion(version);
					
					com.alibaba.fastjson.JSONArray jsonArray = object.getJSONArray("banner");
					BaseApplication.banners = JSON.parseArray(jsonArray.toString(), BannerEntity.class);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		});
    }
    
    
    
	private Map<String, String> setParams(){
		Map<String, String> map = new HashMap<String, String>();
		ArrayList<Object> arry = new ArrayList<Object>();
		arry.add(0, "openTonight");
		arry.add(1, 0);
		String data0 = RC4Utils.RC4("mdwi5uh2p41nd4ae23qy4",
				JsonUtils.list2json(arry));

		System.out.println("RC4加密后：   " + data0);
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("base64解码后：" + decode);

		/*try {
			String data8 = new String(Base64.decode(
					encoded1.getBytes(), Base64.DEFAULT), "ISO-8859-1");
			System.out.println("base64解码后：" + data8);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		// String data7=HloveyRC4(decode,"mdwi5uh2p41nd4ae23qy4");
		String data7 = RC4Utils.RC4("mdwi5uh2p41nd4ae23qy4", decode);
		System.out.println("RC4解密后：    " +data7);
		
		map.put("d", encoded1);
		
		return map;
		
	}


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };
    
    
    Runnable mLoadRunnable = new Runnable() {
        @Override
        public void run() {
        	loadActivity();
			}
    };
    
    
    private void loadActivity(){
    	
    	Intent intent = new Intent(WelcomeActivity.this,
    			MainActivity.class);
    	
    	if(TextUtils.isEmpty(password)||TextUtils.isEmpty(account)){
    		intent = new Intent(WelcomeActivity.this,
    				LoginActivity.class);	
    	}
    	
    	
    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    	startActivity(intent);
		WelcomeActivity.this.finish();
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
    
    private void delayedLoadActivity(int delayMillis) {
        mHideHandler.removeCallbacks(mLoadRunnable);
        mHideHandler.postDelayed(mLoadRunnable, delayMillis);
    }

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void init() {
		account = MyPreference.getInstance(mContext).getLoginName();
		password = MyPreference.getInstance(mContext).getPassword();	
	}
}
