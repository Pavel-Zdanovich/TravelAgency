package com.zdanovich.web.config;

import com.zdanovich.core.config.CoreModuleConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import java.util.Properties;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Autowired
    private Properties properties;

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { CoreModuleConfiguration.class, WebModuleConfiguration.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[0];
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    /*@Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.setInitParameter("contextInitializerClasses", ContextProfileInitializer.class.getTypeName());
    }*/
}

