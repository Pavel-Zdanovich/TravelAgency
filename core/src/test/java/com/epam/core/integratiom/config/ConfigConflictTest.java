package com.epam.core.integratiom.config;

import com.epam.core.config.HibernateConfig;
import com.epam.core.integratiom.embedded.EmbeddedPostgresConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class, EmbeddedPostgresConfig.class})
@ActiveProfiles(profiles = {"dev", "real_dataSource"})
public class ConfigConflictTest {

    @Autowired
    private DataSource pgSimpleDataSource;
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        connection = pgSimpleDataSource.getConnection();
    }

    @Test
    public void get_test_pgSimpleDataSource() throws SQLException {
        Assert.assertFalse(connection.getMetaData().getURL().contains("travel_agency_test"));
    }

    @Test
    public void get_real_pgSimpleDataSource() throws SQLException {
        Assert.assertTrue(connection.getMetaData().getURL().contains("travel_agency_hibernate"));
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }
}
