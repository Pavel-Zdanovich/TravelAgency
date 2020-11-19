package com.zdanovich.core.entity.metamodel;

import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.entity.Review;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.User;
import com.zdanovich.core.entity.enums.TourType;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;
import java.sql.Timestamp;

@StaticMetamodel(Tour.class)
public abstract class Tour_ {

    public static volatile SingularAttribute<Tour, Country> country;
    public static volatile SingularAttribute<Tour, BigDecimal> cost;
    public static volatile SingularAttribute<Tour, TourType> tourType;
    public static volatile ListAttribute<Tour, Review> reviews;
    public static volatile SingularAttribute<Tour, String> photoPath;
    public static volatile SingularAttribute<Tour, Timestamp> endDate;
    public static volatile SingularAttribute<Tour, Long> id;
    public static volatile SingularAttribute<Tour, String> description;
    public static volatile SingularAttribute<Tour, Hotel> hotel;
    public static volatile SingularAttribute<Tour, Timestamp> startDate;
    public static volatile SetAttribute<Tour, User> users;

    public static final String COUNTRY = "country";
    public static final String COST = "cost";
    public static final String TOUR_TYPE = "tourType";
    public static final String REVIEWS = "reviews";
    public static final String PHOTO_PATH = "photoPath";
    public static final String END_DATE = "endDate";
    public static final String TOUR_ID = "id";
    public static final String DESCRIPTION = "description";
    public static final String HOTEL = "hotel";
    public static final String START_DATE = "startDate";
    public static final String USERS = "users";

}

