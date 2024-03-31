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

import java.util.Collections;
import java.util.List;

@Service
public class bbdataService {
    private static MongoTemplate mongoTemplate;

    @Autowired
    public bbdataService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
  
//    public static List<Object> getSearchDetails() {
//
//	    String SOME_VARIABLE_STRING_1="Patna";
//	    String SOME_VARIABLE_STRING_2="";
//
//    	    AggregationOperation match = Aggregation.match(
//    	            Criteria.where("id").is(SOME_VARIABLE_STRING_1));
////    	    AggregationOperation match2 = Aggregation.match(
////    	            Criteria.where("deliveryZipCodeTimings").ne(Collections.EMPTY_LIST));
//    	    String query =
//    	            "{ $lookup: { " +
//    	                    "from: 'bb_admin_panel_vendors_locations'," +
//    	                    "let: { tags: '$tagsCheck' }," +
//    	                    "pipeline: [{" +
//    	                    "$match: {$expr: {$and: [" +
//    	                    "{ $eq: ['$address.city', '" + SOME_VARIABLE_STRING_2 + "']}]}}}," +
//    	                    "{ $project: { _id: 0, gps: 0} }]," +
//    	                    "as: 'locationCheck'}}";
////    	    String query2 =
////    	            "{ $lookup: { " +
////    	                    "from: 'bb_admin_panel_vendors_locations'," +
////    	                    "let: { tags: '$tagsCheck' }," +
////    	                    "pipeline: [{" +
////    	                    "$match: {$expr: {$and: [" +
////    	                    "{ $eq: ['$fulfillmentLocationId', '$$location_id']}," +
////    	                    "{ $eq: ['$zipCode', '" + SOME_VARIABLE_STRING_2 + "']}]}}}," +
////    	                    "{ $project: { _id: 0, zipCode: 1, cutoffTime: 1 } }]," +
////    	                    "as: 'deliveryZipCodeTimings'}}";
//
//    	    TypedAggregation<Object> aggregation = Aggregation.newAggregation(
//    	    		Object.class,
//    	            match,
//    	            new CustomProjectAggregationOperation(query)
//    	            
//    	    );
//
//    	    AggregationResults<Object> results = 
//    	        mongoTemplate.aggregate(aggregation, Object.class);
//    	    return results.getMappedResults();
//    	}
    
    
    public List<Object> getFulfillmentChannels(
    	    String item, 
    	    String city) {
   
    	String query1 ="{$lookup: {from: 'bb_admin_panel_vendors_locations',localField: 'locationsId',foreignField: 'id',pipeline: [{$project: {'_id':0,'id': 1, 'address': 1,'time':1,'gps':1,'circle':1} }], as: 'locations'}},";
    	    String query2 ="{$lookup: {from: 'bb_admin_panel_vendors_locations',localField: 'locationsId',foreignField: 'id',pipeline: [{$project: {'_id':0,'tags': 1 } }], as: 'vendorTags'}},";
    	    String query3 ="{$lookup: {from: 'bb_admin_panel_vendors_fulfillments',localField: 'fulfillmentsId',foreignField: 'id',pipeline: [{$project: {'_id': 0,'id':1,'type':1,'contact':1} }], as: 'fulfillments'}},";
    	    String query4 ="{$lookup: {from: 'bb_admin_panel_vendors_products',localField: 'id',foreignField: 'vendor_ID',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1,'quantity': 1,'price':1,'time':1,'category_id':1,'category_ids':1,'fulfillment_id':1,'location_id':1,'related':1,'recommended':1,'@ondc/org/returnable':1,'@ondc/org/cancellable':1,'@ondc/org/return_window':1 ,'@ondc/org/seller_pickup_return':1,'@ondc/org/time_to_ship':1,'@ondc/org/available_on_cod':1,'@ondc/org/contact_details_consumer_care\':1  } }], as: 'itemsMain'}},";
    	    String query8 ="{$lookup: {from: 'bb_admin_panel_vendors_categories',localField: 'id',foreignField: 'parent_category_id',pipeline: [{$project: {'_id': 0,'id':1,'parent_category_id':1,'descriptor':1,'tags':1} }],as: 'categoriesMain'}},";
    	    String query5 ="{$lookup: {from: 'bb_admin_panel_vendors_custom_groups',localField: 'itemsMain.id',foreignField: 'parentProductId',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1} }], as: 'customGroups'}},";
    	    String query6 ="{$lookup: {from: 'bb_admin_panel_vendors_items',localField: 'customGroups.id',foreignField: 'parentCategoryId',pipeline: [{$project: {'_id': 0,'id':1,'descriptor':1,'tags':1,'quantity': 1,'price':1,'catgory_id':1,'related':1 } }], as: 'itemCollection'}},";
    	    
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
    	            Aggregation.match(Criteria.where("itemsMain.descriptor.name").is(item)),
    	            Aggregation.project()
	                .andExclude("_id")
	                .andInclude("id","time", "fulfillments", "descriptor", "@ondc/org/fssai_license_no", "ttl", "offers", "locations","categories","items", "tags:$vendorTags.tags")
	                .and(ArrayOperators.ConcatArrays.arrayOf("categoriesMain").concat("customGroups")).as("categories")
	                .and(ArrayOperators.ConcatArrays.arrayOf("itemsMain").concat("itemCollection")).as("items")
    	    		);
    	    AggregationResults<Object> results = 
        	        mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors", Object.class);
        	    List<Object> resultDtoString=results.getMappedResults();
        	    return resultDtoString;            
    	}

    public static List<Object> getByItemAndCity(String item, String city) {
    	if(city !="" && item !="") {
    		Aggregation aggregation = Aggregation.newAggregation(
    				Aggregation.lookup("bb_admin_panel_vendors_locations", "locationsId", "id", "locations"),
    	            Aggregation.lookup("bb_admin_panel_vendors_locations", "locationsId", "id", "vendorTags"),
    	            Aggregation.lookup("bb_admin_panel_vendors_fulfillments", "fulfillmentsId", "id", "fulfillments"),
    	            Aggregation.lookup("bb_admin_panel_vendors_products", "id", "vendor_ID", "itemsMain"),
    	            Aggregation.lookup("bb_admin_panel_vendors_custom_groups", "itemsMain.customizationItems", "id", "customGroups"),
    	            Aggregation.lookup("bb_admin_panel_vendors_items", "customGroups.id", "parentCategoryId", "itemCollection"),
    	            Aggregation.lookup("bb_admin_panel_vendors_categories", "id", "parent_category_id", "categoriesMain"),
    	            Aggregation.unwind("vendorTags"),
    	            Aggregation.match(Criteria.where("locations.address.city").is(city)),
    	            Aggregation.match(Criteria.where("itemsMain.descriptor.name").is(item)),
    	            Aggregation.project()
    	                .andExclude("_id")
    	                .andInclude( "id","time", "fulfillments", "descriptor", "@ondc/org/fssai_license_no", "ttl", "offers", "locations","categories","items", "tags:$vendorTags.tags")
    	                .and(ArrayOperators.ConcatArrays.arrayOf("categoriesMain").concat("customGroups")).as("categories")
    	                .and(ArrayOperators.ConcatArrays.arrayOf("itemsMain").concat("itemCollection")).as("items"),
    	            Aggregation.limit(1)  
    	        );
    		return mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors", Object.class).getMappedResults();
//    		Aggregation aggregation = Aggregation.newAggregation(
//                    lookup("bb_admin_panel_vendors_offers", "vendor_ID", "vendorId", "offers"),
//                    new LookupOperation.LookupOperationBuilder()
//                    .from("bb_admin_panel_vendors_locations")
//                    .localField("vendor_ID")
//                    .foreignField("vendorId")
//                    .as("locations"),
//                    pipeline("locations"),
//
//                    lookup("bb_admin_panel_vendors_fulfillments", "vendor_ID", "vendorId", "fulfillments"),
//                    lookup("bb_admin_panel_vendors_products", "vendor_ID", "vendorId", "itemsMain"),
//                    lookup("bb_admin_panel_vendors_custom_groups", "itemsMain.customizationItems", "id", "customGroups"),
//                    lookup("bb_admin_panel_vendors_items", "customGroups.id", "parentCategoryId", "itemCollection"),
//                    lookup("bb_admin_panel_vendors_categories", "vendor_ID", "vendorID", "categoriesMain"),
//                    project()
//                            .andExclude("_id")
//                            .andInclude("id", "time", "fulfillments", "descriptor", "@ondc/org/fssai_license_no", "ttl", "offers", "locations","tags:$vendorTags.tags")
//                            .and(ArrayOperators.ConcatArrays.arrayOf("categoriesMain").concat("customGroups")).as("categories")
//        	                .and(ArrayOperators.ConcatArrays.arrayOf("itemsMain").concat("itemCollection")).as("items"),
//            );
//
//            return mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors", Object.class).getMappedResults();
    	}
    	else if (item!="") {
    		Aggregation aggregation = Aggregation.newAggregation(
//    	            Aggregation.lookup("bb_admin_panel_vendors_offers", "vendor_ID", "vendorId", "offers"),
    	            Aggregation.lookup("bb_admin_panel_vendors_locations", "locationsId", "id", "locations"),
    	            Aggregation.lookup("bb_admin_panel_vendors_locations", "locationsId", "id", "vendorTags"),
    	            Aggregation.lookup("bb_admin_panel_vendors_fulfillments", "fulfillmentsId", "id", "fulfillments"),
    	            Aggregation.lookup("bb_admin_panel_vendors_products", "id", "vendor_ID", "itemsMain"),
    	            Aggregation.lookup("bb_admin_panel_vendors_custom_groups", "itemsMain.customizationItems", "id", "customGroups"),
    	            Aggregation.lookup("bb_admin_panel_vendors_items", "customGroups.id", "parentCategoryId", "itemCollection"),
    	            Aggregation.lookup("bb_admin_panel_vendors_categories", "id", "parent_category_id", "categoriesMain"),
    	            Aggregation.unwind("vendorTags"),
//    	            Aggregation.match(Criteria.where("locations.address.city").is(city)),
    	            Aggregation.match(Criteria.where("itemsMain.descriptor.name").is(item)),
    	            Aggregation.project()
    	                .andExclude("_id")
    	                .andInclude( "id","time", "fulfillments", "descriptor", "@ondc/org/fssai_license_no", "ttl", "offers", "locations","categories","items", "tags:$vendorTags.tags")
    	                .and(ArrayOperators.ConcatArrays.arrayOf("categoriesMain").concat("customGroups")).as("categories")
    	                .and(ArrayOperators.ConcatArrays.arrayOf("itemsMain").concat("itemCollection")).as("items"),
    	            Aggregation.limit(3)  
    	        );
    		return mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors", Object.class).getMappedResults();
		}else {
			Aggregation aggregation = Aggregation.newAggregation(
//    	            Aggregation.lookup("bb_admin_panel_vendors_offers", "vendor_ID", "vendorId", "offers"),
    	            Aggregation.lookup("bb_admin_panel_vendors_locations", "locationsId", "id", "locations"),
    	            Aggregation.lookup("bb_admin_panel_vendors_locations", "locationsId", "id", "vendorTags"),
    	            Aggregation.lookup("bb_admin_panel_vendors_fulfillments", "fulfillmentsId", "id", "fulfillments"),
    	            Aggregation.lookup("bb_admin_panel_vendors_products", "id", "vendor_ID", "itemsMain"),
    	            Aggregation.lookup("bb_admin_panel_vendors_custom_groups", "itemsMain.customizationItems", "id", "customGroups"),
    	            Aggregation.lookup("bb_admin_panel_vendors_items", "customGroups.id", "parentCategoryId", "itemCollection"),
    	            Aggregation.lookup("bb_admin_panel_vendors_categories", "id", "parent_category_id", "categoriesMain"),
    	            Aggregation.unwind("vendorTags"),
    	            Aggregation.match(Criteria.where("locations.address.city").is(city)),
//    	            Aggregation.match(Criteria.where("itemsMain.descriptor.name").is(item)),
    	            Aggregation.project()
    	                .andExclude("_id")
    	                .andInclude( "id","time", "fulfillments", "descriptor", "@ondc/org/fssai_license_no", "ttl", "offers", "locations","categories","items", "tags:$vendorTags.tags")
    	                .and(ArrayOperators.ConcatArrays.arrayOf("categoriesMain").concat("customGroups")).as("categories")
    	                .and(ArrayOperators.ConcatArrays.arrayOf("itemsMain").concat("itemCollection")).as("items"),
    	            Aggregation.limit(3)  
    	        );
    		return mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors", Object.class).getMappedResults();
		}
//    		return mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors", Object.class).getMappedResults();
		
    }   
}

//Aggregation aggregation = Aggregation.newAggregation(
//Aggregation.lookup("bb_admin_panel_vendors_offers", "vendor_ID", "vendorId", "offers"),
//Aggregation.lookup("bb_admin_panel_vendors_locations", "vendor_ID", "vendorId", "locations"),
//Aggregation.lookup("bb_admin_panel_vendors_locations", "vendor_ID", "vendorId","locations"),
//Aggregation.lookup("bb_admin_panel_vendors_locations", "vendor_ID", "vendorId", "vendorTags"),
//Aggregation.lookup("bb_admin_panel_vendors_fulfillments", "vendor_ID", "vendorId", "fulfillments"),
//Aggregation.lookup("bb_admin_panel_vendors_products", "vendor_ID", "vendorId", "itemsMain"),
//Aggregation.lookup("bb_admin_panel_vendors_custom_groups", "itemsMain.customizationItems", "id", "customGroups"),
//Aggregation.lookup("bb_admin_panel_vendors_items", "customGroups.id", "parentCategoryId", "itemCollection"),
//Aggregation.lookup("bb_admin_panel_vendors_categories", "vendor_ID", "vendorID", "categoriesMain"),
//Aggregation.unwind("vendorTags"),
//Aggregation.match(Criteria.where("locations.address.city").is("pune")),
//// Add other stages as needed
//Aggregation.project()
//    .andExclude("_id")
//    .andInclude("id", "time", "fulfillments", "descriptor", "@ondc/org/fssai_license_no", "ttl", "offers", "locations","itemsMain","customGroups","itemCollection","categoriesMain",
//            "categories: { $concatArrays: [\"$categoriesMain\", \"$customGroups\"] }", "items: { $concatArrays: [\"$itemsMain\", \"$itemCollection\"] }", "tags:$vendorTags.tags"),
//    Aggregation.limit(3) 
//);

