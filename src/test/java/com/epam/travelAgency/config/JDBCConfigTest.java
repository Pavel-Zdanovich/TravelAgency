package com.epam.travelAgency.config;

import com.epam.travelAgency.embedded.EmbeddedPostgresConfig;
import com.epam.travelAgency.embedded.FlywayConfig;
import com.zaxxer.hikari.HikariConfig;
import org.flywaydb.core.Flyway;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JDBCConfig.class, EmbeddedPostgresConfig.class, FlywayConfig.class})
public class JDBCConfigTest {

    @Autowired
    private HikariConfig hikariConfig;
    @Autowired
    private PostgresConfig postgresConfig;
    @Autowired
    @Qualifier(value = "dataSource")
    private DataSource hikariDataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier(value = "simpleDriverDataSource")
    private DataSource simpleDriverDataSource;
    @Autowired
    private EmbeddedPostgres embeddedPostgres;
    @Autowired
    private Flyway flyway;

    @Test
    public void getPostgresConfig() {
        Assert.assertNotNull(postgresConfig);
    }

    @Test
    public void getEmbeddedPostgres() {
        Assert.assertNotNull(embeddedPostgres);
    }

    @Test
    public void getDriverManagerDataSource() {
        Assert.assertNotNull(simpleDriverDataSource);
    }

    @Test
    public void getHikariConfig() {
        Assert.assertNotNull(hikariConfig);
    }

    @Test
    public void getHikariDataSource() {
        Assert.assertNotNull(hikariDataSource);
    }

    @Test
    public void getJdbcTemplate() {
        Assert.assertNotNull(jdbcTemplate);
    }

    @Test
    public void getFlyway() {
        Assert.assertNotNull(flyway);
    }

}