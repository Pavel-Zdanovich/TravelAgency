package com.epam.core.integratiom.config;

import com.epam.core.integratiom.embedded.EmbeddedPostgresConfig;
import com.epam.core.integratiom.embedded.EntityManagerConfig;
import com.epam.core.integratiom.embedded.FlywayConfig;
import com.epam.core.repository.GeneralRepository;
import com.epam.core.repository.GeneralRepositoryJPA2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedPostgresConfig.class, FlywayConfig.class, EntityManagerConfig.class, GeneralRepositoryJPA2.class})
@ActiveProfiles(profiles = {"test", "test_dataSource"})
public class RepositoryConfigTest {

    @Autowired
    private GeneralRepository generalRepositoryJPA2;

    @Test
    public void getHotelRepository() {
        Assert.assertNotNull(generalRepositoryJPA2);
    }

}
