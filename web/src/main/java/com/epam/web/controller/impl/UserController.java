package com.epam.web.controller.impl;

import com.epam.core.entity.User;
import com.epam.core.repository.UserRepository;
import com.epam.core.service.impl.UserService;
import com.epam.web.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class UserController extends AbstractController<User, Long, UserRepository, UserService> {

    @Autowired
    public UserController(UserService service) {
        super(service);
    }

    public Optional<User> findByLogin(String login) {
        return service.findByLogin(login);
    }

    public Optional<User> findById(Long entityId) {
        return service.findById(entityId);
    }

    public boolean existsById(Long entityId) {
        return service.existsById(entityId);
    }

    public long count() {
        return service.count();
    }

    public void deleteById(Long entityId) {
        service.deleteById(entityId);
    }
}
