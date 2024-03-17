package com.bornbhukkad.merchant.Service;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

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

//    	    AggregationOperation match = Aggregation.match(
//    	            Criteria.where("id").is(SOME_VARIABLE_STRING_1));
//    	    AggregationOperation match2 = Aggregation.match(
//    	            Criteria.where("deliveryZipCodeTimings").ne(Collections.EMPTY_LIST));
    	    String query =
    	            "{ $lookup: { " +
    	                    "from: 'bb_admin_panel_vendors_locations'," +
    	                    "localField: 'locationsId'," +
    	                    "foreignField: 'id'," +
    	                    "as: 'locations'}}";
    	    String query2 =
    	            "{ $lookup: { " +
    	                    "from: 'bb_admin_panel_vendors_locations'," +
    	                    "pipeline: [{" +
    	                    "$match: {$expr: {$and: [" +
    	                    "{ $eq: ['$id', '" + SOME_VARIABLE_STRING_2 + "']}]}}}," +
    	                    "{ $project: { _id: 0 } }]," +
    	                    "as: 'deliveryZipCodeTimings'}}";
    	    String query3=
    	    		"{$lookup: {from: 'bb_admin_panel_vendors_offers',localField: 'offersAvail',foreignField: 'id', as: 'offers'}}, {$lookup: {from: 'bb_admin_panel_vendors_locations',localField: 'locationsId',foreignField: 'id',as: 'locations'}},"
    	    		+ "        {"
    	    		+ "            $lookup: {"
    	    		+ "                from: \"bb_admin_panel_vendors_locations\","
    	    		+ "                localField: \"locationsId\","
    	    		+ "                foreignField: \"id\","
    	    		+ "                \"pipeline\": ["
    	    		+ "                    { \"$project\": { \"tags\": 1 } }"
    	    		+ "                ],"
    	    		+ "                as: \"vendorTags\""
    	    		+ "            }"
    	    		+ "        },"
    	    		+ "        {"
    	    		+ "            $lookup: {"
    	    		+ "                from: \"bb_admin_panel_vendors_fulfillments\","
    	    		+ "                localField: \"fulfillmentId\","
    	    		+ "                foreignField: \"id\","
    	    		+ "                \"pipeline\": ["
    	    		+ "                    { \"$project\": { \"_id\": 0, } }"
    	    		+ "                ],"
    	    		+ "                as: \"fulfillments\""
    	    		+ "            }"
    	    		+ "        },"
    	    		+ "        {"
    	    		+ "            $lookup: {"
    	    		+ "                from: \"bb_admin_panel_vendors_products\","
    	    		+ "                localField: \"id\","
    	    		+ "                foreignField: \"vendor_ID\","
    	    		+ "                \"pipeline\": ["
    	    		+ "                    { \"$project\": { \"customizationItems\": 0, \"vendor_ID\": 0, \"parent_category_id\": 0 } }"
    	    		+ "                ],"
    	    		+ "                as: \"itemsMain\""
    	    		+ "            }"
    	    		+ "        },"
    	    		+ ""
    	    		+ "        {"
    	    		+ "            $lookup: {"
    	    		+ "                from: \"bb_admin_panel_vendors_custom_groups\","
    	    		+ "                localField: \"itemsMain.customizationItems\","
    	    		+ "                foreignField: \"id\","
    	    		+ "                as: \"customGroups\""
    	    		+ "            }"
    	    		+ "        },"
    	    		+ "        {"
    	    		+ ""
    	    		+ "            $lookup: {"
    	    		+ "                from: \"bb_admin_panel_vendors_items\","
    	    		+ "                localField: \"customGroups.id\","
    	    		+ "                foreignField: \"parentCategoryId\","
    	    		+ "                \"pipeline\": ["
    	    		+ "                    { \"$project\": { \"parentCategoryId\": 0, \"vendor_ID\": 0, } }"
    	    		+ "                ],"
    	    		+ "                as: \"itemCollection\""
    	    		+ "            }"
    	    		+ "        },"
    	    		+ ""
    	    		+ "        {"
    	    		+ ""
    	    		+ "            $lookup: {"
    	    		+ "                from: \"bb_admin_panel_vendors_categories\","
    	    		+ "                localField: \"id\","
    	    		+ "                \"pipeline\": ["
    	    		+ "                    { \"$project\": { \"vendorID\": 0, \"_id\": 0 } }"
    	    		+ "                ],"
    	    		+ "                foreignField: \"vendorID\","
    	    		+ "                as: \"categoriesMain\""
    	    		+ "            }"
//    	    		+ "        },"
    	    + "        },";
//    	    		+ "        {"
//    	    		+ "            $project: {"
//    	    		+ "                \"_id\": 0, \"location_ids\": 0, \"id\": 1,"
//    	    		+ "                \"time\": 1, \"descriptor\": 1, \"ttl\": 1, \"@ondc/org/fssai_license_no\": 1, \"offers\": 1, \"locations\": 1, \"fulfillments\": 1, \"tags\": \"$vendorTags.tags\", categories: { $concatArrays: [\"$categoriesMain\", \"$customGroups\"] }, items: { $concatArrays: [\"$itemsMain\", \"$itemCollection\"] }"
//    	    		+ "            }"
//    	    		+ "        }";

    	    TypedAggregation<Object> aggregation = Aggregation.newAggregation(
    	    		Object.class,
//    	            new CustomProjectAggregationOperation(query),
    	            new CustomProjectAggregationOperation(query3)
    	            
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
    
    
    
    public static List<Object> getByItemAndCity(String item, String city) {
    	AggregationOperation mergeOperation = new AggregationOperation() {
    	    @Override
    	    public Document toDocument(AggregationOperationContext aggregationOperationContext) {
    	        return new Document("$project", new Document("combinedObject",
    	            new Document("$mergeObjects", new Document("firstObject", "$firstObject").append("secondObject", "$secondObject"))
    	        ));
    	    }
    	};
    	Aggregation aggregation = Aggregation.newAggregation(
//            Aggregation.lookup("bb_admin_panel_vendors_offers", "vendor_ID", "vendorId", "offers"),
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
            Aggregation.limit(3)  
        );
        return mongoTemplate.aggregate(aggregation, "bb_admin_panel_vendors", Object.class).getMappedResults();
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

