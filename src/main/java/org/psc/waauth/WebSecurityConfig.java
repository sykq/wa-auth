package org.psc.waauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	/* allowing access by username -> @EnableGlobalMethodSecurity
	@PreAuthorize("#n == authentication.name")
	Contact findContactByName(@Param("n") String name);
	*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
	    http
	    	.authorizeRequests()
               	.antMatchers("/", "/home", "/create-account").permitAll()
               	.antMatchers("/home-sec").hasRole("ADMIN")
               	.and()
           	.formLogin()
               	.loginPage("/login")
               	.permitAll()
               	.and()
           	.logout()
           		.permitAll();
		//@formatter:on
	}

	/*
	 * @Override protected void configure(AuthenticationManagerBuilder builder)
	 * throws Exception { builder.userDetailsService(new
	 * WaAuthUserDetailsService()); }
	 */
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER")
				.build();
		UserDetails admin = User.withDefaultPasswordEncoder().username("admin").password("admin").roles("ADMIN")
				.build();

		return new InMemoryUserDetailsManager(user, admin);
	}
}