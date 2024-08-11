package com.bornbhukkad.merchant.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.KiranaCategoriesDto;


public interface IKiranaCategoriesRepository extends MongoRepository<KiranaCategoriesDto, String>{
	List<KiranaCategoriesDto> findByParentCategoryId(String kiranaId);
}
