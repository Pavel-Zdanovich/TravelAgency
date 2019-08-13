package com.epam.travelAgency.util;

import com.epam.travelAgency.entity.*;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY = createSessionFactory();

    private HibernateUtil() {
    }

    private static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Country.class);
        configuration.addAnnotatedClass(Hotel.class);
        configuration.addAnnotatedClass(Review.class);
        configuration.addAnnotatedClass(Tour.class);
        configuration.addAnnotatedClass(User.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(builder.build());
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void close() throws HibernateException {
        SESSION_FACTORY.close();
    }

}
