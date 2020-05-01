package com.zdanovich.core.service.impl;

import com.zdanovich.core.entity.User;
import com.zdanovich.core.entity.enums.UserRole;
import com.zdanovich.core.repository.UserRepository;
import com.zdanovich.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService extends AbstractService<User, Long, UserRepository> {

    @Autowired
    public UserService(UserRepository repository) {
        super(repository);
    }

    public Optional<User> findByLogin(String login) {
        return repository.findByLogin(login);
    }

    public List<User> findByLoginLike(String loginRegex) {
        return repository.findByLoginLike(loginRegex);
    }

    public List<User> findByLoginStartingWith(String login) {
        return repository.findByLoginStartingWith(login);
    }

    public List<User> findByLoginEndingWith(String login) {
        return repository.findByLoginEndingWith(login);
    }

    public List<User> findByLoginContaining(String login) {
        return repository.findByLoginContaining(login);
    }

    public List<User> findByUserRole(UserRole userRole) {
        return repository.findByRole(userRole);
    }

    public List<User> findByUserRole(String userRole) {
        return findByUserRole(UserRole.valueOf(userRole));
    }
}
