package com.bornbhukkad.merchant.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.RestaurantUser;
import org.springframework.data.mongodb.repository.Query;

public interface IUserRepository  extends MongoRepository <RestaurantUser, String>{
    RestaurantUser findByEmail(String email);
    @Query("{ '$or': [ { 'email': ?0 }, { 'phone': ?1 } ] }")
    RestaurantUser findByEmailOrPhone(String email, Long phone);

}
