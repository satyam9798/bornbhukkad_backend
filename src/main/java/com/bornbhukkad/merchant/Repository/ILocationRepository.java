package com.bornbhukkad.merchant.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.LocationDto;

public interface ILocationRepository  extends MongoRepository<LocationDto, String>  {


	
}

