package com.epam.core.service.impl;

import com.epam.core.config.DataSourceConfig;
import com.epam.core.config.PersistenceConfig;
import com.epam.core.entity.User;
import com.epam.core.entity.enums.UserRole;
import com.epam.core.repository.ReviewRepository;
import com.epam.core.repository.UserRepository;
import com.epam.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@Profile(value = "dev")
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

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("dev", "oracle");
        context.register(DataSourceConfig.class, PersistenceConfig.class, UserRepository.class, UserService.class);
        context.refresh();
        UserService userService = context.getBean(UserService.class);
        User user = new User();
        user.setId(1L);
        user.setLogin("ElonMusk");
        user.setPassword("SpaceXXX");
        user.setRole(UserRole.USER);
        User foundUser = userService.findByLoginStartingWith("Elon").stream().findFirst().orElse(null);
        System.out.println("User : " + user);

    }
}
