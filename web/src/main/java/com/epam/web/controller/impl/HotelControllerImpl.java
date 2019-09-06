package com.epam.web.controller.impl;

import com.epam.core.entity.Hotel;
import com.epam.core.service.GeneralService;
import com.epam.web.controller.GenericController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller(value = "hotelController")
public class HotelControllerImpl implements GenericController<Hotel> {

    @Autowired
    private GeneralService generalServiceImpl;

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return null;//TODO
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        return new ModelAndView("redirect:/", "hotels", generalServiceImpl.findAll(Hotel.class));
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView take() {
        return new ModelAndView("redirect:/", "hotel", generalServiceImpl.findBy(Hotel.class, 0));//TODO
    }

    @Override
    public ModelAndView save(Hotel hotel) {
        generalServiceImpl.save(hotel);
        return new ModelAndView("redirect:/", HttpStatus.OK);
    }

    @Override
    public ModelAndView delete(Hotel hotel) {
        generalServiceImpl.delete(hotel);
        return new ModelAndView("redirect:/", HttpStatus.OK);
    }

}
