package com.bornbhukkad.merchant.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.RestaurantAudienceDto;

public interface IRestaurantAudienceSegmentRepository extends MongoRepository<RestaurantAudienceDto, String>{

	List<RestaurantAudienceDto> findByVendorId(String vendorId);

}
