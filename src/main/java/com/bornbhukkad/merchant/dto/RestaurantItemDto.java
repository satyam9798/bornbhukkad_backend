package com.bornbhukkad.merchant.dto;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document (collection = "bb_admin_panel_vendors_items")
public class RestaurantItemDto {
	@Transient
    public static final String restItem_sequence = "restItem_sequence";
	@Id
	public String _id;
	@Field("id")
    private String id;

    private String parentCategoryId;
    private String parentItemId;

    public String getParentItemId() {
		return parentItemId;
	}

	public void setParentItemId(String parentItemId) {
		this.parentItemId = parentItemId;
	}

	private Descriptor descriptor;

    private Quantity quantity;

    private Price price;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(String parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
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

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public boolean isRelated() {
		return related;
	}

	public void setRelated(boolean related) {
		this.related = related;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@Field("category_id")
    private String categoryId;

    private boolean related;

    private List<Tag> tags;

    // Getters and setters

//    public static class Id {
//        @Field("$oid")
//        private String oid;
//
//        // Getters and setters
//    }

    public static class Descriptor {
        private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

        // Getters and setters
    }

    public static class Quantity {
        private Unitized unitized;
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
		private Available available;
        private Maximum maximum;

        // Getters and setters
    }

    public static class Unitized {
        private Measure measure;

		public Measure getMeasure() {
			return measure;
		}

		public void setMeasure(Measure measure) {
			this.measure = measure;
		}

        // Getters and setters
    }

    public static class Available {
        private String count;

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

        // Getters and setters
    }

    public static class Maximum {
        private String count;

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

        // Getters and setters
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

        // Getters and setters
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

		public String getMaximumValue() {
			return maximumValue;
		}

		public void setMaximumValue(String maximumValue) {
			this.maximumValue = maximumValue;
		}

		@Field("maximum_value")
        private String maximumValue;

        // Getters and setters
    }

    public static class Tag {
        private String code;
        public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public List<ListItem> getList() {
			return list;
		}
		public void setList(List<ListItem> list) {
			this.list = list;
		}
		private List<ListItem> list;

        // Getters and setters
    }

    public static class ListItem {
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

        // Getters and setters
    }
}
