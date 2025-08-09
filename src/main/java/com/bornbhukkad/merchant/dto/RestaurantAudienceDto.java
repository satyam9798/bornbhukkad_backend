package com.bornbhukkad.merchant.dto;

import java.util.Map;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.bornbhukkad.merchant.dto.RestaurantDto.Time;

@Document (collection = "bb_admin_panel_vendors_audience_segments")
public class RestaurantAudienceDto {
	
	@Transient
    public static final String restaurant_audience_segments_sequence = "restaurant_audience_segments_sequence";
	
	
    @Field("id")
	private String id;
	
	private String vendorId;
	private String name;
	private Map<String, Object> rules;

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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Object> getRules() {
		return rules;
	}
	public void setRules(Map<String, Object> rules) {
		this.rules = rules;
	}
}
