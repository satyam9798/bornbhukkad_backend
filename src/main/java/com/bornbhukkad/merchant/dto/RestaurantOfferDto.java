package com.bornbhukkad.merchant.dto;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "bb_admin_panel_vendors_offers")
public class RestaurantOfferDto {

	@Transient
	public static final String restOffer_sequence = "offer_sequence";
	@Id
	private String _id;
	
	@Field("id")
	private String id;

	private String name;
	
	private String vendorId;

	private Descriptor descriptor;
	
	private String audienceId;
	private Boolean active;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getAudienceId() {
		return audienceId;
	}

	public void setAudienceId(String audienceId) {
		this.audienceId = audienceId;
	}

	private List<String> location_ids;

	private List<String> item_ids;

	private Time time;

	private List<Tag> tags;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setVendorId(String vendorId) {
		this.vendorId= vendorId;
	}
	
	public String getVendorId() {
		return vendorId;
	}

	public Descriptor getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(Descriptor descriptor) {
		this.descriptor = descriptor;
	}

	public class Descriptor {
		private String code;
		private List<String> images;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public List<String> getImages() {
			return images;
		}

		public void setImages(List<String> images) {
			this.images = images;
		}

	}

	public List<String> getLocation_ids() {
		return location_ids;
	}

	public void setLocation_ids(List<String> location_ids) {
		this.location_ids = location_ids;
	}

	public List<String> getItem_ids() {
		return item_ids;
	}

	public void setItem_ids(List<String> item_ids) {
		this.item_ids = item_ids;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public static class Time {
		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public String getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(String timestampString) {
			this.timestamp = timestampString;
		}

		private String label;
		private String timestamp;

	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public static class Tag {
		private String code;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public List<TagValue> getList() {
			return list;
		}

		public void setList(List<TagValue> list) {
			this.list = list;
		}

		private List<TagValue> list;

	}

	public static class TagValue {
		private String code;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		private String value;

	}

}
