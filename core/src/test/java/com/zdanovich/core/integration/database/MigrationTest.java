package com.zdanovich.core.integration.database;

import com.zdanovich.core.config.CoreModuleConfiguration;
import com.zdanovich.core.config.MigrationConfiguration;
import com.zdanovich.core.config.PersistenceConfiguration;
import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Feature;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.entity.Review;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.User;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

@Test
@ContextConfiguration(classes = CoreModuleConfiguration.class)
public class MigrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private SpringLiquibase liquibase;

    @Test
    public void checkMetaData() {
        try (Connection connection = dataSource.getConnection();
             ResultSet resultSet = connection.getMetaData().getClientInfoProperties()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            logger.info(String.format("Connection URL: %s", databaseMetaData.getURL()));
            logger.info(String.format("Driver name: %s", databaseMetaData.getDriverName()));
            logger.info(String.format("Username: %s", databaseMetaData.getUserName()));
            logger.info(String.format("Catalog: %s", connection.getCatalog()));
            logger.info(String.format("Schema: %s", connection.getSchema()));
            while (resultSet.next()) {
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    logger.info(String.format("%s (%s) %s", resultSetMetaData.getColumnTypeName(i),
                            resultSetMetaData.getColumnClassName(i), resultSetMetaData.getColumnName(i)));
                }
            }
        } catch (SQLException e) {
            logger.error("SQLException: ", e);
            Assert.fail("SQLException: ", e);
        }
    }

    @Test
    public void checkTableCreation() {
        try (Connection connection = dataSource.getConnection();
             ResultSet resultSet = connection.getMetaData()
                     .getTables(null, null, "%", new String[]{"TABLE"})) {
            while (resultSet.next()) {
                logger.info(String.format("Table name : %s", resultSet.getString(3)));
            }
        } catch (SQLException e) {
            logger.error("SQLException: ", e);
            Assert.fail("SQLException: ", e);
        }
    }

    @DataProvider(name = "tableNameDataProvider")
    public Object[][] createData1() {
        return new Object[][] {
                { Country.COUNTRIES },
                { Feature.FEATURES },
                { Hotel.HOTELS },
                { Review.REVIEWS },
                { Tour.TOURS },
                { User.USERS }
        };
    }


    @Test(dataProvider = "tableNameDataProvider")
    public void checkDataMigration(String tableName) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s", tableName))) {
            while (resultSet.next()) {
                logger.info(resultSet.getObject(1, Long.class));
            }
            logger.info(String.format("isFirst: %s", resultSet.isFirst()));
            logger.info(String.format("isBeforeFirst: %s", resultSet.isBeforeFirst()));
            logger.info(String.format("isAfterLast: %s", resultSet.isAfterLast()));
            logger.info(String.format("isLast: %s", resultSet.isLast()));
        } catch (SQLException e) {
            logger.error("SQLException: ", e);
            Assert.fail("SQLException: ", e);
        }
    }
}
