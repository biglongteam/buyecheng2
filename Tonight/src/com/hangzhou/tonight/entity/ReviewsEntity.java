package com.hangzhou.tonight.entity;

public class ReviewsEntity extends Entity{

	/*reviews[{uid(⽤用户ID)，nick(⽤用户昵称)，
		mark(评分,1~5)，content(评论内容)，img(评
		论所带图⽚片的json数组)，time(评论时间)}]*/
	
	private String uid;
	private String nick;
	private String mark;
	private String content;
	private String []img;
	private String time;
	
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
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String[] getImg() {
		return img;
	}
	public void setImg(String[] img) {
		this.img = img;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
