package com.hangzhou.tonight.entity;

/**
* @ClassName: AcitvesEntity 
* @Description: TODO(活动实体类) 
* @author yanchao 
* @date 2015-9-1 下午11:07:26 
*
 */
public class PinglunEntity extends Entity {

	private String fuwu;

	private String huanjing;
	private String price;
	private String des;
	private String time;
	
	private String imgHeader;
	private String userName;
	
	
	public PinglunEntity(String fuwu, String huanjing, String price,
			String des, String time, String imgHeader, String userName) {
		this.fuwu = fuwu;
		this.huanjing = huanjing;
		this.price = price;
		this.des = des;
		this.time = time;
		this.imgHeader = imgHeader;
		this.userName = userName;
	}

	public String getFuwu() {
		return fuwu;
	}

	public void setFuwu(String fuwu) {
		this.fuwu = fuwu;
	}

	public String getHuanjing() {
		return huanjing;
	}

	public void setHuanjing(String huanjing) {
		this.huanjing = huanjing;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getImgHeader() {
		return imgHeader;
	}

	public void setImgHeader(String imgHeader) {
		this.imgHeader = imgHeader;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public PinglunEntity(){
		
	}
	
	
}
