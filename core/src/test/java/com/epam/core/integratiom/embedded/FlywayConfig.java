package com.epam.core.integratiom.embedded;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile(value = "test")
public class FlywayConfig {

    @Bean
    @Autowired
    public Flyway flyway(DataSource pgSimpleDataSource) {
        Flyway flyway = Flyway.configure().baselineOnMigrate(true).encoding("UTF-8")
                .dataSource(pgSimpleDataSource).locations("db/migration").load();//can't find src/test/resources/db/migration, so use resources
        flyway.migrate();
        return flyway;
    }

}
