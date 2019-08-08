package com.epam.travelAgency.entity.metamodel;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.User;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.sql.Timestamp;

@StaticMetamodel(Review.class)
public abstract class Review_ {

	public static volatile SingularAttribute<Review, Timestamp> date;
	public static volatile SingularAttribute<Review, String> text;
	public static volatile SingularAttribute<Review, Long> reviewId;
	public static volatile SingularAttribute<Review, User> user;
	public static volatile SingularAttribute<Review, Tour> tour;

	public static final String DATE = "date";
	public static final String TEXT = "text";
	public static final String REVIEW_ID = "reviewId";
	public static final String USER = "user";
	public static final String TOUR = "tour";

}

