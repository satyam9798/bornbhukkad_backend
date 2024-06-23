package com.bornbhukkad.merchant.dto;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document (collection = "bb_admin_panel_vendors_products")
public class RestaurantProductDto {
	@Transient
    public static final String restProduct_sequence = "restProduct_sequence";
	@Id
	private String _id;
	@Field("id")
    private String id;
    private ProductTime time;
    private Descriptor descriptor;
    private Quantity quantity;
    private Price price;
    private String category_id;
    private List<String> category_ids;
//    private String vendorId;
    private List<String> customizationItems;
    private String parent_category_id;
    private String fulfillment_id;
    private String location_id;
    private boolean related;
    private boolean recommended;
    @Field("@ondc/org/returnable")
    private boolean ondc_org_returnable;
    @Field("@ondc/org/cancellable")
    private boolean ondc_org_cancellable;
    @Field("@ondc/org/return_window")
    private String ondc_org_return_window;
    @Field("@ondc/org/seller_pickup_return")
    private boolean ondc_org_seller_pickup_return;
    private DimensionDTO dimension;
    private int packagingPrice;
    private String timing;
    private WeightDTO weight;
    public DimensionDTO getDimension() {
        return dimension;
    }

    public void setDimension(DimensionDTO dimension) {
        this.dimension = dimension;
    }
    public int getPackagingPrice() {
        return packagingPrice;
    }

    public void setPackagingPrice(int packagingPrice) {
        this.packagingPrice = packagingPrice;
    }
    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public WeightDTO getWeight() {
        return weight;
    }

    public void setWeight(WeightDTO weight) {
        this.weight = weight;
    }
    public static class DimensionDTO {
        private String unit;
        private int value;

        // Constructor, getters, and setters
//        public DimensionDTO() {
//        }
//
//        public DimensionDTO(String unit, int value) {
//            this.unit = unit;
//            this.value = value;
//        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static class WeightDTO {
        private String unit;
        private int value;

        // Constructor, getters, and setters
//        public WeightDTO() {
//        }
//
//        public WeightDTO(String unit, int value) {
//            this.unit = unit;
//            this.value = value;
//        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ProductTime getTime() {
		return time;
	}

	public void setTime(ProductTime time) {
		this.time = time;
	}

	public Descriptor getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(Descriptor descriptor) {
		this.descriptor = descriptor;
	}

	public Quantity getQuantity() {
		return quantity;
	}

	public void setQuantity(Quantity quantity) {
		this.quantity = quantity;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public List<String> getCategory_ids() {
		return category_ids;
	}

	public void setCategory_ids(List<String> category_ids) {
		this.category_ids = category_ids;
	}

//	public String getVendorId() {
//		return vendorId;
//	}
//
//	public void setVendorId(String vendorId) {
//		this.vendorId = vendorId;
//	}

	public List<String> getCustomizationItems() {
		return customizationItems;
	}

	public void setCustomizationItems(List<String> customizationItems) {
		this.customizationItems = customizationItems;
	}

	public String getParent_category_id() {
		return parent_category_id;
	}

	public void setParent_category_id(String parent_category_id) {
		this.parent_category_id = parent_category_id;
	}

	public String getFulfillment_id() {
		return fulfillment_id;
	}

	public void setFulfillment_id(String fulfillment_id) {
		this.fulfillment_id = fulfillment_id;
	}

	public String getLocation_id() {
		return location_id;
	}

	public void setLocation_id(String location_id) {
		this.location_id = location_id;
	}

	public boolean isRelated() {
		return related;
	}

	public void setRelated(boolean related) {
		this.related = related;
	}

	public boolean isRecommended() {
		return recommended;
	}

	public void setRecommended(boolean recommended) {
		this.recommended = recommended;
	}

	public boolean isOndc_org_returnable() {
		return ondc_org_returnable;
	}

	public void setOndc_org_returnable(boolean ondc_org_returnable) {
		this.ondc_org_returnable = ondc_org_returnable;
	}

	public boolean isOndc_org_cancellable() {
		return ondc_org_cancellable;
	}

	public void setOndc_org_cancellable(boolean ondc_org_cancellable) {
		this.ondc_org_cancellable = ondc_org_cancellable;
	}

	public String getOndc_org_return_window() {
		return ondc_org_return_window;
	}

	public void setOndc_org_return_window(String ondc_org_return_window) {
		this.ondc_org_return_window = ondc_org_return_window;
	}

	public boolean isOndc_org_seller_pickup_return() {
		return ondc_org_seller_pickup_return;
	}

	public void setOndc_org_seller_pickup_return(boolean ondc_org_seller_pickup_return) {
		this.ondc_org_seller_pickup_return = ondc_org_seller_pickup_return;
	}

	public String getOndc_org_time_to_ship() {
		return ondc_org_time_to_ship;
	}

	public void setOndc_org_time_to_ship(String ondc_org_time_to_ship) {
		this.ondc_org_time_to_ship = ondc_org_time_to_ship;
	}

	public boolean isOndc_org_available_on_cod() {
		return ondc_org_available_on_cod;
	}

	public void setOndc_org_available_on_cod(boolean ondc_org_available_on_cod) {
		this.ondc_org_available_on_cod = ondc_org_available_on_cod;
	}

	public String getOndc_org_contact_details_consumer_care() {
		return ondc_org_contact_details_consumer_care;
	}

	public void setOndc_org_contact_details_consumer_care(String ondc_org_contact_details_consumer_care) {
		this.ondc_org_contact_details_consumer_care = ondc_org_contact_details_consumer_care;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	
	@Field("@ondc/org/time_to_ship")
	private String ondc_org_time_to_ship;
	
	@Field("@ondc/org/available_on_cod")
    private boolean ondc_org_available_on_cod;
	
	@Field("@ondc/org/contact_details_consumer_care\"")
    private String ondc_org_contact_details_consumer_care;
    private List<Tag> tags;
    private String vendorId;

    // Constructors, getters, and setters

    public static class ProductTime {
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
        private String symbol;
        public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSymbol() {
			return symbol;
		}
		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}
		public String getShort_desc() {
			return short_desc;
		}
		public void setShort_desc(String short_desc) {
			this.short_desc = short_desc;
		}
		public String getLong_desc() {
			return long_desc;
		}
		public void setLong_desc(String long_desc) {
			this.long_desc = long_desc;
		}
		public List<String> getImages() {
			return images;
		}
		public void setImages(List<String> images) {
			this.images = images;
		}
		private String short_desc;
        private String long_desc;
        private List<String> images;

        
    }

    public static class Quantity {
        private Unitized unitized;
        private Available available;
        public Unitized getUnitized() {
			return unitized;
		}
		public void setUnitized(Unitized unitized) {
			this.unitized = unitized;
		}
		public Available getAvailable() {
			return available;
		}
		public void setAvailable(Available available) {
			this.available = available;
		}
		public Maximum getMaximum() {
			return maximum;
		}
		public void setMaximum(Maximum maximum) {
			this.maximum = maximum;
		}
		private Maximum maximum;

        
    }

    public static class Unitized {
        private Measure measure;

		public Measure getMeasure() {
			return measure;
		}

		public void setMeasure(Measure measure) {
			this.measure = measure;
		}

        
    }

    public static class Measure {
        private String unit;
        public String getUnit() {
			return unit;
		}
		public void setUnit(String unit) {
			this.unit = unit;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		private String value;

        
    }

    public static class Available {
        private String count;

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

        
    }

    public static class Maximum {
        private String count;

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

        
    }

    public static class Price {
        private String currency;
        private String value;
        public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getMaximum_value() {
			return maximum_value;
		}
		public void setMaximum_value(String maximum_value) {
			this.maximum_value = maximum_value;
		}
		public List<Tag> getTags() {
			return tags;
		}
		public void setTags(List<Tag> tags) {
			this.tags = tags;
		}
		private String maximum_value;
        private List<Tag> tags;

       
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

