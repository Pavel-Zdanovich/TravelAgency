package com.epam.core.entity.metamodel;

import com.epam.core.entity.Country;
import com.epam.core.entity.Tour;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Country.class)
public abstract class Country_ {

	public static volatile SingularAttribute<Country, String> name;
	public static volatile ListAttribute<Country, Tour> tours;
	public static volatile SingularAttribute<Country, Long> countryId;

	public static final String NAME = "name";
	public static final String TOURS = "tours";
	public static final String COUNTRY_ID = "countryId";

}

