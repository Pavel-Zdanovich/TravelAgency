package com.zdanovich.web.integration.controller;

import com.zdanovich.core.config.CoreModuleConfiguration;
import com.zdanovich.web.config.WebModuleConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
@ContextConfiguration(classes = {CoreModuleConfiguration.class, WebModuleConfiguration.class})
@WebAppConfiguration
public abstract class AbstractControllerTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @BeforeClass
    public void beforeClass() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).
                alwaysDo(MockMvcResultHandlers.log()).build();
    }
}
