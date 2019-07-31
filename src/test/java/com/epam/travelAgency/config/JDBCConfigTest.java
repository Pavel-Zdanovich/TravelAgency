package com.epam.travelAgency.config;

import com.epam.travelAgency.embedded.EmbeddedPostgresConfig;
import com.epam.travelAgency.embedded.FlywayConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import ru.yandex.qatools.embed.postgresql.PostgresProcess;
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
    private DataSource dataSource;
    @Autowired
    private EmbeddedPostgres embeddedPostgres;
    @Autowired
    private Flyway flyway;
    private ApplicationContext hikariJdbcContext;
    private ApplicationContext embeddedPostgresContext;
    private ApplicationContext flywayContext;

    @Before
    public void setUp() throws Exception {
        hikariJdbcContext = new AnnotationConfigApplicationContext(JDBCConfig.class);
        embeddedPostgresContext = new AnnotationConfigApplicationContext(EmbeddedPostgresConfig.class);
        flywayContext = new AnnotationConfigApplicationContext(FlywayConfig.class);
        hikariConfig = hikariJdbcContext.getBean(HikariConfig.class);
        postgresConfig = embeddedPostgresContext.getBean(PostgresConfig.class);
        dataSource = embeddedPostgresContext.getBean(DataSource.class);
        embeddedPostgres = embeddedPostgresContext.getBean(EmbeddedPostgres.class);
        flyway = flywayContext.getBean(Flyway.class);
    }

    @Test
    public void getPostgresConfig() {
        Assert.assertNotNull(embeddedPostgresContext.getBean(PostgresConfig.class));
    }

    @Test
    public void getPostgresProcess() {
        Assert.assertNotNull(embeddedPostgresContext.getBean(PostgresProcess.class, postgresConfig));
    }

    @Test
    public void getDriverManagerDataSource() {
        Assert.assertNotNull(embeddedPostgresContext.getBean(DataSource.class));
    }

    @Test
    public void getHikariConfig() {
        Assert.assertNotNull(hikariJdbcContext.getBean(HikariConfig.class));
    }

    @Test
    public void getHikariDataSource() {
        Assert.assertNotNull(hikariJdbcContext.getBean(HikariDataSource.class, hikariConfig));
    }

    @Test
    public void getJdbcTemplate() {
        Assert.assertNotNull(hikariJdbcContext.getBean(JdbcTemplate.class, dataSource));
    }

    @Test
    public void getFlyway() {
        Assert.assertNotNull(flywayContext.getBean(Flyway.class, dataSource));
    }

}