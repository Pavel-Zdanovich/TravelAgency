package com.zdanovich.core.config;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.MessageInterpolator;
import javax.validation.ValidatorFactory;
import java.nio.charset.StandardCharsets;

@Configuration
@ComponentScan(basePackages = "com.zdanovich.core.entity")
public class ValidationConfig {

    public static final String MESSAGES_FILE_PATH = "classpath:messages";

    @Bean
    public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(MESSAGES_FILE_PATH);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        return messageSource;
    }

    @Bean
    @Autowired
    public ResourceBundleLocator resourceBundleLocator(ReloadableResourceBundleMessageSource messageSource) {
        return new MessageSourceResourceBundleLocator(messageSource);
    }

    @Bean
    @Autowired
    public MessageInterpolator messageInterpolator(ResourceBundleLocator resourceBundleLocator) {
        return new ResourceBundleMessageInterpolator(resourceBundleLocator);
    }

    @Bean
    @Autowired
    public ValidatorFactory localValidatorFactoryBean(MessageInterpolator messageInterpolator) {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setMessageInterpolator(messageInterpolator);
        return localValidatorFactoryBean;
    }

    @Bean
    @Autowired
    public MethodValidationPostProcessor methodValidationPostProcessor(ValidatorFactory validatorFactory) {
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidatorFactory(validatorFactory);
        return methodValidationPostProcessor;
    }
}
