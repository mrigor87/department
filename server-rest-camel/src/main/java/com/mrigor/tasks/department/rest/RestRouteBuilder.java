/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mrigor.tasks.department.rest;

import com.mrigor.tasks.department.model.Department;
import com.mrigor.tasks.department.service.DepartmentService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Define REST services using the Camel REST DSL
 */
public class RestRouteBuilder extends RouteBuilder {

    @Autowired
    private DepartmentService departmentService;

    @Override
    public void configure() throws Exception {

        // configure we want to use servlet as the component for the rest DSL
        // and we enable json binding mode
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json)
            // and output using pretty print
            .dataFormatProperty("prettyPrint", "true")
            // setup context path and port number that Apache Tomcat will deploy
            // this application with, as we use the servlet component, then we
            // need to aid Camel to tell it these details so Camel knows the url
            // to the REST services.
            // Notice: This is optional, but needed if the RestRegistry should
            // enlist accurate information. You can access the RestRegistry
            // from JMX at runtime
        /*    .contextPath("camel-example-servlet-rest-tomcat/rest").port(8080)*/
        ;

        // this provider REST service is json only
        rest("/departments").description("Department rest service")
            .consumes("application/json").produces("application/json")

/*            .get("/{id}").description("Get department by id").outType(Department.class)
                .to("bean:departmentService?method=getProvider(${header.id})")*/

/*            .put().description("Updates or create a provider").type(Provider.class)
                .to("bean:providerService?method=updateProvider")*/

            .get().description("List all departments").outTypeList(Department.class)

                .to("bean:departmentService?method=getAll");
        from("direct://start").bean(myservice, "process");
                
/*        	.get("/search").description("Search by Zip").outTypeList(Provider.class)
        		.route().log("Incoming zip: ${header.zip}")
        		.to("bean:providerService?method=searchByZip(${header.zip})")
                .endRest();*/
    }

}