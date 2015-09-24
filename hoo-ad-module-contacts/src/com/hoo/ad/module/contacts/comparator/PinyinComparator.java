package com.hoo.ad.module.contacts.comparator;


import java.util.Comparator;

import com.hoo.ad.module.contacts.model.ContactsModel;
import com.hoo.base.constant.SpecialCharacterConstant;

/***
 * 拼音排序比较类
 * @author hank
 */
public class PinyinComparator implements Comparator<ContactsModel> {
	private String st = SpecialCharacterConstant.START
			      ,sp = SpecialCharacterConstant.SHARP;
	@Override public int compare(ContactsModel o1, ContactsModel o2) {
		if (st.equals(o1.getSortLetters())) {
			return -1;
		} else if (st.equals(o2.getSortLetters())) {
			return 1;
		} else if (sp.equals(o1.getSortLetters())) {
			return 1;
		} else if (sp.equals(o2.getSortLetters())) {
			return -1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
