package com.bornbhukkad.merchant.dto;

public class OrderStatusUpdateRequest {
    private RestaurantOrderStatus status;

    public RestaurantOrderStatus getStatus() {
        return status;
    }

    public void setStatus(RestaurantOrderStatus status) {
        this.status = status;
    }
}
