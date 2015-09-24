package com.hangzhou.tonight.entity;

/**
* @ClassName: AcitvesEntity 
* @Description: TODO(活动实体类) 
* @author yanchao 
* @date 2015-9-1 下午11:07:26 
*
 */
public class MerchantInfo extends MerchantEntity {
	
	
	/*sellerInfo{seller_id(商家ID)，name(商家名
			称)，photo(商家照⽚片的json数组，已带扩展
			名)，video(商家视频的地址)，address(商家
			地址)，lon(商家经度)，lat(商家纬度)，
			phone(商家电话)，reception_photo(专职接待
			照⽚片)，reception_phone(专职接待电话)，
			mark(商家评分)}
			acts[{act_id(活动ID)，title(活动标题)，img(活
			动图⽚片的json数组，已带扩展名)，value(套餐
			原价)，price(套餐折后价)，sales_num(销
			量)}]
			reviews[{uid(⽤用户ID)，nick(⽤用户昵称)，
			mark(评分,1~5)，content(评论内容)，img(评
			论所带图⽚片的json数组)，time(评论时间)}]
	*/
	

	private String name;//(商家名称)，
	private String[] photo;//;(活动名称)，
	private String video;//
	private String value;//(套餐原价);//，
	private String price;//(套餐折后价)，
	private String reception_photo;//(专职接待照⽚片)，
	private String reception_phone;//(销量专职接待电话)，
	private String address;//(商家地址)，
	private String	lon;//(商家经度)，
	private String lat;//(商家纬度)}
	private String	mark;//(商家经度)，
	private String	review_num;//(商家经度)，
	
	
	private String seller_id;//(商家ID)，
	public String[] getPhoto() {
		return photo;
	}

	public void setPhoto(String[] photo) {
		this.photo = photo;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getReception_photo() {
		return reception_photo;
	}

	public void setReception_photo(String reception_photo) {
		this.reception_photo = reception_photo;
	}

	public String getReception_phone() {
		return reception_phone;
	}

	public void setReception_phone(String reception_phone) {
		this.reception_phone = reception_phone;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getReview_num() {
		return review_num;
	}

	public void setReview_num(String review_num) {
		this.review_num = review_num;
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

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
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

	
	
	public MerchantInfo(){
		
	}
	
}
