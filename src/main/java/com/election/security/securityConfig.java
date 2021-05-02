package com.election.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class securityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	

	public BCryptPasswordEncoder passEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    
    public DaoAuthenticationProvider authProvider() {
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    	provider.setUserDetailsService(userDetailsService);
    	provider.setPasswordEncoder(passEncoder());
    	return provider;
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(authProvider());
    }
    
    
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
    	    .authorizeRequests()
    	    .antMatchers("/",
    	    		      "/home",
    	    		      "/about",
    	    		      "/Login",
    	    		      "/Register",
    	    		      "/css/**",
    	    		      "/js/**",
    	    		      "/images/**",
    	    		      "/fonts/**",
    	    		      "/media/**"
    	    		      
    	    		      )
    	    .permitAll()
    	    .anyRequest()
    	    .authenticated();
    	
    	//login configuration
    	http.formLogin()
     	     .loginPage("/Login")
     	     .failureUrl("/Login?error=true")
    	     .defaultSuccessUrl("/index")
    	     .permitAll();

    	
    	//logout configuration
    	http.logout()
    	     .logoutUrl("/logout")
    	     .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
    	     .logoutSuccessUrl("/home")   	    
    	     .invalidateHttpSession(true)
    	     .addLogoutHandler(new SecurityContextLogoutHandler())
    	     .deleteCookies("JSESSIONID")
    	     .permitAll();
    	
    	    
    	    
    }
    
   
}
