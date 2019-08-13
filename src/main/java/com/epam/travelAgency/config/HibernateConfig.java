package com.epam.travelAgency.config;

import com.epam.travelAgency.util.HibernateUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Configuration
@Profile(value = "dev")
@EnableTransactionManagement
public class HibernateConfig {

    @Bean(name = "sessionFactory", destroyMethod = "close")
    public EntityManagerFactory sessionFactory() {
        return HibernateUtil.getSessionFactory();
    }

    @Bean(name = "sessionEntityManager", destroyMethod = "close")
    public EntityManager sessionEntityManager() {
        return sessionFactory().createEntityManager();
    }

}
