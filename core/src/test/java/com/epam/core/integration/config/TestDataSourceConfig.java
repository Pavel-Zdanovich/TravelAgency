package com.epam.core.integration.config;

import de.flapdoodle.embed.process.runtime.Network;
import org.h2.Driver;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.PostgreSQL10Dialect;
import org.hibernate.tool.schema.Action;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.vendor.Database;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;
import ru.yandex.qatools.embed.postgresql.distribution.Version;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;

@Configuration
@Profile(value = "test")
public class TestDataSourceConfig {

    @Bean
    @Profile(value = "postgresql")
    public PostgresConfig postgresConfig() throws IOException {
        return new PostgresConfig(
                Version.V11_1,
                new AbstractPostgresConfig.Net("localhost", Network.getFreeServerPort()),
                new AbstractPostgresConfig.Storage("travel_agency_test"),
                new AbstractPostgresConfig.Timeout(),
                new AbstractPostgresConfig.Credentials("postgres", "root")
        );
    }

    @Bean(destroyMethod = "stop")
    @Profile(value = "postgresql")
    @Autowired
    public EmbeddedPostgres embeddedPostgres(PostgresConfig postgresConfig) throws IOException {
        EmbeddedPostgres postgres = new EmbeddedPostgres(postgresConfig.version());
        postgres.start(EmbeddedPostgres.cachedRuntimeConfig(Paths.get("src/test/resources/db")),
                postgresConfig.net().host(), postgresConfig.net().port(), postgresConfig.storage().dbName(),
                postgresConfig.credentials().username(), postgresConfig.credentials().password(), Collections.emptyList());
        return postgres;
    }

    @Bean
    @Profile(value = "postgresql")
    public Database postgresql() {
        return Database.POSTGRESQL;
    }

    @Bean
    @Profile(value = "oracle")
    public Database h2() {
        return Database.H2;
    }

    @Bean
    @Profile(value = "postgresql")
    public Dialect postgresqlDialect() {
        return new PostgreSQL10Dialect();
    }

    @Bean
    @Profile(value = "oracle")
    public Dialect h2Dialect() {
        return new H2Dialect();
    }

    @Bean
    @Profile(value = "postgresql")
    @Autowired
    public DataSource testPostgreSQLDataSource(EmbeddedPostgres embeddedPostgres) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        if (embeddedPostgres.getConfig().isPresent()) {
            PostgresConfig postgresConfig = embeddedPostgres.getConfig().get();
            dataSource.setServerName(postgresConfig.net().host());
            dataSource.setPortNumber(postgresConfig.net().port());
            dataSource.setDatabaseName(postgresConfig.storage().dbName());
            dataSource.setUser(postgresConfig.credentials().username());
            dataSource.setPassword(postgresConfig.credentials().password());
        }
        return dataSource;
    }

    @Bean
    @Profile(value = "oracle")
    public DataSource testOracleDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(Driver.class.getName());
        driverManagerDataSource.setUrl("jdbc:h2:~/test;MODE=Oracle");
        driverManagerDataSource.setUsername("sa");
        driverManagerDataSource.setPassword("");
        return driverManagerDataSource;
    }

}
