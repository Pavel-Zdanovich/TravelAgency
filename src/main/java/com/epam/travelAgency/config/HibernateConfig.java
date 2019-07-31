package com.epam.travelAgency.config;

import com.epam.travelAgency.util.HibernateUtil;
import com.epam.travelAgency.util.JPAUtil;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Configuration
public class HibernateConfig {

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtil.getSessionFactory();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return JPAUtil.getEntityManagerFactory();
    }

    @Bean
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }

}
