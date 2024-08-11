package com.bornbhukkad.merchant.Service;

import java.util.List;

import com.bornbhukkad.merchant.dto.KiranaCategoriesDto;
import com.bornbhukkad.merchant.dto.KiranaCustomGroupDto;
import com.bornbhukkad.merchant.dto.KiranaDefaultCategoriesDto;
import com.bornbhukkad.merchant.dto.KiranaDto;
import com.bornbhukkad.merchant.dto.KiranaFulfillmentDto;
import com.bornbhukkad.merchant.dto.KiranaItemDto;
import com.bornbhukkad.merchant.dto.KiranaLocationDto;
import com.bornbhukkad.merchant.dto.KiranaProductDto;

public interface KiranaService {
	
	
	
	void addKirana(KiranaDto kirana);

    void addKiranaLocation(KiranaLocationDto location);
    
    public void addKiranaProduct(KiranaProductDto product);
    
    public void addKiranaCustomGroup(KiranaCustomGroupDto kiranaCustomGroupDto);
    
    public void addKiranaItem(KiranaItemDto item);
    
    public void addKiranaFulfillment(KiranaFulfillmentDto fulfillment);
    
    public List<KiranaDefaultCategoriesDto> getKiranaDefaultCategories();
    
    public boolean checkName(String name) ;
    
    public List<KiranaCategoriesDto> getCategoriesByKiranaId(String kiranaId);
    
    public List<KiranaLocationDto> getLocationByKiranaId(String kiranaId);
    
    public KiranaDto getKiranaById(String kiranaId) ;
    
    public List<Object> getProductsByKiranaId(String kiranaId) ;
    
   
    
    public void addKiranaCategories(KiranaCategoriesDto categories);

   

    public KiranaProductDto updateProduct(String id, KiranaProductDto newProduct);
    
    public KiranaCustomGroupDto updateCustomGroup(String id, KiranaCustomGroupDto newCustomGroup);
    
    public KiranaItemDto updateItem(String id, KiranaItemDto newItem);
    
    public void deleteProduct(String id) ;
    
    public void deleteCustomGroup(String id) ;
    
    public void deleteItem(String id) ;

    List<KiranaDto> getAll();

	KiranaLocationDto updateMinOrder(String id, String minOrder);

	KiranaLocationDto updateRadius(String id, String radius);

}
