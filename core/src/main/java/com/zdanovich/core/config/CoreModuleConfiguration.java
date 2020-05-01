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

import static com.zdanovich.core.config.CoreModuleConfiguration.CORE_MODULE_PATH;
import static com.zdanovich.core.config.CoreModuleConfiguration.DATABASE_PROPERTIES_PATH;

@Configuration
@PropertySource(value = DATABASE_PROPERTIES_PATH)
@ComponentScan(basePackages = CORE_MODULE_PATH)
public class CoreModuleConfiguration {

    public static final String CORE_MODULE_PATH = "com.zdanovich.core";
    public static final String DATABASE_PROPERTIES_PATH = "classpath:database/database.properties";
    public static final String PROPERTY_SOURCE_NAME = "class path resource [database/database.properties]";

    @Bean
    @Autowired
    public Properties properties(ConfigurableEnvironment environment) throws FileNotFoundException {
        MutablePropertySources mutable = environment.getPropertySources();
        org.springframework.core.env.PropertySource<?> propertySource = mutable.get(PROPERTY_SOURCE_NAME);
        if (propertySource == null) {
            throw new FileNotFoundException(String.format("Can't find properties file: %s", PROPERTY_SOURCE_NAME));
        }
        ResourcePropertySource resourcePropertySource = (ResourcePropertySource) propertySource;
        Properties properties = new Properties();
        resourcePropertySource.getSource().forEach((key, value) -> properties.setProperty(key, String.valueOf(value)));
        return properties;
    }
}