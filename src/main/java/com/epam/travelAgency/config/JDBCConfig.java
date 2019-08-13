package com.epam.travelAgency.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class JDBCConfig {

    public static final String HIKARI_CONFIG_PROPERTY_FILE = "src/main/resources/hikariConfig.properties";

    @Bean
    @Profile(value = "dev")
    public HikariConfig hikariConfig() {
        return new HikariConfig(HIKARI_CONFIG_PROPERTY_FILE);
    }

    @Bean(name = "hikariDataSource", destroyMethod = "close")
    @Profile(value = "dev")
    public DataSource hikariDataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean(name = "pgSimpleDataSource")
    @Profile(value = "real_dataSource")
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
    @Profile(value = "dev")
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(hikariDataSource());
    }

}
