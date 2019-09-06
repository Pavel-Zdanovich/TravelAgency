package com.epam.core.integratiom.config;

import com.epam.core.config.HibernateConfig;
import com.epam.core.integratiom.embedded.EmbeddedPostgresConfig;
import com.epam.core.integratiom.embedded.FlywayConfig;
import org.flywaydb.core.Flyway;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedPostgresConfig.class, FlywayConfig.class, HibernateConfig.class})
@ActiveProfiles(profiles = {"dev", "test", "real_dataSource"})//test_dataSource
public class JDBCConfigTest {

    @Autowired
    private PostgresConfig postgresConfig;
    @Autowired
    private EmbeddedPostgres embeddedPostgres;
    @Autowired
    private DataSource pgSimpleDataSource;
    @Autowired
    private Flyway flyway;
    @Autowired
    private EntityManagerFactory localContainerEntityManagerFactoryBean;
    @Autowired
    private PlatformTransactionManager jpaTransactionManager;

    @Test
    public void get_PostgresConfig() {
        Assert.assertNotNull(postgresConfig);
    }

    @Test
    public void get_DataSource_PGSimpleDataSource() {
        Assert.assertNotNull(pgSimpleDataSource);
    }

    @Test
    public void get_EmbeddedPostgres() {
        Assert.assertNotNull(embeddedPostgres);
    }

    @Test
    public void get_Flyway() {
        Assert.assertNotNull(flyway);
    }

    @Test
    public void get_EntityManager_LocalContainerEntityManagerFactoryBean() {
        Assert.assertNotNull(localContainerEntityManagerFactoryBean);
    }

    @Test
    public void get_PlatformTransactionManager_JpaTransactionManager() {
        Assert.assertNotNull(jpaTransactionManager);
    }

}