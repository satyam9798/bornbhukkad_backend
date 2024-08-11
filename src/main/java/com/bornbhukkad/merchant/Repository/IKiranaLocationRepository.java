package com.bornbhukkad.merchant.Repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.bornbhukkad.merchant.dto.KiranaLocationDto;

public interface IKiranaLocationRepository extends MongoRepository<KiranaLocationDto, String> {
   

	List<KiranaLocationDto> findByKiranaId(String kiranaId);
}