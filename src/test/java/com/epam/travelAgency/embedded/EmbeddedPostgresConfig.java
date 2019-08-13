package com.epam.travelAgency.embedded;

import org.postgresql.Driver;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;
import ru.yandex.qatools.embed.postgresql.distribution.Version;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class EmbeddedPostgresConfig {

    private String embeddedPostgresURL;

    @Bean
    @Profile(value = "test")
    public PostgresConfig postgresConfig() throws IOException {
        return new PostgresConfig(
                Version.V9_6_11,
                new AbstractPostgresConfig.Net("localhost", 5432),//Network.getFreeServerPort()
                new AbstractPostgresConfig.Storage("travel_agency_test"),
                new AbstractPostgresConfig.Timeout(),
                new AbstractPostgresConfig.Credentials("postgres", "root")
        );
    }

    @Bean(destroyMethod = "stop")
    @Profile(value = "test")
    @Autowired
    public EmbeddedPostgres embeddedPostgres(PostgresConfig postgresConfig) throws IOException {
        EmbeddedPostgres postgres = new EmbeddedPostgres(postgresConfig.version());
        embeddedPostgresURL = postgres.start(postgresConfig.net().host(), postgresConfig.net().port(),
                postgresConfig.storage().dbName(), postgresConfig.credentials().username(), postgresConfig.credentials().password());
        return postgres;
    }

    @Bean(name = "pgSimpleDataSource")
    @Profile(value = "test_dataSource")
    public DataSource pgSimpleDataSource() throws IOException {
        PostgresConfig postgresConfig = postgresConfig();
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setServerName(postgresConfig.net().host());
        pgSimpleDataSource.setPortNumber(postgresConfig.net().port());
        pgSimpleDataSource.setDatabaseName(postgresConfig.storage().dbName());
        pgSimpleDataSource.setUser(postgresConfig.credentials().username());
        pgSimpleDataSource.setPassword(postgresConfig.credentials().password());
        return pgSimpleDataSource;
    }

    @Bean(name = "simpleDriverDataSource")
    @Profile(value = "test")
    public DataSource simpleDriverDataSource(EmbeddedPostgres embeddedPostgres) {
        return new SimpleDriverDataSource(new Driver(), embeddedPostgres.getConnectionUrl().orElse(embeddedPostgresURL));
    }

    @Bean(name = "driverManagerDataSource")
    @Profile(value = "test")
    public DataSource driverManagerDataSource(PostgresConfig postgresConfig) {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(Driver.class.getName());//"org.postgresql.Driver"
        driverManagerDataSource.setUrl(String.format("jdbc:postgresql://%s:%s/%s", postgresConfig.net().host(),
                                                        postgresConfig.net().port(), postgresConfig.storage().dbName()));
        driverManagerDataSource.setUsername(postgresConfig.credentials().username());
        driverManagerDataSource.setPassword(postgresConfig.credentials().password());
        return driverManagerDataSource;
    }

}
