package com.epam.travelAgency.config;

import com.epam.travelAgency.util.JPAUtil;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Profile(value = "dev")
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.epam.travelAgency.repository.impl", entityManagerFactoryRef = "localContainerEntityManagerFactoryBean",
        transactionManagerRef = "jpaTransactionManager")
@EnableTransactionManagement
public class TransactionConfig {

    @Bean(name = "localContainerEntityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(@Qualifier(value = "pgSimpleDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.epam.travelAgency");
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("travelAgency");
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.spatial.dialect.postgis.PostgisPG95Dialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        localContainerEntityManagerFactoryBean.setJpaProperties(properties);
        return localContainerEntityManagerFactoryBean;
    }

    @Bean(name = "jpaVendorAdapter")
    public JpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        return hibernateJpaVendorAdapter;
    }

    @Bean(name = "jpaTransactionManager")
    public PlatformTransactionManager jpaTransactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }

    @Bean(name = "entityManagerFactory", destroyMethod = "close")
    public EntityManagerFactory entityManagerFactory() {
        return JPAUtil.getEntityManagerFactory();
    }

    @Bean(name = "persistenceEntityManager", destroyMethod = "close")
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }

}
