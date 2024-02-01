package com.bornbhukkad.merchant.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.RestaurantDto;

public interface IRestaurantRepository extends MongoRepository<RestaurantDto, String> {

}
