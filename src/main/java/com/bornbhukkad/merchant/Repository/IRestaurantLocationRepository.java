package com.bornbhukkad.merchant.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.RestaurantDto;
import com.bornbhukkad.merchant.dto.RestaurantLocationDto;

public interface IRestaurantLocationRepository extends MongoRepository<RestaurantLocationDto, String> {

}
