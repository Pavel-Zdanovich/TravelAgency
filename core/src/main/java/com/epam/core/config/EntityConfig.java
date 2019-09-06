package com.epam.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = {"dev", "test"})
@ComponentScan(basePackages = "com.epam.core.entity")
public class EntityConfig {
}
