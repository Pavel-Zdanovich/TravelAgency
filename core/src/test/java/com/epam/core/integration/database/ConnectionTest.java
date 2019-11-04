package com.epam.core.integration.database;

import com.epam.core.config.DataSourceConfig;
import com.epam.core.integration.config.TestDataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
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
@ContextConfiguration(classes = {DataSourceConfig.class, TestDataSourceConfig.class})
@ActiveProfiles(profiles = {"dev", "test", "postgresql", "oracle"})
@Slf4j
public class ConnectionTest {

    @Autowired
    private DataSource realPostgreSQLDataSource;
    @Autowired
    private DataSource realOracleDataSource;
    @Autowired
    private DataSource testPostgreSQLDataSource;
    @Autowired
    private DataSource testOracleDataSource;

    @Test
    public void get_Real_PostgreSQLDataSource() throws SQLException {
        Connection realPostgreSQLConnection = realPostgreSQLDataSource.getConnection();
        log.info("Real PostgreSQL database url : " + realPostgreSQLConnection.getMetaData().getURL());
        Assert.assertTrue(realPostgreSQLConnection.getMetaData().getURL().contains("travel_agency"));
        realPostgreSQLConnection.close();
    }

    @Test
    public void get_Real_OracleDataSource() throws SQLException {
        Connection realOracleConnection = realOracleDataSource.getConnection();
        log.info("Real Oracle database url : " + realOracleConnection.getMetaData().getURL());
        Assert.assertTrue(realOracleConnection.getMetaData().getURL().contains("xe"));
        realOracleConnection.close();
    }

    @Test
    public void get_Test_PostgreSQLDataSource() throws SQLException {
        Connection testPostgreSQLConnection = testPostgreSQLDataSource.getConnection();
        log.info("Test PotgreSQL database utl: " + testPostgreSQLConnection.getMetaData().getURL());
        Assert.assertTrue(testPostgreSQLConnection.getMetaData().getURL().contains("travel_agency_test"));
        testPostgreSQLConnection.close();
    }

    @Test
    public void get_Test_OracleDataSource() throws SQLException {
        Connection testOracleConnection = testOracleDataSource.getConnection();
        log.info("Test Oracle database url: " + testOracleConnection.getMetaData().getURL());
        Assert.assertTrue(testOracleConnection.getMetaData().getURL().contains("jdbc:h2:~/test"));
        testOracleConnection.close();
    }

}
