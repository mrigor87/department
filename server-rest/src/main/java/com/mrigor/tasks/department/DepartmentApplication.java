package com.mrigor.tasks.department;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class DepartmentApplication { // extends WebMvcConfigurerAdapter

	//@Override
	//public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

		//  <mvc:resources mapping="/webjars/**" location="classpath:/META-INF/resources/webjars/"/>
	//}

	public static void main(String[] args) {
		SpringApplication.run(DepartmentApplication.class, args);
	}
}
