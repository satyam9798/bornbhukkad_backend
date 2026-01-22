package com.bornbhukkad.merchant.dto;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "restaurant_public_links")
public class RestaurantPublicLink {

    @Id
    private String id;

    private String restaurantId;
    private String publicLinkId;
    private Boolean isActive;

    @CreatedDate
    private Instant createdAt;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getPublicLinkId() {
		return publicLinkId;
	}

	public void setPublicLinkId(String publicLinkId) {
		this.publicLinkId = publicLinkId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	@LastModifiedDate
    private Instant updatedAt;

    // getters & setters
}

