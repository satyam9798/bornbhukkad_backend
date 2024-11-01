package com.bornbhukkad.merchant.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.RestaurantCustomGroupDto;
import com.bornbhukkad.merchant.dto.RestaurantProductDto;

public interface IRestaurantCustomGroupRepository extends MongoRepository<RestaurantCustomGroupDto, String>{

	Optional<RestaurantCustomGroupDto> findByDefaultId(String defaultId);

}
