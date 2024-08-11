package com.bornbhukkad.merchant.Repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.bornbhukkad.merchant.dto.KiranaItemDto;

public interface IKiranaItemRepository extends MongoRepository<KiranaItemDto, String> {
    List<KiranaItemDto> findByParentItemId(String parentItemId);
}