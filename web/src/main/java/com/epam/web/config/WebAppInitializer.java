package com.epam.web.config;

import com.epam.core.config.ApplicationConfig;
import com.epam.core.config.CoreModuleConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {ApplicationConfig.class, CoreModuleConfig.class, WebModuleConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {WebMVCConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    /*@Override//  implements WebApplicationInitializer
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(CoreModuleConfig.class);
        applicationContext.register(WebModuleConfig.class);
        applicationContext.register(WebMVCConfig.class);
        applicationContext.setServletContext(servletContext);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("SpringDispatcher", new DispatcherServlet(applicationContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        dispatcher.setInitParameter("contextClass", applicationContext.getClass().getName());
        servletContext.addListener(new ContextLoaderListener(applicationContext));
        servletContext.addListener(RequestContextListener.class);
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, true, "/*");
        FilterRegistration.Dynamic securityFilter = servletContext.addFilter("securityFilter", DelegatingFilterProxy.class);
        securityFilter.addMappingForUrlPatterns(null, true, "/*");
        private String TMP_FOLDER = "/tmp";
        private int MAX_UPLOAD_SIZE = 5 * 1024 * 1024;
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(TMP_FOLDER, MAX_UPLOAD_SIZE, MAX_UPLOAD_SIZE * 2, MAX_UPLOAD_SIZE / 2);
        appServlet.setMultipartConfig(multipartConfigElement);
    }*/

}

