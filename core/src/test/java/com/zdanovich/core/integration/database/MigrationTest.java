package com.zdanovich.core.integration.database;

import com.zdanovich.core.config.CoreModuleConfiguration;
import com.zdanovich.core.config.MigrationCondition;
import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Feature;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.entity.Review;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.User;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
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
@TestPropertySource(properties = {MigrationCondition.CONDITION_KEY + "=true"})
public class MigrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private SpringLiquibase liquibase;

    @Test
    public void checkMetaData() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            logger.info(String.format("Connection URL: %s", databaseMetaData.getURL()));
            logger.info(String.format("Driver name: %s", databaseMetaData.getDriverName()));
            logger.info(String.format("Username: %s", databaseMetaData.getUserName()));
            logger.info(String.format("Catalog: %s", connection.getCatalog()));
            logger.info(String.format("Schema: %s", connection.getSchema()));
        } catch (SQLException e) {
            logger.error("SQLException: ", e);
            Assert.fail("SQLException: ", e);
        }
    }

    @DataProvider(name = "tableNameDataProvider")
    public Object[][] createData1() {
        return new Object[][]{
                {Country.COUNTRIES},
                {Feature.FEATURES},
                {Hotel.HOTELS},
                {Review.REVIEWS},
                {Tour.TOURS},
                {User.USERS}
        };
    }


    @Test(dataProvider = "tableNameDataProvider")
    public void checkDataMigration(String tableName) {
        String query = String.format("SELECT * FROM %s", tableName);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            logger.info(query);
            while (resultSet.next()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    stringBuilder.
                            append(metaData.getColumnName(i)).
                            append(" [").append(metaData.getColumnTypeName(i)).
                            append(" -> ").append(metaData.getColumnClassName(i)).append("] = ").
                            append(resultSet.getObject(i)).append("; ");
                }
                logger.info(stringBuilder.toString());
            }
        } catch (SQLException e) {
            logger.error("SQLException: ", e);
            Assert.fail("SQLException: ", e);
        }
    }
}
