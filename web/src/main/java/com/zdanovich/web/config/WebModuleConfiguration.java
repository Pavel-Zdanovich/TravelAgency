package com.zdanovich.web.config;

import com.zdanovich.core.utils.CoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.el.PropertyNotFoundException;
import java.util.Properties;

@Configuration
@PropertySource(value = WebModuleConfiguration.DATABASE_PROPERTIES_PATH)
@ComponentScan(basePackages = WebModuleConfiguration.WEB_MODULE_PATH)
public class WebModuleConfiguration {

    public static final String WEB_MODULE_PATH = "com.zdanovich.web";
    public static final String DATABASE_PROPERTIES_PATH = "classpath:web-application.properties";
    public static final String PROPERTY_SOURCE_NAME = "class path resource [web-application.properties]";

    @Bean
    @Autowired
    public Properties webProperties(ConfigurableEnvironment environment) {
        Properties properties = CoreUtils.getPropertiesOutOf(environment, PROPERTY_SOURCE_NAME);
        if (properties == null) {
            throw new PropertyNotFoundException(String.format("Can't find properties file: %s", PROPERTY_SOURCE_NAME));
        }
        return properties;
    }
}