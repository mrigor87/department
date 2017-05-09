package com.mrigor.tasks.department;

import javax.xml.ws.Endpoint;

import com.mrigor.tasks.department.service.DepartmentService;
import com.mrigor.tasks.department.service.EmployeeService;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.ImportResource;


import org.apache.cxf.jaxws.EndpointImpl;

@SpringBootApplication
//@ImportResource("classpath:spring/spring-ws.xml")
public class DepartmentApplication {
    @Autowired
    DepartmentService departmentService;
    @Autowired
    EmployeeService employeeService;




    @Autowired
    private Bus bus;


    @Bean
    public Endpoint departmentEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus,departmentService);
        endpoint.publish("/departmentSEI");
        return endpoint;
    }

    @Bean
    public Endpoint employeeEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus,employeeService);
        endpoint.publish("/employeeSEI");
        return endpoint;
    }
/*	@Bean
    public ServletRegistrationBean dispatcherServlet() {
		return new ServletRegistrationBean(new CXFServlet(), "/soap-api");
	}*/

/*    @Bean
    public ServletRegistrationBean cxfServlet() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(new CXFServlet(), "*//*");
        servlet.setLoadOnStartup(1);
        return servlet;
    }*/







/*	@Bean
	public Endpoint departmentEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, departmentService);
		//String s="http://localhost:8080/WebServices/userattributes";
        //endpoint.setAddress(s);
        //endpoint.publish();
//endpoint.publish();
		//endpoint.publish("/departmentSEI");
		return endpoint;
	}
	*/

/*	@Bean
	public Endpoint employeeEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, departmentService);
		endpoint.publish("/employeeSEI");
		return endpoint;
	}*/


    public static void main(String[] args) {
        SpringApplication.run(DepartmentApplication.class, args);
    }
}
