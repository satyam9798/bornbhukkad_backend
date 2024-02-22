package com.bornbhukkad.merchant.dto;

public class RestauranItemRequestDto {
	private RestaurantItemDto restaurantItem;
	
	public RestaurantItemDto getRestaurantItemDto() {
		return restaurantItem;
	}
	public void setRestaurantItemDto(RestaurantItemDto restaurantItem) {
		this.restaurantItem = restaurantItem;
	}
	private RestaurantProductDto restaurantProduct;
	public RestaurantProductDto getRestaurantProductDto() {
		return restaurantProduct;
	}
	public void setRestaurantProductDto(RestaurantProductDto restaurantProduct) {
		this.restaurantProduct = restaurantProduct;
	}
	public RestaurantCustomGroupDto getRestaurantCustomGroupDto() {
		return restaurantCustomGroup;
	}
	public void setRestaurantCustomGroupDto(RestaurantCustomGroupDto restaurantCustomGroup) {
		this.restaurantCustomGroup = restaurantCustomGroup;
	}
	private RestaurantCustomGroupDto restaurantCustomGroup;
	
}
