package com.zdanovich.core.entity.metamodel;

import com.zdanovich.core.entity.Review;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.User;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.sql.Timestamp;

@StaticMetamodel(Review.class)
public abstract class Review_ {

    public static volatile SingularAttribute<Review, Timestamp> reviewDate;
    public static volatile SingularAttribute<Review, String> reviewText;
    public static volatile SingularAttribute<Review, Long> id;
    public static volatile SingularAttribute<Review, User> user;
    public static volatile SingularAttribute<Review, Tour> tour;

    public static final String REVIEW_DATE = "reviewDate";
    public static final String REVIEW_TEXT = "reviewText";
    public static final String REVIEW_ID = "id";
    public static final String USER = "user";
    public static final String TOUR = "tour";

}

