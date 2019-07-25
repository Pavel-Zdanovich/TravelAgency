package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.config.EntityConfig;
import com.epam.travelAgency.embedded.EmbeddedPostgresConfig;
import com.epam.travelAgency.embedded.FlywayConfig;
import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.impl.user.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedPostgresConfig.class, FlywayConfig.class, EntityConfig.class})
public class UserRepositoryTest {

    @Autowired
    private static User user;
    @Autowired
    private AddUserSpecification addUserSpecification;
    @Autowired
    private UpdateUserSpecification updateUserSpecification;
    @Autowired
    private RemoveUserSpecification removeUserSpecification;
    @Autowired
    private RemoveUserByIdSpecification removeUserByIdSpecification;
    @Autowired
    private RemoveUserByLoginSpecification removeUserByLoginSpecification;
    @Autowired
    private FindUserSpecification findUserSpecification;
    @Autowired
    private FindUserByIdSpecification findUserByIdSpecification;
    @Autowired
    private FindUserByLoginSpecification findUserByLoginSpecification;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    @BeforeClass
    public static void setUp() throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(EntityConfig.class);
        user = applicationContext.getBean(User.class);
        user.setUserId(1);
        user.setLogin("ElonMusk");
        user.setPassword("SpaceXXX");
    }

    @Test
    public void add_user_by_addUserSpecification() {
        addUserSpecification.setUser(user);
        userRepository.add(addUserSpecification);
    }

    @Test
    public void update_user_by_updateUserSpecification() {
        updateUserSpecification.setUser(user);
        userRepository.update(updateUserSpecification);
    }

    @Test
    public void remove_user_by_removeUserSpecification() {
        removeUserSpecification.setUser(user);
        userRepository.remove(removeUserSpecification);
    }

    @Test
    public void remove_user_by_removeUserByIdSpecification() {
        removeUserByIdSpecification.setUserId(1L);
        userRepository.remove(removeUserByIdSpecification);
    }

    @Test
    public void remove_user_by_removeUserByLoginSpecification() {
        removeUserByLoginSpecification.setLogin("ElonMusk");
        userRepository.remove(removeUserByLoginSpecification);
    }

    @Test
    public void query_user_by_findUserSpecification() {
        findUserSpecification.setSpecification(user);
        userRepository.query(findUserSpecification);
    }

    @Test
    public void query_user_by_findUserByIdSpecification() {
        findUserByIdSpecification.setSpecification(1L);
        userRepository.query(findUserByIdSpecification);
    }

    @Test
    public void query_user_by_findUserByLoginSpecification() {
        findUserByLoginSpecification.setSpecification("ElonMusk");
        userRepository.query(findUserByLoginSpecification);
    }

}