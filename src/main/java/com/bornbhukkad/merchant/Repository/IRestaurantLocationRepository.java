package com.bornbhukkad.merchant.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.RestaurantLocationDto;

public interface IRestaurantLocationRepository extends MongoRepository<RestaurantLocationDto, String> {

	public List<RestaurantLocationDto> findByVendorId(String vendorId);

}
