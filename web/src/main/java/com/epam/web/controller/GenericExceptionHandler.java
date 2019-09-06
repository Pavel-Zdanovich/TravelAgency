package com.epam.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class GenericExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView handleException(Exception exception) {
        return new ModelAndView("WEB-INF/view/error_page", "message", exception.getMessage());
    }

}
