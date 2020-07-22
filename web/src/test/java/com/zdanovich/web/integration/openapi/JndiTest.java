package com.zdanovich.web.integration.openapi;

import com.zdanovich.core.config.CoreModuleConfiguration;
import com.zdanovich.web.config.WebModuleConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiPropertySource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Properties;

@Test
@ContextConfiguration(classes = {CoreModuleConfiguration.class, WebModuleConfiguration.class})
@WebAppConfiguration
public class JndiTest {

    @Autowired
    private Properties webProperties;
    @Autowired
    private JndiTemplate jndiTemplate;
    @Autowired
    private JndiPropertySource jndiPropertySource;
    @Autowired
    private JndiObjectFactoryBean jndiObjectFactoryBean;

    @Test
    public void jndiTest() {
        Assert.assertNotNull(webProperties);
        Assert.assertNotNull(jndiTemplate);
        Assert.assertNotNull(jndiPropertySource);
        Assert.assertNotNull(jndiObjectFactoryBean);
    }

}