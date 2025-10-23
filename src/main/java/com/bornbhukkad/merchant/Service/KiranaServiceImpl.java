package com.bornbhukkad.merchant.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bornbhukkad.merchant.Repository.IKiranaCategoriesRepository;
import com.bornbhukkad.merchant.Repository.IKiranaCustomGroupRepository;
import com.bornbhukkad.merchant.Repository.IKiranaDefaultCategoriesRepository;
import com.bornbhukkad.merchant.Repository.IKiranaFulfillmentRepository;
import com.bornbhukkad.merchant.Repository.IKiranaItemRepository;
import com.bornbhukkad.merchant.Repository.IKiranaLocationRepository;
import com.bornbhukkad.merchant.Repository.IKiranaOfferRepository;
import com.bornbhukkad.merchant.Repository.IKiranaProductRepository;
import com.bornbhukkad.merchant.Repository.IKiranaRepository;
import com.bornbhukkad.merchant.Repository.IUserRepository;
import com.bornbhukkad.merchant.Repository.IkiranaCredsRepository;
import com.bornbhukkad.merchant.dto.KiranaCategoriesDto;
import com.bornbhukkad.merchant.dto.KiranaCredDto;
import com.bornbhukkad.merchant.dto.KiranaCustomGroupDto;
import com.bornbhukkad.merchant.dto.KiranaDefaultCategoriesDto;
import com.bornbhukkad.merchant.dto.KiranaDto;
import com.bornbhukkad.merchant.dto.KiranaLocationDto;
import com.bornbhukkad.merchant.dto.KiranaLocationDto.Item;
import com.bornbhukkad.merchant.dto.KiranaLocationDto.LocationTime;
import com.bornbhukkad.merchant.dto.KiranaOfferDto;
import com.bornbhukkad.merchant.dto.KiranaProductDto;
import com.bornbhukkad.merchant.dto.KiranaProductDto.ProductTime;
import com.bornbhukkad.merchant.dto.KiranaUser;
import com.mongodb.client.result.UpdateResult;
import com.bornbhukkad.merchant.dto.KiranaDto.Time;
import com.bornbhukkad.merchant.dto.KiranaFulfillmentDto;
import com.bornbhukkad.merchant.dto.KiranaItemDto;

import static com.bornbhukkad.merchant.dto.KiranaDto.kirana_sequence;
import static com.bornbhukkad.merchant.dto.KiranaLocationDto.kiranaLocation_sequence;
import static com.bornbhukkad.merchant.dto.KiranaProductDto.kiranaProduct_sequence;
import static com.bornbhukkad.merchant.dto.KiranaCustomGroupDto.kiranaCG_sequence;
import static com.bornbhukkad.merchant.dto.KiranaItemDto.kiranaItem_sequence;
import static com.bornbhukkad.merchant.dto.KiranaCategoriesDto.kiranaCategories_sequence;
import static com.bornbhukkad.merchant.dto.KiranaFulfillmentDto.fulfillment_sequence;
import static com.bornbhukkad.merchant.dto.KiranaOfferDto.kiranaOffer_sequence;
import static com.bornbhukkad.merchant.dto.KiranaCredDto.kiranaCred_sequence;

@Service
public class KiranaServiceImpl implements KiranaService {

	@Autowired
	IKiranaRepository kiranaRepo;
	@Autowired
	IUserRepository kiranaUserRepo;
	@Autowired
	IKiranaLocationRepository kiranaLocationRepo;
	@Autowired
	IKiranaCategoriesRepository kiranaCategoryRepo;
	@Autowired
	IKiranaProductRepository kiranaProductRepo;
	@Autowired
	IKiranaCustomGroupRepository kiranaCustomGroupRepo;
	@Autowired
	IKiranaItemRepository kiranaItemRepo;
	@Autowired
	IKiranaDefaultCategoriesRepository kiranaDefaultCategoriesRepo;
	@Autowired
	IKiranaFulfillmentRepository kiranaFulfillmentRepo;
	@Autowired
	IKiranaOfferRepository kiranaOfferRepo;
	@Autowired
	IkiranaCredsRepository kiranaCredsRepo;
//	@Autowired
//	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	private static MongoTemplate mongoTemplate;

	private final String vendorTtl;

	private static final Logger logger = LoggerFactory.getLogger(KiranaServiceImpl.class);

	@Autowired
	public KiranaServiceImpl(MongoTemplate mongoTemplate, @Value("${app.service.vendorTTL}") String vendorTtl) {
		this.vendorTtl = vendorTtl;
//		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void addKirana(KiranaDto merchant) {
		boolean repeated = checkName(merchant.getDescriptor().getName());
		if (!repeated) {
			Time time = new Time();
			time.setLabel("enable");
			String timestampString = Instant.now().toString();
			time.setTimestamp(timestampString);

			merchant.setTime(time);
			merchant.setTtl(vendorTtl);
			merchant.setId("P" + sequenceGeneratorService.getSequenceNumber(kirana_sequence));
			kiranaRepo.save(merchant);
			Query query = new Query(Criteria.where("email").is(merchant.getUserEmail()));
			Update update = new Update().set("merchantId", merchant.getId());
			UpdateResult(query, update, KiranaUser.class);
		}
	}

	private UpdateResult UpdateResult(Query query, Update update, Class<?> class1) {
		return mongoTemplate.updateFirst(query, update, class1);
	}

	@Override
	public void addKiranaLocation(KiranaLocationDto location) {
		LocationTime time = new LocationTime();
		time.setLabel("enable");
		String timestampString = Instant.now().toString();
		time.setTimestamp(timestampString);
		time.setDays(location.getTime().getDays());
		time.setSchedule(location.getTime().getSchedule());
		time.setRange(location.getTime().getRange());

		location.setTime(time);
		location.setId("L" + sequenceGeneratorService.getSequenceNumber(kiranaLocation_sequence));

		List<Item> locationAttributes = location.getTags().stream().flatMap(tag -> tag.getList().stream())
				.filter(attribute -> "location".equals(attribute.getCode())).collect(Collectors.toList());

		locationAttributes.forEach(attribute -> attribute.setValue(location.getId()));

		kiranaLocationRepo.save(location);
		Query query = new Query(Criteria.where("id").is(location.getKiranaId()));
		Update update = new Update().set("locationsId", location.getId());
		UpdateResult(query, update, KiranaDto.class);
	}

	@Override
	public void addKiranaCategories(KiranaCategoriesDto categories) {
		categories.setId("V" + sequenceGeneratorService.getSequenceNumber(kiranaCategories_sequence));
		kiranaCategoryRepo.save(categories);
	}

	@Override
	public void addKiranaProduct(KiranaProductDto product) {
		ProductTime time = new ProductTime();
		time.setLabel("enable");
		String timestampString = Instant.now().toString();
		time.setTimestamp(timestampString);
		product.setTime(time);
		product.setId("I" + sequenceGeneratorService.getSequenceNumber(kiranaProduct_sequence));
		kiranaProductRepo.save(product);
	}
	@Override
	public void addKiranaCred(KiranaCredDto cred) {
		String id = "ESG-" + sequenceGeneratorService.getSequenceNumber(kiranaCred_sequence);
		cred.getDescriptor().setCode(id);
		cred.setId(id);
		kiranaCredsRepo.save(cred);
	}

	@Override
	public void addKiranaCustomGroup(KiranaCustomGroupDto kiranaCustomGroupDto) {
		kiranaCustomGroupDto.setId("CG" + sequenceGeneratorService.getSequenceNumber(kiranaCG_sequence));
		kiranaCustomGroupRepo.save(kiranaCustomGroupDto);
	}

	@Override
	public void addKiranaItem(KiranaItemDto item) {
		item.setId("C" + sequenceGeneratorService.getSequenceNumber(kiranaItem_sequence));
		kiranaItemRepo.save(item);
	}

	@Override
	public void addKiranaFulfillment(KiranaFulfillmentDto fulfillment) {
		fulfillment.setId("F" + sequenceGeneratorService.getSequenceNumber(fulfillment_sequence));
		kiranaFulfillmentRepo.save(fulfillment);
		Query query = new Query(Criteria.where("id").is(fulfillment.getKiranaId()));
		Update update = new Update().set("fulfillmentsId", fulfillment.getId());
		UpdateResult(query, update, KiranaDto.class);
	}

	@Override
	public List<KiranaDto> getAll() {
		List<KiranaDto> merchants = new ArrayList<>();
		kiranaRepo.findAll().forEach(merchant -> merchants.add(merchant));
		return merchants;
	}

	@Override
	public List<KiranaDefaultCategoriesDto> getKiranaDefaultCategories() {
		List<KiranaDefaultCategoriesDto> categories = new ArrayList<>();
		kiranaDefaultCategoriesRepo.findAll().forEach(category -> categories.add(category));
		return categories;
	}

	@Override
	public List<KiranaFulfillmentDto> getFulfillmentByVendorId(String id) {
		// TODO Auto-generated method stub
		logger.info("Get Fulfillment service by vendorId:" + id);
		return kiranaFulfillmentRepo.findByKiranaId(id);
	}

	@Override
	public boolean checkName(String name) {
		List<KiranaDto> merchants = getAll();
		return merchants.stream().anyMatch(merchant -> merchant.getDescriptor().getName().equalsIgnoreCase(name)
				&& !merchant.getDescriptor().getName().equalsIgnoreCase("kirana"));
	}

	@Override
	public List<KiranaCategoriesDto> getCategoriesByKiranaId(String kiranaId) {
		logger.info("Get categories in service by kiranaId:" + kiranaId);
		return kiranaCategoryRepo.findByParentCategoryId(kiranaId);
	}

	@Override
	public List<KiranaLocationDto> getLocationByKiranaId(String kiranaId) {
		logger.info("Get Location in service by kiranaId:" + kiranaId);
		return kiranaLocationRepo.findByKiranaId(kiranaId);
	}

	@Override
	public KiranaDto getKiranaById(String kiranaId) {
		logger.info("Get Location in service by kiranaId:" + kiranaId);
		Query query = new Query(Criteria.where("id").is(kiranaId));
		return mongoTemplate.findOne(query, KiranaDto.class);
	}

	@Override
	public List<Object> getProductsByKiranaId(String kiranaId) {
		logger.info("Search product in service by kiranaId:" + kiranaId);
		System.out.println("enter:" + Instant.now());

		String query4 = "{$lookup: {from: 'bb_admin_panel_kirana_products', localField: 'id', foreignField: 'kiranaId', pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1,'price':1,'category_id':1,'category_ids':1,'dimension':1,'packagingPrice':1,'timing':1,'weight':1}}], as: 'product'}},";
		String query5 = "{$lookup: {from: 'bb_admin_panel_kirana_custom_groups', localField: 'product.id', foreignField: 'parentProductId', pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1}}], as: 'customGroups'}},";
		String query6 = "{$lookup: {from: 'bb_admin_panel_kirana_items', localField: 'product.id', foreignField: 'parentItemId', pipeline: [{$project: {'_id': 0,'id':1,'parentItemId':1,'parentCategoryId':1,'descriptor':1,'tags':1,'quantity':1,'price':1,'category_id':1,'related':1}}], as: 'items'}},";

		Aggregation aggregation = Aggregation.newAggregation(new CustomProjectAggregationOperation(query4),
				new CustomProjectAggregationOperation(query5), new CustomProjectAggregationOperation(query6),
				Aggregation.match(Criteria.where("id").is(kiranaId)),
				Aggregation.project().andExclude("_id").andInclude("product", "customGroups", "items"));

		AggregationResults<Object> results = mongoTemplate.aggregate(aggregation, "bb_admin_panel_kirana",
				Object.class);
		List<Object> resultDtoString = results.getMappedResults();
		System.out.println("exit:" + Instant.now());
		return resultDtoString;
	}

	@Override
	public KiranaProductDto updateProduct(String id, KiranaProductDto newProduct) {
		Query query = new Query(Criteria.where("id").is(id));
		KiranaProductDto product = mongoTemplate.findOne(query, KiranaProductDto.class);
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

			return kiranaProductRepo.save(product);
		}
		return null; // or throw an exception indicating item not found
	}

	@Override
	public KiranaLocationDto updateMinOrder(String id, String minOrder) {
		Query query = new Query(Criteria.where("id").is(id));
		KiranaLocationDto location = mongoTemplate.findOne(query, KiranaLocationDto.class);
		if (location != null) {
			logger.info("Updating min order value from updateMinOrder service with id :" + location.getId());
			List<Item> locationAttributes = location.getTags().stream().flatMap(tag -> tag.getList().stream())
					.filter(attribute -> "min_value".equals(attribute.getCode())).collect(Collectors.toList());

			locationAttributes.forEach(attribute -> attribute.setValue(minOrder));

			kiranaLocationRepo.save(location);
		}
		return null; // or throw an exception indicating item not found
	}

	@Override
	public KiranaLocationDto updateRadius(String id, String radius) {
		Query query = new Query(Criteria.where("id").is(id));
		KiranaLocationDto location = mongoTemplate.findOne(query, KiranaLocationDto.class);
		if (location != null) {
			logger.info("Updating min order value from updateMinOrder service with id :" + location.getId());
			List<Item> locationAttributes = location.getTags().stream().flatMap(tag -> tag.getList().stream())
					.filter(attribute -> "val".equals(attribute.getCode())).collect(Collectors.toList());

			locationAttributes.forEach(attribute -> attribute.setValue(radius));
			location.getCircle().getRadius().setValue(radius);

			kiranaLocationRepo.save(location);
		}
		return null; // or throw an exception indicating item not found
	}

	@Override
	public KiranaCustomGroupDto updateCustomGroup(String id, KiranaCustomGroupDto newCustomGroup) {
		Query query = new Query(Criteria.where("id").is(id));
		KiranaCustomGroupDto customGroup = mongoTemplate.findOne(query, KiranaCustomGroupDto.class);
		if (customGroup != null) {
			customGroup.setDescriptor(newCustomGroup.getDescriptor());
			customGroup.setTags(newCustomGroup.getTags());

			return kiranaCustomGroupRepo.save(customGroup);
		}
		return null; // or throw an exception indicating item not found
	}

	@Override
	public KiranaItemDto updateItem(String id, KiranaItemDto newItem) {
		Query query = new Query(Criteria.where("id").is(id));
		KiranaItemDto item = mongoTemplate.findOne(query, KiranaItemDto.class);
		if (item != null) {
			item.setParentCategoryId(newItem.getParentCategoryId());
			item.setDescriptor(newItem.getDescriptor());
			item.setQuantity(newItem.getQuantity());
			item.setPrice(newItem.getPrice());
			item.setCategoryId(newItem.getCategoryId());
			item.setRelated(newItem.isRelated());
			item.setTags(newItem.getTags());
			return kiranaItemRepo.save(item);
		}
		return null; // or throw an exception indicating item not found
	}

	@Override
	public void deleteProduct(String id) {
		// Delete from product
		Query query = new Query(Criteria.where("id").is(id));
		mongoTemplate.remove(query, "bb_admin_panel_kirana_products");

		// Delete from customGroup
		Query query2 = new Query(Criteria.where("parentProductId").is(id));
		mongoTemplate.remove(query2, "bb_admin_panel_kirana_custom_groups");

		// Delete from item
		Query query3 = new Query(Criteria.where("parentItemId").is(id));
		mongoTemplate.remove(query3, "bb_admin_panel_kirana_items");
	}

	@Override
	public void deleteCustomGroup(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		mongoTemplate.remove(query, "bb_admin_panel_kirana_custom_groups");
	}

	@Override
	public void deleteItem(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		mongoTemplate.remove(query, "bb_admin_panel_kirana_items");
	}
	
	@Override
	public List<KiranaOfferDto> addKiranaOffers(List<KiranaOfferDto> offers) {
		for (KiranaOfferDto offer : offers) {
			final String kiranaId = offer.getKiranaId();

			offer.setId("O" + sequenceGeneratorService.getSequenceNumber(kiranaOffer_sequence));
			String offerNameUpper = offer.getName().toUpperCase();

			boolean exists = kiranaOfferRepo.existsByName(offerNameUpper);
			if (exists) {
				throw new ResponseStatusException(HttpStatus.CONFLICT,
						String.format("Offer '%s' already exists for merchant '%s'", offerNameUpper, kiranaId));
			}

			// Set name to uppercase before mapping/saving
			offer.setName(offerNameUpper);
			offer.setActive(true);
		}

		kiranaOfferRepo.saveAll(offers);

		return offers;
	}

	@Override
	public List<KiranaOfferDto> getOffersByKiranaId(String kiranaId) {
		logger.info("Get Location in service by vendorId:" + kiranaId);
		return kiranaOfferRepo.findByKiranaId(kiranaId);
	}

	@Override
	public Optional<KiranaOfferDto> getOfferById(String id) {
		logger.info("Get Offers in service by vendorId:" + id);

		Query query = new Query(Criteria.where("id").is(id));
		KiranaOfferDto offer = mongoTemplate.findOne(query, KiranaOfferDto.class);
		return Optional.of(offer);
	}
	
	public KiranaOfferDto updateOffer(String id, KiranaOfferDto data) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		KiranaOfferDto offer = mongoTemplate.findOne(query, KiranaOfferDto.class);
		if (offer != null) {
			offer.setDescriptor(data.getDescriptor());
			offer.setTags(data.getTags());
			offer.setName(data.getName());
			offer.setItem_ids(data.getItem_ids());
			offer.setLocation_ids(data.getLocation_ids());
			offer.setActive(data.getActive());
			
			return kiranaOfferRepo.save(offer);
		}
		return null; // or throw an exception indicating item not found
	}
	
	public void deleteOffer(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		mongoTemplate.remove(query, "bb_admin_panel_kirana_offers");
	}

	// Send a WebSocket notification to a specific Kirana merchant
	public void sendKiranaNotification(String orderId, String merchantId, String orderDetails) {
//		String notificationMessage = "New Kirana order received! Order ID: " + orderId + " | Details: " + orderDetails;
//		messagingTemplate.convertAndSend("/topic/kirana/" + merchantId, notificationMessage);
	}

}