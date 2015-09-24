package com.hangzhou.tonight.entity;

/**
* @ClassName: AcitvesEntity 
* @Description: TODO(活动实体类) 
* @author yanchao 
* @date 2015-9-1 下午11:07:26 
*
 */
public class ActivesEntity extends Entity {

	private String act_id;
	private String title;
	private String des;
	private String img;
	private String value;
	private String price;
	private String starttime;
	private String endtime;
	private String sales_num;
	private String name;
	private String address;
	private String lon;
	private String lat;
	
	
	
	
	
	
	
	public ActivesEntity(){
		
	}
	public ActivesEntity(String act_id, String title, String des, String img,
			String value, String price, String starttime, String endtime,
			String sales_num, String name, String address, String lon,
			String lat) {
		this.act_id = act_id;
		this.title = title;
		this.des = des;
		this.img = img;
		this.value = value;
		this.price = price;
		this.starttime = starttime;
		this.endtime = endtime;
		this.sales_num = sales_num;
		this.name = name;
		this.address = address;
		this.lon = lon;
		this.lat = lat;
	}
	public String getAct_id() {
		return act_id;
	}
	public void setAct_id(String act_id) {
		this.act_id = act_id;
	}
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
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getSales_num() {
		return sales_num;
	}
	public void setSales_num(String sales_num) {
		this.sales_num = sales_num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	
	
}
