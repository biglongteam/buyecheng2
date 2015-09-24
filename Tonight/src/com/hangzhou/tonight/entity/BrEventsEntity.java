package com.hangzhou.tonight.entity;

public class BrEventsEntity extends Entity{

	private String img;

	private String title;//(活动主题)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	private String des;//(活动描述)，
	private String phone;//(预约电话)}]
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
}
