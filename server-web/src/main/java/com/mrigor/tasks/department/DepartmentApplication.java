package com.mrigor.tasks.department;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.context.annotation.Bean;

import org.springframework.web.client.RestTemplate;


//@EnableWebMvc
//@ComponentScan
//@EnableWebMvc
@SpringBootApplication
public class DepartmentApplication   { //extends SpringBootServletInitializer

/*	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DepartmentApplication.class);
	}*/



/*	@Bean
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);

		return resolver;
	}*/

/*	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DepartmentApplication.class);
	}*/

/*	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}*/

/*	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}*/

/*	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//registry.addResourceHandler("/webjars*//**").addResourceLocations("classpath:/META-INF/resources/webjars/");

	//  <mvc:resources mapping="/webjars*//**" location="classpath:/META-INF/resources/webjars/"/>
	}*/

  //  <mvc:resources mapping="/resources/**" location="/resources/"/>

	@Bean
	public RestTemplate getRrestTemplate() {

		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(DepartmentApplication.class, args);
	}
}
