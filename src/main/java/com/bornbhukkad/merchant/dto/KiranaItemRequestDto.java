package com.bornbhukkad.merchant.dto;

import java.util.List;

public class KiranaItemRequestDto {
	
	private KiranaProductDto kiranaProductDto;
    private List<KiranaCustomGroupDto> kiranaCustomGroup;
    private List<KiranaItemDto> kiranaItem;
	public KiranaProductDto getKiranaProductDto() {
		return kiranaProductDto;
	}
	public void setKiranaProductDto(KiranaProductDto kiranaProductDto) {
		this.kiranaProductDto = kiranaProductDto;
	}
	public List<KiranaCustomGroupDto> getKiranaCustomGroup() {
		return kiranaCustomGroup;
	}
	public void setKiranaCustomGroup(List<KiranaCustomGroupDto> kiranaCustomGroup) {
		this.kiranaCustomGroup = kiranaCustomGroup;
	}
	public List<KiranaItemDto> getKiranaItem() {
		return kiranaItem;
	}
	public void setKiranaItem(List<KiranaItemDto> kiranaItem) {
		this.kiranaItem = kiranaItem;
	}
	public KiranaItemRequestDto(KiranaProductDto kiranaProductDto, List<KiranaCustomGroupDto> kiranaCustomGroup,
			List<KiranaItemDto> kiranaItem) {
		super();
		this.kiranaProductDto = kiranaProductDto;
		this.kiranaCustomGroup = kiranaCustomGroup;
		this.kiranaItem = kiranaItem;
	}
	
	

}
