package com.mrigor.tasks.department;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DepartmentApplication {
/*	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		return builder.build();
	}*/
	@Bean
	public RestTemplate getRrestTemplate() {
	//	MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		//	javax.servlet.http.HttpServletRequest
		//javax/servlet/http/HttpServletRequest
		RestTemplate restTemplate = new RestTemplate();
		//restTemplate.getMessageConverters().add(converter);
		return restTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(DepartmentApplication.class, args);
	}
}
