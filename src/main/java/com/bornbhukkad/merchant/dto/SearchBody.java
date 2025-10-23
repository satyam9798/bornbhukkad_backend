package com.bornbhukkad.merchant.dto;

public class SearchBody {
	public String id;
	public String city;
	public String category;
	
	public String type;
	public String areaCode;
	
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String vendorId;
	public String Latitude;
	public String longitude;
	public String maxDistance;
	public String minOrder;
	public String radius;
	
	public String getRadius() {
		return radius;
	}
	public void setRadius(String Radius) {
		radius= Radius;
	}
	
	public String getMinOrder() {
		return minOrder;
	}
	public void setMinOrder(String MinOrder) {
		minOrder = MinOrder;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getMaxDistance() {
		return maxDistance;
	}
	public void setMaxDistance(String maxDistance) {
		this.maxDistance = maxDistance;
	}
	public String getCity() {
		return city;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String item;

}
