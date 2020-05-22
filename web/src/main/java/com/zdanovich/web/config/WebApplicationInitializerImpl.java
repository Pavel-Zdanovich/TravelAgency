package com.zdanovich.web.config;

import com.zdanovich.core.config.CoreModuleConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class WebApplicationInitializerImpl implements WebApplicationInitializer {

    @Autowired
    private Properties properties;

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();

        webApplicationContext.register(CoreModuleConfiguration.class, WebModuleConfiguration.class);

        servletContext.addListener(new ContextLoaderListener(webApplicationContext));

        ServletRegistration.Dynamic dispatcher = servletContext.
                addServlet("dispatcher", new DispatcherServlet(webApplicationContext));

        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}