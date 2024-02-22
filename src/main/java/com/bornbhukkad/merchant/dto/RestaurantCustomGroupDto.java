package com.bornbhukkad.merchant.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;

@Document(collection = "restaurant_custom_group")
public class RestaurantCustomGroupDto {

	@Transient
    public static final String restCG_sequence = "restCG_sequence";
    
    private String id;

    private Descriptor descriptor;
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Descriptor getDescriptor() {
		return descriptor;
	}
	public void setDescriptor(Descriptor descriptor) {
		this.descriptor = descriptor;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	private List<Tag> tags;

    // Constructors, getters, and setters

    // Inner class representing the "tags" structure
    public static class Tag {
        private String code;
        public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public List<TagListElement> getList() {
			return list;
		}
		public void setList(List<TagListElement> list) {
			this.list = list;
		}
		private List<TagListElement> list;

        // Constructors, getters, and setters
    }

    // Inner class representing the "list" structure inside "tags"
    public static class TagListElement {
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

        // Constructors, getters, and setters
    }
    public static class Descriptor {
    	
    	private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
        
    }
}

