package com.epam.travelAgency.embedded;

import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
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
@EnableTransactionManagement
@ComponentScan("com.epam.travelAgency")
public class EmbeddedPostgresConfig {

    @Bean
    public PostgresConfig postgresConfig() throws IOException {
        return new PostgresConfig(
                Version.V9_6_11,
                new AbstractPostgresConfig.Net("localhost", Network.getFreeServerPort()),
                new AbstractPostgresConfig.Storage("test"),
                new AbstractPostgresConfig.Timeout(),
                new AbstractPostgresConfig.Credentials("admin", "root")
        );
    }

    @Bean(destroyMethod = "stop")
    @DependsOn("postgresConfig")
    public PostgresProcess postgresProcess(PostgresConfig postgresConfig) throws IOException {
        PostgresStarter<PostgresExecutable, PostgresProcess> runtime = PostgresStarter.getInstance(EmbeddedPostgres.cachedRuntimeConfig(Paths.get("src/test/resources/db/extracted_pg_embedded_db")));//PostgresStarter.getInstance(EmbeddedPostgres.cachedRuntimeConfig(Paths.get("src/main/test/resources/hikariConfig.properties")));
        PostgresExecutable exec = runtime.prepare(postgresConfig);
        PostgresProcess process = exec.start();
        /*EmbeddedPostgres embeddedPostgres = new EmbeddedPostgres(postgresConfig.version());
        embeddedPostgres.start(EmbeddedPostgres.cachedRuntimeConfig(Paths.get("src/test/db/extracted_pg_embedded_db")),
                                postgresConfig.net().host(), postgresConfig.net().port(), postgresConfig.storage().dbName(),
                                postgresConfig.credentials().username(), postgresConfig.credentials().password(),
                                postgresConfig.getAdditionalInitDbParams(), postgresConfig.getAdditionalPostgresParams());*/
        return process;
    }

    /*@Bean()
    public HikariConfig hikariConfig(PostgresConfig postgresConfig) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setJdbcUrl(String.format("jdbc:postgresql://%s:%s/%s", postgresConfig.net().host(), postgresConfig.net().port(), postgresConfig.storage().dbName()));
        hikariConfig.setUsername(postgresConfig.credentials().username());
        hikariConfig.setPassword(postgresConfig.credentials().password());
        *//*hikariConfig.addDataSourceProperty("databaseName", postgresConfig.storage().dbName());
        hikariConfig.addDataSourceProperty("serverName", postgresConfig.net().host());
        hikariConfig.addDataSourceProperty("portNumber", postgresConfig.net().port());*//*
        return hikariConfig;
    }

    @Bean(destroyMethod = "close")
    public DataSource hikariDataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }*/

    @Bean
    @DependsOn("postgresProcess")
    public DataSource dataSource(PostgresConfig config) {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        driverManagerDataSource.setUrl(String.format("jdbc:postgresql://%s:%s/%s", config.net().host(), config.net().port(), config.storage().dbName()));
        driverManagerDataSource.setUsername(config.credentials().username());
        driverManagerDataSource.setPassword(config.credentials().password());
        return driverManagerDataSource;
    }

}
