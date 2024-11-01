package com.bornbhukkad.merchant.dto;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection="bb_admin_panel_vendors_fulfillments")
public class RestaurantFulfillmentDto {
	
	@Transient
    public static final String fulfillment_sequence = "fulfillment_sequence";
	@Field("id")
    private String id;

    private String type;
    private String defaultId;

    private ContactDTO contact;

    private String vendorId;
    private DeliveryTime deliveryTime;
    
    public DeliveryTime getDeliveryTime() {
		return deliveryTime;
	}



	public void setDeliveryTime(DeliveryTime deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public static class DeliveryTime {
    	private String deliveryStartDay;
        private String deliveryEndDay;
        private String deliveryStartTime;
        private String deliveryEndTime;
        
        public String getDeliveryStartDay() {
    		return deliveryStartDay;
    	}

    	public void setDeliveryStartDay(String deliveryStartDay) {
    		this.deliveryStartDay = deliveryStartDay;
    	}

    	public String getDeliveryEndDay() {
    		return deliveryEndDay;
    	}

    	public void setDeliveryEndDay(String deliveryEndDay) {
    		this.deliveryEndDay = deliveryEndDay;
    	}

    	public String getDeliveryStartTime() {
    		return deliveryStartTime;
    	}

    	public void setDeliveryStartTime(String deliveryStartTime) {
    		this.deliveryStartTime = deliveryStartTime;
    	}

    	public String getDeliveryEndTime() {
    		return deliveryEndTime;
    	}

    	public void setDeliveryEndTime(String deliveryEndTime) {
    		this.deliveryEndTime = deliveryEndTime;
    	}


    }
    
    

    public String getDefaultId() {
		return defaultId;
	}

	

	public void setDefaultId(String defaultId) {
		this.defaultId = defaultId;
	}

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ContactDTO getContact() {
		return contact;
	}

	public void setContact(ContactDTO contact) {
		this.contact = contact;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public static class ContactDTO {
        private String phone;
        public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		private String email;


    }

}
