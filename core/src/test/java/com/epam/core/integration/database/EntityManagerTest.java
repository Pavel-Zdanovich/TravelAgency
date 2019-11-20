package com.epam.core.integration.database;

import com.epam.core.config.DataSourceConfig;
import com.epam.core.config.PersistenceConfig;
import com.epam.core.integration.config.EntityManagerConfig;
import com.epam.core.integration.config.MigrationConfig;
import com.epam.core.integration.config.TestDataSourceConfig;
import liquibase.integration.spring.SpringLiquibase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class, PersistenceConfig.class, TestDataSourceConfig.class,
        MigrationConfig.class, EntityManagerConfig.class})
@ActiveProfiles(profiles = {"test", "oracle"})
public class EntityManagerTest {

    @Autowired
    private Database database;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SpringLiquibase liquibase;
    @Autowired
    private LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private PlatformTransactionManager jpaTransactionManager;
    @Autowired
    private EntityManager entityManager;

    @Test
    public void get_Database() {
        Assert.assertNotNull(database);
    }

    @Test
    public void get_DataSource() {
        Assert.assertNotNull(dataSource);
    }

    @Test
    public void get_Liquibase() {
        Assert.assertNotNull(liquibase);
    }

    @Test
    public void get_LocalContainerEntityManagerFactoryBean() {
        Assert.assertNotNull(localContainerEntityManagerFactoryBean);
    }

    @Test
    public void get_EntityManagerFactory() {
        Assert.assertNotNull(entityManagerFactory);
    }

    @Test
    public void get_PlatformTransactionManager_JpaTransactionManager() {
        Assert.assertNotNull(jpaTransactionManager);
    }

    @Test
    public void get_EntityManager() {
        Assert.assertNotNull(entityManager);
    }

}