package com.epam.core.integratiom.config;

import com.epam.core.integratiom.embedded.EmbeddedPostgresConfig;
import com.epam.core.integratiom.embedded.FlywayConfig;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;
import ru.yandex.qatools.embed.postgresql.distribution.Version;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedPostgresConfig.class, FlywayConfig.class})
@ActiveProfiles(value = {"test", "test_dataSource"})
@Slf4j
public class MigrationConfigTest {

    @Autowired
    private PostgresConfig postgresConfig;
    private PostgresConfig embeddedPostgresConfig;
    @Autowired
    private EmbeddedPostgres embeddedPostgres;
    @Autowired
    private Flyway flyway;

    @Before
    public void setUp() throws Exception {
        PostgresConfig failedConfig = new PostgresConfig(
                Version.V11_1,
                new AbstractPostgresConfig.Net("localhost", 0),
                new AbstractPostgresConfig.Storage("failed"),
                new AbstractPostgresConfig.Timeout(),
                new AbstractPostgresConfig.Credentials("failed", "failed")
        );
        embeddedPostgresConfig = embeddedPostgres.getConfig().orElse(failedConfig);
    }

    @Test
    public void compare_host() throws IOException {
        Assert.assertEquals(postgresConfig.net().host(), embeddedPostgresConfig.net().host());
    }

    @Test
    public void compare_port() {
        Assert.assertEquals(postgresConfig.net().port(), embeddedPostgresConfig.net().port());
    }

    @Test
    public void compare_dataBaseName() {
        Assert.assertEquals(postgresConfig.storage().dbName(), embeddedPostgresConfig.storage().dbName());
    }

    @Test
    public void compare_username() {
        Assert.assertEquals(postgresConfig.credentials().username(), embeddedPostgresConfig.credentials().username());
    }

    @Test
    public void compare_password() {
        Assert.assertEquals(postgresConfig.credentials().password(), embeddedPostgresConfig.credentials().password());
    }

    @Test
    public void migrate() {
        MigrationInfoService migrationInfoService = flyway.info();
        for (MigrationInfo migrationInfo: migrationInfoService.all()) {
            log.info(" Script : " + migrationInfo.getScript());
            log.info(" Description : " + migrationInfo.getDescription());
            log.info(" Installed by : " + migrationInfo.getInstalledBy());
            log.info(" Checksum : " + migrationInfo.getChecksum());
            log.info(" State [display name] : " + migrationInfo.getState().getDisplayName());
            log.info(" Type : " + migrationInfo.getType().name());
            Assert.assertTrue(migrationInfo.getState().isApplied());
        }
    }

    @Test
    public void check_table_creation() {
        try (Connection connection = DriverManager.getConnection(embeddedPostgres.getConnectionUrl().orElse(
                String.format("jdbc:postgresql://%s:%s/%s", postgresConfig.net().host(),
                        postgresConfig.net().port(), postgresConfig.storage().dbName())
        ), postgresConfig.credentials().username(), postgresConfig.credentials().password())) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(postgresConfig.storage().dbName(), null, "%", new String[]{"TABLE"});
            List<String> tables = new ArrayList<>(7);
            while (resultSet.next()) {
                tables.add(resultSet.getString(3));
                log.info("Table name : " + resultSet.getString(3));
            }
            Assert.assertEquals(tables.size(), 7);
        } catch (SQLException e) {
            log.error("Connection failed");
        }
    }

    @Test
    public void check_filling_of_countries() throws SQLException {
        try (Connection connection = DriverManager.getConnection(embeddedPostgres.getConnectionUrl().orElse(
                String.format("jdbc:postgresql://%s:%s/%s", postgresConfig.net().host(), postgresConfig.net().port(),
                        postgresConfig.storage().dbName())), postgresConfig.credentials().username(), postgresConfig.credentials().password());
             Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM countries");
            StringBuilder countries = new StringBuilder("Countries : { ");
            while (resultSet.next()) {
                countries.append(resultSet.getString("name")).append(" ");
            }
            log.info(countries.append("}\n").toString());
            resultSet.close();
        }
    }

    @Test
    public void check_filling_of_hotels() throws SQLException {
        try (Connection connection = DriverManager.getConnection(embeddedPostgres.getConnectionUrl().orElse(
                String.format("jdbc:postgresql://%s:%s/%s", postgresConfig.net().host(), postgresConfig.net().port(),
                        postgresConfig.storage().dbName())), postgresConfig.credentials().username(), postgresConfig.credentials().password());
             Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM hotels");
            StringBuilder countries = new StringBuilder("Hotels : { ");
            while (resultSet.next()) {
                countries.append(resultSet.getString("name")).append("[").append(new String(resultSet.getBytes("coordinate"))).append("] ");
            }
            log.info(countries.append("}\n").toString());
            resultSet.close();
        }
    }

    @Test
    public void check_filling_of_users() throws SQLException {
        try (Connection connection = DriverManager.getConnection(embeddedPostgres.getConnectionUrl().orElse(
                String.format("jdbc:postgresql://%s:%s/%s", postgresConfig.net().host(), postgresConfig.net().port(),
                        postgresConfig.storage().dbName())), postgresConfig.credentials().username(), postgresConfig.credentials().password());
             Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            StringBuilder users = new StringBuilder("User : { ");
            while (resultSet.next()) {
                users.append(resultSet.getString("login")).append(" ");
            }
            log.info(users.append("}\n").toString());
            resultSet.close();
        }
    }

    @Test
    public void check_filling_of_tours() throws SQLException {
        try (Connection connection = DriverManager.getConnection(embeddedPostgres.getConnectionUrl().orElse(
                String.format("jdbc:postgresql://%s:%s/%s", postgresConfig.net().host(), postgresConfig.net().port(),
                        postgresConfig.storage().dbName())), postgresConfig.credentials().username(), postgresConfig.credentials().password());
             Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tours");
            StringBuilder tours = new StringBuilder("Tours : { ");
            while (resultSet.next()) {
                tours.append(resultSet.getString("tour_type")).append(" ");
            }
            log.info(tours.append("}\n").toString());
            resultSet.close();
        }
    }

    @Test
    public void check_filling_of_user_tours() throws SQLException {
        try (Connection connection = DriverManager.getConnection(embeddedPostgres.getConnectionUrl().orElse(
                String.format("jdbc:postgresql://%s:%s/%s", postgresConfig.net().host(), postgresConfig.net().port(),
                        postgresConfig.storage().dbName())), postgresConfig.credentials().username(), postgresConfig.credentials().password());
             Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users_tours");
            StringBuilder usersTours = new StringBuilder("Users Tours : { ");
            while (resultSet.next()) {
                usersTours.append(resultSet.getString("user_id")).append("-").append(resultSet.getString("tour_id")).append(" ");
            }
            log.info(usersTours.append("}\n").toString());
            resultSet.close();
        }
    }

    @Test
    public void check_filling_of_reviews() throws SQLException {
        try (Connection connection = DriverManager.getConnection(embeddedPostgres.getConnectionUrl().orElse(
                String.format("jdbc:postgresql://%s:%s/%s", postgresConfig.net().host(), postgresConfig.net().port(),
                        postgresConfig.storage().dbName())), postgresConfig.credentials().username(), postgresConfig.credentials().password());
             Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM reviews");
            StringBuilder reviews = new StringBuilder("Reviews : { ");
            while (resultSet.next()) {
                reviews.append(resultSet.getString("date")).append(" ");
            }
            log.info(reviews.append("}\n").toString());
            resultSet.close();
        }
    }
}
