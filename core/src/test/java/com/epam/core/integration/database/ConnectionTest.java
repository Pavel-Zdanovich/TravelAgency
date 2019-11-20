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
import java.sql.DatabaseMetaData;
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
        DatabaseMetaData databaseMetaData = realPostgreSQLConnection.getMetaData();
        String url = databaseMetaData.getURL();
        log.info("REAL PostgreSQL database url: " + url);
        Assert.assertTrue(url.contains("travel_agency"));
        realPostgreSQLConnection.close();
    }

    @Test
    public void get_Real_OracleDataSource() throws SQLException {
        Connection realOracleConnection = realOracleDataSource.getConnection();
        DatabaseMetaData databaseMetaData = realOracleConnection.getMetaData();
        String url = databaseMetaData.getURL();
        log.info("REAL Oracle database url: " + url);
        Assert.assertTrue(url.contains("xe"));
        realOracleConnection.close();
    }

    @Test
    public void get_Test_PostgreSQLDataSource() throws SQLException {
        Connection testPostgreSQLConnection = testPostgreSQLDataSource.getConnection();
        DatabaseMetaData databaseMetaData = testPostgreSQLConnection.getMetaData();
        String url = databaseMetaData.getURL();
        log.info("TEST PotgreSQL database url: " + url);
        Assert.assertTrue(url.contains("jdbc:h2"));
        testPostgreSQLConnection.close();
    }

    @Test
    public void get_Test_OracleDataSource() throws SQLException {
        Connection testOracleConnection = testOracleDataSource.getConnection();
        DatabaseMetaData databaseMetaData = testOracleConnection.getMetaData();
        String url = databaseMetaData.getURL();
        log.info("TEST Oracle database url: " + url);
        Assert.assertTrue(url.contains("jdbc:h2"));
        testOracleConnection.close();
    }

}
