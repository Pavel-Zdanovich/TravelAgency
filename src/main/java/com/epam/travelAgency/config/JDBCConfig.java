package com.epam.travelAgency.config;

import com.zaxxer.hikari.HikariConfig;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JDBCConfig {

    public static final String HIKARI_CONFIG_PROPERTY_FILE = "src/main/resources/hikariConfig.properties";

    @Bean
    public HikariConfig hikariConfig() {
        return new HikariConfig(HIKARI_CONFIG_PROPERTY_FILE);
    }

    @Bean(destroyMethod = "close")
    public DataSource hikariDataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(hikariDataSource());
    }

}
