package com.hangzhou.tonight.adapter;

import java.util.List;

import android.content.Context;
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
import com.hangzhou.tonight.entity.ActivesEntity;
import com.hangzhou.tonight.entity.Entity;
import com.hangzhou.tonight.entity.NearByPeople;
import com.hangzhou.tonight.entity.PinglunEntity;
import com.hangzhou.tonight.entity.ReviewsEntity;
import com.hangzhou.tonight.util.PhotoUtils;

public class PinglunListAdapter extends BaseObjectListAdapter {

	public PinglunListAdapter(BaseApplication application, Context context,
			List<? extends Entity> datas) {
		super(application, context, datas);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_pinglun_list, null);
			holder = new ViewHolder();

			holder.mIvAvatar = (ImageView) convertView
					.findViewById(R.id.kc_Img);

			holder.mTvfuwu = (TextView) convertView
					.findViewById(R.id.tv_merchant_fuwu);
			//holder.mTvhuanjing = (TextView) convertView.findViewById(R.id.tv_merchant_huanjing);
			//holder.mTvprice = (TextView) convertView.findViewById(R.id.tv_merchant_price);
			holder.mTvdesc = (TextView) convertView
					.findViewById(R.id.tv_merchant_desc);
			holder.mTvname = (TextView) convertView
					.findViewById(R.id.tv_pinglun_name);
			holder.mTvtime = (TextView) convertView
					.findViewById(R.id.tv_pinglun_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//畅销套餐
		//290元享价值总价值460元的雪花纯生啤酒套餐
		ReviewsEntity people = (ReviewsEntity) getItem(position);
		holder.mTvfuwu.setTag(people);
		holder.mTvfuwu.setText(people.getMark());
		//holder.mTvhuanjing.setText(people.getMark()+"");
		holder.mTvname.setText(people.getNick()+"");
		holder.mTvdesc.setText(people.getContent());
		//holder.mTvprice.setText(people.getMark());
		holder.mTvtime.setText(people.getTime());
		//holder.mIvAvatar.setBackgroundResource(R.drawable.kc_picture);
	//	holder.mIvAvatar.setImageBitmap(mApplication.getAvatar(people.getImg()));
		return convertView;
	}

	class ViewHolder {

		ImageView mIvAvatar;
		TextView mTvfuwu;
		//TextView mTvhuanjing;
		//TextView mTvprice;
		TextView mTvdesc;
		TextView mTvname;
		TextView mTvtime;
	}
}
