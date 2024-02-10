package com.bornbhukkad.merchant.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.RestaurantCategoriesDto;

public interface IRestaurantCategoriesRepository extends MongoRepository<RestaurantCategoriesDto, String>{

}
