package com.bornbhukkad.merchant.Service;


import java.util.List;

import com.bornbhukkad.merchant.dto.LocationDto;
import com.bornbhukkad.merchant.dto.MerchantDto;


public interface MerchantService {
	public void addVendor(MerchantDto merchant);
	public void addVendorLocation(LocationDto location);
	public void deleteVendor (String id);
	public List<MerchantDto> getAll();

}
