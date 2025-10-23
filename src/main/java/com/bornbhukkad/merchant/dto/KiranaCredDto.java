package com.bornbhukkad.merchant.dto;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.bornbhukkad.merchant.dto.KiranaLocationDto.Item;
import com.bornbhukkad.merchant.dto.KiranaLocationDto.Tag;

@Document(collection = "bb_admin_panel_kirana_creds")
public class KiranaCredDto {

	@Transient
	public static final String kiranaCred_sequence = "kirana_cred_sequence";

	@Field("id")
	private String id;

	@Id
	private String _id;
	
	private String url;
	
	private String kiranaId;

	public String getKiranaId() {
		return kiranaId;
	}
	public void setKiranaId(String kiranaId) {
		this.kiranaId = kiranaId;
	}
	private Descriptor descriptor;
    private List<Tag> tags;

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
	public static class Tag {
        private String code;
        public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public List<Item> getList() {
			return list;
		}
		public void setList(List<Item> list) {
			this.list = list;
		}
		private List<Item> list;

         
    }

	public class Descriptor {
		String code;
		String name;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

	}
	
	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
