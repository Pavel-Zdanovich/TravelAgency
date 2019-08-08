package com.epam.travelAgency.config;

import com.epam.travelAgency.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    @Bean(name = "sessionFactory", destroyMethod = "close")
    public SessionFactory sessionFactory() {
        return HibernateUtil.getSessionFactory();
    }

    @Bean(name = "sessionEntityManager", destroyMethod = "close")
    public EntityManager entityManager() {
        return sessionFactory().createEntityManager();
    }

}
