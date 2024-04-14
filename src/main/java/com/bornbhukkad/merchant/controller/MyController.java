package com.bornbhukkad.merchant.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators.Add;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.util.ThrowableCauseExtractor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bornbhukkad.merchant.Service.KiranaServiceImpl;
import com.bornbhukkad.merchant.Service.RestaurantServiceImpl;
import com.bornbhukkad.merchant.dto.KiranaDto;
import com.bornbhukkad.merchant.dto.KiranaLocationDto;
import com.bornbhukkad.merchant.dto.RestauranItemRequestDto;
import com.bornbhukkad.merchant.dto.RestaurantCategoriesDto;
import com.bornbhukkad.merchant.dto.RestaurantCustomGroupDto;
import com.bornbhukkad.merchant.dto.RestaurantDefaultCategoriesDto;
import com.bornbhukkad.merchant.dto.RestaurantDto;
import com.bornbhukkad.merchant.dto.RestaurantFulfillmentDto;
import com.bornbhukkad.merchant.dto.RestaurantItemDto;
import com.bornbhukkad.merchant.dto.RestaurantLocationDto;
import com.bornbhukkad.merchant.dto.RestaurantProductDto;





@RestController
@RequestMapping("/merchants")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
public class MyController {
	
	private static final Logger logger = LoggerFactory.getLogger(MyController.class);
	
	
	private final RestaurantServiceImpl restaurantService;
	private final KiranaServiceImpl kiranaService;
    @Autowired
    public MyController( RestaurantServiceImpl restaurantService, KiranaServiceImpl kiranaService ){
        this.restaurantService = restaurantService;
        this.kiranaService = kiranaService;
    }
    
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @PostMapping(path="/restFulfillment")
    public ResponseEntity<Object> addRestFulfillment(@RequestBody RestaurantFulfillmentDto fulfillment){
    	restaurantService.addRestaurantFulfillment(fulfillment);
    	return ResponseEntity.status(HttpStatus.CREATED).body(fulfillment);
    }
    
    
    
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @PostMapping(path="/kirana")
    public ResponseEntity<Object> addKirana(@RequestBody KiranaDto merchant) {
    	try {
    		
    		if (merchant == null || merchant.getDescriptor()==null) {
    			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Merchant is empty");
    		}
    		
    		kiranaService.addKirana(merchant);
    		return ResponseEntity.status(HttpStatus.CREATED).body(merchant);
			
		} catch (Exception e) {
			
			Map<String,String> model = new HashMap<>();
        	model.put("error", "Unable to save details");
        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(model);
		}
    }
    
    
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @PostMapping(path = "/restaurant")
    public ResponseEntity<Object> addRestaurant(@RequestBody RestaurantDto merchant) {
    	try {
    		if (merchant == null || merchant.getDescriptor()==null) {
    			// Customize the response for an empty merchant
    			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Merchant is empty");
    		}
    		restaurantService.addRestaurant(merchant);
    		if(merchant.getId()==null) {
    			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already Exists");
    		}
    		return ResponseEntity.status(HttpStatus.CREATED).body(merchant);
			
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occured");
		}
    }

    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @PostMapping(path="/kiranaLocation")
    public ResponseEntity<Object> addKiranaLocation(@RequestBody KiranaLocationDto location) {
    	try {
    		// TODO: if condition for empty data
    		kiranaService.addKiranaLocation(location);
    		return ResponseEntity.status(HttpStatus.CREATED).body(location);
			
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occured");
		}
    }
    
    
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @PostMapping(path="/restaurantLocation")
    public ResponseEntity<Object> addRestaurantLocation(@RequestBody RestaurantLocationDto location) {
    	try {
    		// TODO: if condition for empty data
    		restaurantService.addRestaurantLocation(location);
    		return ResponseEntity.status(HttpStatus.CREATED).body(location);
			
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occured");
		}
    }
    
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @PostMapping(path="/restaurantCategories")
    public ResponseEntity<Object> addRestaurantCategory(@RequestBody RestaurantCategoriesDto categories) {
    	try {
    		// TODO: if condition for empty data
    		restaurantService.addRestaurantCategories(categories);
    		return ResponseEntity.status(HttpStatus.CREATED).body(categories);
			
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occured");
		}
    }
    
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @PostMapping(path="/restaurantProductTest")
    public ResponseEntity<Object> addRestaurantProduct(@RequestBody RestauranItemRequestDto RestauranItemRequestDto) {
    	try {
    		// TODO: if condition for empty data
    		RestaurantProductDto restaurantProductDto= RestauranItemRequestDto.getRestaurantProductDto();
    		List<RestaurantCustomGroupDto> restaurantCustomGroupDto = RestauranItemRequestDto.getRestaurantCustomGroup();
    		List<RestaurantItemDto> restaurantItemDto = RestauranItemRequestDto.getRestaurantItemDto();
        	restaurantService.addRestaurantProduct(restaurantProductDto);
        	if(restaurantCustomGroupDto!= null) {
        		for (RestaurantCustomGroupDto dto : restaurantCustomGroupDto) {
        			dto.setParentProductId(restaurantProductDto.getId());
        			restaurantService.addRestaurantCustomGroup(dto);
        		}
//        		restaurantService.addRestaurantCustomGroup(restaurantCustomGroupDto);
        	}
        	if(restaurantItemDto!= null) {
        		for (RestaurantItemDto dto : restaurantItemDto) {
        		restaurantService.addRestaurantItem(dto);
        		}
        	}
    		
    		return ResponseEntity.status(HttpStatus.CREATED).body(RestauranItemRequestDto);
			
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occured");
		}
    }
    
    
    
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @GetMapping("/products")
    public List<RestaurantProductDto> getProductByVendorId(@RequestBody SearchBody data) {
//    	logger.info("search product in controller  by vendorId:"+vendorId);
        return restaurantService.getProductsByVendorId(data.getVendorId());
    }
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @PutMapping("/products")
    public RestaurantProductDto updateItem(@RequestBody RestaurantProductDto product) {
        return restaurantService.updateProduct(product.getId(), product);
    }
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @DeleteMapping("/products")
    public void deleteItem(@RequestBody SearchBody data) {
    	restaurantService.deleteProduct(data.getId());
    }
    
    
    
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @GetMapping(path="/restDefaultCategories")
    public List <RestaurantDefaultCategoriesDto> greetings() {
    	try {
			
    		return restaurantService.getRestDefaultCategories();
		} catch (Exception e) {
			
			throw new BadCredentialsException("Invalid token");
		}
    }
}
