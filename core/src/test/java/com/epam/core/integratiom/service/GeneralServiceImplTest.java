package com.epam.core.integratiom.service;

import com.epam.core.config.EntityConfig;
import com.epam.core.integratiom.embedded.EmbeddedPostgresConfig;
import com.epam.core.integratiom.embedded.EntityManagerConfig;
import com.epam.core.integratiom.embedded.FlywayConfig;
import com.epam.core.repository.GeneralRepositoryJPA2;
import com.epam.core.service.GeneralService;
import com.epam.core.service.GeneralServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedPostgresConfig.class, FlywayConfig.class, EntityManagerConfig.class,
        EntityConfig.class, GeneralRepositoryJPA2.class, GeneralServiceImpl.class})
@ActiveProfiles(profiles = {"test", "test_dataSource"})
public class GeneralServiceImplTest {

    @Autowired
    private GeneralService generalServiceImpl;

    @Test
    public void save() {
    }

    @Test
    public void saveAll() {
    }

    @Test
    public void findBy() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void update() {
    }

    @Test
    public void updateBy() {
    }

    @Test
    public void updateAll() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void deleteBy() {
    }

    @Test
    public void deleteAll() {
    }

}
