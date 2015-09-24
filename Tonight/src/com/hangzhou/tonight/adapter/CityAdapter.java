package com.hangzhou.tonight.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hangzhou.tonight.R;
import com.hangzhou.tonight.base.BaseApplication;
import com.hangzhou.tonight.base.BaseObjectListAdapter;
import com.hangzhou.tonight.entity.ActivesEntity;
import com.hangzhou.tonight.entity.City;
import com.hangzhou.tonight.entity.Entity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CityAdapter extends BaseObjectListAdapter {


	public CityAdapter(BaseApplication application, Context context,
			List<? extends Entity> datas) {
		super(application, context, datas);
	}


	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_list, null);
			holder = new ViewHolder();

			holder.mCityName = (TextView) convertView
					.findViewById(R.id.tv_name);
			holder.mTvId = (TextView) convertView.findViewById(R.id.tv_id);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 畅销套餐
		// 290元享价值总价值460元的雪花纯生啤酒套餐
		City people = (City) getItem(position);
		holder.mCityName.setTag(people);
		holder.mCityName.setText(people.getName() + "");

		return convertView;

	}

	class ViewHolder {
		TextView mCityName;
		TextView mTvId;
	}

}
