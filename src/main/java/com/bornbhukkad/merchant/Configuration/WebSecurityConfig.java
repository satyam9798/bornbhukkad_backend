
package com.bornbhukkad.merchant.Configuration;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.Arrays;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.bornbhukkad.merchant.Service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	// Method that configures the Authentications controller constructor.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		UserDetailsService userDetailsService = mongoUserDetails();
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());

	}

	// Method to configure Spring Security HTTP security
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable().csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().cors() // Enable CORS configuration
				.and().authorizeRequests().antMatchers("/chat").permitAll()
				.antMatchers("/api/auth/getByLocation")
				.permitAll().antMatchers("/api/auth/login").permitAll().antMatchers("/api/auth/register").permitAll()
				.antMatchers("/api/auth/registerKirana").permitAll().antMatchers("/api/auth/registerRestaurant")
				.permitAll().antMatchers("/merchants/restaurant").permitAll()
				.antMatchers("/merchants/restaurantLocation").permitAll().antMatchers("/merchants/restFulfillment")
				.permitAll().antMatchers("/merchants/restaurantProductTest").permitAll()
				.antMatchers("/merchants/restaurantProduct").permitAll().antMatchers("/merchants/restaurantCategories")
				.permitAll().antMatchers("/merchants/restDefaultCategories").permitAll()
				.antMatchers("/merchants/restProduct").permitAll().antMatchers("/merchants/restCustomGroup").permitAll()
				.antMatchers("/merchants/restItem").permitAll().antMatchers("/products").permitAll()
				.antMatchers("/merchants/products").permitAll().antMatchers("/merchants/categories").permitAll()
				.antMatchers("/merchants/restFulfillments").permitAll()
				.antMatchers("/merchants/offer").permitAll().antMatchers("/merchants/restaurantOffer").permitAll()
				// kirana
				.antMatchers("/merchants/kirana").permitAll().antMatchers("/merchants/kiranaLocation").permitAll()
				.antMatchers("/merchants/kiranaFulfillment").permitAll().antMatchers("/merchants/kiranaProductTest")
				.permitAll().antMatchers("/merchants/kiranaProduct").permitAll()
				.antMatchers("/merchants/kiranaCategories").permitAll()
				.antMatchers("/merchants/kiranaDefaultCategories").permitAll().antMatchers("/merchants/kiranaProduct")
				.permitAll().antMatchers("/merchants/kiranaCustomGroup").permitAll()
				.antMatchers("/merchants/kiranaItem").permitAll().antMatchers("/kiranaProduct").permitAll()
				.antMatchers("/merchants/kiranaProduct").permitAll().antMatchers("/merchants/kiranaCreds").permitAll().antMatchers("/merchants/kiranaCategories")
				.permitAll().antMatchers("/merchants/kiranaFulfillments")
//				.permitAll().antMatchers("/api/upload")
				.permitAll().antMatchers("/api/images").permitAll().antMatchers("/merchants/vendor").permitAll()
				.antMatchers("/merchants/location").permitAll().antMatchers("/merchants/kirana").permitAll()
				.antMatchers("/merchants/kiranaLocation").permitAll().antMatchers("/merchants/**").hasAuthority("ADMIN")
				.anyRequest().authenticated().and().csrf().disable().exceptionHandling()
				.authenticationEntryPoint(unauthorizedEntryPoint()).and().apply(new JwtConfigurer(jwtTokenProvider));
	}
    
	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationEntryPoint unauthorizedEntryPoint() {
		return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				"Unauthorized");
	}

	@Bean
	public UserDetailsService mongoUserDetails() {
		return new CustomUserDetailsService();
	}

}
