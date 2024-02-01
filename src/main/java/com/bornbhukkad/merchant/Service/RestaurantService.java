package com.bornbhukkad.merchant.Service;

import java.util.List;

import com.bornbhukkad.merchant.dto.RestaurantDto;
import com.bornbhukkad.merchant.dto.RestaurantLocationDto;

public interface RestaurantService {
	public void addRestaurant(RestaurantDto merchant);
	public void addRestaurantLocation(RestaurantLocationDto location);
	public List<RestaurantDto> getAll();

}
