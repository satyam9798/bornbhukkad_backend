package com.bornbhukkad.merchant.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.RestaurantDefaultCategoriesDto;

public interface IRestaurantDefaultCategoriesRepository extends MongoRepository<RestaurantDefaultCategoriesDto, String>{

}
