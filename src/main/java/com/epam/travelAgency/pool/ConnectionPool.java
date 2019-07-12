package com.epam.travelAgency.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionPool {

    public static final String HIKARI_CONFIG_PROPERTY_FILE = "hikari_config.properties";

    private static class DataSourceHolder {
        public static final HikariConfig HIKARI_CONFIG = new HikariConfig(HIKARI_CONFIG_PROPERTY_FILE);
        public static final DataSource DATA_SOURCE = new HikariDataSource(HIKARI_CONFIG);
    }

    private ConnectionPool() {

    }

    public static Connection getConnection() throws SQLException {
        return DataSourceHolder.DATA_SOURCE.getConnection();
    }

}
