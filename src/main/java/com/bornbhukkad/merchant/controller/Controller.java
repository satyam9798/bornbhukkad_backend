package com.bornbhukkad.merchant.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bornbhukkad.merchant.Service.KiranaServiceImpl;
import com.bornbhukkad.merchant.Service.RestaurantServiceImpl;
import com.bornbhukkad.merchant.dto.KiranaCategoriesDto;
import com.bornbhukkad.merchant.dto.KiranaCustomGroupDto;
import com.bornbhukkad.merchant.dto.KiranaDefaultCategoriesDto;
import com.bornbhukkad.merchant.dto.KiranaDto;
import com.bornbhukkad.merchant.dto.KiranaFulfillmentDto;
import com.bornbhukkad.merchant.dto.KiranaItemDto;
import com.bornbhukkad.merchant.dto.KiranaItemRequestDto;
import com.bornbhukkad.merchant.dto.KiranaLocationDto;
import com.bornbhukkad.merchant.dto.KiranaProductDto;
import com.bornbhukkad.merchant.dto.RestauranItemRequestDto;
import com.bornbhukkad.merchant.dto.RestaurantCategoriesDto;
import com.bornbhukkad.merchant.dto.RestaurantCustomGroupDto;
import com.bornbhukkad.merchant.dto.RestaurantDefaultCategoriesDto;
import com.bornbhukkad.merchant.dto.RestaurantDto;
import com.bornbhukkad.merchant.dto.RestaurantFulfillmentDto;
import com.bornbhukkad.merchant.dto.RestaurantItemDto;
import com.bornbhukkad.merchant.dto.RestaurantLocationDto;
import com.bornbhukkad.merchant.dto.RestaurantProductDto;
import com.bornbhukkad.merchant.dto.SearchBody;

@RestController
@RequestMapping("/merchants")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
public class Controller {

	private static final Logger logger = LoggerFactory.getLogger(Controller.class);

	private final RestaurantServiceImpl restaurantService;
	private final KiranaServiceImpl kiranaService;

	@Autowired
	public Controller(RestaurantServiceImpl restaurantService, KiranaServiceImpl kiranaService) {
		this.restaurantService = restaurantService;
		this.kiranaService = kiranaService;
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@PostMapping(path = "/restFulfillment")
	public ResponseEntity<Object> addRestFulfillment(@RequestBody List<RestaurantFulfillmentDto> fulfillment) {
		List<RestaurantFulfillmentDto> restFulfillmentDto = fulfillment;
		if (restFulfillmentDto != null) {
			for (RestaurantFulfillmentDto dto : restFulfillmentDto) {
				restaurantService.addRestaurantFulfillment(dto);
			}
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(fulfillment);
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@PostMapping(path = "/restaurant")
	public ResponseEntity<Object> addRestaurant(@RequestBody RestaurantDto merchant) {
		try {
			if (merchant == null || merchant.getDescriptor() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Merchant is empty");
			}
			restaurantService.addRestaurant(merchant);
			if (merchant.getId() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already Exists");
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(merchant);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occured");
		}
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@PostMapping(path = "/restaurantLocation")
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

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@PostMapping(path = "/restaurantMinOrder")
	public ResponseEntity<Object> updateRestMinOrder(@RequestParam("locationId") String locationId,
			@RequestBody SearchBody data) {
		try {
			// TODO: if condition for empty data
			restaurantService.updateMinOrder(locationId, data.getMinOrder());
			return ResponseEntity.status(HttpStatus.CREATED).body("Success");
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred");
		}
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@PostMapping(path = "/restaurantRadius")
	public ResponseEntity<Object> updateRestRadius(@RequestParam("locationId") String locationId,
			@RequestBody SearchBody data) {
		try {
			// TODO: if condition for empty data
			restaurantService.updateRadius(locationId, data.getRadius());
			return ResponseEntity.status(HttpStatus.CREATED).body("Success");
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred");
		}
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@PostMapping(path = "/restaurantCategories")
	public ResponseEntity<Object> addRestaurantCategory(@RequestBody List<RestaurantCategoriesDto> categories) {
		try {
			// TODO: if condition for empty data
			List<RestaurantCategoriesDto> restCategoriesDto = categories;
			if (restCategoriesDto != null) {
				for (RestaurantCategoriesDto dto : restCategoriesDto) {

					restaurantService.addRestaurantCategories(dto);
				}
			}

			return ResponseEntity.status(HttpStatus.CREATED).body(categories);

		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occured");
		}
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@GetMapping("/categories")
	public List<RestaurantCategoriesDto> getCategoriesByVendorId(@RequestParam("vendorId") String vendorId) {
		logger.info("search product in controller  by vendorId:" + vendorId);
		return restaurantService.getCategoriesByVendorId(vendorId);
	}

//    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Authorization", "Content-Type"})
//    @PostMapping(path="/restaurantProduct")
//    public ResponseEntity<Object> addRestaurantProduct(@RequestBody RestauranItemRequestDto RestauranItemRequestDto) {
//    	try {
//    		// TODO: if condition for empty data
//    		RestaurantProductDto restaurantProductDto= RestauranItemRequestDto.getRestaurantProductDto();
//    		List<RestaurantCustomGroupDto> restaurantCustomGroupDto = RestauranItemRequestDto.getRestaurantCustomGroup();
//    		List<RestaurantItemDto> restaurantItemDto = RestauranItemRequestDto.getRestaurantItemDto();
//        	restaurantService.addRestaurantProduct(restaurantProductDto);
//        	if(restaurantCustomGroupDto!= null) {
//        		for (RestaurantCustomGroupDto dto : restaurantCustomGroupDto) {
//        			dto.setParentProductId(restaurantProductDto.getId());
//        			restaurantService.addRestaurantCustomGroup(dto);
//        		}
////        		restaurantService.addRestaurantCustomGroup(restaurantCustomGroupDto);
//        	}
//        	if(restaurantItemDto!= null) {
//        		for (RestaurantItemDto dto : restaurantItemDto) {
//        			dto.setParentItemId(restaurantProductDto.getId());
//        			restaurantService.addRestaurantItem(dto);
//        		}
//        	}
//    		
//    		return ResponseEntity.status(HttpStatus.CREATED).body(RestauranItemRequestDto);
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occured");
//		}
//    }
//    

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@PostMapping(path = "/restaurantProduct")
	public ResponseEntity<Object> addRestaurantProduct(@RequestBody RestauranItemRequestDto restauranItemRequestDto) {
		try {
			RestaurantProductDto restaurantProductDto = restauranItemRequestDto.getRestaurantProductDto();
			List<RestaurantCustomGroupDto> restaurantCustomGroupDto = restauranItemRequestDto
					.getRestaurantCustomGroup();
			List<RestaurantItemDto> restaurantItemDto = restauranItemRequestDto.getRestaurantItemDto();

			// Add product
			RestaurantProductDto savedProduct = restaurantService.addRestaurantProduct(restaurantProductDto);

			// Add custom groups and update product tags
			if (restaurantCustomGroupDto != null && !restaurantCustomGroupDto.isEmpty()) {
				List<String> customGroupIds = new ArrayList<>();
				for (RestaurantCustomGroupDto dto : restaurantCustomGroupDto) {
					dto.setParentProductId(savedProduct.getId());
					RestaurantCustomGroupDto savedCustomGroup = restaurantService.addRestaurantCustomGroup(dto);
					customGroupIds.add(savedCustomGroup.getId());
				}
				restaurantService.updateProductCustomGroupTags(savedProduct.getId(), customGroupIds);
			}

			// Add items and update their tags
			if (restaurantItemDto != null && !restaurantItemDto.isEmpty()) {
				for (RestaurantItemDto dto : restaurantItemDto) {
					dto.setParentItemId(savedProduct.getId());
					restaurantService.addRestaurantItem(dto);
				}
			}

			return ResponseEntity.status(HttpStatus.CREATED).body(restauranItemRequestDto);
		} catch (Exception e) {
			logger.error("Error occurred while adding restaurant product", e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred: " + e.getMessage());
		}
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@GetMapping("/products")
	public List<Object> getProductByVendorId(@RequestParam("vendorId") String vendorId) {
//    	logger.info("search product in controller  by vendorId:"+vendorId);
		return restaurantService.getProductsByVendorId(vendorId);
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@GetMapping("/location")
	public List<RestaurantLocationDto> getLocationByVendorId(@RequestParam("vendorId") String vendorId) {
//    	logger.info("search product in controller  by vendorId:"+vendorId);
		return restaurantService.getLocationByVendorId(vendorId);
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@GetMapping("/vendor")
	public RestaurantDto getVendorByVendorId(@RequestParam("vendorId") String vendorId) {
//    	logger.info("search product in controller  by vendorId:"+vendorId);
		return restaurantService.getVendorById(vendorId);
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@PutMapping(path = "/restaurantProduct")
	public ResponseEntity<Object> updateRestaurantProduct(
			@RequestBody RestauranItemRequestDto RestauranItemRequestDto) {
		try {
			// TODO: if condition for empty data
			RestaurantProductDto restaurantProductDto = RestauranItemRequestDto.getRestaurantProductDto();
			List<RestaurantCustomGroupDto> restaurantCustomGroupDto = RestauranItemRequestDto
					.getRestaurantCustomGroup();
			List<RestaurantItemDto> restaurantItemDto = RestauranItemRequestDto.getRestaurantItemDto();
			logger.info("search product in controller  by vendorId:" + restaurantProductDto.getId());
			if (restaurantProductDto != null) {
				restaurantService.updateProduct(restaurantProductDto.getId(), restaurantProductDto);

				if (restaurantCustomGroupDto != null) {
					for (RestaurantCustomGroupDto dto : restaurantCustomGroupDto) {
						restaurantService.updateCustomGroup(dto.getId(), dto);
					}

				}
				if (restaurantItemDto != null) {
					for (RestaurantItemDto dto : restaurantItemDto) {
						restaurantService.updateItem(dto.getId(), dto);
					}
				}

				return ResponseEntity.status(HttpStatus.CREATED).body(RestauranItemRequestDto);
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No item found with the given ID");

		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occured");
		}
	}

	@DeleteMapping("/restProduct")
	public void deleteProduct(@RequestParam("id") String id) {
		restaurantService.deleteProduct(id);
	}

	@DeleteMapping("/restCustomGroup")
	public void deleteCustomGroup(@RequestParam("id") String id) {
		restaurantService.deleteCustomGroup(id);
	}

	@DeleteMapping("/restItem")
	public void deleteItem(@RequestParam("id") String id) {
		restaurantService.deleteItem(id);
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@GetMapping(path = "/restDefaultCategories")
	public List<RestaurantDefaultCategoriesDto> restDefaultCategories() {
		try {

			return restaurantService.getRestDefaultCategories();
		} catch (Exception e) {

			throw new BadCredentialsException("Invalid token");
		}
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@GetMapping(path = "/restFulfillments")
	public List<RestaurantFulfillmentDto> restFulfillments(@RequestParam("id") String id) {
		try {

			return restaurantService.getFulfillmentByVendorId(id);
		} catch (Exception e) {

			throw new BadCredentialsException("Invalid token");
		}
	}

	// kirana apis

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@PostMapping(path = "/kirana")
	public ResponseEntity<Object> addKirana(@RequestBody KiranaDto merchant) {
		try {
			if (merchant == null || merchant.getDescriptor() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Merchant is empty");
			}
			kiranaService.addKirana(merchant);
			return ResponseEntity.status(HttpStatus.CREATED).body(merchant);
		} catch (Exception e) {
			Map<String, String> model = new HashMap<>();
			model.put("error", "Unable to save details");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(model);
		}
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@PostMapping(path = "/kiranaLocation")
	public ResponseEntity<Object> addKiranaLocation(@RequestBody KiranaLocationDto location) {
		try {
			kiranaService.addKiranaLocation(location);
			return ResponseEntity.status(HttpStatus.CREATED).body(location);
		} catch (Exception e) {
			Map<String, String> model = new HashMap<>();
			model.put("error", "Unable to save details");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(model);
		}
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@PostMapping(path = "/kiranaProduct")
	public ResponseEntity<Object> addKiranaProduct(@RequestBody KiranaItemRequestDto kiranaItemRequestDto) {
		try {

			List<KiranaProductDto> kiranaProductDto = kiranaItemRequestDto.getKiranaProductDto();

			if (kiranaProductDto != null) {
				for (KiranaProductDto dto : kiranaProductDto) {
					kiranaService.addKiranaProduct(dto);
				}
			}

			

			// Return the created request DTO
			return ResponseEntity.status(HttpStatus.CREATED).body(kiranaItemRequestDto);

		} catch (Exception e) {
			// Handle exceptions and return an error response
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred");
		}
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@GetMapping(path = "/kirana")
	public KiranaDto getKiranaById(@RequestParam("id") String id) {
		return kiranaService.getKiranaById(id);
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@GetMapping(path = "/kiranaLocation")
	public List<KiranaLocationDto> getLocationByKiranaId(@RequestParam("kiranaId") String kiranaId) {
		return kiranaService.getLocationByKiranaId(kiranaId);
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@GetMapping(path = "/kiranaProduct")
	public List<Object> getProductsByKiranaId(@RequestParam("kiranaId") String kiranaId) {
		return kiranaService.getProductsByKiranaId(kiranaId);
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@PutMapping(path = "/kiranaProduct")
	public ResponseEntity<Object> updateKiranaProduct(@RequestBody KiranaProductDto kiranaProductDto) {
		try {
			logger.info("Updating product in controller by vendorId:" + kiranaProductDto.getId());

			// Ensure the product DTO is not null
			if (kiranaProductDto != null) {

				kiranaService.updateProduct(kiranaProductDto.getId(), kiranaProductDto);
				// Return the updated request DTO
				return ResponseEntity.status(HttpStatus.CREATED).body(kiranaProductDto);
			}

			// Return an error if no product was found with the given ID
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No item found with the given ID");

		} catch (Exception e) {
			// Handle exceptions and return an error response
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred");
		}
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@PostMapping(path = "/kiranaMinOrder")
	public ResponseEntity<Object> updateKiranaMinOrder(@RequestParam("locationId") String locationId,
			@RequestBody SearchBody data) {
		try {
			// TODO: if condition for empty data
			kiranaService.updateMinOrder(locationId, data.getMinOrder());
			return ResponseEntity.status(HttpStatus.CREATED).body("Success");
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred");
		}
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@PostMapping(path = "/kiranaRadius")
	public ResponseEntity<Object> updateKiranaRadius(@RequestParam("locationId") String locationId,
			@RequestBody SearchBody data) {
		try {
			// TODO: if condition for empty data
			kiranaService.updateRadius(locationId, data.getRadius());
			return ResponseEntity.status(HttpStatus.CREATED).body("Success");
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred");
		}
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@PostMapping(path = "/kiranaCategories")
	public ResponseEntity<Object> addKiranaCategory(@RequestBody List<KiranaCategoriesDto> categories) {
		try {
			// TODO: if condition for empty data
			if (categories != null) {
				for (KiranaCategoriesDto dto : categories) {
					kiranaService.addKiranaCategories(dto);
				}
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(categories);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred");
		}
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@PostMapping(path = "/kiranaFulfillment")
	public ResponseEntity<Object> addKiranaFulfillment(@RequestBody List<KiranaFulfillmentDto> fulfillment) {
		try {
//            kiranaService.addKiranaFulfillment(fulfillment);

			List<KiranaFulfillmentDto> kiranaFulfillmentDto = fulfillment;
			if (kiranaFulfillmentDto != null) {
				for (KiranaFulfillmentDto dto : kiranaFulfillmentDto) {
					kiranaService.addKiranaFulfillment(dto);
				}
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(fulfillment);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred");
		}
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@GetMapping(path = "/kiranaDefaultCategories")
	public List<KiranaDefaultCategoriesDto> getKiranaDefaultCategories() {
		try {
			return kiranaService.getKiranaDefaultCategories();
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid token");
		}
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@GetMapping(path = "/kiranaFulfillments")
	public List<KiranaFulfillmentDto> kiranaFulfillments(@RequestParam("id") String id) {
		try {

			return kiranaService.getFulfillmentByVendorId(id);
		} catch (Exception e) {

			throw new BadCredentialsException("Invalid token");
		}
	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = { "Authorization", "Content-Type" })
	@GetMapping("/kiranaCategories")
	public List<KiranaCategoriesDto> getCategoriesByKiranaId(@RequestParam("kiranaId") String kiranaId) {
		logger.info("search product in controller  by vendorId:" + kiranaId);
		return kiranaService.getCategoriesByKiranaId(kiranaId);
	}

	@DeleteMapping("/kiranaProduct")
	public void deleteKiranaProduct(@RequestParam("id") String id) {
		kiranaService.deleteProduct(id);
	}

	@DeleteMapping("/kiranaCustomGroup")
	public void deleteKiranaCustomGroup(@RequestParam("id") String id) {
		kiranaService.deleteCustomGroup(id);
	}

	@DeleteMapping("/kiranaItem")
	public void deleteKiranaItem(@RequestParam("id") String id) {
		kiranaService.deleteItem(id);
	}

}
