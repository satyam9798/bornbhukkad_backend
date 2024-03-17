package com.bornbhukkad.merchant;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bornbhukkad.merchant.Repository.IRoleRepository;
import com.bornbhukkad.merchant.dto.Role;


@SpringBootApplication
public class BornbhukkadApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(BornbhukkadApplication.class, args);
	}
	
	
	
	@Bean
	CommandLineRunner init(IRoleRepository roleRepository) {

	    return args -> {

	        Role adminRole = roleRepository.findByRole("ADMIN");
	        if (adminRole == null) {
	            Role newAdminRole = new Role();
	            newAdminRole.setRole("ADMIN");
	            roleRepository.save(newAdminRole);
	        }
	    };

	}

}
