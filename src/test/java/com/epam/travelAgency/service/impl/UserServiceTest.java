package com.epam.travelAgency.service.impl;

import com.epam.travelAgency.config.*;
import com.epam.travelAgency.embedded.EmbeddedPostgresConfig;
import com.epam.travelAgency.embedded.FlywayConfig;
import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedPostgresConfig.class, FlywayConfig.class, TransactionConfig.class, EntityConfig.class,
        RepositoryConfig.class, ServiceConfig.class, ApplicationConfig.class})
@ActiveProfiles(profiles = {"dev", "test", "test_dataSource"})
@Transactional(transactionManager = "jpaTransactionManager")
public class UserServiceTest {

    private User user;
    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setUserId(1L);
        user.setLogin("ElonMusk");
        user.setPassword("SpaceXXX");
        Tour tour = new Tour();
        tour.setTourId(1L);
        user.setTours(List.of(tour));
    }

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
        user.setLogin("NicolaTesla");
        user.setPassword("AC/DC");
        userService.update(user);
    }

    @Test
    public void save() {
        userService.save(user);
    }

    @Test
    public void delete() {
        userService.delete(user);
    }

    @Test
    public void deleteById() {
        userService.deleteById(1L);
    }
}