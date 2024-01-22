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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.bornbhukkad.merchant.Configuration.JwtTokenProvider;
import com.bornbhukkad.merchant.Repository.IUserRepository;
import com.bornbhukkad.merchant.Service.CustomUserDetailsService;


import com.bornbhukkad.merchant.Service.bbdataService;
import com.bornbhukkad.merchant.dto.User;




@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;	

    @Autowired
    IUserRepository users;

    @Autowired
    private CustomUserDetailsService userService;
    @Autowired
    private bbdataService bbService;
    
    //test
    @GetMapping(path="/test")
    public List<Object> greetings(@RequestBody SearchBody data) {
    	try {
			
    		return bbService.getFulfillmentChannels(data.getVendorId(),data.getCity());
		} catch (Exception e) {
			
			throw new BadCredentialsException("Invalid token");
		}
    }
    @GetMapping(path="/test1")
    public List<Object> test2() {
    	return bbdataService.runCustomQuery();

    }


    @SuppressWarnings("rawtypes")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthBody data) {
        try {
            String email = data.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, data.getPassword()));
            String token = jwtTokenProvider.createToken(email, this.users.findByEmail(email).getRoles());
            Map<Object, Object> model = new HashMap<>();
            model.put("email", email);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
        	Map<String,String> model = new HashMap<>();
        	model.put("error", "Invalid Credentials");
        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(model);
//            throw new BadCredentialsException("Invalid email/password supplied");
        }
    }
  

    @SuppressWarnings("rawtypes")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user) {
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
//            throw new BadCredentialsException("User with username: " + user.getEmail() + " already exists");
        	Map<String,String> model = new HashMap<>();
        	model.put("error", "User : " + user.getEmail() + " already exists, Please Login");
        	return ResponseEntity.status(HttpStatus.OK).body(model);
        }
        userService.saveUser(user);
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User registered successfully");
        return ok(model);
    }
}
