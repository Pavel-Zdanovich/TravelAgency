package com.zdanovich.web.integration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdanovich.core.config.CoreModuleConfiguration;
import com.zdanovich.web.config.WebModuleConfiguration;
import com.zdanovich.web.controller.system.AuthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.debug.DebugFilter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.servlet.Filter;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Test
@ContextConfiguration(classes = {CoreModuleConfiguration.class, WebModuleConfiguration.class})
@WebAppConfiguration
public class AuthControllerTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private AuthController authController;
    @Autowired
    private DebugFilter debugFilter;
    @Autowired
    private Filter jwtAuthenticationFilter;
    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    @BeforeClass
    public void beforeClass() {
        mockMvc = MockMvcBuilders.
                standaloneSetup(authController).
                addFilters(debugFilter, jwtAuthenticationFilter).
                setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper)).
                alwaysDo(MockMvcResultHandlers.log()).build();
    }

    @Test
    public void register() throws Exception {
        User user = new User("ElonMusk", "SpaceXXX", Collections.emptySet());

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.
                        post(String.format("%s%s", AuthController.PATH, AuthController.REGISTER)).
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString((user)))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();
    }

    @Test
    public void login() throws Exception {
        User user = new User("ElonMusk", "SpaceXXX", Collections.emptySet());

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.
                        post(String.format("%s%s", AuthController.PATH, AuthController.LOGIN)).
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString((user)))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();
    }

    @Test
    public void logout() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(String.format("%s%s", AuthController.PATH, AuthController.LOGOUT))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();
    }

    @Test
    public void filter() throws Exception {
        Field field = DebugFilter.class.getDeclaredField("fcp");
        field.setAccessible(true);
        FilterChainProxy filterChainProxy = (FilterChainProxy) field.get(debugFilter);

        List<Filter> filters = filterChainProxy.getFilterChains()
                .stream().map(SecurityFilterChain::getFilters).flatMap(List::stream).collect(Collectors.toList());
        Assert.assertNotNull(filters);
        Assert.assertFalse(filters.isEmpty());
    }
}