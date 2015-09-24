package com.hoo.ad.module.contacts.model;

import java.io.Serializable;

/**
 * 联系人模型
 * 
 * @author hank
 */
public class ContactsModel {
	/*** 唯一主键 */
	private Serializable id;
	/*** 用户名称 */
	private String name;
	/*** 头像路径 */
	private String headPath;
	/*** 联系人类型 */
	private ContactsType type;
	/*** 拼音首字母 */
	private String sortLetters;
	/*** 名称拼音缩写 */
	private String abbreviation;

	/**
	 * 联系人类型
	 * @author hank
	 */
	public static enum ContactsType {
		/** 用户、模块 */
		USER, MODULE
	}

	public Serializable getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getHeadPath() {
		return headPath;
	}

	public ContactsType getType() {
		return type;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setId(Serializable id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}

	public void setType(ContactsType type) {
		this.type = type;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

}
