package com.epam.core.entity.metamodel;

import com.epam.core.entity.Feature;
import com.epam.core.entity.Hotel;
import com.epam.core.entity.Tour;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;

@StaticMetamodel(Hotel.class)
public abstract class Hotel_ {

	public static volatile SingularAttribute<Hotel, BigDecimal> latitude;
	public static volatile SingularAttribute<Hotel, BigDecimal> longitude;
	public static volatile SingularAttribute<Hotel, String> website;
	public static volatile SingularAttribute<Hotel, String> name;
	public static volatile SingularAttribute<Hotel, Long> id;
	public static volatile SingularAttribute<Hotel, Short> stars;
	public static volatile SetAttribute<Hotel, Feature> features;
	public static volatile ListAttribute<Hotel, Tour> tours;

	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String WEBSITE = "website";
	public static final String NAME = "name";
	public static final String HOTEL_ID = "id";
	public static final String STARS = "stars";
	public static final String FEATURES = "features";
	public static final String TOURS = "tours";

}

