package com.epam.core.entity.metamodel;

import com.epam.core.entity.Feature;
import com.epam.core.entity.Hotel;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Feature.class)
public abstract class Feature_ {

    public static volatile SingularAttribute<Feature, Long> id;
    public static volatile SingularAttribute<Feature, String> name;
    public static volatile SetAttribute<Feature, Hotel> hotels;

    public static final String FEATURE_ID = "id";
    public static final String NAME = "name";
    public static final String HOTELS = "hotels";

}
