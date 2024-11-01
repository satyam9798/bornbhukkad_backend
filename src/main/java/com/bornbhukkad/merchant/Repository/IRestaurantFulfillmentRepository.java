package com.bornbhukkad.merchant.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.RestaurantFulfillmentDto;

public interface IRestaurantFulfillmentRepository extends MongoRepository<RestaurantFulfillmentDto, String>{

	List<RestaurantFulfillmentDto> findByVendorId(String id);

}
