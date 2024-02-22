package com.bornbhukkad.merchant.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bornbhukkad.merchant.Repository.IRestaurantCategoriesRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantCustomGroupRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantItemRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantLocationRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantProductRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantRepository;
import com.bornbhukkad.merchant.dto.RestaurantCategoriesDto;
import com.bornbhukkad.merchant.dto.RestaurantCustomGroupDto;
import com.bornbhukkad.merchant.dto.RestaurantDto;
import com.bornbhukkad.merchant.dto.RestaurantLocationDto;
import com.bornbhukkad.merchant.dto.RestaurantLocationDto.LocationTime;
import com.bornbhukkad.merchant.dto.RestaurantProductDto;
import com.bornbhukkad.merchant.dto.RestaurantDto.Time;
import com.bornbhukkad.merchant.dto.RestaurantItemDto;

import static com.bornbhukkad.merchant.dto.RestaurantDto.restaurant_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantLocationDto.restLocation_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantProductDto.restProduct_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantCustomGroupDto.restCG_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantItemDto.restItem_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantCategoriesDto.restCategories_sequence;

@Service
public class RestaurantServiceImpl implements RestaurantService{
	

	@Autowired
	IRestaurantRepository restaurantRepo;
	@Autowired
	IRestaurantLocationRepository restaurantLocationRepo;
	@Autowired
	IRestaurantCategoriesRepository restaurantCatgoryRepo;
	@Autowired
	IRestaurantProductRepository restaurantProductRepo;
	@Autowired
	IRestaurantCustomGroupRepository restaurantCustomGroupRepo;
	@Autowired
	IRestaurantItemRepository restaurantItemRepo;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
private final String vendorTtl;
	
	public RestaurantServiceImpl(@Value("${app.service.vendorTTL}") String vendorTtl) {
		this.vendorTtl = vendorTtl;
	}

	@Override
	public void addRestaurant(RestaurantDto merchant) {
		boolean repeated = checkName(merchant.getDescriptor().getName());
		if (repeated==false) {
			Time time= new Time();
			time.setLabel("enable");
			time.setTimestamp(Instant.now());
			
			merchant.setTime(merchant.getTime());
			merchant.setTtl(vendorTtl);
			
			merchant.setId("P"+sequenceGeneratorService.getSequenceNumber(restaurant_sequence));
			restaurantRepo.save(merchant);
		}
		
	}

	@Override
	public void addRestaurantLocation(RestaurantLocationDto location) {
		LocationTime time= new LocationTime();
		time.setLabel("enable");
		time.setTimestamp(Instant.now());
		time.setDays(location.getTime().getDays());
		time.setSchedule(location.getTime().getSchedule());
		time.setRange(location.getTime().getRange());
		
		
		location.setTime(time);
		location.setId("L"+sequenceGeneratorService.getSequenceNumber(restLocation_sequence));
		restaurantLocationRepo.save(location);
		
	}	
	
	@Override
	public void addRestaurantCategories(RestaurantCategoriesDto categories) {
		categories.setId(""+sequenceGeneratorService.getSequenceNumber(restCategories_sequence));
		restaurantCatgoryRepo.save(categories);
		
	}
	
	@Override
	public void addRestaurantProduct(RestaurantProductDto product) {
		
		Time time= new Time();
		time.setLabel("enable");
		time.setTimestamp(Instant.now());
		
		product.setTime(product.getTime());
		product.setId("I"+sequenceGeneratorService.getSequenceNumber(restProduct_sequence));
		restaurantProductRepo.save(product);
		
	}
	
	@Override
	public void addRestaurantCustomGroup(RestaurantCustomGroupDto customGroup) {
		
		customGroup.setId("CG" + sequenceGeneratorService.getSequenceNumber(restCG_sequence));
		restaurantCustomGroupRepo.save(customGroup);
		
	}
	@Override
	public void addRestaurantItem(RestaurantItemDto item) {
		// TODO Auto-generated method stub
		item.setId("C" + sequenceGeneratorService.getSequenceNumber(restItem_sequence));
		restaurantItemRepo.save(item);
		
	}
	

	@Override
	public List<RestaurantDto> getAll() {
		List<RestaurantDto> merchants = new ArrayList<>();
		restaurantRepo.findAll().forEach(merchant -> merchants.add(merchant));
		return merchants;
		
	}
	
	public boolean checkName (String name) {
		boolean repeated = false;
		List<RestaurantDto> merchants = new ArrayList<>();
		merchants = getAll();
	    int count = 0;
		while(repeated == false && count<merchants.size()) {
			if (merchants.get(count).getDescriptor().getName().equalsIgnoreCase(name)) {
				if (merchants.get(count).getDescriptor().getName().equalsIgnoreCase("Vendor")) {
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
