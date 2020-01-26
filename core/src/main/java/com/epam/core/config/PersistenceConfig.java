package com.epam.core.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
import java.util.Objects;
import java.util.Properties;

import static com.epam.core.utils.CoreConstants.DATABASE;
import static com.epam.core.utils.CoreConstants.DRIVER_CLASS_NAME;
import static com.epam.core.utils.CoreConstants.HIBERNATE_GENERATE_DDL;
import static com.epam.core.utils.CoreConstants.PASSWORD;
import static com.epam.core.utils.CoreConstants.URL;
import static com.epam.core.utils.CoreConstants.USERNAME;
import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.FORMAT_SQL;
import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;
import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;

@Configuration
@PropertySource(value = "classpath:database/database.properties")
@EnableJpaAuditing
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = "com.epam.core.repository")
@EnableTransactionManagement
public class PersistenceConfig {

    @Autowired
    private Environment environment;

    @Bean
    @Autowired
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.epam.core.entity");
        Properties properties = new Properties();
        properties.put(DIALECT, environment.getProperty(DIALECT));
        properties.put(HBM2DDL_AUTO, environment.getProperty(HBM2DDL_AUTO));
        properties.put(SHOW_SQL, environment.getProperty(SHOW_SQL));
        properties.put(FORMAT_SQL, environment.getProperty(FORMAT_SQL));
        localContainerEntityManagerFactoryBean.setJpaProperties(properties);
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    @Autowired
    public JpaVendorAdapter jpaVendorAdapter(Database database) {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(environment.getProperty(SHOW_SQL, boolean.class));
        hibernateJpaVendorAdapter.setGenerateDdl(environment.getProperty(HIBERNATE_GENERATE_DDL, boolean.class));
        hibernateJpaVendorAdapter.setDatabase(database);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty(DRIVER_CLASS_NAME)));
        dataSource.setUrl(environment.getProperty(URL));
        dataSource.setUsername(environment.getProperty(USERNAME));
        dataSource.setPassword(environment.getProperty(PASSWORD));
        return dataSource;
    }

    @Bean
    public Database database() {
        return Database.valueOf(Objects.requireNonNull(environment.getProperty(DATABASE)).toUpperCase());
    }
}
