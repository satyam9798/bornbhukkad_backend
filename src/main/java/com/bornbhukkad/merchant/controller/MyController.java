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
import com.bornbhukkad.merchant.dto.RestaurantDto;
import com.bornbhukkad.merchant.dto.RestaurantLocationDto;





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
    public KiranaDto addKirana(@RequestBody KiranaDto merchant) {
    	kiranaService.addKirana(merchant);
        return merchant;
//        	if(merchant.getVendorId() != null) {
//		}
//    	else {
//			Map<Object, Object> model = new HashMap<>();
//			model.put("error", "Name already exist");
//			throw new UsernameNotFoundException("Same vendor details already present.");
//			return;
//		} 			
    }
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @PostMapping(path="/restaurant")
    public RestaurantDto addRestaurant(@RequestBody RestaurantDto merchant) {
    	restaurantService.addRestaurant(merchant);
        return merchant;			
    }

    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @PostMapping(path="/kiranaLocation")
    public KiranaLocationDto addKiranaLocation(@RequestBody KiranaLocationDto location) {
    	kiranaService.addKiranaLocation(location);
    	return location;
    }
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @PostMapping(path="/restaurantLocation")
    public RestaurantLocationDto addRestaurantLocation(@RequestBody RestaurantLocationDto location) {
    	restaurantService.addRestaurantLocation(location);
    	return location;
    }
}
