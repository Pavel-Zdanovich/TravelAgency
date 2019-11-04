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
@ActiveProfiles(value = {"test", "create-drop", "oracle"})
@Slf4j
public class MigrationTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private SpringLiquibase liquibase;

    @Before
    public void setUp() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            log.info("\nSchema : " + connection.getSchema());
            log.info("\nCatalog : " + connection.getCatalog());
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            log.info("\nDriver name : " + databaseMetaData.getDriverName());
            log.info("\nURL : " + databaseMetaData.getURL());
            log.info("\nClient info : " + connection.getClientInfo());
        } catch (SQLException e) {
            log.error("Connection failed");
        }
    }

    @Test
    public void check_table_creation() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null, null, "%", new String[]{"TABLE"});
            List<String> tables = new ArrayList<>(7);
            while (resultSet.next()) {
                tables.add(resultSet.getString(3));
                log.info("Table name : " + resultSet.getString(3));
            }
            Assert.assertEquals(tables.size(), 7);
        } catch (SQLException e) {
            log.error("Connection failed");
        }
    }

    @Test
    public void check_filling_of_countries() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM COUNTRIES");
            StringBuilder countries = new StringBuilder("\nCountries : { ");
            while (resultSet.next()) {
                countries.append(resultSet.next());
            }
            log.info(countries.append("}\n").toString());
            resultSet.close();
        }
    }

    @Test
    public void check_filling_of_hotels() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM HOTELS");
            StringBuilder hotels = new StringBuilder("\nHotels : { ");
            while (resultSet.next()) {
                hotels.append(resultSet.next());
            }
            log.info(hotels.append("}\n").toString());
            resultSet.close();
        }
    }

    @Test
    public void check_filling_of_features() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM FEATURES");
            StringBuilder features = new StringBuilder("\nFeatures : { ");
            while (resultSet.next()) {
                features.append(resultSet.next());
            }
            log.info(features.append("}\n").toString());
            resultSet.close();
        }
    }

    @Test
    public void check_filling_of_hotels_features() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM HOTELS_FEATURES");
            StringBuilder hotelsFeatures = new StringBuilder("\nHotels Features : { ");
            while (resultSet.next()) {
                hotelsFeatures.append(resultSet.next());
            }
            log.info(hotelsFeatures.append("}\n").toString());
            resultSet.close();
        }
    }

    @Test
    public void check_filling_of_users() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");
            StringBuilder users = new StringBuilder("\nUser : { ");
            while (resultSet.next()) {
                users.append(resultSet.next());
            }
            log.info(users.append("}\n").toString());
            resultSet.close();
        }
    }

    @Test
    public void check_filling_of_tours() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM TOURS");
            StringBuilder tours = new StringBuilder("\nTours : { ");
            while (resultSet.next()) {
                tours.append(resultSet.next());
            }
            log.info(tours.append("}\n").toString());
            resultSet.close();
        }
    }

    @Test
    public void check_filling_of_user_tours() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS_TOURS");
            StringBuilder usersTours = new StringBuilder("\nUsers Tours : { ");
            while (resultSet.next()) {
                usersTours.append(resultSet.next());
            }
            log.info(usersTours.append("}\n").toString());
            resultSet.close();
        }
    }

    @Test
    public void check_filling_of_reviews() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM REVIEWS");
            StringBuilder reviews = new StringBuilder("\nReviews : { ");
            while (resultSet.next()) {
                reviews.append(resultSet.next());
            }
            log.info(reviews.append("}\n").toString());
            resultSet.close();
        }
    }

}
