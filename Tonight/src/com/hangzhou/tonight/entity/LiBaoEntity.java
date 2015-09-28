package com.hangzhou.tonight.entity;

public class LiBaoEntity extends Entity{
	
	
	/*tickets[{ticket_id(礼包ID)，money(⾦金额)，
		name(礼包名称)，starttime(开始时间)，
		endtime(结束时间)}]*/
	private String ticket_id;
	private String money;
	private String name;
	private String starttime;
	private String endtime;

	
	
	public String getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(String ticket_id) {
		this.ticket_id = ticket_id;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
}
