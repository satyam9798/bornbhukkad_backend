package com.bornbhukkad.merchant.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.bornbhukkad.merchant.dto.KiranaFulfillmentDto;

public interface IKiranaFulfillmentRepository extends MongoRepository<KiranaFulfillmentDto, String> {
}