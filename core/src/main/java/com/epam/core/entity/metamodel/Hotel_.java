package com.epam.core.entity.metamodel;

import com.epam.core.entity.Feature;
import com.epam.core.entity.Hotel;
import com.epam.core.entity.Tour;
import org.postgis.Geometry;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.net.URL;

@StaticMetamodel(Hotel.class)
public abstract class Hotel_ {

	public static volatile SingularAttribute<Hotel, Geometry> coordinate;
	public static volatile SingularAttribute<Hotel, Feature[]> features;
	public static volatile SingularAttribute<Hotel, URL> website;
	public static volatile SingularAttribute<Hotel, String> name;
	public static volatile SingularAttribute<Hotel, Long> hotelId;
	public static volatile SingularAttribute<Hotel, Integer> stars;
	public static volatile ListAttribute<Hotel, Tour> tours;

	public static final String COORDINATE = "coordinate";
	public static final String FEATURES = "features";
	public static final String WEBSITE = "website";
	public static final String NAME = "name";
	public static final String HOTEL_ID = "hotelId";
	public static final String STARS = "stars";
	public static final String TOURS = "tours";

}
