package com.hangzhou.tonight.module.social.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.platform.comapi.map.C;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;
import com.hoo.ad.module.contacts.model.ContactsModel;
import com.hoo.ad.module.contacts.model.ContactsModel.ContactsType;
import com.hoo.base.constant.SpecialCharacterConstant;
import com.hoo.base.util.CharacterParserUtil;
import com.hoo.base.util.ObjectUtil;

public class ContactsFragment extends
		com.hoo.ad.module.contacts.fragment.ContactsFragment {

	protected void loadData() {

		new AsyncTask<Void, Void, Cursor>() {

			@Override
			protected Cursor doInBackground(Void... params) {
				ContentResolver resolver = getActivity().getContentResolver();
				Cursor cursor = resolver.query(Phone.CONTENT_URI, new String[] {
						Phone.DISPLAY_NAME, Phone.NUMBER }, null, null, null);
				return cursor;
			}

			@Override
			protected void onPostExecute(Cursor cursor) {
				super.onPostExecute(cursor);
				if (cursor != null) {
					List<String> phones = new ArrayList<String>();
					while (cursor.moveToNext()) {
						String phoneNum = cursor.getString(1).replace("+86", "").replaceAll("\\s*", "");
						if(phoneNum.startsWith("17951")){ phoneNum = phoneNum.replace("17951", "");}
						if (ObjectUtil.isEmpty(phoneNum) || phoneNum.length()!= 11) { continue; }
						phones.add(phoneNum);
					}
					cursor.close();
					
					JSONObject params = new JSONObject();
					params.put("phones", phones);
					AsyncTaskUtil.postData(getActivity(), "searchPhoneFriend", params, new Callback() {
						
						@Override public void onSuccess(JSONObject result) {
							listData.clear();
							JSONArray array = result.getJSONArray("userList");
							for(int i = 0,len = array.size();i<len;i++){
								JSONObject o = array.getJSONObject(i);
								ContactsModel dm = new ContactsModel();
								dm.setId(o.getString("uid"));
								dm.setName(o.getString("nick"));
								listData.add(dm);
							}
							
							fillData(listData);
							Collections.sort(listData, pyComparator);
							mAdapter.updateListView(listData);
						}
						
						@Override public void onFail(String msg) {
							
						}
					});
				}
			}
		}.execute();
	}

	private void fillData(List<ContactsModel> list) {
		String fristChar, firstSpell;
		for (ContactsModel model : list) {
			if (null != model && model.getName() != null) {
				firstSpell = CharacterParserUtil.getFirstSpell(model.getName());
				model.setAbbreviation(firstSpell);
				if (ObjectUtil.isEmpty(firstSpell)) {
					model.setSortLetters(SpecialCharacterConstant.SHARP);
				} else {
					fristChar = firstSpell.substring(0, 1).toUpperCase();
					if (ContactsType.MODULE.equals(model.getType())) {
						model.setSortLetters(SpecialCharacterConstant.START);
					} else if (fristChar.matches("[A-Z]")) {
						model.setSortLetters(fristChar);
					} else {
						model.setSortLetters(SpecialCharacterConstant.SHARP);
					}
				}
			}
		}
	}
}
