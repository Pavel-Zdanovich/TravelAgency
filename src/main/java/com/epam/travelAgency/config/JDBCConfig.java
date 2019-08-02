package com.epam.travelAgency.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class JDBCConfig {

    public static final String HIKARI_CONFIG_PROPERTY_FILE = "src/main/resources/hikariConfig.properties";

    @Bean
    public HikariConfig hikariConfig() {
        return new HikariConfig(HIKARI_CONFIG_PROPERTY_FILE);
    }

    @Bean(name = "dataSource", destroyMethod = "close")
    public DataSource hikariDataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(hikariDataSource());
    }

    @Bean(name = "dataSourceTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(hikariDataSource());
    }

}
