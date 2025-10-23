

package com.bornbhukkad.merchant.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.KiranaCredDto;

public interface IkiranaCredsRepository extends MongoRepository<KiranaCredDto, String>{


		List<KiranaCredDto> findByKiranaId(String kiranaId);

		Optional<KiranaCredDto> findById(String id);

}
