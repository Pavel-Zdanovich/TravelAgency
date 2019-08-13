package com.epam.travelAgency.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static final String PERSISTENCE_UNIT_NAME = "travelAgency";
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = createEntityManagerFactory();

    private JPAUtil() {
    }

    private static EntityManagerFactory createEntityManagerFactory() {
        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return ENTITY_MANAGER_FACTORY;
    }

    public static void close() {
        ENTITY_MANAGER_FACTORY.close();
    }

}
