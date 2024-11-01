package com.bornbhukkad.merchant.dto;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.bornbhukkad.merchant.dto.RestaurantFulfillmentDto.ContactDTO;
import com.bornbhukkad.merchant.dto.RestaurantFulfillmentDto.DeliveryTime;

@Document(collection = "bb_admin_panel_kirana_fulfillments")
public class KiranaFulfillmentDto {
	@Transient
    public static final String fulfillment_sequence = "fulfillment_sequence";
	@Field("id")
    private String id;

    private String type;

    private ContactDTO contact;

    private String kiranaId;
    private DeliveryTime deliveryTime;
    private String defaultId;
    
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

	public String getKiranaId() {
		return kiranaId;
	}

	public void setKiranaId(String kiranaId) {
		this.kiranaId = kiranaId;
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
