package com.bornbhukkad.merchant.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document (collection = "bb_admin_panel_vendors_orders")
public class RestaurantOrderDto {
	@Transient
    public static final String restOrder_sequence = "restOrder_sequence";
	@Id
	private String _id;
	@Field("id")
    private String id;
    private String vendorId;
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public RestaurantOrderStatus getStatus() {
		return status;
	}
	public void setStatus(RestaurantOrderStatus status) {
		this.status = status;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public LocalDateTime getAcceptanceDeadline() {
		return acceptanceDeadline;
	}
	public void setAcceptanceDeadline(LocalDateTime acceptanceDeadline) {
		this.acceptanceDeadline = acceptanceDeadline;
	}
	private RestaurantOrderStatus status;
    private LocalDateTime timestamp;
    private LocalDateTime acceptanceDeadline;
}
