package com.epam.travelAgency.embedded;

import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.runtime.Network;
import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import ru.yandex.qatools.embed.postgresql.PostgresExecutable;
import ru.yandex.qatools.embed.postgresql.PostgresProcess;
import ru.yandex.qatools.embed.postgresql.PostgresStarter;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;
import ru.yandex.qatools.embed.postgresql.distribution.Version;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@ComponentScan("com.epam.travelAgency")
public class EmbeddedPostgresConfig {

    @Bean
    public PostgresConfig postgresConfig() throws IOException {
        return new PostgresConfig(
                Version.V9_6_11,
                new AbstractPostgresConfig.Net("localhost", Network.getFreeServerPort()),
                new AbstractPostgresConfig.Storage("test"),
                new AbstractPostgresConfig.Timeout(),
                new AbstractPostgresConfig.Credentials("postgres", "root")
        );
    }

    //@Bean(destroyMethod = "stop")
    //@Autowired
    public PostgresProcess postgresProcess(PostgresConfig postgresConfig) throws IOException {
        Path extractedEmbeddedPostgres = Paths.get("src/test/resources/db/extracted_pg_embedded_db");
        IRuntimeConfig runtimeConfig = EmbeddedPostgres.cachedRuntimeConfig(extractedEmbeddedPostgres);
        PostgresStarter<PostgresExecutable, PostgresProcess> runtime = PostgresStarter.getInstance(runtimeConfig);
        PostgresExecutable exec = runtime.prepare(postgresConfig);
        PostgresProcess process = exec.start();
        return process;
    }

    @Bean(destroyMethod = "stop")
    @Autowired
    public EmbeddedPostgres embeddedPostgres(PostgresConfig postgresConfig) throws IOException {
        EmbeddedPostgres postgres = new EmbeddedPostgres(postgresConfig.version());
        String url = postgres.start(postgresConfig.net().host(), postgresConfig.net().port(),
                postgresConfig.storage().dbName(), postgresConfig.credentials().username(), postgresConfig.credentials().password());
        return postgres;
    }

    @Bean(name = "simpleDriverDataSource")
    @Autowired
    public SimpleDriverDataSource simpleDriverDataSource(EmbeddedPostgres embeddedPostgres) {
        return new SimpleDriverDataSource(new Driver(), embeddedPostgres.getConnectionUrl().get());
    }

    @Bean(name = "driverManagerDataSource")
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
