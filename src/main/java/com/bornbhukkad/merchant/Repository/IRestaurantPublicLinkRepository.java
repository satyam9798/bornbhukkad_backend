package com.bornbhukkad.merchant.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.RestaurantPublicLink;

public interface IRestaurantPublicLinkRepository extends MongoRepository<RestaurantPublicLink, String> {

	Optional<RestaurantPublicLink> findByRestaurantId(String restaurantId);
}
