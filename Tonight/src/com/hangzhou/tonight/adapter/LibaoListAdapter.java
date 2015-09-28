package com.hangzhou.tonight.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.base.BaseApplication;
import com.hangzhou.tonight.base.BaseObjectListAdapter;
import com.hangzhou.tonight.base.Config;
import com.hangzhou.tonight.entity.ActivesEntity;
import com.hangzhou.tonight.entity.DriverEntity;
import com.hangzhou.tonight.entity.Entity;
import com.hangzhou.tonight.entity.LiBaoEntity;
import com.hangzhou.tonight.entity.MerchantEntity;
import com.hangzhou.tonight.entity.NearByPeople;
import com.hangzhou.tonight.util.PhotoUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class LibaoListAdapter extends BaseObjectListAdapter {

	ImageLoader imageLoader;
	DisplayImageOptions options;
	public LibaoListAdapter(BaseApplication application, Context context,
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
			convertView = mInflater.inflate(R.layout.item_libao_list, null);
			holder = new ViewHolder();

			holder.mIvAvatar = (Button) convertView
					.findViewById(R.id.img_header);
			
			holder.mTvtitle = (TextView) convertView
					.findViewById(R.id.tv_libao_name);
			holder.mTvcharge = (TextView) convertView
					.findViewById(R.id.tv_libao_date);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//畅销套餐
		//290元享价值总价值460元的雪花纯生啤酒套餐
		final LiBaoEntity people = (LiBaoEntity) getItem(position);
		holder.mTvtitle.setTag(people);
		holder.mIvAvatar.setText(people.getMoney()+"");
		holder.mTvtitle.setText(people.getName()+"");
		holder.mTvcharge.setText(people.getStarttime()+"-"+people.getEndtime());
		/*holder.bt_yuyue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(people.getPhone()==null){
					Toast.makeText(mContext, "电话号码缺失", 1000).show();
					return;
				}
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel://"+people.getPhone()));    
	            mContext.startActivity(intent);
				
			}
		});*/
		
    	//LatLng pt_end = new LatLng(w, j);
		//double distance = DistanceUtil.getDistance(pt_start, pt_end);
    	
    	//Toast.makeText(mContext, lati+""+lonti+""+addr, 1).show();
    	
    	//holder.distance.setText((int)(distance/1000)+"km");
		//imageLoader.displayImage(Config.ACT_IMG+people.getPhoto(), holder.mIvAvatar,options);
	//	holder.mIvAvatar.setImageBitmap(mApplication.getAvatar(people.getImg()));
		return convertView;
	}

	class ViewHolder {

		Button mIvAvatar;
		TextView mTvtitle;
		TextView mTvcharge;
		Button bt_yuyue;
	}
}
