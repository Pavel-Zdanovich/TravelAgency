package com.zdanovich.travelagency.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = ApplicationConfiguration.BASE_PACKAGES)
public class ApplicationConfiguration {

    public static final String BASE_PACKAGES = "com.zdanovich";

}