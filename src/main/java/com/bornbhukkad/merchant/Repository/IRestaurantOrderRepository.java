package com.bornbhukkad.merchant.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.RestaurantOrderDto;
import com.bornbhukkad.merchant.dto.RestaurantOrderStatus;

public interface IRestaurantOrderRepository extends MongoRepository<RestaurantOrderDto, String>{
	List<RestaurantOrderDto> findByStatus(RestaurantOrderStatus status);

	List<RestaurantOrderDto> findByVendorId(String vendorId);
	Optional<RestaurantOrderDto> findById(String id);

}
