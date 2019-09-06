package com.epam.web.controller.impl;

import com.epam.core.entity.User;
import com.epam.core.service.GeneralService;
import com.epam.web.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller(value = "userController")
public class UserControllerImpl implements UserController {

    @Autowired
    private GeneralService generalServiceImpl;

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return null;//TODO
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        return new ModelAndView("redirect:/", "users", generalServiceImpl.findAll(User.class));
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView take() {
        return new ModelAndView("redirect:/", "user", generalServiceImpl.findBy(User.class, 0));//TODO
    }

    @Override
    public ModelAndView save(User user) {
        generalServiceImpl.save(user);
        return new ModelAndView("redirect:/", HttpStatus.OK);
    }

    @Override
    public ModelAndView delete(User user) {
        generalServiceImpl.delete(user);
        return new ModelAndView("redirect:/", HttpStatus.OK);
    }

}
