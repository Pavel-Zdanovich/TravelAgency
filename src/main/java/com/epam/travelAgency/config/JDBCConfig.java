package com.epam.travelAgency.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.Driver;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class JDBCConfig {

    public static final String HIKARI_CONFIG_PROPERTY_FILE = "src/main/resources/hikariConfig.properties";

    @Bean
    public HikariConfig hikariConfig() {
        return new HikariConfig(HIKARI_CONFIG_PROPERTY_FILE);
    }

    @Bean(name = "hikariDataSource", destroyMethod = "close")
    public DataSource hikariDataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean(name = "simpleDriverDataSource")
    public DataSource simpleDriverDataSource() {
        Properties properties = hikariConfig().getDataSourceProperties();
        String url = new StringBuilder("jdbc:").append(properties.getProperty("user")).append("://").append(properties.getProperty("serverName"))
                .append(":").append(properties.getProperty("portNumber")).append("/").append(properties.getProperty("databaseName")).toString();
        Driver driver = new Driver();
        driver.acceptsURL(url);
        return new SimpleDriverDataSource(driver, url);
    }

    @Bean(name = "pgSimpleDataSource")
    public DataSource pgSimpleDataSource() {
        Properties properties = hikariConfig().getDataSourceProperties();
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setServerName(properties.getProperty("serverName"));
        pgSimpleDataSource.setPortNumber(Integer.parseInt(properties.getProperty("portNumber")));
        pgSimpleDataSource.setDatabaseName(properties.getProperty("databaseName"));
        pgSimpleDataSource.setUser(properties.getProperty("user"));
        pgSimpleDataSource.setPassword(properties.getProperty("password"));
        return pgSimpleDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(hikariDataSource());
    }

}
