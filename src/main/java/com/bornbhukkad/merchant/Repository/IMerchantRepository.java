
package com.bornbhukkad.merchant.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.bornbhukkad.merchant.dto.MerchantDto;

public interface IMerchantRepository  extends MongoRepository<MerchantDto, String>  {


	
}

