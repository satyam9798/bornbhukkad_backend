package com.bornbhukkad.merchant.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.KiranaDto;

public interface IKiranaRepository extends MongoRepository<KiranaDto, String> {

}
