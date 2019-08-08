package com.epam.travelAgency.service.impl;

import com.epam.travelAgency.config.EntityConfig;
import com.epam.travelAgency.config.RepositoryConfig;
import com.epam.travelAgency.config.ServiceConfig;
import com.epam.travelAgency.embedded.EmbeddedPostgresConfig;
import com.epam.travelAgency.embedded.FlywayConfig;
import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.repository.Repository;
import com.epam.travelAgency.service.Service;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedPostgresConfig.class, FlywayConfig.class, EntityConfig.class, RepositoryConfig.class, ServiceConfig.class})
public class UserServiceTest {

    @Autowired
    private User user;
    @Autowired
    private Repository<User> userRepository;
    @Autowired
    private Service<User> userService;

    @Test
    public void findAll() {
        Assert.assertNotNull(userService.findAll());
    }

    @Test
    public void findById() {
        Assert.assertNotNull(userService.findById(1L));
    }

    @Test
    public void update() {
        user.setLogin(user.getLogin() + "LMAO");
        userService.update(user);
    }

    @Test
    public void save() {
        User user = new User();
        user.setUserId(0);
        user.setLogin("ElonMusk");
        user.setPassword("SpaceXXX");
        userService.save(user);
    }

    @Test
    public void delete() {
        userService.delete(user);
    }

    @Test
    public void deleteById() {
        userService.deleteById(0);
    }
}