package com.hangzhou.tonight.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.base.BaseApplication;
import com.hangzhou.tonight.base.BaseObjectListAdapter;
import com.hangzhou.tonight.base.Config;
import com.hangzhou.tonight.entity.ActivesEntity;
import com.hangzhou.tonight.entity.Entity;
import com.hangzhou.tonight.entity.MerchantEntity;
import com.hangzhou.tonight.entity.NearByPeople;
import com.hangzhou.tonight.util.MyPreference;
import com.hangzhou.tonight.util.PhotoUtils;
import com.hangzhou.tonight.util.ScreenUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class MerchantListAdapter extends BaseObjectListAdapter {

	ImageLoader imageLoader;
	DisplayImageOptions options;
	public MerchantListAdapter(BaseApplication application, Context context,
			List<? extends Entity> datas) {
		super(application, context, datas);
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.kc_picture)// 正在加载
				.showImageForEmptyUri(R.drawable.kc_picture)// 空图片
				.showImageOnFail(R.drawable.kc_picture)// 错误图片
				.cacheInMemory(true)//设置 内存缓存
				.cacheOnDisk(true)//设置硬盘缓存
				.imageScaleType(ImageScaleType.EXACTLY) // default 推荐.imageScaleType(ImageScaleType.EXACTLY) 节省内存
				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_merchants_list, null);
			holder = new ViewHolder();

			holder.mIvAvatar = (ImageView) convertView
					.findViewById(R.id.img_merchant);

			holder.mTvtitle = (TextView) convertView
					.findViewById(R.id.tv_merchant_name);
			holder.mTvdescribe = (TextView) convertView
					.findViewById(R.id.tv_merchant_desc);
			holder.mTvdistance = (TextView) convertView
					.findViewById(R.id.tv_merc_distance);
			holder.mTvAddress = (TextView) convertView
					.findViewById(R.id.tv_merchant_address);
			holder.mTvcharge = (TextView) convertView
					.findViewById(R.id.tv_merchant_price);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//畅销套餐
		//290元享价值总价值460元的雪花纯生啤酒套餐
		MerchantEntity people = (MerchantEntity) getItem(position);
		
    	
		double distance = ScreenUtils.gps2m(Double.parseDouble(people.getLat()), Double.parseDouble(people.getLon()), Double.parseDouble(MyPreference.getInstance(mContext).getLocation_w()), Double.parseDouble(MyPreference.getInstance(mContext).getLocation_j()));
		
		holder.mTvtitle.setTag(people);
		holder.mTvtitle.setText(people.getName()+"");
		holder.mTvdescribe.setText(people.getTitle()+"");
		holder.mTvdistance.setText((int)(distance/1000)+"km"+"");
		holder.mTvAddress.setText(people.getAddress()+"");
    	//LatLng pt_end = new LatLng(w, j);
		//double distance = DistanceUtil.getDistance(pt_start, pt_end);
    	
    	//Toast.makeText(mContext, lati+""+lonti+""+addr, 1).show();
    	
    	//holder.distance.setText((int)(distance/1000)+"km");
		holder.mTvcharge.setText("￥"+people.getPrice());
		imageLoader.displayImage(Config.ACT_IMG+people.getImg(), holder.mIvAvatar,options);
	//	holder.mIvAvatar.setImageBitmap(mApplication.getAvatar(people.getImg()));
		return convertView;
	}

	class ViewHolder {

		ImageView mIvAvatar;
		TextView mTvtitle;
		TextView mTvdescribe;
		TextView mTvdistance;
		TextView mTvAddress;
		TextView mTvcharge;
	}
}
