package com.hangzhou.tonight.entity;

public class WXPayEntity extends Entity{
	
	private String appid;

	private String noncestr;

	private String packagestr;

	
	private String partnerid;

	private String prepayid;

	private String sign;

	private int timestamp;
	public String getPackagestr() {
		return packagestr;
	}
	public void setPackagestr(String packagestr) {
		this.packagestr = packagestr;
	}
	public void setAppid(String appid){
	this.appid = appid;
	}
	public String getAppid(){
	return this.appid;
	}
	public void setNoncestr(String noncestr){
	this.noncestr = noncestr;
	}
	public String getNoncestr(){
	return this.noncestr;
	}
	public void setPartnerid(String partnerid){
	this.partnerid = partnerid;
	}
	public String getPartnerid(){
	return this.partnerid;
	}
	public void setPrepayid(String prepayid){
	this.prepayid = prepayid;
	}
	public String getPrepayid(){
	return this.prepayid;
	}
	public void setSign(String sign){
	this.sign = sign;
	}
	public String getSign(){
	return this.sign;
	}
	public void setTimestamp(int timestamp){
	this.timestamp = timestamp;
	}
	public int getTimestamp(){
	return this.timestamp;
	}

}
