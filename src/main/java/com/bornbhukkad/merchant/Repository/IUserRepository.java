package com.bornbhukkad.merchant.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.User;

public interface IUserRepository  extends MongoRepository <User, String>{
	
	User findByEmail(String email);

}
