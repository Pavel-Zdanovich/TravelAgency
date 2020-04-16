package com.zdanovich.core.entity.metamodel;

import com.zdanovich.core.entity.Review;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.User;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile ListAttribute<User, Review> reviews;
	public static volatile SingularAttribute<User, String> login;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SetAttribute<User, Tour> tours;

	public static final String PASSWORD = "password";
	public static final String REVIEWS = "reviews";
	public static final String LOGIN = "login";
	public static final String USER_ID = "id";
	public static final String TOURS = "tours";

}

