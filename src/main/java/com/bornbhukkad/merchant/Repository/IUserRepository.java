package com.bornbhukkad.merchant.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.RestaurantUser;

public interface IUserRepository  extends MongoRepository <RestaurantUser, String>{
	
	RestaurantUser findByEmail(String email);

}
