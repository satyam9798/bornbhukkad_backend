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


import com.bornbhukkad.merchant.Repository.IRoleRepository;
import com.bornbhukkad.merchant.Repository.IUserRepository;
import com.bornbhukkad.merchant.dto.Role;
import com.bornbhukkad.merchant.dto.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	
	
	
	@Autowired 
	IUserRepository userRepository;
	
	@Autowired 
	IRoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	
	//Method to search for a user by mail
	public User findUserByEmail(String email) {
	    return userRepository.findByEmail(email);
	}
	
	//Method to save a user.
	public void saveUser(User user) {
	    user.setPassword (bCryptPasswordEncoder.encode(user.getPassword()));
	    user.setEnabled(true);
	    Role userRole = roleRepository.findByRole("ADMIN");
	    user.setRoles(new HashSet<>(Arrays.asList(userRole)));
	    userRepository.save(user);
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
		User user = userRepository.findByEmail(email);
	    if(user != null) {
	        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
	        return buildUserForAuthentication(user, authorities);
	    } else {
	        throw new UsernameNotFoundException("username not found");
	    }
	}
	
	//Method to authenticate users
	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
	    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}
	
}
