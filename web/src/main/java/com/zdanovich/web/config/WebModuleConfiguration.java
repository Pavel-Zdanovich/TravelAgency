package com.zdanovich.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static com.zdanovich.web.config.WebModuleConfiguration.WEB_MODULE_PATH;

@Configuration
@ComponentScan(basePackages = WEB_MODULE_PATH)
public class WebModuleConfiguration {

    public static final String WEB_MODULE_PATH = "com.zdanovich.web";

}
