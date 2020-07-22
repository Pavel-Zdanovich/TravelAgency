package com.zdanovich.core.config;

import com.zdanovich.core.utils.CoreUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.io.FileNotFoundException;
import java.util.Properties;

public class MigrationCondition implements Condition {

    public static final String CONDITION_KEY = "liquibase.enabled";

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        try {
            Properties properties = CoreUtils.getPropertiesOutOf((ConfigurableEnvironment) context.getEnvironment(), CoreModuleConfiguration.PROPERTY_SOURCE_NAME);
            return Boolean.parseBoolean(properties.getProperty(CONDITION_KEY, Boolean.FALSE.toString()));
        } catch (FileNotFoundException e) {
            return false;
        }
    }
}