package com.zdanovich.core.config;

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
@PropertySource(value = CoreModuleConfiguration.DATABASE_PROPERTIES_PATH)
@ComponentScan(basePackages = CoreModuleConfiguration.CORE_MODULE_PATH)
public class CoreModuleConfiguration {

    public static final String CORE_MODULE_PATH = "com.zdanovich.core";
    public static final String DATABASE_PROPERTIES_PATH = "classpath:core-application.properties";
    public static final String PROPERTY_SOURCE_NAME = "class path resource [core-application.properties]";

    @Bean
    @Autowired
    public Properties coreProperties(ConfigurableEnvironment environment) {
        Properties properties = CoreUtils.getPropertiesOutOf(environment, PROPERTY_SOURCE_NAME);
        if (properties == null) {
            throw new PropertyNotFoundException(String.format("Can't find properties file: %s", PROPERTY_SOURCE_NAME));
        }
        return properties;
    }
}