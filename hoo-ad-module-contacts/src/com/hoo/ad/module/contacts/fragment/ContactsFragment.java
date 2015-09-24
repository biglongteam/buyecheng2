package com.hoo.ad.module.contacts.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.hoo.ad.base.fragment.BFragment;
import com.hoo.ad.base.widget.SideBar;
import com.hoo.ad.base.widget.SideBar.OnTouchingLetterChangedListener;
import com.hoo.ad.module.contacts.R;
import com.hoo.ad.module.contacts.adapter.ContactsAdapter;
import com.hoo.ad.module.contacts.comparator.PinyinComparator;
import com.hoo.ad.module.contacts.model.ContactsModel;
import com.hoo.ad.module.contacts.model.ContactsModel.ContactsType;
import com.hoo.base.constant.SpecialCharacterConstant;
import com.hoo.base.util.CharacterParserUtil;
import com.hoo.base.util.ObjectUtil;

/**
 * 联系人【主模块】
 * @author hank
 */
public class ContactsFragment extends BFragment{
	
	EditText etSearch;
	View vEmpty;
	TextView tvDialogTip;
	SideBar  mSideBar;
	protected ListView mListView;
	protected ContactsAdapter mAdapter;
	protected PinyinComparator pyComparator;
	
	protected List<ContactsModel> listData = new ArrayList<ContactsModel>();
	
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_module_contacts, container, false);
	}
	
	@Override protected void doView() {
		super.doView();
		etSearch    = (EditText) findViewById(R.id.contacts_search);
		mListView   = (ListView) findViewById(R.id.base_module_listview);
		vEmpty      =  findViewById(R.id.base_module_view_empty_tip);
		tvDialogTip = (TextView) findViewById(R.id.contacts_dialog_tip);
		mSideBar    = (SideBar)  findViewById(R.id.contacts_sidebar);
	}
	
	@Override protected void doListeners() {
		super.doListeners();
		etSearch.addTextChangedListener(etSearchWatcher);
		mSideBar.setOnTouchingLetterChangedListener(mSideBarTouchingLetterChanged);
	}
	
	@Override protected void doHandler(Bundle bundle) {
		super.doHandler(bundle);
		pyComparator = new PinyinComparator(); 
		
		mSideBar.setTextView(tvDialogTip);
		
		mAdapter = new ContactsAdapter(getActivity(),listData);
		mListView.setEmptyView(vEmpty);
		mListView.setCacheColorHint(0);
		mListView.setSelector(new ColorDrawable(android.R.color.transparent));
		mListView.setAdapter(mAdapter);
		
		loadData();
	};
	
	/**
	 * 模拟处理数据加载【读取手机联系人->此时ID是手机号】,重写时请移除super部分
	 */
	protected void loadData() {
		
		new AsyncTask<Void, Void, Cursor>() {

			@Override protected Cursor doInBackground(Void... params) {
				ContentResolver resolver = getActivity().getContentResolver();
				Cursor cursor = resolver.query(Phone.CONTENT_URI, new String[] {  
					       Phone.DISPLAY_NAME, Phone.NUMBER}, null, null, null);
				return cursor;
			}
			@Override
			protected void onPostExecute(Cursor cursor) {
				super.onPostExecute(cursor);
				if(cursor != null){
					while(cursor.moveToNext()){
						String phoneNum = cursor.getString(1),
								   name = cursor.getString(0);
						ContactsModel dm= new ContactsModel();
						if(ObjectUtil.isEmpty(phoneNum)){ continue; }
						dm.setId(phoneNum);
						dm.setName(name);
						listData.add(dm);
					}
					cursor.close();
				}
				fillData(listData);
				Collections.sort(listData,pyComparator);
				mAdapter.updateListView(listData);
			}
		}.execute();
	}
	
	TextWatcher etSearchWatcher = new TextWatcher() {
		
		@Override public void onTextChanged(CharSequence s, int start, int before, int count) {
			filterData(s.toString(), listData);
		}
		
		@Override public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
		
		@Override public void afterTextChanged(Editable s) {}
	};
	
	OnTouchingLetterChangedListener mSideBarTouchingLetterChanged = new OnTouchingLetterChangedListener() {
		@Override public void onTouchingLetterChanged(String s) {
			int position = 0;
			if(mAdapter != null){
				position = mAdapter.getPositionForSection(s.charAt(0));
			}
			if(-1 != position){
				mListView.setSelection(position);
			}
		}
	};
	
	
	/**
	 * 填充对应数据
	 * @param list
	 */
	@SuppressLint("DefaultLocale")
	private void fillData(List<ContactsModel> list){
		String fristChar,firstSpell;
		for(ContactsModel model : list){
			if(null != model && model.getName() != null){
				firstSpell = CharacterParserUtil.getFirstSpell(model.getName());
				model.setAbbreviation(firstSpell);
				if(ObjectUtil.isEmpty(firstSpell)){
					model.setSortLetters(SpecialCharacterConstant.SHARP);
				}else{
					fristChar = firstSpell.substring(0, 1).toUpperCase();
					if(ContactsType.MODULE.equals(model.getType())){
						model.setSortLetters(SpecialCharacterConstant.START);
					}else if(fristChar.matches("[A-Z]")){
						model.setSortLetters(fristChar);
					}else{
						model.setSortLetters(SpecialCharacterConstant.SHARP);
					}
				}
			}
		}
	}
	
	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr, List<ContactsModel> list) {
		List<ContactsModel> filterDateList = new ArrayList<ContactsModel>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = list;
		} else {
			filterDateList.clear();
			for (ContactsModel model : list) {
				String name = model.getName();
				String suoxie = model.getAbbreviation();
				if (name.indexOf(filterStr.toString()) != -1 || suoxie.indexOf(filterStr.toString()) != -1 || CharacterParserUtil.getInstance().getSelling(name).startsWith(filterStr.toString())) {
					filterDateList.add(model);
				}
			}
		}

		Collections.sort(filterDateList, pyComparator);
		mAdapter.updateListView(filterDateList);
	}
}
