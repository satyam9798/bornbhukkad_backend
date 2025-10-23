package com.bornbhukkad.merchant.dto;

import java.util.List;

public class KiranaItemRequestDto {

	private List<KiranaProductDto> kiranaProductDto;
	private List<KiranaCustomGroupDto> kiranaCustomGroup;
	private List<KiranaItemDto> kiranaItem;
	private List<KiranaCredDto> kiranaCredDto;

	public List<KiranaCredDto> getKiranaCredDto() {
		return kiranaCredDto;
	}

	public void setKiranaCredDto(List<KiranaCredDto> kiranaCredDto) {
		this.kiranaCredDto = kiranaCredDto;
	}

	public List<KiranaProductDto> getKiranaProductDto() {
		return kiranaProductDto;
	}

	public void setKiranaProductDto(List<KiranaProductDto> kiranaProductDto) {
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

	public KiranaItemRequestDto(List<KiranaProductDto> kiranaProductDto, List<KiranaCustomGroupDto> kiranaCustomGroup,
			List<KiranaItemDto> kiranaItem, List<KiranaCredDto> kiranaCred) {
		super();
		this.kiranaProductDto = kiranaProductDto;
		this.kiranaCustomGroup = kiranaCustomGroup;
		this.kiranaItem = kiranaItem;
		this.kiranaCredDto = kiranaCred;
	}

}
