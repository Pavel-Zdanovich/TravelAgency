package com.zdanovich.core.utils;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.Map;
import java.util.Properties;

public class CoreUtils {

    public static final String RESOURCES = "resources";

    public static final String DATABASE = "database";
    public static final String DRIVER_CLASS_NAME = "driver";
    public static final String CLASS_PATH = "classpath";
    public static final String URL = "url";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static final String LIQUIBASE_CHANGELOG_FILE = "changeLogFile";

    public static final String HIBERNATE_GENERATE_DDL = "hibernate.generate_ddl";

    public static final String AIR_CONDITIONER = "air conditioner";
    public static final String CABLE_TV = "cable TV";
    public static final String CAR_RENTAL = "car rental";
    public static final String MINI_BAR = "mini-bar";
    public static final String PARKING = "parking";
    public static final String RESTAURANT = "restaurant";
    public static final String ROOM_SERVICE = "room service";
    public static final String SPA = "spa";
    public static final String SWIMMING_POOL = "swimming pool";
    public static final String WI_FI = "wi-fi";

    public static final String EMPTY_STRING = "";

    private CoreUtils() {
    }

    public static Properties getPropertiesOutOf(ConfigurableEnvironment environment, String propertySourceName) {
        MutablePropertySources mutablePropertySources = environment.getPropertySources();
        if (mutablePropertySources == null) {
            return null;
        }
        PropertySource<?> propertySource = mutablePropertySources.get(propertySourceName);
        if (propertySource == null) {
            return null;
        }
        Map<String, Object> source = (Map<String, Object>) propertySource.getSource();
        if (source == null) {
            return null;
        } else {
            Properties properties = new Properties();
            properties.putAll(source);
            return properties;
        }
        /*if (propertySource instanceof CompositePropertySource) {
            CompositePropertySource compositePropertySource = (CompositePropertySource) propertySource;
            propertySource = compositePropertySource.getPropertySources().stream()
                    .filter(source -> source.getName().equals(propertySourceName))
                    .findAny()
                    .orElseThrow(() -> new FileNotFoundException(String.format("Can't find properties file: %s", propertySourceName)));
        }
        if (propertySource instanceof ResourcePropertySource) {
            ResourcePropertySource resourcePropertySource = (ResourcePropertySource) propertySource;
            resourcePropertySource.getSource().forEach((key, value) -> properties.setProperty(key, String.valueOf(value)));
        }
        if (propertySource instanceof MapPropertySource) {
            MapPropertySource mapPropertySource = (MapPropertySource) propertySource;
        }*/
    }

    public static String absoluteToClasspath(String absolutePath) {
        String relativePath = absolutePath.substring(absolutePath.lastIndexOf(RESOURCES) + RESOURCES.length());
        return String.format("%s:%s", CLASS_PATH, relativePath);
    }
}