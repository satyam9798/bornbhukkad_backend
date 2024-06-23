package com.bornbhukkad.merchant.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.RestaurantItemDto;

public interface IRestaurantItemRepository extends MongoRepository<RestaurantItemDto, String> {

}
