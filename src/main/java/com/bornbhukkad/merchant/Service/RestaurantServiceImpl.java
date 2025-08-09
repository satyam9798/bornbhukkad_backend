package com.bornbhukkad.merchant.Service;

//import java.security.KeyStore.Entry.Attribute;
import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
//import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.ArrayList;
//import java.util.Collection;
import java.util.List;
import java.util.Optional;
//import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bornbhukkad.merchant.Repository.IRestaurantAudienceSegmentRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantCategoriesRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantCustomGroupRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantDefaultCategoriesRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantFulfillmentRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantItemRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantLocationRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantOfferRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantOrderRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantProductRepository;
import com.bornbhukkad.merchant.Repository.IRestaurantRepository;
import com.bornbhukkad.merchant.Repository.IUserRepository;
import com.bornbhukkad.merchant.dto.RestaurantAudienceDto;
import com.bornbhukkad.merchant.dto.RestaurantCategoriesDto;
import com.bornbhukkad.merchant.dto.RestaurantCustomGroupDto;
import com.bornbhukkad.merchant.dto.RestaurantDefaultCategoriesDto;
import com.bornbhukkad.merchant.dto.RestaurantDto;
import com.bornbhukkad.merchant.dto.RestaurantDto.Time;
import com.bornbhukkad.merchant.dto.RestaurantLocationDto;
import com.bornbhukkad.merchant.dto.RestaurantLocationDto.Item;
import com.bornbhukkad.merchant.dto.RestaurantLocationDto.LocationTime;
import com.bornbhukkad.merchant.dto.RestaurantOfferDto;
import com.bornbhukkad.merchant.dto.RestaurantOrderDto;
import com.bornbhukkad.merchant.dto.RestaurantOrderStatus;
import com.bornbhukkad.merchant.dto.RestaurantProductDto;
import com.bornbhukkad.merchant.dto.RestaurantProductDto.ProductTime;
import com.bornbhukkad.merchant.dto.RestaurantProductDto.Tag;
import com.bornbhukkad.merchant.dto.RestaurantUser;
import com.mongodb.client.result.UpdateResult;
import com.bornbhukkad.merchant.dto.RestaurantFulfillmentDto;
import com.bornbhukkad.merchant.dto.RestaurantItemDto;

import static com.bornbhukkad.merchant.dto.RestaurantDto.restaurant_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantLocationDto.restLocation_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantProductDto.restProduct_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantCustomGroupDto.restCG_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantItemDto.restItem_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantCategoriesDto.restCategories_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantFulfillmentDto.fulfillment_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantOrderDto.restOrder_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantOfferDto.restOffer_sequence;
import static com.bornbhukkad.merchant.dto.RestaurantAudienceDto.restaurant_audience_segments_sequence;

@Service
public class RestaurantServiceImpl implements RestaurantService {
	private static MongoTemplate mongoTemplate;

	@Autowired
	public RestaurantServiceImpl(MongoTemplate mongoTemplate, @Value("${app.service.vendorTTL}") String vendorTtl) {
		this.vendorTtl = vendorTtl;
		this.mongoTemplate = mongoTemplate;
	}

	enum OrderStatus {
		NEW, CONFIRMED, COOKING, DELIVERY_PARTNER_ASSIGNED, PICKED_UP, DELIVERED, REJECTED
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
	IRestaurantOrderRepository restaurantOrderRepository;
	@Autowired
	IRestaurantOfferRepository restaurantOfferRepository;
	@Autowired
	IRestaurantAudienceSegmentRepository restaurantAudienceSegmentRepo;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	private final String vendorTtl;

	@Override
	public void addRestaurant(RestaurantDto merchant) {
		boolean repeated = checkName(merchant.getDescriptor().getName());
		if (repeated == false) {
			Time time = new Time();
			time.setLabel("enable");
			String timestampString = Instant.now().toString();
			time.setTimestamp(timestampString);

			merchant.setTime(time);
			merchant.setTtl(vendorTtl);

			merchant.setId("P" + sequenceGeneratorService.getSequenceNumber(restaurant_sequence));
			restaurantRepo.save(merchant);
			Query query = new Query(Criteria.where("email").is(merchant.getUserEmail()));
			Update update = new Update().set("merchantId", merchant.getId());
			UpdateResult(query, update, RestaurantUser.class);
		}

	}

	private UpdateResult UpdateResult(Query query, Update update, Class<?> class1) {
		return mongoTemplate.updateFirst(query, update, class1);
	}

	@Override
	public void addRestaurantLocation(RestaurantLocationDto location) {
		LocationTime time = new LocationTime();
		time.setLabel("enable");
		String timestampString = Instant.now().toString();
		time.setTimestamp(timestampString);
		time.setDays(location.getTime().getDays());
		time.setSchedule(location.getTime().getSchedule());
		time.setRange(location.getTime().getRange());

		location.setTime(time);
		String locationId = "L" + sequenceGeneratorService.getSequenceNumber(restLocation_sequence);
		location.setId(locationId);

		List<Item> locationAttributes = location.getTags().stream().flatMap(tag -> tag.getList().stream())
				.filter(attribute -> "location".equals(attribute.getCode())).collect(Collectors.toList());

		locationAttributes.forEach(attribute -> attribute.setValue(locationId));

		restaurantLocationRepo.save(location);
		Query query = new Query(Criteria.where("id").is(location.getVendorId()));
		Update update = new Update().set("locationsId", location.getId());

		UpdateResult(query, update, RestaurantDto.class);

	}

	@Override
	public void addRestaurantCategories(RestaurantCategoriesDto categories) {
		categories.setId("" + sequenceGeneratorService.getSequenceNumber(restCategories_sequence));
		restaurantCatgoryRepo.save(categories);

	}

	@Override
	public RestaurantProductDto addRestaurantProduct(RestaurantProductDto product) {
		logger.info("Adding product by post service");
		ProductTime time = new ProductTime();
		time.setLabel("enable");
		String timestampString = Instant.now().toString();
		time.setTimestamp(timestampString);
		product.setTime(time);
		product.setId("I" + sequenceGeneratorService.getSequenceNumber(restProduct_sequence));
		return restaurantProductRepo.save(product);
	}

	@Override
	public RestaurantCustomGroupDto addRestaurantCustomGroup(RestaurantCustomGroupDto restaurantCustomGroupDto) {
		restaurantCustomGroupDto.setId("CG" + sequenceGeneratorService.getSequenceNumber(restCG_sequence));
		return restaurantCustomGroupRepo.save(restaurantCustomGroupDto);
	}

	@Override
	public RestaurantItemDto addRestaurantItem(RestaurantItemDto item) {
		item.setId("C" + sequenceGeneratorService.getSequenceNumber(restItem_sequence));

		// Update the parent tag with the custom group ID
		updateItemParentTag(item);

		return restaurantItemRepo.save(item);
	}

	@Override
	public List<RestaurantOfferDto> addRestaurantOffers(List<RestaurantOfferDto> offers) {
		for (RestaurantOfferDto offer : offers) {
			final String vendorId = offer.getVendorId();

			offer.setId("O" + sequenceGeneratorService.getSequenceNumber(restOffer_sequence));
			String offerNameUpper = offer.getName().toUpperCase();

			boolean exists = restaurantOfferRepository.existsByName(offerNameUpper);
			if (exists) {
				throw new ResponseStatusException(HttpStatus.CONFLICT,
						String.format("Offer '%s' already exists for merchant '%s'", offerNameUpper, vendorId));
			}

			// Set name to uppercase before mapping/saving
			offer.setName(offerNameUpper);
			offer.setActive(true);
		}

		restaurantOfferRepository.saveAll(offers);

		return offers;
	}

	@Override
	public List<RestaurantOfferDto> getOffersByVendorId(String vendorId) {
		logger.info("Get Location in service by vendorId:" + vendorId);
		return restaurantOfferRepository.findByVendorId(vendorId);
	}

	@Override
	public Optional<RestaurantOfferDto> getOfferById(String id) {
		logger.info("Get Location in service by vendorId:" + id);

		return restaurantOfferRepository.findById(id);
	}
	
	public RestaurantOfferDto updateOffer(String id, RestaurantOfferDto data) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		RestaurantOfferDto offer = mongoTemplate.findOne(query, RestaurantOfferDto.class);
		if (offer != null) {
			offer.setDescriptor(data.getDescriptor());
			offer.setTags(data.getTags());
			offer.setName(data.getName());
			offer.setItem_ids(data.getItem_ids());
			offer.setLocation_ids(data.getLocation_ids());
			offer.setActive(data.getActive());
			
			return restaurantOfferRepository.save(offer);
		}
		return null; // or throw an exception indicating item not found
	}
	
	public void deleteOffer(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		mongoTemplate.remove(query, "bb_admin_panel_vendors_offers");
	}

	@Override
	public RestaurantAudienceDto addRestaurantAudienceSegment(RestaurantAudienceDto audience) {

		audience.setId("A" + sequenceGeneratorService.getSequenceNumber(restaurant_audience_segments_sequence));
		 restaurantAudienceSegmentRepo.save(audience);
		 return audience;

	}
	
	@Override
	public List<RestaurantAudienceDto> getAudienceSegmentByVendorId(String vendorId) {
		logger.info("getAudienceSegmentByVendorId in service by vendorId:" + vendorId);
		return restaurantAudienceSegmentRepo.findByVendorId(vendorId);
	}

	@Override
	public Optional<RestaurantAudienceDto> getAudienceSegmentById(String id) {
		logger.info("getAudienceSegmentById in service by vendorId:" + id);

		return restaurantAudienceSegmentRepo.findById(id);
	}

	@Override
	public void updateProductCustomGroupTags(String productId, List<String> customGroupIds) {
		Query query = new Query(Criteria.where("id").is(productId));
		RestaurantProductDto product = mongoTemplate.findOne(query, RestaurantProductDto.class);

		List<Tag> tags = product.getTags();
		Tag customGroupTag = tags.stream().filter(tag -> "custom_group".equals(tag.getCode())).findFirst()
				.orElseGet(() -> {
					Tag newTag = new Tag();
					newTag.setCode("custom_group");
					newTag.setList(new ArrayList<>());
					tags.add(newTag);
					return newTag;
				});

		List<RestaurantProductDto.TagValue> customGroupTagValues = new ArrayList<>();
		for (String cgId : customGroupIds) {
			RestaurantProductDto.TagValue tagValue = new RestaurantProductDto.TagValue();
			tagValue.setCode("id");
			tagValue.setValue(cgId);
			customGroupTagValues.add(tagValue);
		}
		customGroupTag.setList(customGroupTagValues);

		restaurantProductRepo.save(product);
	}

	private void updateItemParentTag(RestaurantItemDto item) {
		List<RestaurantItemDto.Tag> tags = item.getTags();

		// Find parent tag
		RestaurantItemDto.Tag parentTag = tags.stream().filter(tag -> "parent".equals(tag.getCode())).findFirst()
				.orElse(null);

		// Find child tag
		RestaurantItemDto.Tag childTag = tags.stream().filter(tag -> "child".equals(tag.getCode())).findFirst()
				.orElse(null);

		// Update parent tag if parentIdValue and parentCustomGroup exist
		if (parentTag != null) {
			String parentIdValue = parentTag.getList().stream().filter(listItem -> "id".equals(listItem.getCode()))
					.map(tagItem -> tagItem.getValue()).findFirst().orElse(null);

			if (parentIdValue != null) {
				Query parentCustomGroupQuery = new Query(Criteria.where("defaultId").is(parentIdValue));
				RestaurantCustomGroupDto parentCustomGroup = mongoTemplate.findOne(parentCustomGroupQuery,
						RestaurantCustomGroupDto.class);

				if (parentCustomGroup != null) {
					parentTag.getList().stream().filter(tagValue -> "id".equals(tagValue.getCode())).findFirst()
							.ifPresent(tagValue -> tagValue.setValue(parentCustomGroup.getId()));
				}
			}
		}

		// Update child tag if childIdValue and childCustomGroup exist
		if (childTag != null) {
			String childIdValue = childTag.getList().stream().filter(listItem -> "id".equals(listItem.getCode()))
					.map(tagItem -> tagItem.getValue()).findFirst().orElse(null);

			if (childIdValue != null) {
				Query childCustomGroupQuery = new Query(Criteria.where("defaultId").is(childIdValue));
				RestaurantCustomGroupDto childCustomGroup = mongoTemplate.findOne(childCustomGroupQuery,
						RestaurantCustomGroupDto.class);

				if (childCustomGroup != null) {
					childTag.getList().stream().filter(tagValue -> "id".equals(tagValue.getCode())).findFirst()
							.ifPresent(tagValue -> tagValue.setValue(childCustomGroup.getId()));
				}
			}
		}
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

	public boolean checkName(String name) {
		boolean repeated = false;
		List<RestaurantDto> merchants = new ArrayList<>();
		merchants = getAll();
		int count = 0;
		while (repeated == false && count < merchants.size()) {
			if (merchants.get(count).getDescriptor().getName().equalsIgnoreCase(name)) {
				if (merchants.get(count).getDescriptor().getName().equalsIgnoreCase("Vendor")) {
					repeated = false;
				} else {
					repeated = true;
				}
			}
			count++;
		}
		return repeated;
	}

	@Override
	public void addRestaurantFulfillment(RestaurantFulfillmentDto fulfillment) {
		fulfillment.setId("F" + sequenceGeneratorService.getSequenceNumber(fulfillment_sequence));
		restaurantFulfillmentRepo.save(fulfillment);
		Query query = new Query(Criteria.where("id").is(fulfillment.getVendorId()));
		Update update = new Update().addToSet("fulfillmentsId", fulfillment.getId());
		UpdateResult(query, update, RestaurantDto.class);
	}

	@Override
	public List<Object> getProductsByVendorId(String vendorId) {

		// return after lookup
		System.out.println("Search started for Product at :" + Instant.now());

		String query4 = "{$lookup: {from: 'bb_admin_panel_vendors_products',localField: 'id',foreignField: 'vendorId',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1,'price':1,'category_id':1,'category_ids':1, 'parent_category_id':1, 'dimension':1,'packagingPrice':1,'timing':1,'weight':1,'@ondc/org/returnable':1, '@ondc/org/cancellable':1,'@ondc/org/return_window':1,'@ondc/org/seller_pickup_return':1,'@ondc/org/time_to_ship':1,'@ondc/org/available_on_cod':1,'@ondc/org/contact_details_consumer_care':1  } }], as: 'product'}},";
		String query5 = "{$lookup: {from: 'bb_admin_panel_vendors_custom_groups',localField: 'product.id',foreignField: 'parentProductId',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1} }], as: 'customGroups'}},";
		String query6 = "{$lookup: {from: 'bb_admin_panel_vendors_items',localField: 'product.id',foreignField: 'parentItemId',pipeline: [{$project: {'_id': 0,'id':1,'parentItemId':1, 'parentCategoryId':1, 'descriptor':1,'tags':1,'quantity': 1,'price':1,'catgory_id':1,'related':1 } }], as: 'items'}},";

		Aggregation aggregation = Aggregation.newAggregation(

				new CustomProjectAggregationOperation(query4), new CustomProjectAggregationOperation(query5),
				new CustomProjectAggregationOperation(query6), Aggregation.match(Criteria.where("id").is(vendorId)),
				Aggregation.project().andExclude("_id").andInclude("product", "customGroups", "items"));
		AggregationResults<Object> results = mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors",
				Object.class);
		List<Object> resultDtoString = results.getMappedResults();
		System.out.println("search ended at :" + Instant.now());
		return resultDtoString;
	}
	
	@Override
	public List<RestaurantProductDto> getRawProductsByVendorId(String vendorId) {

		// return after lookup
		return restaurantProductRepo.findByVendorId(vendorId);

	}

	@Override
	public List<RestaurantCategoriesDto> getCategoriesByVendorId(String vendorId) {
		// TODO Auto-generated method stub
		logger.info("Get categories in service by vendorId:" + vendorId);
		return restaurantCatgoryRepo.findByVendorId(vendorId);
	}

	@Override
	public List<RestaurantLocationDto> getLocationByVendorId(String vendorId) {
		// TODO Auto-generated method stub
		logger.info("Get Location in service by vendorId:" + vendorId);
		return restaurantLocationRepo.findByVendorId(vendorId);
	}

	@Override
	public List<RestaurantFulfillmentDto> getFulfillmentByVendorId(String id) {
		// TODO Auto-generated method stub
		logger.info("Get Fulfillment service by vendorId:" + id);
		return restaurantFulfillmentRepo.findByVendorId(id);
	}

	@Override
	public RestaurantDto getVendorById(String vendorId) {
		logger.info("Get Location in service by vendorId:" + vendorId);
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
			List<Item> locationAttributes = location.getTags().stream().flatMap(tag -> tag.getList().stream())
					.filter(attribute -> "min_value".equals(attribute.getCode())).collect(Collectors.toList());

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
			List<Item> locationAttributes = location.getTags().stream().flatMap(tag -> tag.getList().stream())
					.filter(attribute -> "val".equals(attribute.getCode())).collect(Collectors.toList());

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
		if (product != null) {
			logger.info("Updating product from updateProduct service with id :" + product.getId());
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
		if (customGroup != null) {
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
		if (item != null) {
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
		// delete from product
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		mongoTemplate.remove(query, "bb_admin_panel_vendors_products");

		// delete from customGroup
		Query query2 = new Query();
		query2.addCriteria(Criteria.where("parentProductId").is(id));
		mongoTemplate.remove(query2, "bb_admin_panel_vendors_custom_groups");

		// delete from item
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

	public void sendRestaurantNotification(String orderId, String merchantId, String orderDetails) {
//		String notificationMessage = "New Restaurant order received! Order ID: " + orderId + " | Details: "
//				+ orderDetails;
//		messagingTemplate.convertAndSend("/topic/restaurant/" + merchantId, notificationMessage);
	}

	public RestaurantOrderDto createOrder(RestaurantOrderDto order) {
		order.setId("O" + sequenceGeneratorService.getSequenceNumber(restOrder_sequence));
		order.setStatus(RestaurantOrderStatus.NEW);
		order.setTimestamp(LocalDateTime.now());
		order.setAcceptanceDeadline(LocalDateTime.now().plusSeconds(30));
		return restaurantOrderRepository.save(order);
	}

	public Optional<RestaurantOrderDto> updateOrderStatus(String orderId, RestaurantOrderStatus status) {
		Query query = new Query(Criteria.where("id").is(orderId));
		RestaurantOrderDto order = mongoTemplate.findOne(query, RestaurantOrderDto.class);
		if (order != null) {

			order.setStatus(status);
			return Optional.of(restaurantOrderRepository.save(order));
		}
		return Optional.empty();
	}

	// Automatically reject orders not accepted within 30 seconds
	@Scheduled(fixedRate = 10000) // Runs every 10 seconds
	public void autoRejectOrders() {
		List<RestaurantOrderDto> newOrders = restaurantOrderRepository.findByStatus(RestaurantOrderStatus.NEW);
		newOrders.stream().filter(order -> LocalDateTime.now().isAfter(order.getAcceptanceDeadline()))
				.forEach(order -> {
					order.setStatus(RestaurantOrderStatus.REJECTED);
					restaurantOrderRepository.save(order);
				});
	}

}
