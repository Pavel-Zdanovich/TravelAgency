package com.epam.core.util;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class EntityAnalyzer<E extends Serializable> {

    private Class<E> entityClass;

    private EntityAnalyzer() {
    }

    /*private <P> P createEntity(Class<P> entityClass) {
        P entity = null;
        try {
            entity = entityClass.getConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            log.error("Specified entity class does not have a default constructor");
        } catch (InstantiationException e) {
            log.error("Specified entity class object cannot be instantiated");
        } catch (IllegalAccessException e) {
            log.error("Specified entity class does not have an available constructor");
        } catch (InvocationTargetException e) {
            log.error("Cannot invoke constructor on the specified entity class");
        }
        return entity;
    }*/

    public static boolean containsField(Class<?> entityClass, String fieldName) {
        Field field = null;
        try {
            field = entityClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            log.info("Specified entity class : " + entityClass.getName() + " does not have a field of a specified name : " + fieldName);
        }
        return field != null;
    }

    public static boolean isFieldInitialized(Object entity, Field field) {
        boolean result = false;
        try {
            field.setAccessible(true);
            result = field.get(entity) != null;
        } catch (IllegalAccessException e) {
            log.info("Specified entity class does not have access to the field : " + field.getName());
        }
        return result;
    }

    public static List<Field> getDependentEntitiesField(Class<?> entityClass) {
        List<Field> fieldList = new ArrayList<>();
        for (Field field : entityClass.getDeclaredFields()) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                if (annotation.annotationType().equals(ManyToMany.class) || annotation.annotationType().equals(OneToMany.class)
                        || annotation.annotationType().equals(ManyToOne.class)) {
                    fieldList.add(field);
                }
            }
        }
        return fieldList;
    }

    public static boolean isDependentEntitiesPersisted(Object entity) {
        List<Field> fieldList = EntityAnalyzer.getDependentEntitiesField(entity.getClass());
        boolean dependentEntitiesPersisted = false;
        for (Field field: fieldList) {
            if (EntityAnalyzer.isFieldInitialized(entity, field)) {
                dependentEntitiesPersisted = true;
                break;
            }
        }
        return dependentEntitiesPersisted;
    }

    /*public static Map<String, List<String>> getDependentEntityFieldNames(Class<?> entityClass) {
        Map<String, List<String>> dependentEntityFieldNamesMap = new HashMap<>();
        for (Field field : getDependentEntitiesField(entityClass)) {
            List<String> dependentEntityFieldNames = Arrays.stream(field.getType().getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
            dependentEntityFieldNamesMap.put(field.getName(), dependentEntityFieldNames);
        }
        return dependentEntityFieldNamesMap;
    }*/

}
