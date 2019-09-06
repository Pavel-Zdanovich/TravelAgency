package com.epam.web.controller.impl;

import com.epam.core.entity.Review;
import com.epam.core.service.GeneralService;
import com.epam.web.controller.ReviewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller(value = "reviewController")
public class ReviewControllerImpl implements ReviewController {

    @Autowired
    private GeneralService generalServiceImpl;

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return null;//TODO
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        return new ModelAndView("redirect:/", "reviews", generalServiceImpl.findAll(Review.class));
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView take() {
        return new ModelAndView("redirect:/", "review", generalServiceImpl.findBy(Review.class, 0));//TODO
    }

    @Override
    public ModelAndView save(Review review) {
        generalServiceImpl.save(review);
        return new ModelAndView("redirect:/", HttpStatus.OK);
    }

    @Override
    public ModelAndView delete(Review review) {
        generalServiceImpl.delete(review);
        return new ModelAndView("redirect:/", HttpStatus.OK);
    }

}
