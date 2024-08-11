package com.bornbhukkad.merchant.Service;

import java.security.KeyStore.Entry.Attribute;
import java.time.Instant;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.bornbhukkad.merchant.dto.KiranaLocationDto;
import com.bornbhukkad.merchant.dto.KiranaProductDto;
import com.bornbhukkad.merchant.dto.RestaurantCategoriesDto;
import com.bornbhukkad.merchant.dto.RestaurantCustomGroupDto;
import com.bornbhukkad.merchant.dto.RestaurantDefaultCategoriesDto;
import com.bornbhukkad.merchant.dto.RestaurantDto;
import com.bornbhukkad.merchant.dto.RestaurantLocationDto;
import com.bornbhukkad.merchant.dto.RestaurantLocationDto.Item;
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
private static MongoTemplate mongoTemplate;
	
	@Autowired
	public RestaurantServiceImpl(MongoTemplate mongoTemplate,@Value("${app.service.vendorTTL}") String vendorTtl) {
		this.vendorTtl = vendorTtl;
		this.mongoTemplate = mongoTemplate;
	}
	
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
    
//	@Autowired
//	public RestaurantServiceImpl(@Value("${app.service.vendorTTL}") String vendorTtl) {
//		this.vendorTtl = vendorTtl;
//	}

	@Override
	public void addRestaurant(RestaurantDto merchant) {
		boolean repeated = checkName(merchant.getDescriptor().getName());
		if (repeated==false) {
			Time time= new Time();
			time.setLabel("enable");
			String timestampString = Instant.now().toString();
			time.setTimestamp(timestampString);
			
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
		String timestampString = Instant.now().toString();
		time.setTimestamp(timestampString);
		time.setDays(location.getTime().getDays());
		time.setSchedule(location.getTime().getSchedule());
		time.setRange(location.getTime().getRange());
		
		
		location.setTime(time);
		String locationId="L"+sequenceGeneratorService.getSequenceNumber(restLocation_sequence);
		location.setId(locationId);
		
		List<Item> locationAttributes = location.getTags().stream()
                .flatMap(tag -> tag.getList().stream())
                .filter(attribute -> "location".equals(attribute.getCode()))
                .collect(Collectors.toList());

        locationAttributes.forEach(attribute -> attribute.setValue(locationId));
		
		
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
		logger.info("Adding product by post service");
		ProductTime time= new ProductTime();
		time.setLabel("enable");
		String timestampString = Instant.now().toString();
		time.setTimestamp(timestampString);
		product.setTime(time);
		product.setId("I"+sequenceGeneratorService.getSequenceNumber(restProduct_sequence));
		restaurantProductRepo.save(product);
		
	}
	
	@Override
	public void addRestaurantCustomGroup(RestaurantCustomGroupDto restaurantCustomGroupDto) {
		
		restaurantCustomGroupDto.setId("CG" + sequenceGeneratorService.getSequenceNumber(restCG_sequence));
		restaurantCustomGroupRepo.save(restaurantCustomGroupDto);
		
	}
	@Override
	public void addRestaurantItem(RestaurantItemDto item) {
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
	            .addToSet("fulfillmentsId",fulfillment.getId());
	   UpdateResult(query, update, RestaurantDto.class);
	}

	@Override
	public List<Object> getProductsByVendorId(String vendorId) {
		// TODO Auto-generated method stub
//		logger.info("search product in service by vendorId:"+vendorId);
//		return restaurantProductRepo.findByVendorId(vendorId);
		//return after lookup
    	System.out.println("enter:"+Instant.now());
    	
    	    String query4 ="{$lookup: {from: 'bb_admin_panel_vendors_products',localField: 'id',foreignField: 'vendorId',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1,'price':1,'category_id':1,'category_ids':1,'dimension':1,'packagingPrice':1,'timing':1,'weight':1  } }], as: 'product'}},";
    	    String query5 ="{$lookup: {from: 'bb_admin_panel_vendors_custom_groups',localField: 'product.id',foreignField: 'parentProductId',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1} }], as: 'customGroups'}},";
    	    String query6 ="{$lookup: {from: 'bb_admin_panel_vendors_items',localField: 'product.id',foreignField: 'parentItemId',pipeline: [{$project: {'_id': 0,'id':1,'parentItemId':1, 'parentCategoryId':1, 'descriptor':1,'tags':1,'quantity': 1,'price':1,'catgory_id':1,'related':1 } }], as: 'items'}},";
    	    
    	    Aggregation aggregation = Aggregation.newAggregation(

    	    		new CustomProjectAggregationOperation(query4),
    	    		new CustomProjectAggregationOperation(query5),
    	    		new CustomProjectAggregationOperation(query6),
//    	            Aggregation.unwind("vendorTags"),
    	            Aggregation.match(Criteria.where("id").is(vendorId)),
    	            Aggregation.project()
	                .andExclude("_id")
	                .andInclude("product","customGroups","items")
    	    		);
    	    AggregationResults<Object> results = 
        	        mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors", Object.class);
        	    List<Object> resultDtoString=results.getMappedResults();
        	    System.out.println("exit:"+Instant.now());
        	    return resultDtoString;  
	}
	
	@Override
	public List<RestaurantCategoriesDto> getCategoriesByVendorId(String vendorId) {
		// TODO Auto-generated method stub
		logger.info("Get categories in service by vendorId:"+vendorId);
		return restaurantCatgoryRepo.findByParentCategoryId(vendorId);
	}
	
	@Override
	public List<RestaurantLocationDto> getLocationByVendorId(String vendorId) {
		// TODO Auto-generated method stub
		logger.info("Get Location in service by vendorId:"+vendorId);
		return restaurantLocationRepo.findByVendorId(vendorId);
	}
	
	@Override
	public RestaurantDto getVendorById(String vendorId) {
		// TODO Auto-generated method stub
		logger.info("Get Location in service by vendorId:"+vendorId);
//		return restaurantRepo.findById(vendorId);
        Query query = new Query();
		query.addCriteria(Criteria.where("id").is(vendorId));
        RestaurantDto vendor = mongoTemplate.findOne(query, RestaurantDto.class);
        return vendor;
	}
	
	 public RestaurantLocationDto updateMinOrder(String id, String minOrder) {
	        Query query = new Query(Criteria.where("id").is(id));
	        RestaurantLocationDto location = mongoTemplate.findOne(query, RestaurantLocationDto.class);
	        if (location != null) {
	            logger.info("Updating min order value from updateMinOrder service with id :" + location.getId());
	            List<Item> locationAttributes = location.getTags().stream()
	    				.flatMap(tag -> tag.getList().stream())
	    				.filter(attribute -> "min_value".equals(attribute.getCode()))
	    				.collect(Collectors.toList());

	    		locationAttributes.forEach(attribute -> attribute.setValue(minOrder));

	    		restaurantLocationRepo.save(location);
	        }
	        return null; // or throw an exception indicating item not found
	    }
	    
	    @Override
	    public RestaurantLocationDto updateRadius(String id, String radius) {
	        Query query = new Query(Criteria.where("id").is(id));
	        RestaurantLocationDto location = mongoTemplate.findOne(query, RestaurantLocationDto.class);
	        if (location != null) {
	            logger.info("Updating min order value from updateMinOrder service with id :" + location.getId());
	            List<Item> locationAttributes = location.getTags().stream()
	    				.flatMap(tag -> tag.getList().stream())
	    				.filter(attribute -> "val".equals(attribute.getCode()))
	    				.collect(Collectors.toList());

	    		locationAttributes.forEach(attribute -> attribute.setValue(radius));
	    		location.getCircle().getRadius().setValue(radius);

	    		restaurantLocationRepo.save(location);
	        }
	        return null; // or throw an exception indicating item not found
	    }

	
	
	 public RestaurantProductDto updateProduct(String id, RestaurantProductDto newProduct) {
	        Query query = new Query();
	        query.addCriteria(Criteria.where("id").is(id));
	        RestaurantProductDto product = mongoTemplate.findOne(query, RestaurantProductDto.class);
	        if (product!= null) {
	        	logger.info("Updating product from updateProduct service with id :"+product.getId());
	        	product.setDescriptor(newProduct.getDescriptor());
	        	product.setPrice(newProduct.getPrice());
	        	product.setCategory_id(newProduct.getCategory_id());
	        	product.setCategory_ids(newProduct.getCategory_ids());
	        	product.setRelated(newProduct.isRelated());
	        	product.setRecommended(newProduct.isRecommended());
	        	product.setWeight(newProduct.getWeight());
	        	product.setTiming(newProduct.getTiming());
	        	product.setPackagingPrice(newProduct.getPackagingPrice());
	        	product.setDimension(newProduct.getDimension());
	        	product.setOndc_org_returnable(newProduct.isOndc_org_returnable());
	        	product.setOndc_org_cancellable(newProduct.isOndc_org_cancellable());
	        	product.setOndc_org_seller_pickup_return(newProduct.isOndc_org_seller_pickup_return());
	        	product.setOndc_org_available_on_cod(newProduct.isOndc_org_available_on_cod());
	        	product.setTags(newProduct.getTags());

	            
	            return restaurantProductRepo.save(product);
	        }
	        return null; // or throw an exception indicating item not found

	        
	    }
	 public RestaurantCustomGroupDto updateCustomGroup(String id, RestaurantCustomGroupDto newCustomGroup) {
		 	Query query = new Query();
	        query.addCriteria(Criteria.where("id").is(id));
	        RestaurantCustomGroupDto customGroup = mongoTemplate.findOne(query, RestaurantCustomGroupDto.class);
	        if (customGroup!=null) {
	        	customGroup.setDescriptor(newCustomGroup.getDescriptor());
	        	customGroup.setTags(newCustomGroup.getTags());
	            
	            return restaurantCustomGroupRepo.save(customGroup);
	        }
	        return null; // or throw an exception indicating item not found
	    }
	 public RestaurantItemDto updateItem(String id, RestaurantItemDto newItem) {
		 	Query query = new Query();
	        query.addCriteria(Criteria.where("id").is(id));
	        RestaurantItemDto item = mongoTemplate.findOne(query, RestaurantItemDto.class);
	        if (item!=null) {
	        	if (newItem.getPrice() != null) {
	        		item.setParentCategoryId(newItem.getParentCategoryId());
	        		item.setDescriptor(newItem.getDescriptor());
	        		item.setQuantity(newItem.getQuantity());
	        		item.setPrice(newItem.getPrice());
	        		item.setCategoryId(newItem.getCategoryId());
	        		item.setRelated(newItem.isRelated());
	        		item.setTags(newItem.getTags());
	        		return restaurantItemRepo.save(item);
	            }
	           
	            
	        }
	        return null; // or throw an exception indicating item not found
	    }

	    public void deleteProduct(String id) {
	    	//delete from product
	    	Query query = new Query();
	    	query.addCriteria(Criteria.where("id").is(id));
	    	mongoTemplate.remove(query, "bb_admin_panel_vendors_products");
	    	
	    	//delete from customGroup
	    	Query query2 = new Query();
	    	query2.addCriteria(Criteria.where("parentProductId").is(id));
	    	mongoTemplate.remove(query2, "bb_admin_panel_vendors_custom_groups");
	    	
	    	//delete from item
	    	Query query3 = new Query();
	        query3.addCriteria(Criteria.where("parentItemId").is(id));
	        mongoTemplate.remove(query3, "bb_admin_panel_vendors_items");
	    }
	    public void deleteCustomGroup(String id) {
	    	Query query = new Query();
	        query.addCriteria(Criteria.where("id").is(id));
	        mongoTemplate.remove(query, "bb_admin_panel_vendors_custom_groups");
	    }
	    public void deleteItem(String id) {
	    	Query query = new Query();
	        query.addCriteria(Criteria.where("id").is(id));
	        mongoTemplate.remove(query, "bb_admin_panel_vendors_items");
	    }
	    
	    
	    
	    
	    



 
	




}
