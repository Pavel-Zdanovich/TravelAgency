package com.epam.travelAgency.service.impl;

import com.epam.travelAgency.config.EntityConfig;
import com.epam.travelAgency.config.RepositoryConfig;
import com.epam.travelAgency.config.ServiceConfig;
import com.epam.travelAgency.embedded.EmbeddedPostgresConfig;
import com.epam.travelAgency.embedded.FlywayConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedPostgresConfig.class, FlywayConfig.class, EntityConfig.class, RepositoryConfig.class, ServiceConfig.class})
public class UserServiceTest {

    @Test
    public void findAll() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void update() {
    }

    @Test
    public void save() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void deleteById() {
    }
}