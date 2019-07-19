package com.epam.travelAgency.config;

import com.zaxxer.hikari.HikariConfig;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
//@PropertySource(value = "classpath:hikariConfig.properties")
public class JDBCConfig {

    /*@Autowired
    private Environment environment;*/
    public static final String HIKARI_CONFIG_PROPERTY_FILE = "src/main/resources/hikariConfig.properties";

    @Autowired
    @Bean
    public HikariConfig hikariConfig() {
        return new HikariConfig(HIKARI_CONFIG_PROPERTY_FILE);
    }

    @Autowired
    @Bean(destroyMethod = "close")
    public DataSource hikariDataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Autowired
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
