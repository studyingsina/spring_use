package com.studying.spring.domain;


/**
 * Desc
 * 
 * @Date: 2015年5月11日
 */
public class OrderForm {
	private Integer poiId;
	private Long goodsId;
	private boolean isNeedRegistered;
	private String name;
	private String phone;
	private String identity;
	private Integer partnerId;
	private Integer roomId;
	private Long checkinTime;
	private Long checkoutTime;
	private String contactorName;
	private String guestNames;
	private Integer roomCount;
	private Integer goodsType;
	private String contactorPhone;
	private String platformString;
	private String codes;

	public Integer getPoiId() {
		return poiId;
	}

	public void setPoiId(Integer poiId) {
		this.poiId = poiId;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public boolean isNeedRegistered() {
		return isNeedRegistered;
	}

	public void setNeedRegistered(boolean isNeedRegistered) {
		this.isNeedRegistered = isNeedRegistered;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Long getCheckinTime() {
		return checkinTime;
	}

	public void setCheckinTime(Long checkinTime) {
		this.checkinTime = checkinTime;
	}

	public Long getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(Long checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	public String getContactorName() {
		return contactorName;
	}

	public void setContactorName(String contactorName) {
		this.contactorName = contactorName;
	}

	public String getGuestNames() {
		return guestNames;
	}

	public void setGuestNames(String guestNames) {
		this.guestNames = guestNames;
	}

	public Integer getRoomCount() {
		return roomCount;
	}

	public void setRoomCount(Integer roomCount) {
		this.roomCount = roomCount;
	}

	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}

	public String getContactorPhone() {
		return contactorPhone;
	}

	public void setContactorPhone(String contactorPhone) {
		this.contactorPhone = contactorPhone;
	}

	public String getPlatformString() {
		return platformString;
	}

	public void setPlatformString(String platformString) {
		this.platformString = platformString;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

}
