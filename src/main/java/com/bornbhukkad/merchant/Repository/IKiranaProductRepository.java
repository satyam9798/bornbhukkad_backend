package com.bornbhukkad.merchant.Repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.bornbhukkad.merchant.dto.KiranaProductDto;

public interface IKiranaProductRepository extends MongoRepository<KiranaProductDto, String> {
//    List<KiranaProductDto> findByKiranaId(String kiranaId);
}