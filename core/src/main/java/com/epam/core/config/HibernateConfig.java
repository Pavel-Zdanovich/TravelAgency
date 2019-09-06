package com.epam.core.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

@Configuration
@PropertySource(value = "classpath:database.properties")
@Profile(value = "dev")
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.epam.core.repository")//, transactionManagerRef = "jpaTransactionManager")
@EnableTransactionManagement
public class HibernateConfig {

    @Autowired
    private Environment environment;
    @Autowired
    private DataSource pgSimpleDataSource;

    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(pgSimpleDataSource);
        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.epam.core.entity");
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("travel_agency");
        Properties properties = new Properties();
        properties.setProperty(DIALECT, "org.hibernate.spatial.dialect.postgis.PostgisPG95Dialect");
        properties.setProperty(HBM2DDL_AUTO, "update");
        properties.setProperty(SHOW_SQL, "true");
        properties.setProperty(FORMAT_SQL, "true");
        localContainerEntityManagerFactoryBean.setJpaProperties(properties);
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    @Profile(value = "real_dataSource")
    public DataSource pgSimpleDataSource() {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setServerName(environment.getProperty("jdbc.serverName"));
        pgSimpleDataSource.setPortNumber(Integer.parseInt(Objects.requireNonNull(environment.getProperty("jdbc.portNumber"))));
        pgSimpleDataSource.setDatabaseName(environment.getProperty("jdbc.dataBaseName"));
        pgSimpleDataSource.setUser(environment.getProperty("jdbc.userName"));
        pgSimpleDataSource.setPassword(environment.getProperty("jdbc.password"));
        return pgSimpleDataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
