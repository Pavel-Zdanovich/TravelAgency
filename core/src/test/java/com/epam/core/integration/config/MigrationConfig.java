package com.epam.core.integration.config;

import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile(value = {"dev", "test"})
@Slf4j
public class MigrationConfig {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private String migrationFilePath;

    @Bean

    SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("db/migration/changelog-master.xml");
        return liquibase;
    }

    @Bean
    @Profile(value = "postgresql")
    String postgresqlMigrationFilePath() {
        return "db/migration/postgresql/changelog-master.xml";
    }

    @Bean
    @Profile(value = "oracle")
    String oracleMigrationFilePah() {
        return "db/migration/changelog-master.xml";
    }

}
