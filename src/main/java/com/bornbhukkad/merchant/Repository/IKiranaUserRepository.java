package com.bornbhukkad.merchant.Repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.KiranaUser;

public interface IKiranaUserRepository  extends MongoRepository <KiranaUser, String>{
	
	KiranaUser findByEmail(String email);

}

