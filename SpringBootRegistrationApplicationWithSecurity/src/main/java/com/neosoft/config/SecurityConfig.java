package com.neosoft.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final static  String baseUrl="/employee";

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder passEncoder;

	@Autowired
	InvalidUserAuthEntryPoint authenticationEntryPoint;

	@Autowired
	private SecurityFilter securityFilter;

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(passEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests()
				.antMatchers(baseUrl+"/save",baseUrl+"/login").permitAll()
				.antMatchers(baseUrl+"/allEmployee",baseUrl+"/update",baseUrl+"/getOneEmployee/{empId}",
						baseUrl+"/delete/{empId}",baseUrl+"//getByFirstName/{firstName}",baseUrl+"delete/{empId}",
						baseUrl+"/getBySurName/{surName}",baseUrl+"/getByFirstName/{firstName}",
						baseUrl+"/getByPincode/{pincode}",baseUrl+"/sortByDob/{asc}",baseUrl+"/sortByDob/{desc}").hasAnyAuthority("ADMIN")
				.antMatchers(baseUrl+"/update",baseUrl+"/getOneEmployee/{empId}").hasAuthority("EMPLOYEE")
				.anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
