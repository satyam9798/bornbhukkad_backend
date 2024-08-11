package com.bornbhukkad.merchant.dto;

import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.bornbhukkad.merchant.dto.RestaurantDefaultCategoriesDto.Descriptor;
import com.bornbhukkad.merchant.dto.RestaurantDefaultCategoriesDto.Tag;
import com.bornbhukkad.merchant.dto.RestaurantDefaultCategoriesDto.TagValue;


@Document(collection = "default_kirana_categories")
public class KiranaDefaultCategoriesDto {
	@Transient
    public static final String kiranaCategories_sequence = "kiranaCategories_sequence";
	@Field("id")
	private String id;
	@Field("parent_category_id")
    private String parentCategoryId;
    private Descriptor descriptor;
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

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	

	public String getKiranaId() {
		return kiranaId;
	}

	public void setKiranaId(String kiranaId) {
		this.kiranaId = kiranaId;
	}



	private List<Tag> tags;
    private String kiranaId;

    

    public static class Descriptor {
        public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getShortDesc() {
			return shortDesc;
		}
		public void setShortDesc(String shortDesc) {
			this.shortDesc = shortDesc;
		}
		public String getLongDesc() {
			return longDesc;
		}
		public void setLongDesc(String longDesc) {
			this.longDesc = longDesc;
		}
		public List<String> getImages() {
			return images;
		}
		public void setImages(List<String> images) {
			this.images = images;
		}
		private String name;
		@Field("short_desc")
        private String shortDesc;
		@Field("long_desc")
        private String longDesc;
        private List<String> images;

        
    }

    public static class Tag {
        private String code;
        private List<TagValue> list;
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

        
    }

    public static class TagValue {
        private String code;
        private String value;
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

        
    }

}
