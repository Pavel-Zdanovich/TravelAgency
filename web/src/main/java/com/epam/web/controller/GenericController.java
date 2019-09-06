package com.epam.web.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public interface GenericController<E> extends Controller {

    ModelAndView list();
    ModelAndView take();//parameter
    ModelAndView save(E entity);
    ModelAndView delete(E entity);

}
