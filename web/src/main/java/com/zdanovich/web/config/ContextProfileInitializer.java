package com.zdanovich.web.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static com.zdanovich.core.utils.Utils.DATABASE;

@Configuration
@Log4j2
public class ContextProfileInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Autowired
    private Properties properties;

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        log.debug("ContextProfileInitializer#initialize");
        log.debug("Properties : {}", properties);
        String activeProfile = properties.getProperty(DATABASE);
        log.debug("Active profile from properties file: {}", activeProfile);
        String[] activeProfiles = configurableApplicationContext.getEnvironment().getActiveProfiles();
        log.debug("Active profile in environment: {}", String.join(", ", activeProfiles));
        configurableApplicationContext.getEnvironment().setActiveProfiles(activeProfiles);
        activeProfiles = configurableApplicationContext.getEnvironment().getActiveProfiles();
        log.debug("Active profile in environment: {}", String.join(", ", activeProfiles));
    }
}
