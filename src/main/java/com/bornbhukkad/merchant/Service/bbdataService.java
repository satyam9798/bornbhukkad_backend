package com.bornbhukkad.merchant.Service;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.aggregation.Aggregation;
//import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
//import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
//import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
//import org.springframework.data.mongodb.core.aggregation.Fields;
//import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
//import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class bbdataService {
    private static MongoTemplate mongoTemplate;

    @Autowired
    public bbdataService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
  

    
    public List<Object> getFulfillmentChannels(
    	    String item, 
    	    String city) {
    	System.out.println("enter:"+Instant.now());
    	String query1 ="{$lookup: {from: 'bb_admin_panel_vendors_locations',localField: 'locationsId',foreignField: 'id',pipeline: [{$project: {'_id':0,'id': 1, 'address': 1,'time':1,'gps':'$circle.gps','circle':1} }], as: 'locations'}},";
    	    String query2 ="{$lookup: {from: 'bb_admin_panel_vendors_locations',localField: 'locationsId',foreignField: 'id',pipeline: [{$project: {'_id':0,'tags': 1 } }], as: 'vendorTags'}},";
    	    String query4 = "{$lookup: {from: 'bb_admin_panel_vendors_products', localField: 'id', foreignField: 'vendorId', pipeline: [{$match: {'descriptor.name': {$regex: '.*"+item+".*', $options: 'i'}}}, {$project: {'_id': 0, 'id': 1, 'descriptor': 1, 'tags': 1, 'quantity': 1, 'price': 1, 'time': 1, 'category_id': 1, 'category_ids': 1, 'parent_category_id':1,'fulfillment_id': 1, 'location_id': 1, 'related': 1, 'recommended': 1, '@ondc/org/returnable': 1, '@ondc/org/cancellable': 1, '@ondc/org/return_window': 1, '@ondc/org/seller_pickup_return': 1, '@ondc/org/time_to_ship': 1, '@ondc/org/available_on_cod': 1, '@ondc/org/contact_details_consumer_care': 1}}], as: 'itemsMain'}}";
    	    String query3 ="{$lookup: {from: 'bb_admin_panel_vendors_fulfillments',localField: 'fulfillmentsId',foreignField: 'id',pipeline: [{$project: {'_id': 0,'id':'$defaultId','type':1,'contact':1} }], as: 'fulfillments'}},";
    	    String query8 ="{$lookup: {from: 'bb_admin_panel_vendors_categories',localField: 'itemsMain.parent_category_id',foreignField: 'id',pipeline: [{$project: {'_id': 0,'id':1,'parent_category_id':1,'descriptor':1,'tags':1} }],as: 'categoriesMain'}},";
    	    String query5 ="{$lookup: {from: 'bb_admin_panel_vendors_custom_groups',localField: 'itemsMain.id',foreignField: 'parentProductId',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1} }], as: 'customGroups'}},";
    	    String query6 ="{$lookup: {from: 'bb_admin_panel_vendors_items',localField: 'itemsMain.id',foreignField: 'parentItemId',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1,'quantity': 1,'price':1,'catgory_id':1,'related':1 } }], as: 'itemCollection'}},";
    	 
    	    Aggregation aggregation = Aggregation.newAggregation(
 	    		new CustomProjectAggregationOperation(query1),
 	    		new CustomProjectAggregationOperation(query2),
 	    		new CustomProjectAggregationOperation(query3),
 	    		new CustomProjectAggregationOperation(query4),
 	    		Aggregation.match(Criteria.where("itemsMain.descriptor.name").is(item)),
 	    		new CustomProjectAggregationOperation(query5),
 	    		new CustomProjectAggregationOperation(query6),
 	            new CustomProjectAggregationOperation(query8),
 	            Aggregation.unwind("vendorTags"),
 	            Aggregation.match(Criteria.where("locations.address.city").is(city)),
 	            Aggregation.project()
	                .andExclude("_id")
	                .andInclude("id","time", "fulfillments", "descriptor", "@ondc/org/fssai_license_no", "ttl", "offers", "locations","categories","items", "tags:$vendorTags.tags","fulfillments.id:$fulfillments.defaultId")
	                .and(ArrayOperators.ConcatArrays.arrayOf("categoriesMain").concat("customGroups")).as("categories")
	                .and(ArrayOperators.ConcatArrays.arrayOf("itemsMain").concat("itemCollection")).as("items")
 	    		);
    	
    	    AggregationResults<Object> results = 
        	        mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors", Object.class);
        	    List<Object> resultDtoString=results.getMappedResults();
        	    System.out.println("exit:"+Instant.now());
        	    return resultDtoString;            
    	}
    
    public List<Object> getByCity( 
    	    String city) {
    	System.out.println("enter:"+Instant.now());
    	String query1 ="{$lookup: {from: 'bb_admin_panel_vendors_locations',localField: 'locationsId',foreignField: 'id',pipeline: [{$project: {'_id':0,'id': 1, 'address': 1,'time':1,'gps':'$circle.gps','circle':1} }], as: 'locations'}},";
    	    String query2 ="{$lookup: {from: 'bb_admin_panel_vendors_locations',localField: 'locationsId',foreignField: 'id',pipeline: [{$project: {'_id':0,'tags': 1 } }], as: 'vendorTags'}},";
    	    String query4 ="{$lookup: {from: 'bb_admin_panel_vendors_products',localField: 'id',foreignField: 'vendorId',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1,'quantity': 1,'price':1,'time':1,'category_id':1,'category_ids':1,'fulfillment_id':1,'location_id':1,'related':1,'recommended':1,'@ondc/org/returnable':1,'@ondc/org/cancellable':1,'@ondc/org/return_window':1 ,'@ondc/org/seller_pickup_return':1,'@ondc/org/time_to_ship':1,'@ondc/org/available_on_cod':1,'@ondc/org/contact_details_consumer_care\':1  } }], as: 'itemsMain'}},";
    	    String query3 ="{$lookup: {from: 'bb_admin_panel_vendors_fulfillments',localField: 'fulfillmentsId',foreignField: 'id',pipeline: [{$project: {'_id': 0,'id':1,'type':1,'contact':1} }], as: 'fulfillments'}},";
    	    String query8 ="{$lookup: {from: 'bb_admin_panel_vendors_categories',localField: 'itemsMain.parent_category_id',foreignField: 'id',pipeline: [{$project: {'_id': 0,'id':1,'parent_category_id':1,'descriptor':1,'tags':1} }],as: 'categoriesMain'}},";
    	    String query5 ="{$lookup: {from: 'bb_admin_panel_vendors_custom_groups',localField: 'itemsMain.id',foreignField: 'parentProductId',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1} }], as: 'customGroups'}},";
    	    String query6 ="{$lookup: {from: 'bb_admin_panel_vendors_items',localField: 'itemsMain.id',foreignField: 'parentItemId',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1,'quantity': 1,'price':1,'catgory_id':1,'related':1 } }], as: 'itemCollection'}},";
    	 
    	    Aggregation aggregation = Aggregation.newAggregation(
 	    		new CustomProjectAggregationOperation(query1),
 	    		new CustomProjectAggregationOperation(query2),
 	    		new CustomProjectAggregationOperation(query3),
 	    		new CustomProjectAggregationOperation(query4),
 	    		new CustomProjectAggregationOperation(query5),
 	    		new CustomProjectAggregationOperation(query6),
 	            new CustomProjectAggregationOperation(query8),
 	            Aggregation.unwind("vendorTags"),
 	            Aggregation.match(Criteria.where("locations.address.city").is(city)),
 	            Aggregation.project()
	                .andExclude("_id")
	                .andInclude("id","time", "fulfillments", "descriptor", "@ondc/org/fssai_license_no", "ttl", "offers", "locations","categories","items", "tags:$vendorTags.tags")
	                .and(ArrayOperators.ConcatArrays.arrayOf("categoriesMain").concat("customGroups")).as("categories")
	                .and(ArrayOperators.ConcatArrays.arrayOf("itemsMain").concat("itemCollection")).as("items")
 	    		);
    	
    	    AggregationResults<Object> results = 
        	        mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors", Object.class);
        	    List<Object> resultDtoString=results.getMappedResults();
        	    System.out.println("exit:"+Instant.now());
        	    return resultDtoString;            
    	}
    
    public List<Object> getFulfillmentChannelsByGeoLocation(String Latitude, String Longitude, String maxDistance) {
    	String geoNearQuery = "{$geoNear: {near: { type: 'Point', coordinates: ["+Latitude+","+ Longitude+"] }, distanceField: 'dist.calculated', maxDistance:"+maxDistance+", spherical: true }}";
    	String query1 = "{$lookup: {from: 'bb_admin_panel_vendors_locations', let: { locationId: '$locationsId' }, pipeline: [" + geoNearQuery + ",{$match: {$expr: {$eq: ['$id', '$$locationId']}}},{$project: {'_id':0,'id': 1, 'address': 1,'time':1,'gps':'$circle.gps','circle':1} }], as: 'locations'}}";
    	String query2 = "{$lookup: {from: 'bb_admin_panel_vendors_locations',localField: 'locationsId',foreignField: 'id',pipeline: [{$project: {'_id':0,'tags': 1 } }], as: 'vendorTags'}}";
    	String query3 = "{$lookup: {from: 'bb_admin_panel_vendors_fulfillments',localField: 'fulfillmentsId',foreignField: 'id',pipeline: [{$project: {'_id': 0,'id':1,'type':1,'contact':1} }], as: 'fulfillments'}}";
    	String query4 = "{$lookup: {from: 'bb_admin_panel_vendors_products',localField: 'id',foreignField: 'vendorId',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1,'quantity': 1,'price':1,'time':1,'category_id':1,'category_ids':1,'fulfillment_id':1,'location_id':1,'related':1,'recommended':1,'@ondc/org/returnable':1,'@ondc/org/cancellable':1,'@ondc/org/return_window':1 ,'@ondc/org/seller_pickup_return':1,'@ondc/org/time_to_ship':1,'@ondc/org/available_on_cod':1,'@ondc/org/contact_details_consumer_care\':1  } }], as: 'itemsMain'}}";
    	String query8 = "{$lookup: {from: 'bb_admin_panel_vendors_categories',localField: 'id',foreignField: 'parent_category_id',pipeline: [{$project: {'_id': 0,'id':1,'parent_category_id':1,'descriptor':1,'tags':1} }],as: 'categoriesMain'}}";
    	String query5 = "{$lookup: {from: 'bb_admin_panel_vendors_custom_groups',localField: 'itemsMain.id',foreignField: 'parentProductId',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1} }], as: 'customGroups'}}";
    	String query6 = "{$lookup: {from: 'bb_admin_panel_vendors_items',localField: 'itemsMain.id',foreignField: 'parentItemId',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1,'quantity': 1,'price':1,'catgory_id':1,'related':1 } }], as: 'itemCollection'}}";

    	Aggregation aggregation = Aggregation.newAggregation(
    	    new CustomProjectAggregationOperation(query1),
    	    new CustomProjectAggregationOperation(query2),
    	    new CustomProjectAggregationOperation(query3),
    	    new CustomProjectAggregationOperation(query4),
    	    new CustomProjectAggregationOperation(query5),
    	    new CustomProjectAggregationOperation(query6),
    	    new CustomProjectAggregationOperation(query8),
    	    Aggregation.unwind("vendorTags"),
    	    Aggregation.match(Criteria.where("locations").exists(true).not().size(0)),

    	    Aggregation.project()
    	        .andExclude("_id")
    	        .andInclude("id","time", "fulfillments", "descriptor", "@ondc/org/fssai_license_no", "ttl", "offers", "locations","categories","items", "tags:$vendorTags.tags")
    	        .and(ArrayOperators.ConcatArrays.arrayOf("categoriesMain").concat("customGroups")).as("categories")
    	        .and(ArrayOperators.ConcatArrays.arrayOf("itemsMain").concat("itemCollection")).as("items")
    	);

    	AggregationResults<Object> results = mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors", Object.class);
    	List<Object> resultDtoString = results.getMappedResults();
    	System.out.println("exit:"+Instant.now());
    	return resultDtoString;

         
    	} 

//    working, commented due to testing with implemetation of ondc
//    public static List<Object> getByItemAndCity(String item, String city) {
//    	if(city !="" && item !="") {
//    		System.out.println("checking item and city products");    		Aggregation aggregation = Aggregation.newAggregation(
//    				Aggregation.lookup("bb_admin_panel_vendors_locations", "locationsId", "id", "locations"),
//    				Aggregation.match(Criteria.where("locations.address.city").is(city)),
//    				Aggregation.lookup("bb_admin_panel_vendors_products", "id", "vendor_ID", "itemsMain"),
//    				Aggregation.unwind("itemsMain"),
//    				Aggregation.match(Criteria.where("itemsMain.descriptor.name").is(item)),
//    				Aggregation.lookup("bb_admin_panel_vendors_locations", "locationsId", "id", "vendorTags"),
//    				Aggregation.lookup("bb_admin_panel_vendors_fulfillments", "fulfillmentsId", "id", "fulfillments"),
//    				Aggregation.lookup("bb_admin_panel_vendors_custom_groups", "itemsMain.customizationItems", "id", "customGroups"),
//    				Aggregation.lookup("bb_admin_panel_vendors_items", "customGroups.id", "parentCategoryId", "itemCollection"),
//    				Aggregation.lookup("bb_admin_panel_vendors_categories", "id", "parent_category_id", "categoriesMain"),
//    				Aggregation.unwind("vendorTags"),
//    				Aggregation.project()
//    				    .andExclude("_id")
//    				    .andInclude("id", "time", "fulfillments", "descriptor", "@ondc/org/fssai_license_no", "ttl", "offers", "locations", "categories", "items", "tags:$vendorTags.tags")
//    				    .and(ArrayOperators.ConcatArrays.arrayOf("categoriesMain").concat("customGroups")).as("categories")
//    				    .and(ArrayOperators.ConcatArrays.arrayOf("itemsMain").concat("itemCollection")).as("items"),
//    				Aggregation.limit(1)
//    	        );
//    		return mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors", Object.class).getMappedResults();
//    	}
//    	else if (item!="") {
//    		Aggregation aggregation = Aggregation.newAggregation(
//    	            Aggregation.lookup("bb_admin_panel_vendors_locations", "locationsId", "id", "locations"),
//    	            Aggregation.lookup("bb_admin_panel_vendors_locations", "locationsId", "id", "vendorTags"),
//    	            Aggregation.lookup("bb_admin_panel_vendors_fulfillments", "fulfillmentsId", "id", "fulfillments"),
//    	            Aggregation.lookup("bb_admin_panel_vendors_products", "id", "vendor_ID", "itemsMain"),
//    	            Aggregation.lookup("bb_admin_panel_vendors_custom_groups", "itemsMain.customizationItems", "id", "customGroups"),
//    	            Aggregation.lookup("bb_admin_panel_vendors_items", "customGroups.id", "parentCategoryId", "itemCollection"),
//    	            Aggregation.lookup("bb_admin_panel_vendors_categories", "id", "parent_category_id", "categoriesMain"),
//    	            Aggregation.unwind("vendorTags"),
//    	            Aggregation.match(Criteria.where("itemsMain.descriptor.name").is(item)),
//    	            Aggregation.project()
//    	                .andExclude("_id")
//    	                .andInclude( "id","time", "fulfillments", "descriptor", "@ondc/org/fssai_license_no", "ttl", "offers", "locations","categories","items", "tags:$vendorTags.tags")
//    	                .and(ArrayOperators.ConcatArrays.arrayOf("categoriesMain").concat("customGroups")).as("categories")
//    	                .and(ArrayOperators.ConcatArrays.arrayOf("itemsMain").concat("itemCollection")).as("items"),
//    	            Aggregation.limit(3)  
//    	        );
//    		return mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors", Object.class).getMappedResults();
//		}else {
//			Aggregation aggregation = Aggregation.newAggregation(
//    	            Aggregation.lookup("bb_admin_panel_vendors_locations", "locationsId", "id", "locations"),
//    	            Aggregation.lookup("bb_admin_panel_vendors_locations", "locationsId", "id", "vendorTags"),
//    	            Aggregation.lookup("bb_admin_panel_vendors_fulfillments", "fulfillmentsId", "id", "fulfillments"),
//    	            Aggregation.lookup("bb_admin_panel_vendors_products", "id", "vendor_ID", "itemsMain"),
//    	            Aggregation.lookup("bb_admin_panel_vendors_custom_groups", "itemsMain.customizationItems", "id", "customGroups"),
//    	            Aggregation.lookup("bb_admin_panel_vendors_items", "customGroups.id", "parentCategoryId", "itemCollection"),
//    	            Aggregation.lookup("bb_admin_panel_vendors_categories", "id", "parent_category_id", "categoriesMain"),
//    	            Aggregation.unwind("vendorTags"),
//    	            Aggregation.match(Criteria.where("locations.address.city").is(city)),
//    	            Aggregation.project()
//    	                .andExclude("_id")
//    	                .andInclude( "id","time", "fulfillments", "descriptor", "@ondc/org/fssai_license_no", "ttl", "offers", "locations","categories","items", "tags:$vendorTags.tags")
//    	                .and(ArrayOperators.ConcatArrays.arrayOf("categoriesMain").concat("customGroups")).as("categories")
//    	                .and(ArrayOperators.ConcatArrays.arrayOf("itemsMain").concat("itemCollection")).as("items"),
//    	            Aggregation.limit(3)  
//    	        );
//    		return mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors", Object.class).getMappedResults();
//		}
//		
//    }   
    
    // code of actual implementation
    public static List<Object> getByItemAndCity( String item,String city) {
 	   
//	    SearchRestaurantSeller.log.info(" Inside the getByItem metod of SearchRestaurantSeller class");
		System.out.println("enter:"+Instant.now());
		List<Object> resultDtoString=null;
		 try {
//			String query1 ="{$lookup: {from: 'bb_admin_panel_vendors_locations',localField: 'locationsId',foreignField: 'id',pipeline: [{$project: {'_id':0,'id': 1, 'address': 1,'time':1,'gps':'$circle.gps','circle':1} }], as: 'locations'}},";
//		    String query2 ="{$lookup: {from: 'bb_admin_panel_vendors_locations',localField: 'locationsId',foreignField: 'id',pipeline: [{$project: {'_id':0,'tags': 1 } }], as: 'vendorTags'}},";
//		    String query4 = "{$lookup: {from: 'bb_admin_panel_vendors_products', localField: 'id', foreignField: 'vendorId', pipeline: [{$match: {'descriptor.name': {$regex: '.*"+item+".*', $options: 'i'}}}, {$project: {'_id': 0, 'id': 1, 'descriptor': 1, 'tags': 1, 'quantity': 1, 'price': 1, 'time': 1, 'category_id': 1, 'category_ids': 1, 'parent_category_id':1,'fulfillment_id': 1, 'location_id': 1, 'related': 1, 'recommended': 1, '@ondc/org/returnable': 1, '@ondc/org/cancellable': 1, '@ondc/org/return_window': 1, '@ondc/org/seller_pickup_return': 1, '@ondc/org/time_to_ship': 1, '@ondc/org/available_on_cod': 1, '@ondc/org/contact_details_consumer_care': 1}}], as: 'itemsMain'}}";
//		    String query3 ="{$lookup: {from: 'bb_admin_panel_vendors_fulfillments',localField: 'fulfillmentsId',foreignField: 'id',pipeline: [{$project: {'_id': 0,'id':'$defaultId','type':1,'contact':1} }], as: 'fulfillments'}},";
//		    String query8 ="{$lookup: {from: 'bb_admin_panel_vendors_categories',localField: 'itemsMain.parent_category_id',foreignField: 'id',pipeline: [{$project: {'_id': 0,'id':1,'parent_category_id':1,'descriptor':1,'tags':1} }],as: 'categoriesMain'}},";
//		    String query5 ="{$lookup: {from: 'bb_admin_panel_vendors_custom_groups',localField: 'itemsMain.id',foreignField: 'parentProductId',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1} }], as: 'customGroups'}},";
//		    String query6 ="{$lookup: {from: 'bb_admin_panel_vendors_items',localField: 'itemsMain.id',foreignField: 'parentItemId',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1,'quantity': 1,'price':1,'category_id':1,'related':1 } }], as: 'itemCollection'}},";
//		 
//		    Aggregation aggregation = Aggregation.newAggregation(
//		    		new CustomProjectAggregationOperation(query1),
//		    		new CustomProjectAggregationOperation(query2),
//		    		new CustomProjectAggregationOperation(query3),
//		    		new CustomProjectAggregationOperation(query4),
//		    	    Aggregation.match(Criteria.where("itemsMain.descriptor.name").is(item)),
//		    		new CustomProjectAggregationOperation(query5),
//		    		new CustomProjectAggregationOperation(query6),
//		            new CustomProjectAggregationOperation(query8),
//		            Aggregation.unwind("vendorTags"),
//		            Aggregation.match(Criteria.where("locations.address.city").is(city)),
//		            Aggregation.project()
//	               .andExclude("_id")
//	               .andInclude("id","time", "fulfillments", "descriptor", "@ondc/org/fssai_license_no", "ttl", "offers", "locations","categories","items", "tags:$vendorTags.tags")
//	               .and(ArrayOperators.ConcatArrays.arrayOf("categoriesMain").concat("customGroups")).as("categories")
//	               .and(ArrayOperators.ConcatArrays.arrayOf("itemsMain").concat("itemCollection")).as("items")
//		    		);
//		
//		    AggregationResults<Object> results = 
//	   	        mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors", Object.class);
//	   	    resultDtoString=results.getMappedResults();
			 
			 String query1 = "{$lookup: {from: 'bb_admin_panel_vendors_locations', localField: 'locationsId', foreignField: 'id', pipeline: [{$project: {_id: 0, id: 1, address: 1, time: 1, 'gps': '$circle.gps', circle: 1}}], as: 'locations'}}";
			 String query2 = "{$lookup: {from: 'bb_admin_panel_vendors_locations', localField: 'locationsId', foreignField: 'id', pipeline: [{$project: {_id: 0, tags: 1}}], as: 'vendorTags'}}";
			 String query3 = "{$lookup: {from: 'bb_admin_panel_vendors_fulfillments', localField: 'fulfillmentsId', foreignField: 'id', pipeline: [{$project: {_id: 0, id: '$defaultId', type: 1, contact: 1}}], as: 'fulfillments'}}";
			    String query4 = "{$lookup: {from: 'bb_admin_panel_vendors_products', localField: 'id', foreignField: 'vendorId', pipeline: [{$match: {'descriptor.name': {$regex: '.*"+item+".*', $options: 'i'}}}, {$project: {'_id': 0, 'id': 1, 'descriptor': 1, 'tags': 1, 'quantity': 1, 'price': 1, 'time': 1, 'category_id': 1, 'category_ids': 1, 'parent_category_id':1,'fulfillment_id': 1, 'location_id': 1, 'related': 1, 'recommended': 1, '@ondc/org/returnable': 1, '@ondc/org/cancellable': 1, '@ondc/org/return_window': 1, '@ondc/org/seller_pickup_return': 1, '@ondc/org/time_to_ship': 1, '@ondc/org/available_on_cod': 1, '@ondc/org/contact_details_consumer_care': 1}}], as: 'itemsMain'}}";
			 String query5 = "{$lookup: {from: 'bb_admin_panel_vendors_custom_groups', localField: 'itemsMain.id', foreignField: 'parentProductId', pipeline: [{$project: {_id: 0, id: 1, descriptor: 1, tags: 1}}], as: 'customGroups'}}";
			 String query6 = "{$lookup: {from: 'bb_admin_panel_vendors_items', localField: 'itemsMain.id', foreignField: 'parentItemId', pipeline: [{$project: {_id: 0, id: 1, descriptor: 1, tags: 1, quantity: 1, price: 1, category_id: 1, related: 1}}], as: 'itemCollection'}}";
			 String query8 = "{$lookup: {from: 'bb_admin_panel_vendors_categories', localField: 'itemsMain.parent_category_id', foreignField: 'id', pipeline: [{$project: {_id: 0, id: 1, parent_category_id: 1, descriptor: 1, tags: 1}}], as: 'categoriesMain'}}";
			 String unsetParentCategoryId = "{$unset: ['items.parent_category_id']}";


			 Aggregation aggregation = Aggregation.newAggregation(
					    new CustomProjectAggregationOperation(query1),
					    new CustomProjectAggregationOperation(query2),
					    new CustomProjectAggregationOperation(query3),
					    new CustomProjectAggregationOperation(query4),
					    Aggregation.match(Criteria.where("itemsMain.descriptor.name").is(item)),
					    new CustomProjectAggregationOperation(query5),
					    new CustomProjectAggregationOperation(query6),
					    new CustomProjectAggregationOperation(query8),
					    Aggregation.unwind("vendorTags"),
					    Aggregation.match(Criteria.where("locations.address.city").is(city)),
					    Aggregation.project()
					        .andExclude("_id")
					        .andInclude("id", "time", "fulfillments", "descriptor", "@ondc/org/fssai_license_no", "ttl", "offers", "locations", "categories", "items", "tags")
					        .and("vendorTags.tags").as("tags")
					        .and(ArrayOperators.ConcatArrays.arrayOf("categoriesMain").concat("customGroups")).as("categories")
					        .and(ArrayOperators.ConcatArrays.arrayOf("itemsMain").concat("itemCollection")).as("items"),
					    new CustomProjectAggregationOperation(unsetParentCategoryId)

					        
					);

				 AggregationResults<Object> results = mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors", Object.class);
				 resultDtoString = results.getMappedResults();


	   	    
	   	    System.out.println("exit:"+Instant.now());
	   	    return resultDtoString;
   	    
		 }catch (Exception e) {
//	        	SearchRestaurantSeller.log.info(" Exception while fetching the records for method getByItem for search API");
	        	e.printStackTrace();
	     }
//		 SearchRestaurantSeller.log.info(" Exiting the getByItem metod of SearchRestaurantSeller class");
   	    return resultDtoString;
       	             
   	}


//	String query1 = "{$lookup: {from: 'bb_admin_panel_vendors_products', let: { locationId: '$locationsId' }, pipeline: [{$match: {$expr: {$eq: ['$id', '$$locationId']}}},{$project: {'_id':0,'id': 1} }], as: 'locations'}}";
//	String query3 = "{$lookup: {from: 'bb_admin_panel_vendors_fulfillments',localField: 'fulfillmentsId',foreignField: 'id',pipeline: [{$project: {'_id': 0,'id':1,'type':1} }], as: 'fulfillments'}}";
//	String query4 = "{$lookup: {from: 'bb_admin_panel_vendors_products',localField: 'id',foreignField: 'vendorId',pipeline: [{$project: {'_id': 0,'id':1,'fulfillment_id':1 } }], as: 'itemsMain'}}";
//	String query8 = "{$lookup: {from: 'bb_admin_panel_vendors_categories',localField: 'id',foreignField: 'parent_category_id',pipeline: [{$project: {'_id': 0,'id':1,'parent_category_id':1,'descriptor':1,'tags':1} }],as: 'categoriesMain'}}";
//	String query5 = "{$lookup: {from: 'bb_admin_panel_vendors_custom_groups',localField: 'product.id',foreignField: 'parentProductId',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1} }], as: 'customGroups'}}";
//	String query6 = "{$lookup: {from: 'bb_admin_panel_vendors_items',localField: 'product.id',foreignField: 'parentItemId',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1,'quantity': 1,'price':1,'catgory_id':1,'related':1 } }], as: 'itemCollection'}}";
//
//	Aggregation aggregation = Aggregation.newAggregation(
//	    new CustomProjectAggregationOperation(query1),
//	    new CustomProjectAggregationOperation(query3),
//	    new CustomProjectAggregationOperation(query4),
//	    new CustomProjectAggregationOperation(query5),
//	    new CustomProjectAggregationOperation(query6),
//	    new CustomProjectAggregationOperation(query8),
//	    Aggregation.unwind("vendorTags"),
////	    Aggregation.match(Criteria.where("locations").exists(true).not().size(0)),
//	    Aggregation.match(Criteria.where("itemsMain.id").is(itemId)),
//
//	    Aggregation.project()
//	        .andExclude("_id")
//	        .andInclude("id","time", "fulfillments", "descriptor", "@ondc/org/fssai_license_no", "ttl", "offers", "locations","categories","items", "tags:$vendorTags.tags")
//	        .and(ArrayOperators.ConcatArrays.arrayOf("categoriesMain").concat("customGroups")).as("categories")
//	        .and(ArrayOperators.ConcatArrays.arrayOf("itemsMain").concat("itemCollection")).as("items")
//	);
//
//	AggregationResults<Object> results = mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors", Object.class);
    	public List<Object> onSelectQuery() {
	List<String> itemidsToFind = new ArrayList<>();
//    itemidsToFind.add("I85");
//    itemidsToFind.add("I83");	
	
	
	String query6 ="{$lookup: {from: 'bb_admin_panel_vendors_items',localField: 'id',foreignField: 'parentItemId',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1,'quantity': 1,'price':1,'category_id':1,'related':1 } }], as: 'item'}},";
	String query7 ="{$lookup: {from: 'bb_admin_panel_vendors_custom_groups',localField: 'id',foreignField: 'parentProductId',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1,'quantity': 1,'price':1,'category_id':1,'related':1 } }], as: 'customGroup'}},";

	Aggregation aggregation = Aggregation.newAggregation(
//			   Aggregation.match(Criteria.where("id").in(itemidsToFind)),

		   new CustomProjectAggregationOperation(query6),
		   new CustomProjectAggregationOperation(query7),
		   Aggregation.project()
		   .andExclude("_id")
		   .andInclude("id","dimension","weight","item","customGroup"));
		   
AggregationResults<Object> results = 
	        mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors_products", Object.class);
	List<Object> resultDtoString = results.getMappedResults();
	System.out.println("exit:"+Instant.now());
	return resultDtoString;

     
	} }




