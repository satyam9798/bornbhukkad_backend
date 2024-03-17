package com.bornbhukkad.merchant.Service;

import java.time.Instant;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bornbhukkad.merchant.Repository.IRestaurantCategoriesRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantCustomGroupRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantDefaultCategoriesRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantFulfillmentRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantItemRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantLocationRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantProductRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantRepository;
import com.bornbhukkad.merchant.Repository.IUserRepository;
import com.bornbhukkad.merchant.controller.AuthController;
import com.bornbhukkad.merchant.dto.RestaurantCategoriesDto;
import com.bornbhukkad.merchant.dto.RestaurantCustomGroupDto;
import com.bornbhukkad.merchant.dto.RestaurantDefaultCategoriesDto;
import com.bornbhukkad.merchant.dto.RestaurantDto;
import com.bornbhukkad.merchant.dto.RestaurantLocationDto;
import com.bornbhukkad.merchant.dto.RestaurantLocationDto.LocationTime;
import com.bornbhukkad.merchant.dto.RestaurantProductDto;
import com.bornbhukkad.merchant.dto.RestaurantProductDto.ProductTime;
import com.bornbhukkad.merchant.dto.RestaurantUser;
import com.mongodb.client.result.UpdateResult;
import com.bornbhukkad.merchant.dto.RestaurantDto.Time;
import com.bornbhukkad.merchant.dto.RestaurantFulfillmentDto;
import com.bornbhukkad.merchant.dto.RestaurantItemDto;

import static com.bornbhukkad.merchant.dto.RestaurantDto.restaurant_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantLocationDto.restLocation_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantProductDto.restProduct_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantCustomGroupDto.restCG_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantItemDto.restItem_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantCategoriesDto.restCategories_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantFulfillmentDto.fulfillment_sequence;

@Service
public class RestaurantServiceImpl implements RestaurantService{
	
	private static final Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);
	@Autowired
	IRestaurantRepository restaurantRepo;
	@Autowired
	IUserRepository restaurantUser;
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
	IRestaurantDefaultCategoriesRepository restDefCategoriesRepo;
	@Autowired
	IRestaurantFulfillmentRepository restaurantFulfillmentRepo;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
    private final String vendorTtl;
    
    @Autowired
    private MongoTemplate mongoTemplate;
	
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
			
			merchant.setTime(time);
			merchant.setTtl(vendorTtl);
			
			merchant.setId("P"+sequenceGeneratorService.getSequenceNumber(restaurant_sequence));
			restaurantRepo.save(merchant);
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
		Query query = new Query(Criteria.where("id").is(location.getVendorId()));
		Update update = new Update()
	            .set("locationsId",location.getId());
	        
	   UpdateResult(query, update, RestaurantDto.class);
		
	}	
	
	@Override
	public void addRestaurantCategories(RestaurantCategoriesDto categories) {
		categories.setId(""+sequenceGeneratorService.getSequenceNumber(restCategories_sequence));
		restaurantCatgoryRepo.save(categories);
		
	}
	
	@Override
	public void addRestaurantProduct(RestaurantProductDto product) {
		
		ProductTime time= new ProductTime();
		time.setLabel("enable");
		time.setTimestamp(Instant.now());
		product.setTime(time);
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
	
	@Override
	public List<RestaurantDefaultCategoriesDto> getRestDefaultCategories() {
		List<RestaurantDefaultCategoriesDto> categories = new ArrayList<>();
		restDefCategoriesRepo.findAll().forEach(category -> categories.add(category));
		return categories;
		
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

	@Override
	public void addRestaurantFulfillment(RestaurantFulfillmentDto fulfillment) {
		fulfillment.setId("F" + sequenceGeneratorService.getSequenceNumber(fulfillment_sequence));
		restaurantFulfillmentRepo.save(fulfillment);
		Query query = new Query(Criteria.where("id").is(fulfillment.getVendorId()));
		Update update = new Update()
	            .set("fulfillmentsId",fulfillment.getId());
	   UpdateResult(query, update, RestaurantDto.class);
	}



 
	




}
