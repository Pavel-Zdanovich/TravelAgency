package com.zdanovich.travelAgency.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = ApplicationConfig.BASE_PACKAGES)
@Log4j2
public class ApplicationConfig {

    public static final String BASE_PACKAGES = "com.zdanovich";

}
