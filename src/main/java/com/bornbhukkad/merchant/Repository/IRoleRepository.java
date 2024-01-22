package com.bornbhukkad.merchant.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.Role;

public interface IRoleRepository  extends MongoRepository <Role, String>{
	
	Role findByRole(String role);
	  

}
