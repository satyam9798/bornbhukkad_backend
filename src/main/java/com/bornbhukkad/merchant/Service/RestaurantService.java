package com.bornbhukkad.merchant.Service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.bornbhukkad.merchant.dto.RestaurantCategoriesDto;
import com.bornbhukkad.merchant.dto.RestaurantCustomGroupDto;
import com.bornbhukkad.merchant.dto.RestaurantDefaultCategoriesDto;
import com.bornbhukkad.merchant.dto.RestaurantDto;
import com.bornbhukkad.merchant.dto.RestaurantFulfillmentDto;
import com.bornbhukkad.merchant.dto.RestaurantItemDto;
import com.bornbhukkad.merchant.dto.RestaurantLocationDto;
import com.bornbhukkad.merchant.dto.RestaurantProductDto;

public interface RestaurantService {
	public void addRestaurant(RestaurantDto merchant);
	public void addRestaurantLocation(RestaurantLocationDto location);
	public void addRestaurantCategories(RestaurantCategoriesDto categories);
	public void addRestaurantProduct(RestaurantProductDto product);
	public void addRestaurantCustomGroup(RestaurantCustomGroupDto customGroup);
	public void addRestaurantItem(RestaurantItemDto item);
	public void addRestaurantFulfillment(RestaurantFulfillmentDto filfillment);
	public List<RestaurantDto> getAll();
	public List<RestaurantDefaultCategoriesDto> getRestDefaultCategories();
	public List<RestaurantProductDto> getProductsByVendorId(String vendorId);

}
