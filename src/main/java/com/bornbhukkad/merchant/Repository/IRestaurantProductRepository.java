package com.bornbhukkad.merchant.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.RestaurantProductDto;

public interface IRestaurantProductRepository extends MongoRepository<RestaurantProductDto, String> {

	List<RestaurantProductDto> findByVendorId(String vendorId);
	Optional<RestaurantProductDto> findById(String id);



}
