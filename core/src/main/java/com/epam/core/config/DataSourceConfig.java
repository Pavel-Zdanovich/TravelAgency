package com.epam.core.config;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.pool.OracleDataSource;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.PostgreSQL10Dialect;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.vendor.Database;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Objects;

@Configuration
@Profile(value = "dev")
@PropertySource(value = {"classpath:postgresql.properties", "classpath:oracle.properties"})
@Slf4j
public class DataSourceConfig {

    @Autowired
    private Environment environment;

    @Bean
    @Profile(value = "postgresql")
    Database postgresql() {
        return Database.POSTGRESQL;
    }

    @Bean
    @Profile(value = "oracle")
    Database oracle() {
        return Database.ORACLE;
    }

    @Bean
    @Profile(value = "postgresql")
    Dialect postgresqlDialect() {
        return new PostgreSQL10Dialect();
    }

    @Bean
    @Profile(value = "oracle")
    Dialect oracleDialect() {
        return new Oracle10gDialect();
    }

    @Bean
    @Profile(value = "postgresql")
    DataSource realPostgreSQLDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(environment.getProperty("jdbc.postgresql.url"));
        dataSource.setServerName(environment.getProperty("jdbc.postgresql.serverName"));
        dataSource.setPortNumber(Integer.parseInt(Objects.requireNonNull(environment.getProperty("jdbc.postgresql.portNumber"))));
        dataSource.setDatabaseName(environment.getProperty("jdbc.postgresql.dataBaseName"));
        dataSource.setUser(environment.getProperty("jdbc.postgresql.userName"));
        dataSource.setPassword(environment.getProperty("jdbc.postgresql.password"));
        return dataSource;
    }

    @Bean
    @Profile(value = "oracle")
    DataSource realOracleDataSource() {
        OracleDataSource dataSource = null;
        try {
            dataSource = new OracleDataSource();
            dataSource.setDriverType(environment.getProperty("jdbc.oracle.driverType"));
            dataSource.setURL(environment.getProperty("jdbc.oracle.url"));
            dataSource.setNetworkProtocol(environment.getProperty("jdbc.oracle.networkProtocol"));
            dataSource.setServerName(environment.getProperty("jdbc.oracle.serverName"));
            dataSource.setPortNumber(Integer.parseInt(Objects.requireNonNull(environment.getProperty("jdbc.oracle.portNumber"))));
            dataSource.setDatabaseName(environment.getProperty("jdbc.oracle.dataBaseName"));
            dataSource.setUser(environment.getProperty("jdbc.oracle.userName"));
            dataSource.setPassword(environment.getProperty("jdbc.oracle.password"));
        }  catch (SQLException e) {
            log.error("Connect to data source oracle error");
        }
        return dataSource;
    }

}
