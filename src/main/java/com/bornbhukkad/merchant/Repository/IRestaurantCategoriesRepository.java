package com.bornbhukkad.merchant.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.RestaurantCategoriesDto;

public interface IRestaurantCategoriesRepository extends MongoRepository<RestaurantCategoriesDto, String>{
	List<RestaurantCategoriesDto> findByParentCategoryId(String vendorId);
}
