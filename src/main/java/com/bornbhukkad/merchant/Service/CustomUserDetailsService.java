package com.bornbhukkad.merchant.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bornbhukkad.merchant.Repository.IKiranaUserRepository;

import com.bornbhukkad.merchant.Repository.IRoleRepository;
import com.bornbhukkad.merchant.Repository.IUserRepository;
import com.bornbhukkad.merchant.dto.KiranaUser;
import com.bornbhukkad.merchant.dto.RestaurantUser;
import com.bornbhukkad.merchant.dto.Role;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	
	
	
	@Autowired 
	IUserRepository restaurantUserRepository;
	
	@Autowired 
	IKiranaUserRepository kiranaUserRepository;
	
	
	@Autowired 
	IRoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	

	//Method to search for a user by mail
	public KiranaUser findKiranaUserByEmail(String email) {
	    return kiranaUserRepository.findByEmail(email);
	}
	//Method to search for a user by mail
	public RestaurantUser findRestaurantUserByEmail(String email) {
	    return restaurantUserRepository.findByEmail(email);
	}
	

	
	//Method to save a kirana user.
		public void saveKiranaUser(KiranaUser user) {
		    user.setPassword (bCryptPasswordEncoder.encode(user.getPassword()));
		    user.setEnabled(true);
		    Role userRole = roleRepository.findByRole("ADMIN");
		    user.setRoles(new HashSet<>(Arrays.asList(userRole)));
		    kiranaUserRepository.save(user);
		}
		
		//Method to save a kirana user.
				public void saveRestaurantUser(RestaurantUser user) {
				    user.setPassword (bCryptPasswordEncoder.encode(user.getPassword()));
				    user.setEnabled(true);
				    Role userRole = roleRepository.findByRole("ADMIN");
				    user.setRoles(new HashSet<>(Arrays.asList(userRole)));
				    restaurantUserRepository.save(user);
				}
	
	//method that returns the list of roles that a user has.
	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
	    Set<GrantedAuthority> roles = new HashSet<>();
	    userRoles.forEach((role) -> {
	        roles.add(new SimpleGrantedAuthority(role.getRole()));
	    });

	    List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
	    return grantedAuthorities;
	}
	
	//Method to find a user by mail
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		KiranaUser kiranaUser = kiranaUserRepository.findByEmail(email);
		RestaurantUser restaurantUser = restaurantUserRepository.findByEmail(email);
		if(kiranaUser != null || restaurantUser != null) {
			if(kiranaUser != null) {
				List<GrantedAuthority> authorities = getUserAuthority(kiranaUser.getRoles());
		        return buildKiranaUserForAuthentication(kiranaUser, authorities);
			}else {
				List<GrantedAuthority> authorities = getUserAuthority(restaurantUser.getRoles());
		        return buildRestaurantUserForAuthentication(restaurantUser, authorities);
			}
	    } else {
	        throw new UsernameNotFoundException("username not found");
	    }
//	    if(user != null) {
//	        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
//	        return buildUserForAuthentication(user, authorities);
//	    } else {
//	        throw new UsernameNotFoundException("username not found");
//	    }
	}

//	
//	//Method to authenticate users
//	private UserDetails buildUserForAuthentication(RestaurantUser user, List<GrantedAuthority> authorities) {
//	    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
//	}
	//Method to authenticate users
		private UserDetails buildKiranaUserForAuthentication(KiranaUser user, List<GrantedAuthority> authorities) {
		    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
		}
		//Method to authenticate users
		private UserDetails buildRestaurantUserForAuthentication(RestaurantUser user, List<GrantedAuthority> authorities) {
		    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
		}
	
}
