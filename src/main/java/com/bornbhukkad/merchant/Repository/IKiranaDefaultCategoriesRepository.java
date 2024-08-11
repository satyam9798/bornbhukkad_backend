package com.bornbhukkad.merchant.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.bornbhukkad.merchant.dto.KiranaDefaultCategoriesDto;

public interface IKiranaDefaultCategoriesRepository extends MongoRepository<KiranaDefaultCategoriesDto, String> {
}