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
import android.widget.TextView;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.base.BaseApplication;
import com.hangzhou.tonight.base.BaseObjectListAdapter;
import com.hangzhou.tonight.entity.BrEventsEntity;
import com.hangzhou.tonight.entity.Entity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class BrdayListAdapter extends BaseObjectListAdapter {

	ImageLoader imageLoader;
	DisplayImageOptions options;
	public BrdayListAdapter(BaseApplication application, Context context,
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
			convertView = mInflater.inflate(R.layout.item_brday_list, null);
			holder = new ViewHolder();

			holder.mIvAvatar = (ImageView) convertView
					.findViewById(R.id.iv_brday1);

			holder.mTvtitle = (TextView) convertView
					.findViewById(R.id.tv_brday_name);
			holder.mTvdescribe = (TextView) convertView
					.findViewById(R.id.tv_brday_desc);
			holder.mContct = (Button) convertView
					.findViewById(R.id.bt_brday_yuyue);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//畅销套餐
		//290元享价值总价值460元的雪花纯生啤酒套餐
		final BrEventsEntity people = (BrEventsEntity) getItem(position);
		holder.mTvtitle.setTag(people);
		holder.mTvtitle.setText(people.getTitle()+"");
		holder.mTvdescribe.setText(people.getDes()+"");
		holder.mContct.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel://"+people.getPhone()));    
	            mContext.startActivity(intent); 
				
			}
		});
		imageLoader.displayImage(people.getImg(), holder.mIvAvatar,options);
		return convertView;
	}

	class ViewHolder {

		ImageView mIvAvatar;
		TextView mTvtitle;
		TextView mTvdescribe;
		Button mContct;
	}
}
