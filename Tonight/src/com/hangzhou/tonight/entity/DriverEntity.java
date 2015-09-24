package com.hangzhou.tonight.entity;



public class DriverEntity extends Entity{


	private String name;//(代驾名称)
	private String photo;//(代驾照⽚片)，
	private String phone;//(代驾电话)，
	private String drive_age;//(驾龄)，
	private String 	drive_num;//(服务次数)}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDrive_age() {
		return drive_age;
	}
	public void setDrive_age(String drive_age) {
		this.drive_age = drive_age;
	}
	public String getDrive_num() {
		return drive_num;
	}
	public void setDrive_num(String drive_num) {
		this.drive_num = drive_num;
	}
	
	
}
