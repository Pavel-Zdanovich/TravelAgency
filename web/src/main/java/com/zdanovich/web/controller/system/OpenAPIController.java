package com.zdanovich.web.controller.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zdanovich.web.openapi.OpenAPIConfiguration;
import lombok.extern.log4j.Log4j2;
import org.springdoc.webmvc.api.OpenApiResource;
import org.springdoc.webmvc.ui.SwaggerWelcome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@Log4j2
public class OpenAPIController {

    @Autowired
    private OpenApiResource openApiResource;
    @Autowired
    private SwaggerWelcome swaggerWelcome;

    /*@GetMapping(path = OpenAPIConfiguration.OPEN_API_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOpenAPI(HttpServletRequest request) {
        try {
            String string = openApiResource.openapiJson(request, OpenAPIConfiguration.OPEN_API_PATH);
            return new ResponseEntity<>(string, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            log.error(e);
        }
        return new ResponseEntity<>("Server error on : " + OpenAPIConfiguration.OPEN_API_PATH, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    @GetMapping(path = {OpenAPIConfiguration.SWAGGER_UI_PATH, OpenAPIConfiguration.SWAGGER_UI_PATH + ".html"})
    public ModelAndView redirectToSwaggerUI(HttpServletRequest request) {
        return new ModelAndView(swaggerWelcome.redirectToUi(request), HttpStatus.OK);
    }
}