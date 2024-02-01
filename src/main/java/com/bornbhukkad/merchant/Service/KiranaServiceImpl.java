package com.bornbhukkad.merchant.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bornbhukkad.merchant.Repository.IKiranaLocationRepository;
import com.bornbhukkad.merchant.Repository.IKiranaRepository;
import com.bornbhukkad.merchant.dto.KiranaDto;
import com.bornbhukkad.merchant.dto.KiranaLocationDto;
import com.bornbhukkad.merchant.dto.KiranaLocationDto.LocationTime;
import com.bornbhukkad.merchant.dto.KiranaDto.Time;
@Service
public class KiranaServiceImpl implements KiranaService {
	
	@Autowired
	IKiranaRepository kiranaRepo;
	@Autowired
	IKiranaLocationRepository kiranaLocationRepo;
	
	private final String vendorTtl;
	
	public KiranaServiceImpl(@Value("${app.service.vendorTTL}") String vendorTtl) {
		this.vendorTtl = vendorTtl;
	}

	@Override
	public void addKirana(KiranaDto merchant) {
		boolean repeated = checkName(merchant.getDescriptor().getName());
		if (repeated==false) {
			Time time= new Time();
			time.setLabel("enable");
			time.setTimestamp(Instant.now());
			
			merchant.setTime(merchant.getTime());
			
			merchant.setTtl(vendorTtl);
			merchant.setVendorId("P" + UUID.randomUUID().toString().substring(0,2));
//			logger.info("getLongDesc"+merchant.getDescriptor().getLongDesc());
			kiranaRepo.save(merchant);
		}
		
	}

	@Override
	public void addKiranaLocation(KiranaLocationDto location) {
		LocationTime time= new LocationTime();
		time.setLabel("enable");
		time.setTimestamp(Instant.now());
		time.setDays(location.getTime().getDays());
		time.setSchedule(location.getTime().getSchedule());
		time.setRange(location.getTime().getRange());
		
		
		location.setTime(time);
		location.setLocationId("L" + UUID.randomUUID().toString().substring(0,2));
		kiranaLocationRepo.save(location);
		
	}
	
	@Override
	public List<KiranaDto> getAll() {
		List<KiranaDto> merchants = new ArrayList<>();
		kiranaRepo.findAll().forEach(merchant -> merchants.add(merchant));
		return merchants;
		
	}
	
	public boolean checkName (String name) {
		boolean repeated = false;
		List<KiranaDto> merchants = new ArrayList<>();
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
