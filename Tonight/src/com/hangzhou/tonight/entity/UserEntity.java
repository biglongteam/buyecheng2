package com.hangzhou.tonight.entity;

public class UserEntity extends Entity{
	

	private String uid;
	private String nick;//(⽤用户昵称)；
	private String birth;//(如2014-1-1)；
	private String sex;//(1男,0⼥女)；
	private String phone;//
	private String money;//(余额)；
	private String favorite;//(收藏的活动ID数组)；
	private String praised;//(点赞过的动态ID数组)；
	private String groups;//(加⼊入的群组的map){gid，position(群内位置，1:群主,9:普通成员)，time(加群时间)}；
	private String friends;//(好友uid数组)；

	private String  paypass;
	private String  s; //1
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getFavorite() {
		return favorite;
	}
	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}
	public String getPraised() {
		return praised;
	}
	public void setPraised(String praised) {
		this.praised = praised;
	}
	public String getGroups() {
		return groups;
	}
	public void setGroups(String groups) {
		this.groups = groups;
	}
	public String getFriends() {
		return friends;
	}
	public void setFriends(String friends) {
		this.friends = friends;
	}
	
}
