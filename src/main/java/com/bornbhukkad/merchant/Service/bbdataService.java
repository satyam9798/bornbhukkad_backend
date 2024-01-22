package com.bornbhukkad.merchant.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.bornbhukkad.merchant.dto.User;

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
    	    String SOME_VARIABLE_STRING_1, 
    	    String SOME_VARIABLE_STRING_2) {

    	    AggregationOperation match = Aggregation.match(
    	            Criteria.where("id").is(SOME_VARIABLE_STRING_1));
    	    AggregationOperation match2 = Aggregation.match(
    	            Criteria.where("deliveryZipCodeTimings").ne(Collections.EMPTY_LIST));
//    	    String query =
//    	            "{ $lookup: { " +
//    	                    "from: 'bb_admin_panel_vendors_locations'," +
//    	                    "pipeline: [{" +
//    	                    "$match: {$expr: {$and: [" +
//    	                    "{ $eq: ['$id', '" + SOME_VARIABLE_STRING_2 + "']}]}}}," +
//    	                    "{ $project: { _id: 0 } }]," +
//    	                    "as: 'deliveryZipCodeTimings'}}";
    	    String query2 =
    	            "{ $lookup: { " +
    	                    "from: 'bb_admin_panel_vendors_locations'," +
    	                    "pipeline: [{" +
    	                    "$match: {$expr: {$and: [" +
    	                    "{ $eq: ['$id', '" + SOME_VARIABLE_STRING_2 + "']}]}}}," +
    	                    "{ $project: { _id: 0 } }]," +
    	                    "as: 'deliveryZipCodeTimings'}}";

    	    TypedAggregation<Object> aggregation = Aggregation.newAggregation(
    	    		Object.class,
    	            match,
    	            new CustomProjectAggregationOperation(query2)
    	            
    	    );

    	    AggregationResults<Object> results = 
    	        mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors", Object.class);
    	    return results.getMappedResults();
    	}
    
    
//    
//    public static List<Object> runCustomAggregation() {
//
//        String collectionName = "bb_admin_panel_vendors";
//        List<Object> dataEntities= mongoTemplate.findAll( Object.class, collectionName);
//        
//        return dataEntities;
//    }
    
    public static List<Object> runCustomQuery() {

        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.lookup("bb_admin_panel_vendors_offers", "vendor_ID", "vendorId", "offers"),
            Aggregation.lookup("bb_admin_panel_vendors_locations", "vendor_ID", "vendorId", "locations"),
            Aggregation.lookup("bb_admin_panel_vendors_locations", "vendor_ID", "vendorId","locations"),
            Aggregation.lookup("bb_admin_panel_vendors_locations", "vendor_ID", "vendorId", "vendorTags"),
            Aggregation.lookup("bb_admin_panel_vendors_fulfillments", "vendor_ID", "vendorId", "fulfillments"),
            Aggregation.lookup("bb_admin_panel_vendors_products", "vendor_ID", "vendorId", "itemsMain"),
            Aggregation.lookup("bb_admin_panel_vendors_custom_groups", "itemsMain.customizationItems", "id", "customGroups"),
            Aggregation.lookup("bb_admin_panel_vendors_items", "customGroups.id", "parentCategoryId", "itemCollection"),
            Aggregation.lookup("bb_admin_panel_vendors_categories", "vendor_ID", "vendorID", "categoriesMain"),
            Aggregation.unwind("vendorTags"),
            // Add other stages as needed
            Aggregation.project()
                .andExclude("_id")
                .andInclude("id", "time", "fulfillments", "descriptor", "@ondc/org/fssai_license_no", "ttl", "offers", "locations","itemsMain","customGroups","itemCollection","categoriesMain",
                        "categories: { $concatArrays: [\"$categoriesMain\", \"$customGroups\"] }", "items: { $concatArrays: [\"$itemsMain\", \"$itemCollection\"] }", "tags:$vendorTags.tags"),
                Aggregation.limit(3) 
        );

        return mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors", Object.class).getMappedResults();
    }
    
   
	
    
}

