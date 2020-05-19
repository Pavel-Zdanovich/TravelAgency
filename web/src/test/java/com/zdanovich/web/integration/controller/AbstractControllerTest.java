package com.zdanovich.web.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdanovich.core.config.CoreModuleConfiguration;
import com.zdanovich.core.entity.AbstractEntity;
import com.zdanovich.web.config.WebModuleConfiguration;
import com.zdanovich.web.controller.impl.CountryController;
import com.zdanovich.web.controller.impl.FeatureController;
import com.zdanovich.web.controller.impl.HotelController;
import com.zdanovich.web.controller.impl.ReviewController;
import com.zdanovich.web.controller.impl.TourController;
import com.zdanovich.web.controller.impl.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
@ContextConfiguration(classes = {CoreModuleConfiguration.class, WebModuleConfiguration.class})
@WebAppConfiguration
public abstract class AbstractControllerTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    protected CountryController countryController;
    @Autowired
    protected FeatureController featureController;
    @Autowired
    protected HotelController hotelController;
    @Autowired
    protected ReviewController reviewController;
    @Autowired
    protected TourController tourController;
    @Autowired
    protected UserController userController;
    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    @BeforeClass
    public void beforeClass() {
        mockMvc = MockMvcBuilders.
                //webAppContextSetup(webApplicationContext).
                standaloneSetup(
                        countryController,
                        featureController,
                        hotelController,
                        reviewController,
                        tourController,
                        userController
                ).
                setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper)).
                alwaysDo(MockMvcResultHandlers.log()).build();
        //_serializerFactory -> _factoryConfig -> _additionalSerializers
    }

    public MvcResult request(HttpMethod method, String uriTemplate, AbstractEntity entity, HttpStatus httpStatus) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.
                        request(method, uriTemplate).
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString((entity)))).
                andExpect(MockMvcResultMatchers.status().is(httpStatus.value())).
                andReturn();
    }
}
