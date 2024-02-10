package com.bornbhukkad.merchant.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.bornbhukkad.merchant.dto.RestaurantCategoriesDto;
import com.bornbhukkad.merchant.dto.RestaurantDto;
import com.bornbhukkad.merchant.dto.RestaurantLocationDto;
import com.bornbhukkad.merchant.dto.RestaurantProductDto;





@RestController
@RequestMapping("/merchants")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
public class MyController {
	
	
	private final RestaurantServiceImpl restaurantService;
	private final KiranaServiceImpl kiranaService;
    @Autowired
    public MyController( RestaurantServiceImpl restaurantService, KiranaServiceImpl kiranaService ){
        this.restaurantService = restaurantService;
        this.kiranaService = kiranaService;
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
    @PostMapping(path="/restaurantProduct")
    public ResponseEntity<Object> addRestaurantProduct(@RequestBody RestaurantProductDto product) {
    	try {
    		// TODO: if condition for empty data
    		restaurantService.addRestaurantProduct(product);
    		return ResponseEntity.status(HttpStatus.CREATED).body(product);
			
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occured");
		}
    }
}
