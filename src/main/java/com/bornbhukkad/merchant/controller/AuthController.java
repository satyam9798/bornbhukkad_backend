package com.bornbhukkad.merchant.controller;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.springframework.http.ResponseEntity.ok;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.bornbhukkad.merchant.Configuration.JwtTokenProvider;
import com.bornbhukkad.merchant.Repository.IKiranaUserRepository;

import com.bornbhukkad.merchant.Repository.IUserRepository;
import com.bornbhukkad.merchant.Service.CustomUserDetailsService;


import com.bornbhukkad.merchant.Service.bbdataService;
import com.bornbhukkad.merchant.dto.KiranaUser;
import com.bornbhukkad.merchant.dto.RestaurantUser;





//@CrossOrigin(origins="https://localhost:4200")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;	

    @Autowired
    IUserRepository restaurantUsers;
    
    @Autowired
    IKiranaUserRepository kiranaUsers;

    @Autowired
    private CustomUserDetailsService userService;
    @Autowired
    private bbdataService bbService;
    
    //test
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @SuppressWarnings("rawtypes")
    @GetMapping(path="/getByLocation")
    public ResponseEntity<Object> greetings(@RequestBody SearchBody data) {
    	try {
			
//    		 bbService.getFulfillmentChannelsByGeoLocation(data.getLatitude(),data.getLongitude(), data.getMaxDistance());
    		return ResponseEntity.status(HttpStatus.CREATED).body(bbService.getFulfillmentChannelsByGeoLocation(data.getLatitude(),data.getLongitude(), data.getMaxDistance()));
    	} catch (Exception e) {
			Map<Object, Object> model = new HashMap<>();
			model.put("error", e);
        	return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(model);
        	
//			return null;
		}
    }
    @GetMapping(path="/getByItemAndCity")
    public List<Object> getByItemAndCity(@RequestBody SearchBody data) {
    	return bbService.getFulfillmentChannels(data.getItem(),data.getCity());
//    	return bbService.getByCity(data.getCity());

    }
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @SuppressWarnings("rawtypes")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthBody data) {
        try {
        	String email = data.getEmail();
        	logger.info("emailKirana"+this.kiranaUsers.findByEmail(email));
        	logger.info("emailKirana"+this.restaurantUsers.findByEmail(email));
        	logger.info("This is an info message");
        	Map<Object, Object> model = new HashMap<>();
        	if ((data.getMerchantType()).equals("kirana") && this.kiranaUsers.findByEmail(email)!=null) {
        		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, data.getPassword()));
        		String token = jwtTokenProvider.createToken(email, this.kiranaUsers.findByEmail(email).getRoles());
        		String merchantId= this.kiranaUsers.findByEmail(email).getMerchantId();
        		model.put("email", email);
        		model.put("merchantId",merchantId);
        		model.put("token", token);
        		model.put("merchantType", "kirana");
        		return ok(model);
				
			} else if((data.getMerchantType()).equals("restaurant")&& this.restaurantUsers.findByEmail(email)!=null){
        		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, data.getPassword()));
				String token = jwtTokenProvider.createToken(email, this.restaurantUsers.findByEmail(email).getRoles());
				String merchantId= this.restaurantUsers.findByEmail(email).getMerchantId();
        		model.put("email", email);
        		model.put("token", token);
        		model.put("merchantId",merchantId);
        		model.put("merchantType", "restaurant");
        		return ok(model);
			} else {
				model.put("error", "Invalid Credentials");
	        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(model);
			}
        } catch (AuthenticationException e) {
        	Map<String,String> model = new HashMap<>();
        	model.put("error", "Invalid Credentials");
        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(model);
//            throw new BadCredentialsException("Invalid email/password supplied");
        }
    }
  
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @SuppressWarnings("rawtypes")
    @PostMapping("/registerKirana")
    public ResponseEntity register(@RequestBody KiranaUser user) {
    	try {
			
    		KiranaUser userExists = userService.findKiranaUserByEmail(user.getEmail());
    		if (userExists != null) {
//            throw new BadCredentialsException("User with username: " + user.getEmail() + " already exists");
    			Map<String,String> model = new HashMap<>();
    			model.put("error", "User : " + user.getEmail() + " already exists, Please Login");
    			return ResponseEntity.status(HttpStatus.OK).body(model);
    		}
    		userService.saveKiranaUser(user);
    		Map<Object, Object> model = new HashMap<>();
    		model.put("message", "User registered successfully");
    		return ok(model);
		} catch (Exception e) {
			// TODO: handle exception
			Map<String,String> model = new HashMap<>();
        	model.put("error", "Error occured");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(model);
		}
    }
    
    
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @SuppressWarnings("rawtypes")
    @PostMapping("/registerRestaurant")
    public ResponseEntity registerRestaurant(@RequestBody RestaurantUser user) {
    	try {
			
    		RestaurantUser userExists = userService.findRestaurantUserByEmail(user.getEmail());
    		if (userExists != null) {
//            throw new BadCredentialsException("User with username: " + user.getEmail() + " already exists");
    			Map<String,String> model = new HashMap<>();
    			model.put("error", "User : " + user.getEmail() + " already exists, Please Login");
    			return ResponseEntity.status(HttpStatus.OK).body(model);
    		}
    		userService.saveRestaurantUser(user);
    		Map<Object, Object> model = new HashMap<>();
    		model.put("message", "User registered successfully");
    		return ok(model);
		} catch (Exception e) {
			// TODO: handle exception
			Map<String,String> model = new HashMap<>();
        	model.put("error", "Invalid Credentials");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(model);
		}
    }
}
