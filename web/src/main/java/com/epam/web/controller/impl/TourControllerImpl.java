package com.epam.web.controller.impl;

import com.epam.core.entity.Tour;
import com.epam.core.service.GeneralService;
import com.epam.web.controller.TourController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller(value = "tourController")
public class TourControllerImpl implements TourController {

    @Autowired
    private GeneralService generalServiceImpl;

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return null;//TODO
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        return new ModelAndView("redirect:/", "tours", generalServiceImpl.findAll(Tour.class));
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView take() {
        return new ModelAndView("redirect:/", "tour", generalServiceImpl.findBy(Tour.class, 0));//TODO
    }

    @Override
    public ModelAndView save(Tour tour) {
        generalServiceImpl.save(tour);
        return new ModelAndView("redirect:/", HttpStatus.OK);
    }

    @Override
    public ModelAndView delete(Tour tour) {
        generalServiceImpl.delete(tour);
        return new ModelAndView("redirect:/", HttpStatus.OK);
    }

}
