package com.epam.core.integration.database;

import com.epam.core.integration.config.MigrationConfig;
import com.epam.core.integration.config.TestDataSourceConfig;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataSourceConfig.class, MigrationConfig.class})
@ActiveProfiles(value = {"test", "oracle"})
@Slf4j
public class MigrationTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private SpringLiquibase liquibase;

    @Test
    public void check_meta_data() throws Exception {
        try (Connection connection = dataSource.getConnection();
             ResultSet resultSet = connection.getMetaData().getClientInfoProperties()) {
            log.info("Catalog: " + connection.getCatalog());
            log.info("Schema: " + connection.getSchema());
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            log.info("Driver name: " + databaseMetaData.getDriverName());
            log.info("URL: " + databaseMetaData.getURL());
            log.info("Username: " + databaseMetaData.getUserName());
            while (resultSet.next()) {
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    log.info(String.format("%s (%s) %s", resultSetMetaData.getColumnTypeName(i),
                            resultSetMetaData.getColumnClassName(i), resultSetMetaData.getColumnName(i)));
                }
            }
        }
    }

    @Test
    public void check_table_creation() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             ResultSet resultSet = connection.getMetaData()
                     .getTables(null, null, "%", new String[]{"TABLE"})) {
            int expected = 10;
            List<String> tables = new ArrayList<>(10);
            while (resultSet.next()) {
                tables.add(resultSet.getString(3));
                log.info("Table name : " + resultSet.getString(3));
            }
            Assert.assertEquals(expected, tables.size());
        }
    }

    @Test
    public void check_filling_of_countries() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM COUNTRIES")) {
            while (resultSet.next()) {
                log.info(resultSet.getString("NAME"));
            }
            log.info("isFirst: " + resultSet.isFirst());
            log.info("isBeforeFirst: " + resultSet.isBeforeFirst());
            log.info("isAfterLast: " + resultSet.isAfterLast());
            log.info("isLast: " + resultSet.isLast());
            Assert.assertTrue(resultSet.isAfterLast());
        }
    }

    @Test
    public void check_filling_of_hotels() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM HOTELS")) {
            while (resultSet.next()) {
                log.info(resultSet.getString("NAME"));
            }
            Assert.assertTrue(resultSet.isAfterLast());
        }
    }

    @Test
    public void check_filling_of_features() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM FEATURES")) {
            while (resultSet.next()) {
                log.info(resultSet.getString("NAME"));
            }
            Assert.assertTrue(resultSet.isAfterLast());
        }
    }

    @Test
    public void check_filling_of_hotels_features() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM HOTELS_FEATURES")) {
            while (resultSet.next()) {
                log.info(String.format("%d-%d", resultSet.getLong("HOTEL_ID"),
                        resultSet.getLong("FEATURE_ID")));
            }
            Assert.assertTrue(resultSet.isAfterLast());
        }
    }

    @Test
    public void check_filling_of_users() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS")) {
            while (resultSet.next()) {
                log.info(resultSet.getString("LOGIN"));
            }
            Assert.assertTrue(resultSet.isAfterLast());
        }
    }

    @Test
    public void check_filling_of_tours() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM TOURS")) {
            while (resultSet.next()) {
                log.info(resultSet.getString("DESCRIPTION"));
            }
            Assert.assertTrue(resultSet.isAfterLast());
        }
    }

    @Test
    public void check_filling_of_user_tours() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS_TOURS")) {
            while (resultSet.next()) {
                log.info(String.format("%d-%d", resultSet.getLong("USER_ID"),
                        resultSet.getLong("TOUR_ID")));
            }
            Assert.assertTrue(resultSet.isAfterLast());
        }
    }

    @Test
    public void check_filling_of_reviews() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM REVIEWS")) {
            while (resultSet.next()) {
                log.info(resultSet.getString("REVIEW_TEXT"));
            }
            Assert.assertTrue(resultSet.isAfterLast());
        }
    }

}
