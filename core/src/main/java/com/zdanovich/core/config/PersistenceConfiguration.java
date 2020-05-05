package com.zdanovich.core.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

import static com.zdanovich.core.utils.Utils.DATABASE;
import static com.zdanovich.core.utils.Utils.DRIVER_CLASS_NAME;
import static com.zdanovich.core.utils.Utils.HIBERNATE_GENERATE_DDL;
import static com.zdanovich.core.utils.Utils.PASSWORD;
import static com.zdanovich.core.utils.Utils.URL;
import static com.zdanovich.core.utils.Utils.USERNAME;
import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;

@Configuration
@EnableJpaRepositories(basePackages = PersistenceConfiguration.REPOSITORY_PACKAGE_PATH)
@EnableTransactionManagement(proxyTargetClass = true)
public class PersistenceConfiguration {

    public static final String REPOSITORY_PACKAGE_PATH = "com.zdanovich.core.repository";
    public static final String ENTITY_PACKAGE_PATH = "com.zdanovich.core.entity";

    @Autowired
    private Properties properties;

    @Bean
    @Autowired
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        localContainerEntityManagerFactoryBean.setPackagesToScan(ENTITY_PACKAGE_PATH);
        localContainerEntityManagerFactoryBean.setJpaProperties(properties);
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    @Autowired
    public JpaVendorAdapter jpaVendorAdapter(Database database) {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(Boolean.parseBoolean(properties.getProperty(SHOW_SQL)));
        hibernateJpaVendorAdapter.setGenerateDdl(Boolean.parseBoolean(properties.getProperty(HIBERNATE_GENERATE_DDL)));
        hibernateJpaVendorAdapter.setDatabase(database);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory, DataSource dataSource) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        jpaTransactionManager.setDataSource(dataSource);
        return jpaTransactionManager;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(properties.getProperty(DRIVER_CLASS_NAME));
        dataSource.setUrl(properties.getProperty(URL));
        dataSource.setUsername(properties.getProperty(USERNAME));
        dataSource.setPassword(properties.getProperty(PASSWORD));
        return dataSource;
    }

    @Bean
    public Database database() {
        return Database.valueOf(properties.getProperty(DATABASE).toUpperCase());
    }
}
