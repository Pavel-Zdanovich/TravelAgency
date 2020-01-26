package com.epam.core.integration.database;

import com.epam.core.config.PersistenceConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class)
@Slf4j
public class ConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void connectionTest() throws SQLException {
        Connection connection = dataSource.getConnection();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        log.info("Connection URL: {}", databaseMetaData.getURL());
        log.info("Driver name: {}", databaseMetaData.getDriverName());
        log.info("Username: {}", databaseMetaData.getUserName());
        Assert.assertFalse(connection.isClosed());
        connection.close();
    }
}
