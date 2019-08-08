package com.epam.travelAgency.entity.metamodel;

import com.epam.travelAgency.entity.*;
import org.postgresql.util.PGmoney;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.sql.Timestamp;

@StaticMetamodel(Tour.class)
public abstract class Tour_ {

	public static volatile SingularAttribute<Tour, Country> country;
	public static volatile SingularAttribute<Tour, PGmoney> cost;
	public static volatile SingularAttribute<Tour, TourType> tourType;
	public static volatile ListAttribute<Tour, Review> reviews;
	public static volatile SingularAttribute<Tour, String> photoPath;
	public static volatile SingularAttribute<Tour, Timestamp> endDate;
	public static volatile SingularAttribute<Tour, Long> tourId;
	public static volatile SingularAttribute<Tour, String> description;
	public static volatile SingularAttribute<Tour, Hotel> hotel;
	public static volatile SingularAttribute<Tour, Timestamp> startDate;
	public static volatile ListAttribute<Tour, User> users;

	public static final String COUNTRY = "country";
	public static final String COST = "cost";
	public static final String TOUR_TYPE = "tourType";
	public static final String REVIEWS = "reviews";
	public static final String PHOTO_PATH = "photoPath";
	public static final String END_DATE = "endDate";
	public static final String TOUR_ID = "tourId";
	public static final String DESCRIPTION = "description";
	public static final String HOTEL = "hotel";
	public static final String START_DATE = "startDate";
	public static final String USERS = "users";

}

