package com.hangzhou.tonight.entity;

/**
* @ClassName: AcitvesEntity 
* @Description: TODO(活动实体类) 
* @author yanchao 
* @date 2015-9-1 下午11:07:26 
*
 */
public class ActivesInfo extends ActivesEntity {


	
	
	
	/*actInfo{act_id(活动ID)，title(活动名称)，
		des(活动简介)，img(活动图⽚片的json数组，已
		带扩展名)，value(套餐原价)，price(套餐折后
		价)，starttime(开始时间)，endtime(结束时
		间)，，
		review_num(评价总数)，sales_num(销量)，
		name(商家名称)，address(商家地址)，lon(商
		家经度)，lat(商家纬度)，(商家电话)}
		
		
		reviews[{uid(⽤用户ID)，nick(⽤用户昵称)，
		mark(评分,1~5)，content(评论内容)，img(评
		论所带图⽚片的json数组)，time(评论时间)}]*/
	
	
	
	private String phone;
	private String content;//(活动详情)，
	private ReviewsEntity reviews;
	
	
	
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	private String tip;//(注意事项)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public ReviewsEntity getReviews() {
		return reviews;
	}
	public void setReviews(ReviewsEntity reviews) {
		this.reviews = reviews;
	}
	
	
	
	
	
	
}
