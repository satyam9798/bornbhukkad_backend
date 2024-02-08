package com.bornbhukkad.merchant.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.RestaurantCategoriesDto;

public interface IRestaurantCategories extends MongoRepository<RestaurantCategoriesDto, String>{

}
