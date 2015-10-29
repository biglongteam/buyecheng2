package com.hangzhou.tonight.module.social.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.StrictMode;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.BaseSingeFragmentActivity;
import com.hangzhou.tonight.module.base.constant.SysModuleConstant;
import com.hangzhou.tonight.module.base.fragment.BEmptyListviewFragment;
import com.hangzhou.tonight.module.base.helper.ActivityHelper.OnIntentCreateListener;
import com.hangzhou.tonight.module.base.helper.PicTextHelper;
import com.hangzhou.tonight.module.base.helper.model.TbarViewModel;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.DateUtil;
import com.hangzhou.tonight.module.base.util.ViewUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;

/**
 * 不夜城 - 同城[动态列表]
 * @author hank
 *
 */
public class TonightCircleCityWideFragment extends BEmptyListviewFragment {

	CollectionAdapter adapter;
	List<DataModel> listData = null;
	
	@Override protected void doListeners() {
		mListView.setOnItemClickListener(new OnItemClickListener() {
			DataModel m;
			@Override public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				m = listData.get(position);
				BaseSingeFragmentActivity.startActivity(getActivity(), TonightCircleDetailFragment.class, new TbarViewModel(m.nick), new OnIntentCreateListener() {
					@Override public void onCreate(Intent intent) {
						intent.putExtra("mid", m.mid);
					}
				});
			}
		});
	}
	
	@Override protected void doHandler() {
		//TODO 开启主线程访问
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());  
		listData = new ArrayList<DataModel>();
		adapter  = new CollectionAdapter();
		mListView.setAdapter(adapter);

	}
	
	/**
	 * @return 1 同城 2 好友 3某人[个人]
	 */
	public int getSort(){
		return 1;
	}
	
	public String getTuid(){
		return null;
	}
	
	long page = 0,time;//sort = 1|2 时需要
	//String tuid;//当sort为3时需要
	boolean isAllowLoad = true;
	@Override protected void doPostData() {
		page = 0;
		listData.clear();
		loadMore();
	}
	
	
	private void loadMore(){
		if(page == 0){ time = 0;}
		page++;
		int sort = getSort();
		JSONObject params = new JSONObject();
		if(sort == 1){ params.put("city", SysModuleConstant.getCityId(getActivity())); }
		if(sort == 3){ params.put("tuid", getTuid());}
		params.put("page", page);
		params.put("time", time);
		params.put("sort", sort);
		AsyncTaskUtil.postData(getActivity(), "getMoodList", params, new Callback() {
			@Override public void onSuccess(JSONObject result) {

				if(result==null||result.equals("")){
					return;
				}
				
				isAllowLoad = result.containsKey("nomore")? true : (result.getIntValue("nomore") != 1);	

				time = result.getLongValue("time");
				List<DataModel>  res = JSONArray.parseArray(result.getString("moods"), DataModel.class);
				if(null != res){
					listData.addAll(res);
				}
				adapter.notifyDataSetChanged();
			}
			
			@Override public void onFail(String msg) {
				if(SysModuleConstant.VALUE_DEV_MODEL){
					String[] strs = {
							 "蜜桃"
							,"8090迷幻系小聚"
							,"19:01"
							,"已售220"};
					for(int i=0,len = 10;i<len;i++){
						DataModel m = new DataModel();
						m.nick	 = strs[0];
						m.content= strs[1];
						m.time 	 = strs[2];
						m.birth  = "198" + i + "-01-2" + i;
						m.pralse_num   = 5;
						m.reply_num= 6;
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

		@SuppressWarnings("deprecation")
		@Override public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			DataModel model = listData.get(position);
			if (convertView == null) {
				convertView = View.inflate(getActivity(),R.layout.item_fragment_tonight_circle, null);
				holder = new ViewHolder(convertView);
			}
			holder = (ViewHolder) convertView.getTag();
			holder.tv_age.setText(""+DateUtil.getCurrentAgeByBirthdate(model.birth));
			holder.tv_age.setBackgroundDrawable(getResources().getDrawable(model.sex==1 ? R.drawable.shape_module_sex_male : R.drawable.shape_module_sex_female));
			
			String content = ViewUtil.getPicTextMutil(model.content, model.url, model.type==2);
			holder.tv_content.setText( Html.fromHtml(content,new PicTextHelper(),null));
			holder.tv_good	 .setText("赞 " + model.pralse_num);
			holder.tv_comment.setText("评论 " + model.reply_num);
			holder.tv_name	 .setText(model.nick);
			return convertView;
		}

		class ViewHolder {
			public ImageView iv_headphoto;
			public TextView tv_name, tv_content, tv_time, tv_age,tv_good,tv_comment;
			public ViewHolder(View view) {
				iv_headphoto= (ImageView)view.findViewById(R.id.tonight_circle_head_photo);
				tv_name 	= (TextView) view.findViewById(R.id.tonight_circle_name);
				tv_content 	= (TextView) view.findViewById(R.id.tonight_circle_content);
				tv_time 	= (TextView) view.findViewById(R.id.tonight_circle_time);
				tv_age 		= (TextView) view.findViewById(R.id.tonight_circle_age);
				tv_comment  = (TextView) view.findViewById(R.id.tonight_circle_comment);
				tv_good     = (TextView) view.findViewById(R.id.tonight_circle_good);
				view.setTag(this);
			}
		}
	}

	public static class DataModel {
		String mid,uid,nick, content, time,birth,url;
		int pralse_num,reply_num,type,sex;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public int getSex() { return sex; } public void setSex(int sex) { this.sex = sex; } public String getMid() { return mid; } public String getUid() { return uid; } public String getNick() { return nick; } public String getContent() { return content; } public String getTime() { return time; } public String getBirth() { return birth; } public int getPralse_num() { return pralse_num; } public int getReply_num() { return reply_num; } public int getType() { return type; } public void setMid(String mid) { this.mid = mid; } public void setUid(String uid) { this.uid = uid; } public void setNick(String nick) { this.nick = nick; } public void setContent(String content) { this.content = content; } public void setTime(String time) { this.time = time; } public void setBirth(String birth) { this.birth = birth; } public void setPralse_num(int pralse_num) { this.pralse_num = pralse_num; } public void setReply_num(int reply_num) { this.reply_num = reply_num; } public void setType(int type) { this.type = type; }

	}
	
}
