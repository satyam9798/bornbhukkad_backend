package com.bornbhukkad.merchant.Service;


import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
//import org.springframework.data.mongodb.core.mapping.Document;

import com.bornbhukkad.merchant.controller.AuthController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.bson.Document;



public class CustomProjectAggregationOperation implements AggregationOperation {
    private String jsonOperation;
    private static final Logger logger = LoggerFactory.getLogger(CustomProjectAggregationOperation.class);
    public CustomProjectAggregationOperation(String jsonOperation) {
        this.jsonOperation = jsonOperation;
    }

    @Override
    public Document toDocument(AggregationOperationContext aggregationOperationContext) {
        return aggregationOperationContext.getMappedObject(Document.parse(jsonOperation));
    }
}
