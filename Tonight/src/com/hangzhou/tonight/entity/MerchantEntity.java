package com.hangzhou.tonight.entity;

/**
* @ClassName: AcitvesEntity 
* @Description: TODO(活动实体类) 
* @author yanchao 
* @date 2015-9-1 下午11:07:26 
*
 */
public class MerchantEntity extends Entity {
	
	
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
	
	private String seller_id;//(商家ID)，
	private String name;//(商家名称)，
	private String title;//;(活动名称)，
	private String img;//(活动图⽚片的json数组，已带扩展名)，
	private String value;//(套餐原价);//，
	private String price;//(套餐折后价)，
	private String sales_num;//(销量)，
	private String address;//(商家地址)，
	private String	lon;//(商家经度)，
	private String lat;//(商家纬度)}
	
	
	public MerchantEntity(String seller_id, String name, String title,
			String img, String value,String price,String sales_num, String address, String lon, String lat) {
		super();
		this.seller_id = seller_id;
		this.name = name;
		this.title = title;
		this.img = img;
		this.value = value;
		this.price = price;
		this.sales_num = sales_num;
		this.address = address;
		this.lon = lon;
		this.lat = lat;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getSales_num() {
		return sales_num;
	}

	public void setSales_num(String sales_num) {
		this.sales_num = sales_num;
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

	
	
	public MerchantEntity(){
		
	}
	
}
