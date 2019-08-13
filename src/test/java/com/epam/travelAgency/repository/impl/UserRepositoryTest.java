package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.config.ApplicationConfig;
import com.epam.travelAgency.config.EntityConfig;
import com.epam.travelAgency.config.RepositoryConfig;
import com.epam.travelAgency.config.TransactionConfig;
import com.epam.travelAgency.embedded.EmbeddedPostgresConfig;
import com.epam.travelAgency.embedded.FlywayConfig;
import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.repository.UserRepository;
import com.epam.travelAgency.specification.impl.user.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedPostgresConfig.class, FlywayConfig.class, TransactionConfig.class, EntityConfig.class,
        RepositoryConfig.class, ApplicationConfig.class})
@ActiveProfiles(profiles = {"dev", "test", "test_dataSource"})
@Transactional(transactionManager = "jpaTransactionManager")
public class UserRepositoryTest {

    @Autowired
    private User user;
    @Autowired
    private UserRepository userRepository;
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
    private FindAllUsersSpecification findAllUsersSpecification;
    @Autowired
    private FindUserSpecification findUserSpecification;
    @Autowired
    private FindUserByIdSpecification findUserByIdSpecification;
    @Autowired
    private FindUserByLoginSpecification findUserByLoginSpecification;

    @Before
    public void setUp() throws Exception {
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
        removeUserByIdSpecification.setUserId(Long.MAX_VALUE);
        userRepository.remove(removeUserByIdSpecification);
    }

    @Test
    public void remove_user_by_removeUserByLoginSpecification() {
        removeUserByLoginSpecification.setLogin("ElonMusk");
        userRepository.remove(removeUserByLoginSpecification);
    }

    @Test
    public void query_all_users_by_findAllUsersSpecification() {
        userRepository.query(findAllUsersSpecification);
    }

    @Test
    public void query_user_by_findUserSpecification() {
        findUserSpecification.setSpecification(user);
        userRepository.query(findUserSpecification);
    }

    @Test
    public void query_user_by_findUserByIdSpecification() {
        findUserByIdSpecification.setSpecification(Long.MAX_VALUE);
        userRepository.query(findUserByIdSpecification);
    }

    @Test
    public void query_user_by_findUserByLoginSpecification() {
        findUserByLoginSpecification.setSpecification("ElonMusk");
        userRepository.query(findUserByLoginSpecification);
    }

}