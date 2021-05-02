package com.election.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DefaultViewConfig implements WebMvcConfigurer{
  public void addViewController(ViewControllerRegistry registry) {
	  registry.addViewController("/").setViewName("/home");
	  registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
  }
}
