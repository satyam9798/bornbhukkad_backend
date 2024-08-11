package com.bornbhukkad.merchant.Repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.bornbhukkad.merchant.dto.KiranaCustomGroupDto;

public interface IKiranaCustomGroupRepository extends MongoRepository<KiranaCustomGroupDto, String> {
    List<KiranaCustomGroupDto> findByParentProductId(String parentProductId);
}