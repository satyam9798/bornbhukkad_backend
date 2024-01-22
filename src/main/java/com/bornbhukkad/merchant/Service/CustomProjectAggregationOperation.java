package com.bornbhukkad.merchant.Service;

import org.apache.commons.logging.Log;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
//import org.springframework.data.mongodb.core.mapping.Document;

import org.bson.Document;



public class CustomProjectAggregationOperation implements AggregationOperation {
    private String jsonOperation;

    public CustomProjectAggregationOperation(String jsonOperation) {
        this.jsonOperation = jsonOperation;
    }

    @Override
    public Document toDocument(AggregationOperationContext aggregationOperationContext) {
        return aggregationOperationContext.getMappedObject(Document.parse(jsonOperation));
    }
}
