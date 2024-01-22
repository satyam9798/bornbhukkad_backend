package com.bornbhukkad.merchant.Service;


import java.time.Instant;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import com.bornbhukkad.merchant.Repository.ILocationRepository;
import com.bornbhukkad.merchant.Repository.IMerchantRepository;
import com.bornbhukkad.merchant.dto.LocationDto;
import com.bornbhukkad.merchant.dto.MerchantDto;
import com.bornbhukkad.merchant.dto.MerchantDto.Time;
import com.bornbhukkad.merchant.dto.LocationDto.LocationTime;;


@Service
public class MerchantServiceImpl implements MerchantService {
	@Autowired
	IMerchantRepository merchantRepo;
	
	@Autowired
	ILocationRepository locationRepo;
	private final String vendorTtl;
	
	public MerchantServiceImpl(@Value("${app.service.vendorTTL}") String vendorTtl) {
				this.vendorTtl = vendorTtl;

			}
	
	
	@Override
	public List<MerchantDto> getAll() {
		List<MerchantDto> merchants = new ArrayList<>();
		merchantRepo.findAll().forEach(merchant -> merchants.add(merchant));
		return merchants;
		
	}
	
	@Override
	public void addVendor(MerchantDto merchant) {
		boolean repeated = checkName(merchant.getDescriptor().getName());
		if (repeated==false) {
			Time time= new Time();
			time.setLabel("enable");
			time.setTimestamp(Instant.now());
			
			merchant.setTime(time);
			
			merchant.setTtl(vendorTtl);
			merchant.setVendorId("P" + UUID.randomUUID().toString().substring(0,2));
			merchantRepo.save(merchant);
		} 
		
	}
	
	@Override
	public void addVendorLocation(LocationDto location) {
			LocationTime time= new LocationTime();
			time.setLabel("enable");
			time.setTimestamp(Instant.now());
			time.setDays(location.getTime().getDays());
			time.setSchedule(location.getTime().getSchedule());
			time.setRange(location.getTime().getRange());
			
			
			location.setTime(time);
			location.setLocationId("L" + UUID.randomUUID().toString().substring(0,2));
			locationRepo.save(location);
	}

	@Override
	public void deleteVendor(String id) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean checkName (String name) {
		boolean repeated = false;
		List<MerchantDto> merchants = new ArrayList<>();
		merchants = getAll();
	    int count = 0;
		while(repeated == false && count<merchants.size()) {
			if (merchants.get(count).getDescriptor().getName().equalsIgnoreCase(name)) {
				if (merchants.get(count).getDescriptor().getName().equalsIgnoreCase("Anònim")) {
					repeated = false;
				} else {
					repeated = true;
				}
			}
			count ++;
		}
		return repeated;
	}

}
