package com.bornbhukkad.merchant.Service;

import java.util.List;
import java.util.Optional;

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
	public RestaurantProductDto addRestaurantProduct(RestaurantProductDto product);
	public RestaurantCustomGroupDto addRestaurantCustomGroup(RestaurantCustomGroupDto customGroup);
	public RestaurantItemDto addRestaurantItem(RestaurantItemDto item);
	public void addRestaurantFulfillment(RestaurantFulfillmentDto filfillment);
	public List<RestaurantDto> getAll();
	public List<RestaurantDefaultCategoriesDto> getRestDefaultCategories();
	public List<Object> getProductsByVendorId(String vendorId);
	public List<RestaurantCategoriesDto> getCategoriesByVendorId(String vendorId);
	public List<RestaurantLocationDto> getLocationByVendorId(String vendorId);
	public RestaurantDto getVendorById(String vendorId);
	RestaurantLocationDto updateRadius(String id, String radius);
	void updateProductCustomGroupTags(String productId, List<String> customGroupIds);
	List<RestaurantFulfillmentDto> getFulfillmentByVendorId(String id);
	public void sendRestaurantNotification(String orderId, String merchantId, String orderDetails);

}
