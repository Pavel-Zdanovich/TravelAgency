package com.epam.core.integration.config;

import org.h2.jdbcx.JdbcDataSource;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.vendor.Database;

import javax.sql.DataSource;

@Configuration
@Profile(value = "test")
public class TestDataSourceConfig {

    @Bean
    @Profile(value = "postgresql")
    public Database postgresql() {
        return Database.POSTGRESQL;
    }

    @Bean
    @Profile(value = "oracle")
    public Database oracle() {
        return Database.ORACLE;
    }

    @Bean
    @Profile(value = "h2")
    public Database h2() {
        return Database.H2;
    }

    @Bean
    public Dialect h2Dialect() {
        return new H2Dialect();
    }

    @Bean
    @Profile(value = "postgresql")
    public DataSource testPostgreSQLDataSource() {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setUrl("jdbc:h2:~/test;MODE=PostgreSQL");
        jdbcDataSource.setUser("sa");
        jdbcDataSource.setPassword("");
        return jdbcDataSource;
    }

    @Bean
    @Profile(value = "oracle")
    public DataSource testOracleDataSource() {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setUrl("jdbc:h2:~/test;MODE=ORACLE");
        jdbcDataSource.setUser("sa");
        jdbcDataSource.setPassword("");
        return jdbcDataSource;
    }

}
