package com.bornbhukkad.merchant.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.RestaurantOfferDto;


public interface IRestaurantOfferRepository extends MongoRepository<RestaurantOfferDto, String>{

	List<RestaurantOfferDto> findByVendorId(String vendorId);
	Optional<RestaurantOfferDto> findById(String id);
	boolean existsByName(String offerNameUpper);
}
