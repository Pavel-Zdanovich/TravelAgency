package com.epam.travelAgency.embedded;

import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
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
                Version.V10_6,
                new AbstractPostgresConfig.Net("localhost", Network.getFreeServerPort()),
                new AbstractPostgresConfig.Storage("test"),
                new AbstractPostgresConfig.Timeout(),
                new AbstractPostgresConfig.Credentials("postgres", "root")
        );
    }

    @Bean(destroyMethod = "stop")
    @DependsOn("postgresConfig")
    public PostgresProcess postgresProcess(PostgresConfig postgresConfig) throws IOException {
        Path extractedEmbeddedPostgres = Paths.get("src/test/resources/db/extracted_pg_embedded_db");
        IRuntimeConfig runtimeConfig = EmbeddedPostgres.cachedRuntimeConfig(extractedEmbeddedPostgres);
        PostgresStarter<PostgresExecutable, PostgresProcess> runtime = PostgresStarter.getInstance(runtimeConfig);
        PostgresExecutable exec = runtime.prepare(postgresConfig);
        PostgresProcess process = exec.start();
        /*EmbeddedPostgres embeddedPostgres = new EmbeddedPostgres(postgresConfig.version());
        embeddedPostgres.start(EmbeddedPostgres.cachedRuntimeConfig(Paths.get("src/test/db/extracted_pg_embedded_db")),
                                postgresConfig.net().host(), postgresConfig.net().port(), postgresConfig.storage().dbName(),
                                postgresConfig.credentials().username(), postgresConfig.credentials().password(),
                                postgresConfig.getAdditionalInitDbParams(), postgresConfig.getAdditionalPostgresParams());*/
        return process;
    }

    @Bean(destroyMethod = "stop")
    public EmbeddedPostgres embeddedPostgres() throws IOException {
        EmbeddedPostgres postgres = new EmbeddedPostgres();
        String url = postgres.start("localhost", Network.getFreeServerPort(), "test", "postgres", "root");
        //postgres.getProcess().get().importFromFile(new File(Thread.currentThread().getContextClassLoader().getResource("db/schema.ddl").getFile()));
        //postgres.getProcess().get().importFromFile(new File(Thread.currentThread().getContextClassLoader().getResource("db/test-data.ddl").getFile()));
        return postgres;
    }

    @Bean
    @DependsOn("embeddedPostgres")//postgresProcess
    public DataSource dataSource(PostgresConfig postgresConfig) {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        driverManagerDataSource.setUrl(String.format("jdbc:postgresql://%s:%s/%s", postgresConfig.net().host(),
                                                        postgresConfig.net().port(), postgresConfig.storage().dbName()));
        driverManagerDataSource.setUsername(postgresConfig.credentials().username());
        driverManagerDataSource.setPassword(postgresConfig.credentials().password());
        return driverManagerDataSource;
    }

}
