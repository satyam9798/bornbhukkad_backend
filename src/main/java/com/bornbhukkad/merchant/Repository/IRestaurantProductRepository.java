package com.bornbhukkad.merchant.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.RestaurantProductDto;

public interface IRestaurantProductRepository extends MongoRepository<RestaurantProductDto, String> {

}
