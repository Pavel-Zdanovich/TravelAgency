package com.epam.web.config;

import com.epam.core.config.CoreModuleConfig;
import com.epam.web.security.WebSecurityConfig;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {CoreModuleConfig.class, WebSecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {WebModuleConfig.class, WebMVCConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    /*@Override
    protected Filter[] getServletFilters() {
        return new Filter[] {new DelegatingFilterProxy("springSecurityFilterChain")};
    }*/

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.setInitParameter("contextInitializerClasses", ContextProfileInitializer.class.getTypeName());
    }



}

