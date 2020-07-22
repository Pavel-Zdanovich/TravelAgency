package com.zdanovich.web.config;

import com.zdanovich.core.config.CoreModuleConfiguration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class WebApplicationInitializerImpl implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();

        webApplicationContext.register(CoreModuleConfiguration.class, WebModuleConfiguration.class);
        webApplicationContext.register(org.springdoc.webmvc.ui.SwaggerConfig.class,
                org.springdoc.core.SpringDocConfiguration.class,
                org.springdoc.core.SpringDocConfigProperties.class,
                org.springdoc.core.SwaggerUiConfigParameters.class,
                org.springdoc.core.SwaggerUiConfigProperties.class,
                org.springdoc.core.SwaggerUiOAuthProperties.class,
                org.springdoc.webmvc.core.SpringDocWebMvcConfiguration.class,
                org.springdoc.webmvc.core.MultipleOpenApiSupportConfiguration.class,
                org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration.class);

        servletContext.addListener(new ContextLoaderListener(webApplicationContext));

        ServletRegistration.Dynamic dispatcher = servletContext.
                addServlet("dispatcher", new DispatcherServlet(webApplicationContext));

        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}