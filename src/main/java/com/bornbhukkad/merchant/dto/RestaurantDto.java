package com.bornbhukkad.merchant.dto;


import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;





@Document (collection = "restaurant")
public class RestaurantDto{

	
    @Transient
    public static final String restaurant_sequence = "restaurant_sequence";
	
	
    
	private String id;
	

    private Time time;
	

    private Descriptor descriptor;
	
	@Field("@ondc/org/fssai_license_no")
    private String fssaiLicenseNo;
	
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Descriptor getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(Descriptor descriptor) {
		this.descriptor = descriptor;
	}

	public String getFssaiLicenseNo() {
		return fssaiLicenseNo;
	}

	public void setFssaiLicenseNo(String fssaiLicenseNo) {
		this.fssaiLicenseNo = fssaiLicenseNo;
	}

	public String getTtl() {
		return ttl;
	}

	public void setTtl(String ttl) {
		this.ttl = ttl;
	}

	public List<String> getLocationsId() {
		return locationsId;
	}

	public void setLocationsId(List<String> locationsId) {
		this.locationsId = locationsId;
	}

	public List<String> getFulfillmentsId() {
		return fulfillmentsId;
	}

	public void setFulfillmentsId(List<String> fulfillmentsId) {
		this.fulfillmentsId = fulfillmentsId;
	}

	public List<String> getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(List<String> categoryId) {
		this.categoryId = categoryId;
	}

	public List<String> getOffersAvail() {
		return offersAvail;
	}

	public void setOffersAvail(List<String> offersAvail) {
		this.offersAvail = offersAvail;
	}
	
	

	private String ttl;
	
    private List<String> locationsId;
	
    private List<String> fulfillmentsId;
	
	@Field("category_id")
    private List<String> categoryId;
	
    private List<String> offersAvail;



    public static class Time {
        public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public Instant getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(Instant timestamp) {
			this.timestamp = timestamp;
		}
		private String label;
        private Instant timestamp;


    }

    public static class Descriptor {
    	

        private String name;
    	
        public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getLongDesc() {
			return longDesc;
		}
		public void setLongDesc(String longDesc) {
			this.longDesc = longDesc;
		}
		public String getShortDesc() {
			return shortDesc;
		}
		public void setShortDesc(String shortDesc) {
			this.shortDesc = shortDesc;
		}
		public List<String> getImages() {
			return images;
		}
		public void setImages(List<String> images) {
			this.images = images;
		}
		public String getSymbol() {
			return symbol;
		}
		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}
		@Field("long_desc")
		private String longDesc;
		

        private String shortDesc;
		
		@JsonProperty("images")
        private List<String> images;
		
        private String symbol;


    }
}
