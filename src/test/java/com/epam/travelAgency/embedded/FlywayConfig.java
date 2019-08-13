package com.epam.travelAgency.embedded;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile(value = "test")
public class FlywayConfig {

    @Bean(initMethod = "migrate")
    public Flyway flyway(@Qualifier("simpleDriverDataSource") DataSource dataSource) {
        Flyway flyway = Flyway.configure().dataSource(dataSource).baselineOnMigrate(true).locations("src/test/resources/db/migration").load();
        return flyway;
    }

}
