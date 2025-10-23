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
import java.util.function.Supplier;

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
import com.bornbhukkad.merchant.Configuration.UserAlreadyExistsException;
import com.bornbhukkad.merchant.Repository.IKiranaUserRepository;

import com.bornbhukkad.merchant.Repository.IUserRepository;
import com.bornbhukkad.merchant.Service.CustomUserDetailsService;


import com.bornbhukkad.merchant.Service.bbdataService;
import com.bornbhukkad.merchant.dto.AuthBody;
import com.bornbhukkad.merchant.dto.KiranaUser;
import com.bornbhukkad.merchant.dto.RestaurantUser;
import com.bornbhukkad.merchant.dto.SearchBody;





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
			
    		 bbService.getFulfillmentChannelsByGeoLocation(data.getLatitude(),data.getLongitude(), data.getMaxDistance());
    		return ResponseEntity.status(HttpStatus.CREATED).body(bbService.getFulfillmentChannelsByGeoLocation(data.getLatitude(),data.getLongitude(), data.getMaxDistance()));
    	} catch (Exception e) {
			Map<Object, Object> model = new HashMap<>();
			model.put("error", e);
        	return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(model);
		}
    }
    
    @GetMapping(path="/getByItemAndCity")
    public List<Object> getByItemAndCity(@RequestBody SearchBody data) {
//    	return bbService.getFulfillmentChannels(data.getItem(),data.getCity());
//    	return bbService.getByCity(data.getCity(), data.getCategory());
//    	return bbService.onSelectQuery();
    	return bbdataService.getByItemAndCity(data.getItem(),data.getCity());
    }
    
    @GetMapping(path="/getByItem")
    public List<Object> getByItem(@RequestBody SearchBody data) {
    	return bbService.getByItem(data.getItem(),data.getCity(),data.getLatitude(),data.getLongitude(), data.getMaxDistance(), data.getType(), data.getAreaCode());
    }
    
    @GetMapping(path="/getByCity")
    public List<Object> getByCity(@RequestBody SearchBody data) {
    	return bbService.getByCity(data.getCity(), data.getCategory(), data.getType());
    }
    
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthBody data) {
        try {
            String email = data.getEmail();
            String merchantType = data.getMerchantType();
            Map<String, String> model = new HashMap<>();
            
            if ("kirana".equals(merchantType) && kiranaUsers.findByEmail(email) != null) {
                authenticateUser(email, data.getPassword());
                String token = jwtTokenProvider.createToken(email, kiranaUsers.findByEmail(email).getRoles());
                String merchantId = kiranaUsers.findByEmail(email).getMerchantId();
                model.put("email", email);
                model.put("merchantId", merchantId);
                model.put("token", token);
                model.put("merchantType", "kirana");
                return ResponseEntity.ok(model);

            } else if ("restaurant".equals(merchantType) && restaurantUsers.findByEmail(email) != null) {
                authenticateUser(email, data.getPassword());
                String token = jwtTokenProvider.createToken(email, restaurantUsers.findByEmail(email).getRoles());
                String merchantId = restaurantUsers.findByEmail(email).getMerchantId();
                model.put("email", email);
                model.put("token", token);
                model.put("merchantId", merchantId);
                model.put("merchantType", "restaurant");
                return ResponseEntity.ok(model);

            } else {
                model.put("error", "Invalid Credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(model);
            }
        } catch (AuthenticationException e) {
            Map<String, String> model = new HashMap<>();
            model.put("error", "Invalid Credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(model);
        }
    }

    private void authenticateUser(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @PostMapping("/registerKirana")
    public ResponseEntity<Map<String, String>> registerKirana(@RequestBody KiranaUser user) {
    	try {
        return registerUser(
            () -> userService.findKiranaUserByEmail(user.getEmail()),
            () -> userService.saveKiranaUser(user),
            user.getEmail()
        );
    	}
        catch (UserAlreadyExistsException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "User with this email already exists.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
    @PostMapping("/registerRestaurant")
    public ResponseEntity<Map<String, String>> registerRestaurant(@RequestBody RestaurantUser user) {
    	try {
    	return registerUser(
    		    () -> userService.findRestaurantUserByEmail(user.getEmail()),
    		    () -> userService.saveRestaurantUser(user),
    		    user.getEmail()
    		);
    	}catch (UserAlreadyExistsException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "User with this email already exists.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }

    private ResponseEntity<Map<String, String>> registerUser(Supplier<?> findUser, Runnable saveUser, String email) {
//        try {
            if (findUser.get() != null) {
//                Map<String, String> model = new HashMap<>();
//                model.put("error", "User: " + email + " already exists, Please Login");
//                return ResponseEntity.status(HttpStatus.OK).body(model);
                throw new UserAlreadyExistsException("User already exists with email: " + email);

            }
            saveUser.run();
            Map<String, String> model = new HashMap<>();
            model.put("message", "User registered successfully");
            return ResponseEntity.status(HttpStatus.OK).body(model);
//        } catch (Exception e) {
//            Map<String, String> model = new HashMap<>();
//            model.put("error", "An error occurred while processing your request.");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(model);
//        }
    }
}
