package com.epam.core.integratiom.embedded;

import de.flapdoodle.embed.process.runtime.Network;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
public class EmbeddedPostgresConfig {

    private String embeddedPostgresURL;

    @Bean(name = "postgresConfig")
    public PostgresConfig postgresConfig() throws IOException {
        return new PostgresConfig(
                Version.V11_1,
                new AbstractPostgresConfig.Net("localhost", Network.getFreeServerPort()),
                new AbstractPostgresConfig.Storage("travel_agency_test"),
                new AbstractPostgresConfig.Timeout(),
                new AbstractPostgresConfig.Credentials("postgres", "root")
        );
    }

    @Bean(name = "embeddedPostgres", destroyMethod = "stop")
    @Autowired
    public EmbeddedPostgres embeddedPostgres(PostgresConfig postgresConfig) throws IOException {
        EmbeddedPostgres postgres = new EmbeddedPostgres(postgresConfig.version());
        embeddedPostgresURL = postgres.start(EmbeddedPostgres.cachedRuntimeConfig(Paths.get("src/test/resources/db")),
                postgresConfig.net().host(), postgresConfig.net().port(), postgresConfig.storage().dbName(),
                postgresConfig.credentials().username(), postgresConfig.credentials().password(), Collections.emptyList());
        return postgres;
    }

    @Bean
    @Profile(value = "test_dataSource")
    @Autowired
    public DataSource pgSimpleDataSource(EmbeddedPostgres embeddedPostgres) {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        if (embeddedPostgres.getConfig().isPresent()) {
            PostgresConfig postgresConfig = embeddedPostgres.getConfig().get();
            pgSimpleDataSource.setURL(embeddedPostgres.getConnectionUrl().orElse(embeddedPostgresURL));
            pgSimpleDataSource.setServerName(postgresConfig.net().host());
            pgSimpleDataSource.setPortNumber(postgresConfig.net().port());
            pgSimpleDataSource.setDatabaseName(postgresConfig.storage().dbName());
            pgSimpleDataSource.setUser(postgresConfig.credentials().username());
            pgSimpleDataSource.setPassword(postgresConfig.credentials().password());
        }
        return pgSimpleDataSource;
    }

}
