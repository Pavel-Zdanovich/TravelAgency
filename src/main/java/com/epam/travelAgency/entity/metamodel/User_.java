package com.epam.travelAgency.entity.metamodel;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.User;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile ListAttribute<User, Review> reviews;
	public static volatile SingularAttribute<User, String> login;
	public static volatile SingularAttribute<User, Long> userId;
	public static volatile ListAttribute<User, Tour> tours;

	public static final String PASSWORD = "password";
	public static final String REVIEWS = "reviews";
	public static final String LOGIN = "login";
	public static final String USER_ID = "userId";
	public static final String TOURS = "tours";

}

