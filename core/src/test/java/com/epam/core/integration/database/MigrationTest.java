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
            StringBuilder countries = new StringBuilder("Countries : { ");
            while (resultSet.next()) {
                countries.append(resultSet.getString("NAME"));
            }
            log.info(countries.append("}").toString());
            log.info("isFirst: " + resultSet.isFirst());
            log.info("isBeforeFirst: " + resultSet.isBeforeFirst());
            log.info("isAfterLast: " + resultSet.isAfterLast());
            log.info("isLast: " + resultSet.isLast());
            Assert.assertTrue(resultSet.isLast());
        }
    }

    @Test
    public void check_filling_of_hotels() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM HOTELS")) {
            StringBuilder hotels = new StringBuilder("Hotels : { ");
            while (resultSet.next()) {
                hotels.append(resultSet.getString("NAME"));
            }
            log.info(hotels.append("}\n").toString());
            Assert.assertTrue(resultSet.isLast());
        }
    }

    @Test
    public void check_filling_of_features() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM FEATURES")) {
            StringBuilder features = new StringBuilder("Features : { ");
            while (resultSet.next()) {
                features.append(resultSet.getString("NAME"));
            }
            log.info(features.append("}\n").toString());
            Assert.assertTrue(resultSet.isLast());
        }
    }

    @Test
    public void check_filling_of_hotels_features() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM HOTELS_FEATURES")) {
            StringBuilder hotelsFeatures = new StringBuilder("Hotels Features : { ");
            while (resultSet.next()) {
                hotelsFeatures.append(String.format("%d-%d",
                        resultSet.getLong("HOTEL_ID"),
                        resultSet.getLong("FEATURE_ID")
                ));
            }
            log.info(hotelsFeatures.append("}\n").toString());
            Assert.assertTrue(resultSet.isLast());
        }
    }

    @Test
    public void check_filling_of_users() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS")) {
            StringBuilder users = new StringBuilder("User : { ");
            while (resultSet.next()) {
                users.append(resultSet.getString("LOGIN"));
            }
            log.info(users.append("}\n").toString());
            Assert.assertTrue(resultSet.isLast());
        }
    }

    @Test
    public void check_filling_of_tours() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM TOURS")) {
            StringBuilder tours = new StringBuilder("Tours : { ");
            while (resultSet.next()) {
                tours.append(resultSet.getString("DESCRIPTION"));
            }
            log.info(tours.append("}\n").toString());
            Assert.assertTrue(resultSet.isLast());
        }
    }

    @Test
    public void check_filling_of_user_tours() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS_TOURS")) {
            StringBuilder usersTours = new StringBuilder("Users Tours : { ");
            while (resultSet.next()) {
                usersTours.append(resultSet.next());
            }
            log.info(usersTours.append("}\n").toString());
            Assert.assertTrue(resultSet.isLast());
        }
    }

    @Test
    public void check_filling_of_reviews() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM REVIEWS")) {
            StringBuilder reviews = new StringBuilder("Reviews : { ");
            while (resultSet.next()) {
                reviews.append(resultSet.getString("REVIEW_TEXT"));
            }
            log.info(reviews.append("}\n").toString());
            Assert.assertTrue(resultSet.isLast());
        }
    }

}
