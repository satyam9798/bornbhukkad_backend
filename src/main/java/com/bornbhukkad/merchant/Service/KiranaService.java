package com.bornbhukkad.merchant.Service;

import java.util.List;

import com.bornbhukkad.merchant.dto.KiranaDto;
import com.bornbhukkad.merchant.dto.KiranaLocationDto;

public interface KiranaService {
	public void addKirana(KiranaDto merchant);
	public void addKiranaLocation(KiranaLocationDto location);
	public List<KiranaDto> getAll();

}
