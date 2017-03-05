package com.mrigor.tasks.department.rest;


import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import springfox.documentation.staticdocs.Swagger2MarkupResultHandler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;

/**
 * Created by igor on 005 05.03.17.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql")
public class Swagger2MarkupTest {
    @Autowired
    private WebApplicationContext webApplicationContext;


    private static final String API_URI = "/v2/api-docs";



    private MockMvc mockMvc;

    private File projectDir;

    @Before
    public void setup() throws IOException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();

        ClassPathResource pathfileRes = new ClassPathResource("config/application.yml");
        projectDir = pathfileRes.getFile().getParentFile().getParentFile().getParentFile().getParentFile();
    }

    @Test
    public void convertSwaggerToAsciiDoc() throws Exception {
        Swagger2MarkupResultHandler.Builder builder = Swagger2MarkupResultHandler
                .outputDirectory(outputDirForFormat("asciidoc"));
        mockMvc.perform(get(API_URI).accept(MediaType.APPLICATION_JSON))
                .andDo(builder.build())
                .andExpect(status().isOk());

    }

    private String outputDirForFormat(String format) throws IOException {
        return new File(projectDir, "target/docs/" + format + "/generated").getAbsolutePath();
    }
}
