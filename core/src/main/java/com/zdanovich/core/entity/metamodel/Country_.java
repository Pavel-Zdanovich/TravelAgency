package com.zdanovich.core.entity.metamodel;

import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Tour;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Country.class)
public abstract class Country_ {

	public static volatile SingularAttribute<Country, String> name;
	public static volatile SetAttribute<Country, Tour> tours;
	public static volatile SingularAttribute<Country, Long> id;

	public static final String NAME = "name";
	public static final String TOURS = "tours";
	public static final String COUNTRY_ID = "id";

}

