package com.bornbhukkad.merchant.Repository;


import com.bornbhukkad.merchant.dto.RestaurantUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.KiranaUser;
import org.springframework.data.mongodb.repository.Query;

public interface IKiranaUserRepository  extends MongoRepository <KiranaUser, String>{
    KiranaUser findByEmail(String email);
    @Query("{ '$or': [ { 'email': ?0 }, { 'phone': ?1 } ] }")
    KiranaUser findByEmailOrPhone(String email, Long phone);

}

