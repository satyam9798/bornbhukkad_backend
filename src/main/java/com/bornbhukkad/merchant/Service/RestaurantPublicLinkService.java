package com.bornbhukkad.merchant.Service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bornbhukkad.merchant.Repository.IRestaurantPublicLinkRepository;
import com.bornbhukkad.merchant.dto.RestaurantPublicLink;

@Service
public class RestaurantPublicLinkService {

    private final IRestaurantPublicLinkRepository repository;

    public RestaurantPublicLinkService(IRestaurantPublicLinkRepository repository) {
        this.repository = repository;
    }

    public RestaurantPublicLink getOrCreateLink(String restaurantId) {
        return repository.findByRestaurantId(restaurantId)
                .orElseGet(() -> createNewLink(restaurantId));
    }

    public RestaurantPublicLink regenerateLink(String restaurantId) {
        RestaurantPublicLink link = repository.findByRestaurantId(restaurantId)
                .orElseThrow(() -> new RuntimeException("Link not found"));

        link.setPublicLinkId(generateLinkId());
        link.setIsActive(true);
        return repository.save(link);
    }

    private RestaurantPublicLink createNewLink(String restaurantId) {
        RestaurantPublicLink link = new RestaurantPublicLink();
        link.setRestaurantId(restaurantId);
        link.setPublicLinkId(generateLinkId());
        link.setIsActive(true);
        return repository.save(link);
    }

    private String generateLinkId() {
        return "r_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }
}

