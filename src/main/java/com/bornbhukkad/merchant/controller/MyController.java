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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bornbhukkad.merchant.Service.MerchantServiceImpl;
import com.bornbhukkad.merchant.dto.LocationDto;
import com.bornbhukkad.merchant.dto.MerchantDto;





@RestController
@RequestMapping("/merchants")
public class MyController {
	
	
	private final MerchantServiceImpl merchantService;

    
    

    @Autowired
    public MyController( MerchantServiceImpl merchantService ){
        this.merchantService = merchantService;

    }

    @PostMapping
    public MerchantDto add(@RequestBody MerchantDto merchant) {
    	merchantService.addVendor(merchant);
    	return merchant;
    	
    }
    
    @PostMapping(path="/location")
    public LocationDto addVendorLocation(@RequestBody LocationDto location) {
    	merchantService.addVendorLocation(location);
    	return location;
    }
  

 
}
