package com.zdanovich.core.config;

import com.zdanovich.core.utils.CoreUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import javax.el.PropertyNotFoundException;
import java.util.Properties;

public class MigrationCondition implements Condition {

    public static final String CONDITION_KEY = "liquibase.enabled";
    public static final String TEST_PROPERTY_SOURCE_NAME = "Inlined Test Properties";

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Properties properties = CoreUtils.getPropertiesOutOf((ConfigurableEnvironment) context.getEnvironment(), CoreModuleConfiguration.PROPERTY_SOURCE_NAME);
        if (properties == null) {
            throw new PropertyNotFoundException(String.format("Can't find properties file: %s", CoreModuleConfiguration.PROPERTY_SOURCE_NAME));
        }
        Properties testProperties = CoreUtils.getPropertiesOutOf((ConfigurableEnvironment) context.getEnvironment(), TEST_PROPERTY_SOURCE_NAME);
        if (testProperties != null) {
            properties.replaceAll(testProperties::getOrDefault);
        }
        return Boolean.parseBoolean(properties.getProperty(CONDITION_KEY, Boolean.FALSE.toString()));
    }
}