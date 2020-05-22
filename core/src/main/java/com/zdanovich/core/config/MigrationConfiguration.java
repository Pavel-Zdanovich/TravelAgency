package com.zdanovich.core.config;

import com.zdanovich.core.utils.Utils;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class MigrationConfiguration {

    @Autowired
    private Properties properties;
    @Autowired
    private DataSource dataSource;

    @Bean
    @Conditional(value = MigrationCondition.class)
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        String changelog = properties.getProperty(Utils.LIQUIBASE_CHANGELOG_FILE);
        liquibase.setChangeLog(Utils.absoluteToClasspath(changelog));
        return liquibase;
    }
}