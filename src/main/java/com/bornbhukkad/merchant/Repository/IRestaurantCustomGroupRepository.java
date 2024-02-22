package com.bornbhukkad.merchant.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.RestaurantCustomGroupDto;

public interface IRestaurantCustomGroupRepository extends MongoRepository<RestaurantCustomGroupDto, String>{

}
