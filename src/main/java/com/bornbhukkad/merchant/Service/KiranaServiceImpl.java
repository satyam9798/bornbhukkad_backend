package com.bornbhukkad.merchant.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.bornbhukkad.merchant.Repository.IKiranaLocationRepository;
import com.bornbhukkad.merchant.Repository.IKiranaRepository;
import com.bornbhukkad.merchant.dto.KiranaDto;
import com.bornbhukkad.merchant.dto.KiranaLocationDto;
import com.bornbhukkad.merchant.dto.RestaurantUser;
import com.bornbhukkad.merchant.dto.KiranaLocationDto.LocationTime;
import com.mongodb.client.result.UpdateResult;
import com.bornbhukkad.merchant.dto.KiranaDto.Time;

import static com.bornbhukkad.merchant.dto.KiranaDto.kirana_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantDto.restaurant_sequence;

@Service
public class KiranaServiceImpl implements KiranaService {
	
	@Autowired
	IKiranaRepository kiranaRepo;
	@Autowired
	IKiranaLocationRepository kiranaLocationRepo;
	
	private static MongoTemplate mongoTemplate;
	
	private final String vendorTtl;
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
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
			
			merchant.setTime(time);
			merchant.setTtl(vendorTtl);
			
			merchant.setId("P"+sequenceGeneratorService.getSequenceNumber(kirana_sequence));
			kiranaRepo.save(merchant);
			Query query = new Query(Criteria.where("email").is(merchant.getUserEmail()));	
			Update update = new Update()
		            .set("merchantId", merchant.getId());
		    UpdateResult(query, update, RestaurantUser.class);
		}
		
		
	}
	private UpdateResult UpdateResult(Query query, Update update, Class<?> class1) {
		return mongoTemplate.updateFirst(query,  update, class1);	
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
