package com.hoo.ad.module.contacts.adapter;

import java.util.List;

import com.hoo.ad.module.contacts.R;
import com.hoo.ad.module.contacts.model.ContactsModel;
import com.hoo.base.constant.SpecialCharacterConstant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 联系人适配器[数据请按sortLetter]
 * @author hank
 */
public class ContactsAdapter extends BaseAdapter {

	Context context;
	List<ContactsModel> listData;
	
	public ContactsAdapter(Context context,List<ContactsModel> listData) {
		this.context  = context;
		this.listData = listData;
	}
	
	@Override public int getCount() {
		return listData.size();
	}

	@Override public ContactsModel getItem(int position) {
		return listData.get(position);
	}

	@Override public long getItemId(int position) {
		return 0;
	}

	@Override public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		ContactsModel model = getItem(position);
		if (convertView == null) {
			convertView = View.inflate(context,R.layout.item_fragment_module_contacts, null);
			holder = new ViewHolder(convertView);
		}
		holder = (ViewHolder) convertView.getTag();
		if(null != model){
			holder.tv_name.setText(model.getName());
			int section = getSectionForPosition(position);
			// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
			if (position == getPositionForSection(section)) {
				if(SpecialCharacterConstant.START.equals(model.getSortLetters())){
					holder.v_letter_area.setVisibility(View.GONE);
				}else{
					holder.v_letter_area.setVisibility(View.VISIBLE);
					holder.tv_letter.setText( model.getSortLetters());
				}
			} else {
				holder.v_letter_area.setVisibility(View.GONE);
			}
		}
		return convertView;
	}
	
	public void updateListView(List<ContactsModel> listData){
		this.listData = listData;
		notifyDataSetChanged();
	}
	
	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return getItem(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	@SuppressLint("DefaultLocale")
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = getItem(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}

	class ViewHolder {
		public ImageView iv_headphoto;
		public TextView tv_name,tv_letter;
		public View v_letter_area;
		public ViewHolder(View view) {
			iv_headphoto= (ImageView)view.findViewById(R.id.item_module_contacts_headimg);
			tv_name 	= (TextView) view.findViewById(R.id.item_module_contacts_name);
			tv_letter   = (TextView) view.findViewById(R.id.item_module_contacts_letter);
			v_letter_area = view.findViewById(R.id.item_module_contacts_letter_area);
			view.setTag(this);
		}
	}
}
