package com.bornbhukkad.merchant.dto;

import java.util.List;

public class RestauranItemRequestDto {
	private List<RestaurantItemDto> restaurantItem;
	
	public List<RestaurantItemDto> getRestaurantItemDto() {
		return restaurantItem;
	}
	public void setRestaurantItemDto(List<RestaurantItemDto> restaurantItem) {
		this.restaurantItem = restaurantItem;
	}
	private RestaurantProductDto restaurantProduct;
	public RestaurantProductDto getRestaurantProductDto() {
		return restaurantProduct;
	}
	public void setRestaurantProductDto(RestaurantProductDto restaurantProduct) {
		this.restaurantProduct = restaurantProduct;
	}
	public List<RestaurantCustomGroupDto> getRestaurantCustomGroup() {
		return restaurantCustomGroup;
	}
	public void setRestaurantCustomGroup(List<RestaurantCustomGroupDto> restaurantCustomGroup) {
		this.restaurantCustomGroup = restaurantCustomGroup;
	}
	private List<RestaurantCustomGroupDto> restaurantCustomGroup;
	
}
