package com.zdanovich.web.controller.system;

import com.zdanovich.web.openapi.OpenAPIConfiguration;
import org.springdoc.webmvc.api.OpenApiResource;
import org.springdoc.webmvc.ui.SwaggerWelcome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OpenAPIController {

    @Autowired
    private OpenApiResource openApiResource;
    @Autowired
    private SwaggerWelcome swaggerWelcome;

    @GetMapping(path = {OpenAPIConfiguration.SWAGGER_UI_PATH, OpenAPIConfiguration.SWAGGER_UI_PATH + ".html"})
    public ModelAndView redirectToSwaggerUI(HttpServletRequest request) {
        return new ModelAndView(swaggerWelcome.redirectToUi(request), HttpStatus.OK);
    }
}