package com.zdanovich.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.FileNotFoundException;
import java.util.Properties;

@Configuration
@PropertySource(value = CoreModuleConfiguration.DATABASE_PROPERTIES_PATH)
@ComponentScan(basePackages = CoreModuleConfiguration.CORE_MODULE_PATH)
public class CoreModuleConfiguration {

    public static final String CORE_MODULE_PATH = "com.zdanovich.core";
    public static final String DATABASE_PROPERTIES_PATH = "classpath:application.properties";
    public static final String PROPERTY_SOURCE_NAME = "class path resource [application.properties]";

    @Bean
    @Autowired
    public Properties properties(ConfigurableEnvironment environment) throws FileNotFoundException {
        return getPropertiesOutOf(environment, PROPERTY_SOURCE_NAME);
    }

    public static Properties getPropertiesOutOf(ConfigurableEnvironment environment, String propertySourceName) throws FileNotFoundException {
        MutablePropertySources mutable = environment.getPropertySources();
        org.springframework.core.env.PropertySource<?> propertySource = mutable.get(propertySourceName);
        if (propertySource == null) {
            throw new FileNotFoundException(String.format("Can't find properties file: %s", propertySourceName));
        }
        ResourcePropertySource resourcePropertySource = (ResourcePropertySource) propertySource;
        Properties properties = new Properties();
        resourcePropertySource.getSource().forEach((key, value) -> properties.setProperty(key, String.valueOf(value)));
        return properties;
    }
}