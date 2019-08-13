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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JDBCConfig.class, TransactionConfig.class, HibernateConfig.class, EmbeddedPostgresConfig.class, FlywayConfig.class})
@ActiveProfiles(profiles = {"dev", "test", "test_dataSource"})
public class JDBCConfigTest {

    @Autowired
    private HikariConfig hikariConfig;
    @Autowired
    private PostgresConfig postgresConfig;
    @Autowired
    @Qualifier(value = "hikariDataSource")
    private DataSource hikariDataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier(value = "simpleDriverDataSource")
    private DataSource simpleDriverDataSource;
    @Autowired
    @Qualifier(value = "driverManagerDataSource")
    private DataSource driverManagerDataSource;
    @Autowired
    @Qualifier(value = "pgSimpleDataSource")
    private DataSource pgSimpleDataSource;
    @Autowired
    private EmbeddedPostgres embeddedPostgres;
    @Autowired
    private Flyway flyway;
    @Autowired
    @Qualifier(value = "sessionEntityManager")
    private EntityManager sessionEntityManager;
    @Autowired
    @Qualifier(value = "localContainerEntityManagerFactoryBean")
    private EntityManagerFactory localContainerEntityManagerFactoryBean;
    @Autowired
    @Qualifier(value = "sessionFactory")
    private EntityManagerFactory sessionFactory;
    @Autowired
    @Qualifier(value = "entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    @Qualifier(value = "persistenceEntityManager")
    private EntityManager persistenceEntityManager;
    @Autowired
    @Qualifier(value = "jpaTransactionManager")
    private PlatformTransactionManager jpaTransactionManager;

    @Test
    public void getHikariConfig() {
        Assert.assertNotNull(hikariConfig);
    }

    @Test
    public void getPostgresConfig() {
        Assert.assertNotNull(postgresConfig);
    }

    @Test
    public void get_DataSource_HikariDataSource() {
        Assert.assertNotNull(hikariDataSource);
    }

    @Test
    public void getJdbcTemplate() {
        Assert.assertNotNull(jdbcTemplate);
    }

    @Test
    public void get_DataSource_SimpleDriverDataSource() {
        Assert.assertNotNull(simpleDriverDataSource);
    }

    @Test
    public void get_DataSource_DriverManagerDataSource() {
        Assert.assertNotNull(driverManagerDataSource);
    }

    @Test
    public void get_DataSource_PGSimpleDataSource() {
        Assert.assertNotNull(pgSimpleDataSource);
    }

    @Test
    public void getEmbeddedPostgres() {
        Assert.assertNotNull(embeddedPostgres);
    }

    @Test
    public void getFlyway() {
        Assert.assertNotNull(flyway);
    }

    @Test
    public void get_EntityManagerFactory_SessionFactory() {
        Assert.assertNotNull(sessionFactory);
    }

    @Test
    public void get_EntityManager_SessionEntityManager() {
        Assert.assertNotNull(sessionEntityManager);
    }

    @Test
    public void get_EntityManager_LocalContainerEntityManagerFactoryBean() {
        Assert.assertNotNull(localContainerEntityManagerFactoryBean);
    }

    @Test
    public void getEntityManagerFactory() {
        Assert.assertNotNull(entityManagerFactory);
    }

    @Test
    public void get_EntityManager_PersistenceEntityManager() {
        Assert.assertNotNull(persistenceEntityManager);
    }

    @Test
    public void get_PlatformTransactionManager_JpaTransactionManager() {
        Assert.assertNotNull(jpaTransactionManager);
    }

}