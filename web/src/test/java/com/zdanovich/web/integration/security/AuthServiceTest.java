package com.zdanovich.web.integration.security;

import com.zdanovich.core.config.CoreModuleConfiguration;
import com.zdanovich.web.config.WebModuleConfiguration;
import com.zdanovich.web.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

@Test
@ContextConfiguration(classes = {CoreModuleConfiguration.class, WebModuleConfiguration.class})
@WebAppConfiguration
public class AuthServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private AuthService authService;

    @Test
    public void test() {
        HttpServletRequest httpServletRequest = new MockHttpServletRequest();
    }
}
