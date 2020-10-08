package com.zdanovich.web.serialization.mixin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.entity.Review;
import com.zdanovich.core.entity.User;

import java.util.List;
import java.util.Set;

//@JsonIgnoreProperties(value = {"users", "reviews"})
public class TourMixIn {

    @JsonBackReference
    private Set<User> users;

    @JsonManagedReference
    private Hotel hotel;

    @JsonManagedReference
    private Country country;

    @JsonBackReference
    private List<Review> reviews;
}