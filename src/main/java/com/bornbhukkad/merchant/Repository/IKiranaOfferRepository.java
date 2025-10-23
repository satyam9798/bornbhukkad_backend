package com.bornbhukkad.merchant.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.KiranaOfferDto;

public interface IKiranaOfferRepository extends MongoRepository<KiranaOfferDto, String>{


		List<KiranaOfferDto> findByKiranaId(String kiranaId);

		Optional<KiranaOfferDto> findById(String id);

		boolean existsByName(String offerNameUpper);

}
