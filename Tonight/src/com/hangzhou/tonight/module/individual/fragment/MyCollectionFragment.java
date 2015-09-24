package com.hangzhou.tonight.module.individual.fragment;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.constant.SysModuleConstant;
import com.hangzhou.tonight.module.base.fragment.BEmptyListviewFragment;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.DateUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;

import android.database.DatabaseUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 我的收藏
 * @author hank
 */
public class MyCollectionFragment extends BEmptyListviewFragment {

	CollectionAdapter adapter;
	List<CollectionDataModel> listData = null;
	
	@Override protected void doListeners() {
		
	}
	
	@Override protected void doHandler() {

		listData = new ArrayList<CollectionDataModel>();
		adapter = new CollectionAdapter();
		mListView.setAdapter(adapter);

	}
	
	@Override protected void doPostData() {
		super.doPostData();
		AsyncTaskUtil.postData(getActivity(), "getFavoriteAct", null, new Callback() {
			
			@Override public void onSuccess(JSONObject result) {
				listData.addAll(JSONArray.parseArray(result.getString("acts"), CollectionDataModel.class));
				adapter.notifyDataSetChanged();
			}
			
			@Override public void onFail(String msg) {
				if(SysModuleConstant.VALUE_DEV_MODEL){
					listData.clear();
					String[] strs = {
							 "蜜桃酒吧夜色派对"
							,"8090迷幻系小聚"
							,"活动时间：周一至周五"
							,"已售220"};
					for(int i=0,len = 10;i<len;i++){
						CollectionDataModel m = new CollectionDataModel();
						m.title	 = strs[0];
						m.des= strs[1];
						m.sales_num 	 = strs[3];
						listData.add(m);
					}
					adapter.notifyDataSetChanged();
				}
			}
		});
	}
	
	
	class CollectionAdapter extends BaseAdapter {

		@Override public int getCount() {
			return listData.size();
		}

		@Override public Object getItem(int position) {
			return listData.get(position);
		}

		@Override public long getItemId(int position) {
			return 0;
		}

		@Override public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			CollectionDataModel model = listData.get(position);
			if (convertView == null) {
				convertView = View.inflate(getActivity(),R.layout.item_individual_fragment_mycollection, null);
				holder = new ViewHolder(convertView);
			}
			holder = (ViewHolder) convertView.getTag();
			holder.tv_title.setText(model.title);
			holder.tv_content.setText(model.des);
			holder.tv_time.setText(DateUtil.formatDate(model.starttime) + "至" + DateUtil.formatDate(model.endtime));
			holder.tv_sale.setText("已售："+model.sales_num);
			// holder.iv_image.setB

			return convertView;
		}

		class ViewHolder {
			public ImageView iv_image;
			public TextView tv_title, tv_content, tv_time, tv_sale;
			public ViewHolder(View view) {
				iv_image 	= (ImageView)view.findViewById(R.id.individual_mycollection_image);
				tv_title 	= (TextView) view.findViewById(R.id.individual_mycollection_title);
				tv_content 	= (TextView) view.findViewById(R.id.individual_mycollection_content);
				tv_time 	= (TextView) view.findViewById(R.id.individual_mycollection_activity_time);
				tv_sale 	= (TextView) view.findViewById(R.id.individual_mycollection_sale_detail);
				view.setTag(this);
			}
		}
	}

	public static class CollectionDataModel {
		//TODO 图片的问题,是数组则取第一个
		String title, des, sales_num;
		String img;//原为['','']
		long starttime,endtime;
		public String getTitle() {
			return title;
		}
		public String getDes() {
			return des;
		}
		public String getSales_num() {
			return sales_num;
		}
		
		public long getStarttime() {
			return starttime;
		}
		public long getEndtime() {
			return endtime;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public void setDes(String des) {
			this.des = des;
		}
		public void setSales_num(String sales_num) {
			this.sales_num = sales_num;
		}
		
		public void setStarttime(long starttime) {
			this.starttime = starttime;
		}
		public void setEndtime(long endtime) {
			this.endtime = endtime;
		}
		public String getImg() {
			return img;
		}
		public void setImg(String img) {
			this.img = img;
		}
		
	}
}
