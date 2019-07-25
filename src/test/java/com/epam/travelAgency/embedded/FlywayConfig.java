package com.epam.travelAgency.embedded;

import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FlywayConfig {

    @Before
    public void setUp() throws Exception {
        Path embeddedPostgresDir = Paths.get("src/test/resources/db/extracted_pg_embedded_db");
        File postgisDirectory = new File("src/test/resources/db/extracted_pg_embedded_db/pgsql-9.6.11-1/pgsql/share");
    }

    @Bean(initMethod = "migrate")
    @DependsOn("dataSource")
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        return flyway;
    }

}
