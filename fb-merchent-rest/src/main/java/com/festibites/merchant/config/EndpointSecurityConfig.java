package com.festibites.merchant.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.festibites.merchant.filters.JwtRequestFilter;
import com.festibites.merchant.service.UserService;

@Configuration
@EnableWebSecurity
public class EndpointSecurityConfig extends WebSecurityConfigurerAdapter {

 
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            // Disable CSRF protection for simplicity in this example
//            .csrf().disable()
//            
//            // Configure the requests
//            .authorizeRequests()
//                // Permit all requests to the login endpoint
//                .antMatchers("/**").permitAll()
//            // Any other request must be authenticated
//            .anyRequest().authenticated();
//    }
	
	@Autowired
	UserService userService;
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests().antMatchers("/api/authenticate").permitAll()
			.anyRequest().authenticated()
			.and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}


	
}
